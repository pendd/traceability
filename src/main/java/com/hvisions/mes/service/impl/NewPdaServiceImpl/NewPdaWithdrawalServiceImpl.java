package com.hvisions.mes.service.impl.NewPdaServiceImpl;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.dto.WithdrawalCodeRefDTO;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaAssignMaterialMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaBasicInfoMapper;
import com.hvisions.mes.service.INewPdaService.INewPdaCodingService;
import com.hvisions.mes.service.INewPdaService.INewPdaWithdrawalService;
import com.hvisions.mes.util.RandomNumber;
import com.hvisions.mes.util.UUIDGenerator;
import com.hvisions.mes.xa.postgresqlMapper.GoodsPostgreMapper;
import com.hvisions.mes.xa.postgresqlMapper.WithdrawalPostgreMapper;
import com.hvisions.mes.xa.postgresqlMapper.XaBasicInfoMapper;
import com.hvisions.mes.xa.postgresqlMapper.XaWithdrawalMapper;
import com.hvisions.mes.xa.sqlServerMapper.WithdrawalSqlServerMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dpeng
 * @create 2019-06-17 9:38
 */
@Service
public class NewPdaWithdrawalServiceImpl implements INewPdaWithdrawalService {

    @Autowired
    private XaWithdrawalMapper withdrawalMapper;

    @Autowired
    private XaBasicInfoMapper basicInfoMapper;

    @Autowired
    private NewPdaBasicInfoMapper newPdaBasicInfoMapper;

    @Autowired
    private NewPdaAssignMaterialMapper assignMaterialMapper;

    @Autowired
    private GoodsPostgreMapper goodsPostgreMapper;

    @Autowired
    private INewPdaCodingService codingService;

    @Autowired
    private WithdrawalPostgreMapper withdrawalPostgreMapper;

    @Autowired
    private WithdrawalSqlServerMapper withdrawalSqlServerMapper;

    @Override
    public int cutWithdrawalCodeRef(WithdrawalCodeRefDTO refDTO) {
        WithdrawalCodeRef ref = new WithdrawalCodeRef();
        BeanUtils.copyProperties(refDTO,ref);
        return withdrawalMapper.deleteWithdrawalCodeRef(ref);
    }

    /**
     *  库房退料任务列表
     * @param storeroomId
     * @return
     */
    @Override
    public List<Withdrawal> selectWithdrawalList(int storeroomId) {
        return withdrawalMapper.selectWithdrawalList(storeroomId);
    }

    /**
     * 一个任务对应的具体的物料明细
     * @param produceNumber
     * @param storeroomId
     * @return
     */
    @Override
    public List<Material> selectWithdrawalDetailList(String produceNumber, Integer storeroomId) {
        List<Material> list = withdrawalMapper.selectWithdrawalDetailList(produceNumber, storeroomId);
        list.forEach(e -> e.setReturnAmount(withdrawalMapper.selectSumFromWithdrawalCodeRefByWithdrawalId(e.getWithdrawalId())));
        return list;
    }

    /**
     *  扫二维码逻辑
     * @param code 物料二维码
     * @return  1 匹配   0不匹配
     */
    @Override
    public String selectWithdrawalLogic(String code,Integer materialId) {

        // 通过原料码获取原料
        Material m = newPdaBasicInfoMapper.selectMaterialFromMaterialCode(code);

        if (Objects.equals(m.getMaterialId(),materialId)) {
            // 匹配
            return "1";
        }
        return "0";
    }

    /**
     *  新增退料码表记录
     * @param ref
     */
    @Override
    public void appendWithdrawalCodeRef(WithdrawalCodeRef ref) {
        withdrawalMapper.insertWithdrawalCodeRef(ref);
    }

    /**
     *  退料回库
     * @param produceNumber  工单号
     * @param storeroomId    库房ID
     */
    /*@Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "jtaTransaction")
    public void materialBackHistory(String produceNumber, Integer storeroomId,Integer userId,Integer teamId) {

        // 获取库房对象
        Storeroom storeroom = basicInfoMapper.selectStoreroomById(storeroomId);

        // 获取所有退料ID
        List<Material> list = withdrawalMapper.selectWithdrawalDetailList(produceNumber, storeroomId);

        // 获取生产订单号
        String code = withdrawalPostgreMapper.selectCodeByWorkNumber(produceNumber);

        List<MaterialStoreroomHistory> allMaterial = new ArrayList<>();

        List<WithdrawalCodeRef> withdrawalCodeRefList = new ArrayList<>();

        list.forEach(e -> {
            // 更新退料表
            withdrawalMapper.updateWithdrawalState(1,e.getWithdrawalId());

            List<WithdrawalCodeRef> codeList = withdrawalMapper.selectWithdrawalCodeRef(e.getWithdrawalId());
            withdrawalCodeRefList.addAll(codeList);
        });

        Date time = new Date();

        withdrawalCodeRefList.forEach(t -> {
            MaterialStoreroomHistory history = new MaterialStoreroomHistory();

            Material material = basicInfoMapper.selectBasicMaterialByCode(t.getQrCode());
            history.setBatchId(Long.valueOf(material.getBatchId()));
            history.setUnit(material.getUnit());
            history.setMaterialSignCode(material.getMaterialSignCode());
            history.setMaterialId(material.getMaterialId());
            history.setCreateTime(time);
            history.setUserId(userId.longValue());
            history.setProduceNumber(produceNumber);
            history.setTeamId(teamId.longValue());
            history.setMaterialCode(t.getQrCode());
            history.setAmount(t.getAmount().doubleValue());

            allMaterial.add(history);

            // 更新库存信息
            Stock stock = basicInfoMapper.selectStockByCode(material.getMaterialSignCode(), storeroom.getStoreroomCode());
            stock.setActualcount(stock.getActualcount() + t.getAmount());
            basicInfoMapper.updateStock(stock);
        });

        // 插入到原料回库记录
        withdrawalMapper.insertMaterialBackHistory(allMaterial);

        Rdrecord11 rdrecord11 = new Rdrecord11();
        String cCode = RandomNumber.getRandomNumber();
        String cGuid = UUIDGenerator.getUpperCaseUUID();
        String cAccId = withdrawalPostgreMapper.selectCAccId();

        int maxId = withdrawalMapper.selectMaxIdFromRdrecords11();

        Map<String, List<MaterialStoreroomHistory>> collect = allMaterial.stream().collect(Collectors.groupingBy(MaterialStoreroomHistory::getMaterialSignCode));

        collect.forEach((k,v) -> {
            Rdrecords11 rdrecords11 = new Rdrecords11();

            // 获取所有数量
            double sum = v.stream().mapToDouble(MaterialStoreroomHistory::getAmount).sum();

            rdrecords11.setCMoCode(code);
            rdrecords11.setCAccId(cAccId);
            rdrecords11.setCguid(cGuid);
            rdrecords11.setId(maxId);
            rdrecords11.setCCode(cCode);
            rdrecords11.setCBatch(withdrawalPostgreMapper.selectBatchByMaterialSignCode(v.get(0).getMaterialSignCode()));
            rdrecords11.setCInvCode(v.get(0).getMaterialSignCode());
            rdrecords11.setIQuantity( new BigDecimal(sum*(-1)));
            rdrecords11.setDCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            withdrawalPostgreMapper.insertRdrecords11(rdrecords11);
            withdrawalSqlServerMapper.insertRdrecords11(rdrecords11);
        });

        String account = basicInfoMapper.selectEmpByUserId(userId).getAccount();

        int size = 0;

        for (Map.Entry<String,List<MaterialStoreroomHistory>> entry : collect.entrySet()) {
            size = entry.getValue().size();
        }

        rdrecord11.setId(maxId);
        rdrecord11.setCAccId(cAccId);
        rdrecord11.setCguid(cGuid);
        rdrecord11.setCCode(cCode);
        rdrecord11.setDDate(new Date());
        rdrecord11.setCWhCode(storeroom.getStoreroomCode());
        rdrecord11.setCPersonCode(account);
        rdrecord11.setCDepCode(goodsPostgreMapper.selectDepCodeByEmpId(userId).getCDepCode());
        rdrecord11.setISubRows(size);
        rdrecord11.setCMaker(account);
        rdrecord11.setCVerifier(account);
        rdrecord11.setDCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        withdrawalPostgreMapper.insertRdrecord11(rdrecord11);
        withdrawalSqlServerMapper.insertRdrecord11(rdrecord11);
    }*/

    /**
     *  退料回库新
     * @param produceNumber  工单号
     * @param storeroomId    库房ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "jtaTransaction")
    public void materialBackHistory(String produceNumber, Integer storeroomId,Integer userId,Integer teamId) {

        // 获取库房对象
        Storeroom storeroom = basicInfoMapper.selectStoreroomById(storeroomId);

        // 获取所有退料ID
        List<Material> list = withdrawalMapper.selectWithdrawalDetailList(produceNumber, storeroomId);

        // 获取生产订单号
        String code = withdrawalPostgreMapper.selectCodeByWorkNumber(produceNumber);

        List<MaterialStoreroomHistory> allMaterial = new ArrayList<>();

        List<WithdrawalCodeRef> withdrawalCodeRefList = new ArrayList<>();

        list.forEach(e -> {
            // 更新退料表
            withdrawalMapper.updateWithdrawalState(1,e.getWithdrawalId());

            List<WithdrawalCodeRef> codeList = withdrawalMapper.selectWithdrawalCodeRef(e.getWithdrawalId());
            withdrawalCodeRefList.addAll(codeList);
        });

        Date time = new Date();

        withdrawalCodeRefList.forEach(t -> {
            MaterialStoreroomHistory history = new MaterialStoreroomHistory();

            Material material = basicInfoMapper.selectBasicMaterialByCode(t.getQrCode());
            history.setBatchId(Long.valueOf(material.getBatchId()));
            history.setUnit(material.getUnit());
            history.setMaterialSignCode(material.getMaterialSignCode());
            history.setMaterialId(material.getMaterialId());
            history.setCreateTime(time);
            history.setUserId(userId.longValue());
            history.setProduceNumber(produceNumber);
            history.setTeamId(teamId.longValue());
            history.setMaterialCode(t.getQrCode());
            history.setAmount(t.getAmount().doubleValue());

            allMaterial.add(history);

            // 更新库存信息
            Stock stock = basicInfoMapper.selectStockByCode(material.getMaterialSignCode(), storeroom.getStoreroomCode());
            stock.setActualcount(stock.getActualcount() + t.getAmount());
            basicInfoMapper.updateStock(stock);
        });

        // 插入到原料回库记录
        withdrawalMapper.insertMaterialBackHistory(allMaterial);

        Rdrecord11 rdrecord11 = new Rdrecord11();
        String cCode = RandomNumber.getRandomNumber();
        String cGuid = UUIDGenerator.getUpperCaseUUID();
        String cAccId = withdrawalPostgreMapper.selectCAccId();

        int maxId = withdrawalMapper.selectMaxIdFromRdrecords11();

        Map<String, Map<Long,List<MaterialStoreroomHistory>>> collect = allMaterial.stream().collect(Collectors.groupingBy(MaterialStoreroomHistory::getMaterialSignCode,Collectors.groupingBy(MaterialStoreroomHistory::getBatchId)));

        int[] size = new int[]{0};

        // k是物料编码   v是map集合
        collect.forEach((k,v) -> {
            // k1 是批次号  v1 是List<MaterialStoreroomHistory>
            v.forEach((k1,v1) -> {
                Rdrecords11 rdrecords11 = new Rdrecords11();

                // 获取所有数量
                double sum = v1.stream().mapToDouble(MaterialStoreroomHistory::getAmount).sum();

                rdrecords11.setCMoCode(code);
                rdrecords11.setCAccId(cAccId);
                rdrecords11.setCguid(cGuid);
                rdrecords11.setId(maxId);
                rdrecords11.setCCode(cCode);
                rdrecords11.setCBatch(withdrawalPostgreMapper.selectBatchByBatchId(k1.intValue()));
                rdrecords11.setCInvCode(k);
                rdrecords11.setIQuantity( new BigDecimal(sum*(-1)));
                rdrecords11.setDCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

                size[0]++;

                withdrawalPostgreMapper.insertRdrecords11(rdrecords11);
                withdrawalSqlServerMapper.insertRdrecords11(rdrecords11);
            });
        });

        String account = basicInfoMapper.selectEmpByUserId(userId).getAccount();

        rdrecord11.setId(maxId);
        rdrecord11.setCAccId(cAccId);
        rdrecord11.setCguid(cGuid);
        rdrecord11.setCCode(cCode);
        rdrecord11.setDDate(new Date());
        rdrecord11.setCWhCode(storeroom.getStoreroomCode());
        rdrecord11.setCPersonCode(account);
        rdrecord11.setCDepCode(goodsPostgreMapper.selectDepCodeByEmpId(userId).getCDepCode());
        rdrecord11.setISubRows(size[0]);
        rdrecord11.setCMaker(account);
        rdrecord11.setCVerifier(account);
        rdrecord11.setDCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        withdrawalPostgreMapper.insertRdrecord11(rdrecord11);
        withdrawalSqlServerMapper.insertRdrecord11(rdrecord11);
    }



    /**
     *  原料回库
     * @param list
     */
    /*@Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "jtaTransaction")
    public boolean insertMaterialBackHistory(List<MaterialStoreroomHistory> list) {

        List<Boolean> booleanList = new ArrayList<>();

        List<MaterialStoreroomHistory> allMaterial = new ArrayList<>();

        Date time = new Date();
        Storeroom storeroom = basicInfoMapper.selectStoreroomById(list.get(0).getStoreroomId());
        list.forEach(e -> {
            e.setCreateTime(time);

            // 1.首先要判断里面的码是原料码还是包装码
            MaterialPackRef materialPackRef = assignMaterialMapper.selectMaterialPackRefByMaterialCode(e.getMaterialCode());
            // 在原料包装表中查找是否存在
            MaterialPack materialPack = assignMaterialMapper.selectMaterialPackByCode(e.getMaterialCode());
            if (materialPackRef == null && materialPack == null) {
                // 原料码


                Material material = basicInfoMapper.selectBasicMaterialByCode(e.getMaterialCode());
                e.setBatchId(Long.valueOf(material.getBatchId()));
                e.setUnit(material.getUnit());
                e.setMaterialSignCode(material.getMaterialSignCode());
                e.setMaterialId(material.getMaterialId());

                // 更新库存信息
                Stock stock = basicInfoMapper.selectStockByCode(material.getMaterialSignCode(), storeroom.getStoreroomCode());
                stock.setActualcount(stock.getActualcount() + e.getAmount().intValue());
                basicInfoMapper.updateStock(stock);

                e.setAmount(e.getAmount()/Double.valueOf(material.getSpecs()));
                allMaterial.add(e);
            }else {
                // 包装码

                // 获取所有的第一层包装码
                List<String> firstCodes = codingService.getMinFirstCode(e.getMaterialCode());
                // 所有的原料码
                List<MaterialPackRef> materialPackRefs = assignMaterialMapper.selectMaterialPackRef(firstCodes);
                // 通过原料码获取原料信息 并求和
                int sum = materialPackRefs.stream()
                        .mapToInt(pack -> {
                            Material material = basicInfoMapper.selectBasicMaterialByCode(pack.getMaterialCode());
                            return Integer.valueOf(material.getSpecs());
                        }).sum();
                // 如果包装码中数量和上面求和的数量一致  表示这是个完整的包装  否则 不是
                if (sum == e.getAmount()) {
                    // 完整包装


                    for (MaterialPackRef ref : materialPackRefs) {
                        MaterialStoreroomHistory history = new MaterialStoreroomHistory();
                        Material material = basicInfoMapper.selectBasicMaterialByCode(e.getMaterialCode());

                        history.setCreateTime(time);
                        history.setBatchId(material.getBatchId().longValue());
                        history.setUnit(material.getUnit());
                        history.setMaterialSignCode(material.getMaterialSignCode());
                        history.setMaterialId(material.getMaterialId());

                        // 更新库存信息
                        Stock stock = basicInfoMapper.selectStockByCode(material.getMaterialSignCode(), storeroom.getStoreroomCode());
                        stock.setActualcount(stock.getActualcount() + Integer.valueOf(material.getSpecs()));
                        basicInfoMapper.updateStock(stock);

                        // 因为是完整包装  所以都是整袋
                        e.setAmount(1.0);
                        history.setAmount(1.0);
                        allMaterial.add(history);
                    }
                }else {
                    // 不是完整包装
                    booleanList.add(false);
                }

            }

        });

        for (Boolean aBoolean : booleanList) {
            if(!aBoolean) {
                // 存在不是完整包装  退出
                return false;
            }
        }

        // 插入到原料回库记录
        withdrawalMapper.insertMaterialBackHistory(allMaterial);

        Rdrecord11 rdrecord11 = new Rdrecord11();
        String cCode = RandomNumber.getRandomNumber();
        String cGuid = UUIDGenerator.getUpperCaseUUID();

        String account = basicInfoMapper.selectEmpByUserId(list.get(0).getUserId().intValue()).getAccount();

        Map<String, List<MaterialStoreroomHistory>> collect = allMaterial.stream().collect(Collectors.groupingBy(MaterialStoreroomHistory::getMaterialSignCode));

        int size = 0;

        for (Map.Entry<String,List<MaterialStoreroomHistory>> entry : collect.entrySet()) {
            size = entry.getValue().size();
        }

        *//**
         *  供应商
         *//*
        rdrecord11.setCAccId(withdrawalPostgreMapper.selectCAccId());
        rdrecord11.setCguid(cGuid);
        rdrecord11.setCCode(cCode);
        rdrecord11.setDDate(new Date());
        rdrecord11.setCWhCode(storeroom.getStoreroomCode());
        rdrecord11.setCPersonCode(account);
        rdrecord11.setCDepCode(goodsPostgreMapper.selectDepCodeByEmpId(list.get(0).getUserId().intValue()).getCDepCode());
        rdrecord11.setISubRows(size);
        rdrecord11.setCMaker(account);
        rdrecord11.setCVerifier(account);
        rdrecord11.setDCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        withdrawalPostgreMapper.insertRdrecord11(rdrecord11);
        withdrawalSqlServerMapper.insertRdrecord11(rdrecord11);

        collect.forEach((k,v) -> {
            Rdrecords11 rdrecords11 = new Rdrecords11();

            rdrecords11.setCAccId(withdrawalPostgreMapper.selectCAccId());
            rdrecords11.setCguid(cGuid);
            rdrecords11.setId(rdrecord11.getId());
            rdrecords11.setCCode(cCode);
            rdrecords11.setCInvCode(v.get(0).getMaterialSignCode());
            rdrecords11.setIQuantity( new BigDecimal(v.size()*(-1)));
            rdrecords11.setDCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            withdrawalPostgreMapper.insertRdrecords11(rdrecords11);
            withdrawalSqlServerMapper.insertRdrecords11(rdrecords11);
        });

        return true;
    }*/



}
