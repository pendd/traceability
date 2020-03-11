package com.hvisions.mes.service.impl.NewPdaServiceImpl;

import com.hvisions.mes.controller.vo.newpdavo.SaleScanCodeVo;
import com.hvisions.mes.domain.*;
import com.hvisions.mes.dto.SaleCodeRefDTO;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaBasicInfoMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaGoodsMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaSaleMapper;
import com.hvisions.mes.service.INewPdaService.INewPdaGoodsService;
import com.hvisions.mes.service.INewPdaService.INewPdaSaleService;
import com.hvisions.mes.util.RandomNumber;
import com.hvisions.mes.util.UUIDGenerator;
import com.hvisions.mes.xa.postgresqlMapper.SalePostgreMapper;
import com.hvisions.mes.xa.sqlServerMapper.SaleSqlServerMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author dpeng
 * @description
 * @date 2019-08-12 17:18
 */
@Service
public class NewPdaSaleServiceImpl implements INewPdaSaleService {

    @Autowired
    private NewPdaSaleMapper saleMapper;

    @Autowired
    private INewPdaGoodsService goodsService;

    @Autowired
    private NewPdaBasicInfoMapper basicInfoMapper;

    @Autowired
    private NewPdaGoodsMapper goodsMapper;

    @Autowired
    private SalePostgreMapper salePostgreMapper;

    @Autowired
    private SaleSqlServerMapper saleSqlServerMapper;

    @Override
    public int cutSaleCodeRef(SaleCodeRefDTO refDTO) {
        SaleCodeRef ref = new SaleCodeRef();
        BeanUtils.copyProperties(refDTO,ref);
        return saleMapper.deleteSaleCodeRef(ref);
    }

    /**
     *  获取销售发货单主表信息
     * @param storeroomId 库房ID
     * @return            主表信息
     */
    @Override
    public List<DispatchList> queryDispatchList(Integer storeroomId) {
        return saleMapper.selectDispatchList(basicInfoMapper.selectStoreroomById(storeroomId).getStoreroomCode());
    }

    /**
     *  通过单据编号查询销售发货单子表信息
     * @param storeroomId 库房 ID
     * @param cCode 主表单据编号
     * @return      子表信息
     */
    @Override
    public List<DispatchLists> queryDispatchListsByCCode(Integer storeroomId,String cCode) {
        List<DispatchLists> list = saleMapper.selectDispatchListsByCCode(basicInfoMapper.selectStoreroomById(storeroomId).getStoreroomCode(),cCode);
        list.forEach(e -> e.setScanAmount(saleMapper.selectCountByParentIdAndChildId(e.getId(), e.getIDlsId())));
        return list;
    }

    /**
     *  销售出库扫二维码逻辑
     * @param qrCode       成品码(扫的二维码)
     * @param parentId     主表ID
     * @param childId      子表ID
     * @return             是否匹配  0 不匹配  1 匹配   2 空包装
     */
    @Override
    public SaleScanCodeVo saleScanCodeLogic(String qrCode,Integer parentId,Integer childId) {

        // 通过主表ID和子表ID查询子表信息
        DispatchLists childMsg = saleMapper.selectDispatchListsByParentIdAndChildId(parentId, childId);

        SaleScanCodeVo vo = new SaleScanCodeVo();

        // 判断是否是包装码  0 不是 1是
        int type = goodsService.guessGoodsCode(qrCode);
        if (type == 0) {
            // 成品二维码  判断是否匹配
            // 通过成品二维码获取物料码
            String materialSignCode = saleMapper.selectMaterialSignCodeByQrCode(qrCode);

            boolean flag = Objects.equals(materialSignCode,childMsg.getCInvCode());
            if (flag) {
                // 匹配
                vo.setIsMatch(1);
            }else {
                // 不匹配
                vo.setIsMatch(0);
            }

        }else {
            // 通过包装码获取第一层包装码
            List<String> firstPackCode = goodsService.getMinFirstCode(qrCode);

            // 成品二维码
            List<String> scanCodes = goodsMapper.selectGoodsCodeByFirstCode(firstPackCode.get(0));

            if (scanCodes.isEmpty()) {
                // 空包装
                vo.setIsMatch(2);
            }else {
                String code = saleMapper.selectMaterialSignCodeByQrCode(scanCodes.get(0));
                if (Objects.equals(code,childMsg.getCInvCode())) {
                    vo.setIsMatch(1);
                }else {
                    vo.setIsMatch(0);
                }
            }
        }
        return vo;
    }

    /**
     *  新增销售出库 扫码记录
     * @param ref
     */
    @Override
    public void appendSaleCodeRef(SaleCodeRef ref) {
        saleMapper.insertSaleCodeRef(ref);
    }

    /**
     *  销售出库
     * @param parentId     主表ID
     * @param storeroomId  子表ID
     * @param userId       用户ID
     */
    @Transactional(transactionManager = "jtaTransaction",rollbackFor = Exception.class)
    @Override
    public void saleOutStoreroom(Integer parentId, Integer storeroomId,Integer userId,Integer teamId) {

        // 通过库房ID获取库房编码
        Storeroom storeroom = basicInfoMapper.selectStoreroomById(storeroomId);

        // 获取所有记录的二维码
        List<SaleCodeRef> refs = saleMapper.selectSaleCodeRefByParentIdAndStoreroomId(parentId, storeroomId);

        List<SaleOutStoreroom> outStorerooms = new ArrayList<>();

        List<SaleCodeRef> codeList = new ArrayList<>();

        refs.forEach(e -> {
            // 判断是否是包装码
            int type = goodsService.guessGoodsCode(e.getQrCode());
            if (type == 1) {
                // 包装码  获取第一层包装编码
                List<String> firstPackCodes = goodsService.getMinFirstCode(e.getQrCode());
                List<String> goodsCodes = goodsMapper.selectGoodsCodeByFirstCode(firstPackCodes.get(0));

                goodsCodes.forEach(g -> {
                    SaleCodeRef ref = new SaleCodeRef();
                    ref.setQrCode(g);
                    ref.setMaterialSignCode(e.getMaterialSignCode());
                    codeList.add(ref);
                });

            }else {
                // 成品码
                SaleCodeRef codeRef = new SaleCodeRef();
                codeRef.setQrCode(e.getQrCode());
                codeRef.setMaterialSignCode(e.getMaterialSignCode());
                codeList.add(codeRef);
            }
        });

        codeList.forEach(e -> {
            SaleOutStoreroom outStoreroom = new SaleOutStoreroom();
            outStoreroom.setUserId(userId);
            outStoreroom.setTeamId(teamId);
            outStoreroom.setStoreroomId(storeroomId);
            outStoreroom.setGoodsCode(e.getQrCode());
            outStorerooms.add(outStoreroom);
        });

        // 销售出库记录
        outStorerooms.forEach(o -> saleMapper.insertSaleOutStoreroom(o));

        // 更新销售发货单为已发货
        salePostgreMapper.updateDispatchListsState(parentId,storeroom.getStoreroomCode());

        // 获取销售发货单主表信息
        DispatchList dispatchList = salePostgreMapper.selectDispatchListById(parentId);

        // 获取销售发货单子表信息
        List<DispatchLists> dispatchLists = salePostgreMapper.selectDispatchListsByIdAndStoreroomId(parentId, storeroom.getStoreroomCode());

        String cGuid = UUIDGenerator.getUpperCaseUUID();
        String cCode = RandomNumber.getRandomNumber();
        int maxId = salePostgreMapper.selectMaxId();

        for (DispatchLists list : dispatchLists) {
            Rdrecords32 rdrecords32 = new Rdrecords32();

            rdrecords32.setIquantity(list.getIQuantity());
            rdrecords32.setCbatch(list.getCBatch());
            rdrecords32.setDvdate(list.getDVDate());
            rdrecords32.setSodid(list.getSodId());
            rdrecords32.setCsocode(list.getCSoCode());
            rdrecords32.setIsodrowno(list.getISodRowNo());
            rdrecords32.setCinvcode(list.getCInvCode());

            rdrecords32.setCGuid(cGuid);
            rdrecords32.setCaccId(dispatchList.getCAccId());
            rdrecords32.setId(maxId);
            rdrecords32.setCcode(cCode);
            rdrecords32.setIdlsid(list.getIDlsId());
            rdrecords32.setCdlcode(list.getCCode());
            rdrecords32.setIdlsrowno(list.getIRowNo());
            rdrecords32.setDcreatedate(new Date());
            rdrecords32.setBerpisread(0);
            rdrecords32.setBistoerp(0);

            // 入postgre销售出库单子表
            salePostgreMapper.insertRdrecords32(rdrecords32);
            // 入sqlserver销售出库单子表
            saleSqlServerMapper.insertRdrecords32(rdrecords32);
        }

        Rdrecord32 rdrecord32 = new Rdrecord32();

        rdrecord32.setCaccId(dispatchList.getCAccId());
        rdrecord32.setId(maxId);
        rdrecord32.setCcuscode(dispatchList.getCCusCode());
        rdrecord32.setCdepcode(dispatchList.getCDepCode());
        rdrecord32.setCpersoncode(dispatchList.getCPersonCode());
        rdrecord32.setCshipaddress(dispatchList.getCShipAddress());
        rdrecord32.setCstcode(dispatchList.getCSTCode());
        rdrecord32.setCstname(dispatchList.getCSTName());

        rdrecord32.setCGuid(cGuid);
        rdrecord32.setCcode(cCode);
        rdrecord32.setDdate(new Date());
        rdrecord32.setCwhcode(storeroom.getStoreroomCode());
        rdrecord32.setCsource(dispatchList.getCCode());
        rdrecord32.setCmemo(null);
        rdrecord32.setIsubrows((short) dispatchLists.size());
        rdrecord32.setDcreatedate(new Date());
        rdrecord32.setBerpisread(0);
        rdrecord32.setBistoerp(0);
        rdrecord32.setCmaker(basicInfoMapper.selectEmpByUserId(userId).getAccount());

        // 入postgre销售出库单主表
        salePostgreMapper.insertRdrecord32(rdrecord32);
        // 入sqlserver销售出库单主表
        saleSqlServerMapper.insertRdrecord32(rdrecord32);
    }
}
