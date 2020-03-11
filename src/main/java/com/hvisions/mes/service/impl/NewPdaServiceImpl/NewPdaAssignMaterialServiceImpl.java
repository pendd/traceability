package com.hvisions.mes.service.impl.NewPdaServiceImpl;

import com.hvisions.mes.controller.vo.newpdavo.AssignMaterialScanQrCodeVo;
import com.hvisions.mes.domain.*;
import com.hvisions.mes.dto.AssignMaterialQrRefDTO;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaAssignMaterialMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaBasicInfoMapper;
import com.hvisions.mes.service.INewPdaService.INewPdaAssignMaterialService;
import com.hvisions.mes.service.exception.ErrorCode;
import com.hvisions.mes.service.exception.ServiceException;
import com.hvisions.mes.xa.postgresqlMapper.MaterialPostgreMapper;
import com.hvisions.mes.xa.postgresqlMapper.XaAssignMaterialMapper;
import com.hvisions.mes.xa.postgresqlMapper.XaBasicInfoMapper;
import com.hvisions.mes.xa.sqlServerMapper.MaterialSqlServerMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *  配料
 *
 * @author dpeng
 * @date 2019-06-10 16:34
 */
@Service
public class NewPdaAssignMaterialServiceImpl implements INewPdaAssignMaterialService {

    @Autowired
    private NewPdaAssignMaterialMapper assignMaterialMapper;

    @Autowired
    private NewPdaBasicInfoMapper basicInfoMapper;

    @Autowired
    private XaAssignMaterialMapper xaAssignMaterialMapper;

    @Autowired
    private XaBasicInfoMapper xaBasicInfoMapper;

    @Autowired
    private MaterialPostgreMapper materialPostgreMapper;

    @Autowired
    private MaterialSqlServerMapper materialSqlServerMapper;

    /**
     *  配料任务列表
     * @param storeroomId 库房ID
     * @return
     */
    @Override
    public List<CallOff> selectCallOffStoreList(Integer storeroomId) {
        return assignMaterialMapper.selectCallOffList(storeroomId);
    }

    /**
     *  一个任务对应的具体的物料明细
     * @param produceNumber   生产工单号
     * @param storeroomId     库房ID
     * @return
     */
    @Override
    public List<Material> selectCallOffMaterialList(String produceNumber, Integer storeroomId) {
        List<Material> materials = assignMaterialMapper.selectCallOffMaterialList(produceNumber, storeroomId);
        // 添加已扫码数量
        materials.forEach(e -> {
            Integer num = assignMaterialMapper.selectAssignRefCountByCallOffId(e.getCallOffId());
            e.setReturnAmount(num == null?0:num);
        });
        return materials;
    }

    /**
     *  扫二维码出库逻辑
     * @param qrCode  原料二维码
     * @param materialId 原料ID
     * @return 是否对应物料 0 不匹配   1 匹配
     */
    @Override
    public AssignMaterialScanQrCodeVo selectCallOffLogic(Integer storeroomId,String qrCode,Integer materialId) {

        // 是否对应物料 0 不匹配   1 匹配
        int status;
        AssignMaterialScanQrCodeVo vo = new AssignMaterialScanQrCodeVo();

        // 通过库房ID查询库房类型
        Storeroom storeroom = basicInfoMapper.selectStoreroomById(storeroomId);

        if (storeroom == null) {
            throw new ServiceException("库房不存在", ErrorCode.UNBIND_OK);
        }

        Material m = new Material();

        if (storeroom.getType() == 0) {
            // 原料库
            // 通过原料码获取原料
            m = basicInfoMapper.selectBasicMaterialByCode(qrCode);
        }else if (storeroom.getType() == 1) {
            // 成品库
            m = basicInfoMapper.selectMaterialByGoodsCode(qrCode);
        }

        if (m != null && Objects.equals(m.getMaterialId(),materialId)) {
            status = 1;
            vo.setType(isHaveEarly(materialId, qrCode));
        }else {
            status = 0;
        }
        vo.setStatus(status);
        return vo;
    }

    /**
     *  配料任务列表 产线
     * @param userId
     * @return
     */
    @Override
    public List<CallOff> selectCallOffOrderList(Integer userId) {
        return assignMaterialMapper.selectCallOffOrderList(userId);
    }

    /**
     *  配料出库列表 （工序人员）
     * @param userId  用户ID
     * @return
     */
    @Override
    public List<CallOff> selectCallOffList(Integer userId,String workNumber) {
        return assignMaterialMapper.selectCallOffListByUserId(userId,workNumber);
    }

    /**
     *  更新配料状态
     * @param produceNumber   生产工单号
     * @param state           状态
     */
    @Override
    public void updateCallOffState(String produceNumber, Integer state) {
        assignMaterialMapper.updateCallOffState(produceNumber,state);
    }

    /**
     *  通过原料二维码获取袋子中剩余数量
     * @param qrCode   原料二维码
     * @return         袋子中还剩余数量
     */
    @Override
    public int queryAmountPlusSpecsByQrCode(String qrCode) {
        // 数量 (袋)
        double amount = assignMaterialMapper.selectAmountByQrCode(qrCode);
        // 规格 (一袋多少个)
        double specs = assignMaterialMapper.selectSpecsByQrCode(qrCode);
        Double floor = Math.floor(amount * specs);
        return floor.intValue();
    }

    /**
     *  新增配料任务原料二维码扫码记录
     * @param qrRef  记录对象
     */
    @Override
    public void appendAssignMaterialQrRef(AssignMaterialQrRef qrRef) {
        assignMaterialMapper.insertAssignMaterialQrRef(qrRef);
    }

     /**
     *  备料出库
     * @param produceNumber  生产工单号
     * @param storeroomId    库房ID
     * @param userId         用户ID
     * @param teamId         班组ID
     * @return               是否出库成功 0 成功  1 出库失败,没有发现材料出库单
     */
    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "jtaTransaction")
    public int assignMaterialOut(String produceNumber, Integer storeroomId,Integer userId,Integer teamId) {

        // 通过生产工单号获取生产订单号
        String cMoCode = xaAssignMaterialMapper.selectCMoCodeByWorkNumber(produceNumber);

        // 通过生产工单号和库房ID获取生产子件表ID
        List<CallOff> callOffs = xaAssignMaterialMapper.selectAllocateIdByWorkNumberAndStoreroomId(produceNumber, storeroomId);

        boolean flag = false;

        // 遍历子件表ID 判断材料出库单是否存在
        for (CallOff callOff : callOffs) {
            Rdrecords11 rdrecords11 = xaAssignMaterialMapper.selectRdrecordsByCMoCodeAndModId(cMoCode, callOff.getAllocateId());
            flag = rdrecords11 == null;

            if (flag) {
                // 存在部分材料出库单不存在
                return 1;
            }
        }

        List<MaterialStoreroomHistory> list = new ArrayList<>();

        // 材料出库单存在
        callOffs.forEach(e -> {
            // 通过叫料表ID查询出库的原料二维码以及出库数量
            List<AssignMaterialQrRef> refs = xaAssignMaterialMapper.selectQrRefByCallOffId(e.getCallOffId());
            refs.forEach(c -> {
                // 通过原料二维码查询原料入库所需信息
                MaterialStoreroomHistory history = xaAssignMaterialMapper.selectMaterialHistoryByQrCode(c.getQrCode());

                history.setAmount(Double.valueOf(c.getOutAmount()));
                history.setUserId(userId.longValue());
                history.setTeamId(teamId.longValue());
                history.setProduceNumber(produceNumber);

                // 回写给callOff对象
                e.setMaterialSignCode(history.getMaterialSignCode());
                list.add(history);
            });

            // 更新订单子件表已领数量
            materialPostgreMapper.updateMomMoallocate(e.getAllocateId(),e.getAmount());
            materialSqlServerMapper.updateMomMoallocate(e.getAllocateId(),e.getAmount());
        });

        // 原料出库
        if (list.size() > 0) {
            xaAssignMaterialMapper.insertMaterialHistory(list);
        }

        // 按原料码分组
        Map<String, List<CallOff>> collect = callOffs.stream().collect(Collectors.groupingBy(CallOff::getMaterialSignCode));
        collect.forEach((k,v) -> {
            Stock stock = new Stock();
            stock.setCode(k);
            stock.setStoreroomCode(basicInfoMapper.selectStoreroomById(storeroomId).getStoreroomCode());
            // 要插入的数量
            int amount = v.stream().mapToInt(CallOff::getAmount).sum();
            isExistStock(k, stock, amount);
        });

        // 更新叫料表中状态
        callOffs.forEach(e -> xaAssignMaterialMapper.updateCallOffStateByCallOffId(e.getCallOffId(),1));

        return 0;
    }

    /**
     *  判断库房对象是否存在  存在则修改 不存在新增
     * @param materialSignCode   原料编码
     * @param stock              传入的库房对象
     * @param amount             数量
     */
    @Override
    public void isExistStock(String materialSignCode, Stock stock, int amount) {
        // 判断库存是否存在
        Stock isExist = xaBasicInfoMapper.selectStockByCode(materialSignCode, stock.getStoreroomCode());
        if (isExist == null) {
            // 不存在 新增
            stock.setActualcount(amount);
            xaBasicInfoMapper.insertStock(stock);
        }else {
            // 存在 更新
            stock.setActualcount(isExist.getActualcount() + amount);
            xaBasicInfoMapper.updateStock(stock);
        }
    }

    /**
     *  判断是否有这种物料比当前时间还小的并且不是一个批次的
     * @param materialId 物料ID
     * @param qrCode     物料二维码
     * @return           0 没有  1有
     */
    @Override
    public int isHaveEarly(Integer materialId, String qrCode) {

        // 通过物料二维码查询物料的最早入库时间以及批次ID
        MaterialStoreroomHistory history = assignMaterialMapper.selectOneByQrCodeTop(qrCode);

        // 通过原料ID在原料出入库历史表中查询所有时间小于createTime的入库和回库的物料二维码
        List<String> codes = assignMaterialMapper.selectQrCodeByMaterialId(materialId, history.getCreateTime(), history.getBatchId().intValue());
        for (String code : codes) {
            // 通过物料二维码查询原料入库+回库
            MaterialStoreroomHistory inHistory = assignMaterialMapper.selectAllByQrCode(code, 0);
            MaterialStoreroomHistory outHistory = assignMaterialMapper.selectAllByQrCode(code, 1);

            if (outHistory == null || !(inHistory.getAmount() - outHistory.getAmount() >= 0 && inHistory.getAmount() - outHistory.getAmount() <= 0.01)) {
                // 数量不为0 表示存在比当前扫码物料入库时间还早的并且不是同一批次的物料
                return 1;
            }
        }
        return 0;
    }

    /**
     *  删除配料出库扫码记录
     * @param refDTO
     * @return
     */
    @Override
    public int cutAssignMaterialQrRef(AssignMaterialQrRefDTO refDTO) {
        AssignMaterialQrRef ref = new AssignMaterialQrRef();
        BeanUtils.copyProperties(refDTO,ref);
        return assignMaterialMapper.deleteAssignMaterialQrRef(ref);
    }


}
