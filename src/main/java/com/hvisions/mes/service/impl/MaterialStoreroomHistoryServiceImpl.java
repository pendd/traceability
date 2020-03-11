package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.Constant;
import com.hvisions.mes.controller.vo.MaterialStoreroomHistoryVo;
import com.hvisions.mes.domain.MaterialStoreroomHistory;
import com.hvisions.mes.mapper.MaterialStoreroomHistoryMapper;
import com.hvisions.mes.service.IMaterialStoreroomHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaterialStoreroomHistoryServiceImpl implements IMaterialStoreroomHistoryService {

    @Autowired
    private MaterialStoreroomHistoryMapper mshMapper;

    /**
     *  获取原料出入库历史
     * @param page            分页模型对象
     * @param inOutType      出入库   0 入库  1 出库
     * @return
     */
    @Override
    public Page<MaterialStoreroomHistoryVo> queryAllMaterialStoreroomHistory(Page<MaterialStoreroomHistoryVo> page,String workNumber, String startTime,String endTime, Integer inOutType) {
        page.setRecords(mshMapper.selectMaterialStoreroomHistory(page,workNumber,startTime,endTime,inOutType));
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertInHistory(MaterialStoreroomHistory history) {
        mshMapper.insertInHistory(history);
    }

    @Override
    public String updateHistoryByMaterialCode(String materialCode,Integer qualified) {
        MaterialStoreroomHistory history = mshMapper.selectHistoryByMaterialCode(materialCode);
        if(history == null) return "为查询到相关记录！";
        else{
            history.setQualified(qualified);
            history.setIsRealIn(Constant.MATERIAL_IS_REAL_IN_YES);
            mshMapper.updateRealInHistory(history);
            return "";
        }
    }

}
