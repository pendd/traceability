package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Technology;


/**
 * 工艺接口
 *
 * @author dpeng
 * @create 2019-07-03 21:59
 */
public interface ITechnologyService {

    /**
     *  查询所有工艺  分页
     * @param page
     * @param technology
     * @return
     */
    Page<Technology> queryAllTechnology(Page<Technology> page,Technology technology);

    /**
     *  新增工艺
     * @param technology
     */
    void incrementTechnology(Technology technology);

    /**
     *  修改工艺
     * @param technology
     */
    void modifyTechnology(Technology technology);

    /**
     *  删除工艺
     * @param ids
     */
    void removeTechnology(Integer[] ids);
}
