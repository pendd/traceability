package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Technology;
import com.hvisions.mes.mapper.TechnologyMapper;
import com.hvisions.mes.service.ITechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author dpeng
 * @create 2019-07-03 22:02
 */
@Service
public class TechnologyServiceImpl implements ITechnologyService {

    @Autowired
    private TechnologyMapper technologyMapper;

    /**
     *  查询所有工艺  分页
     * @param page
     * @param technology
     * @return
     */
    @Override
    public Page<Technology> queryAllTechnology(Page<Technology> page, Technology technology) {
        page.setRecords(technologyMapper.selectAllTechnology(page,technology));
        return page;
    }

    /**
     *  新增工艺
     * @param technology
     */
    @Override
    public void incrementTechnology(Technology technology) {
        technologyMapper.insertTechnology(technology);
    }

    /**
     *  修改工艺
     * @param technology
     */
    @Override
    public void modifyTechnology(Technology technology) {
        technologyMapper.updateTechnology(technology);
    }

    /**
     *  删除工艺
     * @param ids
     */
    @Override
    public void removeTechnology(Integer[] ids) {
        technologyMapper.deleteTechnology(ids);
    }
}
