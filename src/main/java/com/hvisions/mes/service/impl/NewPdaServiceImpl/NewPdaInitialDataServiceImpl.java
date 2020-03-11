package com.hvisions.mes.service.impl.NewPdaServiceImpl;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.dto.InitialDataDTO;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaBasicInfoMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaInitialDataMapper;
import com.hvisions.mes.service.INewPdaService.INewPdaInitialDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 旧库存初始化
 * @author dpeng
 * @date 2019-11-18 17:05
 */
@Service
public class NewPdaInitialDataServiceImpl implements INewPdaInitialDataService {

    @Autowired
    private NewPdaInitialDataMapper initialDataMapper;

    @Autowired
    private NewPdaBasicInfoMapper basicInfoMapper;

    /**
     *  旧库存初始化
     *  新建一条班组记录  ID 为0
     *  1.入原料码表
     * @param initialDataDTO
     * @return
     */
    @Transactional(transactionManager = "primaryTransaction",rollbackFor = Exception.class)
    @Override
    public String addInitialData(InitialDataDTO initialDataDTO) {

        // 通过物料编码获取物料ID
        Material material = initialDataMapper.selectMaterialByMaterialSignCode(initialDataDTO.getMaterialSignCode());
        if (material == null) {
            return "不存在该物料编码";
        }

        // 通过供应商编码获取供应商ID
        Integer supplierId = initialDataMapper.selectSupplierIdBySupplierCode(initialDataDTO.getSupplierCode());
        if (supplierId == null) {
            return "供应商编码不存在";
        }

        // 通过库房编码获取库房ID
        Integer storeroomId = initialDataMapper.selectStoreroomIdByStoreroomCode(initialDataDTO.getStoreroomCode());
        if (storeroomId == null) {
            return "库房编码不存在";
        }

        MaterialCode materialCode = new MaterialCode();
        materialCode.setMaterialCode(initialDataDTO.getMaterialCode());
        materialCode.setMaterialId(material.getMaterialId());
        materialCode.setSupplierId(supplierId);
        materialCode.setSupplierNumber(initialDataDTO.getSupplierNumber());
        materialCode.setUnit(material.getUnit());
        materialCode.setMaterialSignCode(initialDataDTO.getMaterialSignCode());

        // 入原料码表
        initialDataMapper.insertMaterialCode(materialCode);

        MaterialBatch materialBatch = new MaterialBatch();
        materialBatch.setSupplierNumber(initialDataDTO.getSupplierNumber());
        materialBatch.setStoreroomId(storeroomId);
        materialBatch.setSupplierId(supplierId);
        materialBatch.setMaterialId(material.getMaterialId());

        // 入原料批次表  会回写batchId
        initialDataMapper.insertMaterialBatch(materialBatch);

        MaterialStoreroomHistory history = new MaterialStoreroomHistory();
        history.setBatchId(materialBatch.getBatchId().longValue());
        history.setMaterialCode(initialDataDTO.getMaterialCode());
        history.setUnit(material.getUnit());
        history.setAmount(initialDataDTO.getAmount().doubleValue());

        // 入原料出入库历史表
        initialDataMapper.insertMaterialStoreroomHistory(history);

        Stock stock = new Stock();
        stock.setCode(initialDataDTO.getMaterialSignCode());
        stock.setStoreroomCode(initialDataDTO.getStoreroomCode());

        // 入库存表
        isExistStock(stock,initialDataDTO.getAmount());

        return "操作成功";
    }

    /**
     *  判断库房对象是否存在  存在则修改 不存在新增
     * @param stock              传入的库房对象
     * @param amount             数量
     */
    private void isExistStock(Stock stock, int amount) {
        // 判断库存是否存在
        Stock isExist = basicInfoMapper.selectStockByCode(stock.getCode(), stock.getStoreroomCode());
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
