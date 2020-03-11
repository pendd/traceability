package com.hvisions.mes.service.impl.NewPdaServiceImpl;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaMaterialMapper;
import com.hvisions.mes.service.INewPdaService.INewPdaMaterialService;
import com.hvisions.mes.util.RandomNumber;
import com.hvisions.mes.util.UUIDGenerator;
import com.hvisions.mes.xa.postgresqlMapper.GoodsPostgreMapper;
import com.hvisions.mes.xa.postgresqlMapper.MaterialPostgreMapper;
import com.hvisions.mes.xa.sqlServerMapper.MaterialSqlServerMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dpeng
 * @create 2019-05-31 16:43
 */
@Service
public class NewPdaMaterialServiceImpl implements INewPdaMaterialService {

    @Autowired
    private com.hvisions.mes.xa.postgresqlMapper.XaBasicInfoMapper basicInfoMapper;

    @Autowired
    private com.hvisions.mes.xa.postgresqlMapper.XaMaterialMapper xaMaterialMapper;

    @Autowired
    private NewPdaMaterialMapper materialMapper;

    @Autowired
    private MaterialPostgreMapper materialPostgreMapper;

    @Autowired
    private MaterialSqlServerMapper materialSqlServerMapper;

    @Autowired
    private GoodsPostgreMapper goodsPostgreMapper;

    @Value("file:///" + "${web.uploadPath}" + "json/setting.json")
    private Resource resource;

    /**
     * 原料入库列表历史记录
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Override
    public List<MaterialStoreroomHistory> queryMaterialHistoryList(String startTime, String endTime) {
        return materialMapper.selectMaterialHistoryList(startTime, endTime);
    }

    /**
     *  查询子表中是对应库房的主表信息
     * @param storeroomId   库房ID
     * @return  到货单主表信息
     */
    @Override
    public List<PuArrivalvouch> queryPuArrivalVouch(Integer storeroomId) {
        return materialMapper.selectPuArrivalVouch(storeroomId);
    }

    /**
     *  通过主表Id查询对应库房的子表信息
     * @param storeroomId  库房ID
     * @param id   主表ID
     * @return  子表集合
     */
    @Override
    public List<PuArrivalvouchs> queryPuArrivalVouchs(Integer storeroomId, Integer id) {
        List<PuArrivalvouchs> list = materialMapper.selectPuArrivalVouchs(storeroomId, id);
        list.forEach(e -> {
            int amount = materialMapper.selectCountCodeByParentAndChildId(id, e.getIarrsid());
            e.setAmount(amount);
        });
        return list;
    }

    /**
     *  原料入库
     * @param batch
     */
    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "jtaTransaction")
    public void putMaterialStoreroom(MaterialBatch batch) {

        // 获取到货单主表信息
        PuArrivalvouch puArrivalvouch = materialPostgreMapper.selectPuArrivalVouchById(batch.getParentId());

        // 通过主表Id查询对应库房的子表信息
        List<PuArrivalvouchs> list = materialMapper.selectPuArrivalVouchs(batch.getStoreroomId(), batch.getParentId());

        List<MaterialBatch> batchList = new ArrayList<>();

        ArrivalOrderRef ref = new ArrivalOrderRef();
        ref.setParentId(batch.getParentId());
        ref.setStoreroomId(batch.getStoreroomId());
        // 通过主表ID获取所有入库原料二维码
        List<ArrivalOrderRef> orderRefs = materialMapper.selectArrivalOrderRefByCondition(ref);
        // 通过物料二维码获取库房、供应商、原料、单位等信息
        orderRefs.forEach(e -> {
            List<MaterialBatch> materialBatchList = materialMapper.selectErpMesInfoByQrCode(e.getQrCode(), batch.getParentId(), batch.getStoreroomId());

            if (materialBatchList != null) {
                MaterialBatch materialBatch = materialBatchList.get(0);
                materialBatch.setUserId(batch.getUserId());
                materialBatch.setTeamId(batch.getTeamId());
                materialBatch.setAmount(e.getAmount());

                batchList.add(materialBatch);

                // 更新原料码表中的供应商ID和原批次号
                xaMaterialMapper.updateMaterialCodeByQrCode(e.getQrCode(),materialBatch.getSupplierId(),materialBatch.getSupplierNumber());
            }
        });

        // 先按批次分组 再按原料ID分组
        Map<String, Map<Integer, List<MaterialBatch>>> collect = batchList.stream().collect(Collectors.groupingBy(MaterialBatch::getSupplierNumber, Collectors.groupingBy(MaterialBatch::getMaterialId)));

        collect.forEach((k,v) -> v.forEach((k2,v2) -> {
            if (!v2.isEmpty()) {
                MaterialBatch batchV2 = v2.get(0);
                // 入原料批次表
                xaMaterialMapper.insertMaterialBatch(batchV2);

                // 遍历 把入批次表返回的batchId给赋值
                v2.forEach(e -> e.setBatchId(batchV2.getBatchId()));
            }
        }));

            // 入原料入库表
        xaMaterialMapper.insertMaterialHistoryList(batchList);

        // 更新子表为已入库
        list.forEach(e -> xaMaterialMapper.updatePuArrivalVouchs(batch.getParentId(),e.getIarrsid(),1));

        // 按物料分组  求数量入库存表
        Map<String, List<MaterialBatch>> materialStock = batchList.stream().collect(Collectors.groupingBy(MaterialBatch::getMaterialSignCode));
        materialStock.forEach((k,v) -> {
            Stock stock = new Stock();
            stock.setCode(k);
            stock.setStoreroomCode(v.get(0).getStoreroomCode());
            // 要插入的数量
            int amount = v.stream().mapToInt(MaterialBatch::getAmount).sum();
            // 改变库存
            isExistStock(k,stock,amount);
        });

        Rdrecord01 rdrecord01 = new Rdrecord01();

        BeanUtils.copyProperties(puArrivalvouch,rdrecord01);

        String cCode = RandomNumber.getRandomNumber();
        String cGuid = UUIDGenerator.getUpperCaseUUID();

        String account = basicInfoMapper.selectEmpByUserId(batch.getUserId()).getAccount();

        int maxId = materialPostgreMapper.selectMaxIdFromRdrecord01();

        // 先回写子表
        for (PuArrivalvouchs puArrivalvouchs : list) {
            Rdrecords01 rdrecords01 = new Rdrecords01();
            BeanUtils.copyProperties(puArrivalvouchs,rdrecords01);

            rdrecords01.setCguid(cGuid);
            rdrecords01.setCaccId(rdrecord01.getCaccId());
            rdrecords01.setId(maxId);
            rdrecords01.setCcode(cCode);
            rdrecords01.setCbatch(rdrecords01.getCbatch());
            rdrecords01.setIarrsid(puArrivalvouchs.getIarrsid());
            rdrecords01.setCarrcode(puArrivalvouchs.getCcode());
            rdrecords01.setIarrrowno(puArrivalvouchs.getIrowno());
            rdrecords01.setDcreatedate(new Date());
            rdrecords01.setBerpisread(0);
            rdrecords01.setBistoerp(0);

            materialPostgreMapper.insertRdrecords01(rdrecords01);
            materialSqlServerMapper.insertRdrecords01(rdrecords01);
        }

        // 再回写主表
        rdrecord01.setId(maxId);
        rdrecord01.setCguid(cGuid);
        rdrecord01.setCcode(cCode);
        rdrecord01.setDdate(new Date());
        rdrecord01.setCwhcode(batchList.get(0).getStoreroomCode());
        rdrecord01.setCpersoncode(account);
        rdrecord01.setCdepcode(goodsPostgreMapper.selectDepCodeByEmpId(batch.getUserId()).getCDepCode());
        rdrecord01.setCordercode(list.get(0).getCsocode());
        rdrecord01.setIsubrows(list.size());
        rdrecord01.setCmaker(account);
        rdrecord01.setCverifier(account);
        rdrecord01.setDcreatedate(new Date());
        rdrecord01.setBerpisread(0);
        rdrecord01.setBistoerp(0);

        materialPostgreMapper.insertRdrecord01(rdrecord01);
        materialSqlServerMapper.insertRdrecord01(rdrecord01);

    }

    /**
     *  判断库房对象是否存在  存在则修改 不存在新增   xa事务里的
     * @param materialSignCode   原料编码
     * @param stock              传入的库房对象
     * @param amount             数量
     */
    public void isExistStock(String materialSignCode, Stock stock, int amount) {
        // 判断库存是否存在
        Stock isExist = basicInfoMapper.selectStockByCode(materialSignCode, stock.getStoreroomCode());
        if (isExist == null) {
            // 不存在 新增
            stock.setActualcount(amount);
            basicInfoMapper.insertStock(stock);
        }else {
            // 存在 更新
            stock.setActualcount(isExist.getActualcount() + amount);
            basicInfoMapper.updateStock(stock);
        }
    }

}