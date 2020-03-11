package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.LineStoreroomHistoryVo;
import com.hvisions.mes.controller.vo.MaterialStoreroomHistoryVo;
import com.hvisions.mes.domain.Material;
import com.hvisions.mes.domain.Storeroom;
import com.hvisions.mes.mapper.LineStoreroomHistoryMapper;
import com.hvisions.mes.mapper.MaterialMapper;
import com.hvisions.mes.mapper.StoreroomMapper;
import com.hvisions.mes.service.ILineStoreroomHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dpeng
 * @create 2019-03-13 16:46
 */
@Service
public class LineStoreroomHistoryServiceImpl implements ILineStoreroomHistoryService {

    @Autowired
    private LineStoreroomHistoryMapper mapper;
    @Autowired
    private StoreroomMapper storeroomMapper;
    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 查询线边库出库历史纪录
     * @param page                   分页模型对象
     * @param lineStoreroomId        线边库编号
     * @return                       线边库出库历史集合
     * @param inOutType              0入库  1出库
     */
    @Override
    public Page<LineStoreroomHistoryVo> queryAllLineStoreroomHistory(Page<LineStoreroomHistoryVo> page, Long lineStoreroomId,Integer inOutType) {
        page.setRecords(mapper.selectLineStoreroomHistory(page,lineStoreroomId,inOutType));
        return page;
    }

    /**
     *  查询线边库出库历史
     * @param page
     * @param lineStoreroomId
     * @return
     */
    @Override
    public Page<LineStoreroomHistoryVo> queryLineStoreroomOutHistory(Page<LineStoreroomHistoryVo> page, Long lineStoreroomId) {
        List<LineStoreroomHistoryVo> records = mapper.selectLineStoreroomOutHistory(page, lineStoreroomId);
        List<Storeroom> storerooms = storeroomMapper.selectAllStoreroom();
        List<MaterialStoreroomHistoryVo> materials = materialMapper.selectMaterialNameCode();
        // list 转为 map
        Map<Integer,String> storeMap = storerooms.stream().collect(Collectors.toMap(Storeroom::getStoreroomId,Storeroom::getStoreroomName,(oldValue,newValue) -> oldValue));
        Map<String, String> materialMap = materials.stream().collect(Collectors.toMap(MaterialStoreroomHistoryVo::getMaterialCode, MaterialStoreroomHistoryVo::getMaterialName, (oldValue, newValue) -> oldValue));

        for (LineStoreroomHistoryVo record : records) {
            record.setMaterialName(materialMap.get(record.getMaterialCode()));
            record.setStoreroomName(storeMap.get(record.getStoreroomId().intValue()));
            record.setLineStoreroomName(storeMap.get(record.getLineStoreroomId().intValue()));
        }
        page.setRecords(records);
        return page;
    }

    /**
     *  查询线边库入库历史
     * @param page
     * @param lineStoreroomId
     * @return
     */
    @Override
    public Page<LineStoreroomHistoryVo> queryLineStoreroomInHistory(Page<LineStoreroomHistoryVo> page, Long lineStoreroomId) {
        page.setRecords(mapper.selectLineStoreroomInHistory(page,lineStoreroomId));
        return page;
    }


}
