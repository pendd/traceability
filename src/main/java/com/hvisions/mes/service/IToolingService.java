package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Tooling;

import java.util.List;


/**
 * @author dpeng
 * @description 工装
 * @date 2019-08-27 16:21
 */
public interface IToolingService {

    /**
     *  分页查询工装信息
     * @param page    分页对象
     * @param tooling 工装对象
     * @return        工装
     */
    Page<Tooling> selectAllByPage(Page<Tooling> page, Tooling tooling);

    /**
     *  添加工装信息
     * @param tooling  工装对象
     */
    void insertTooling(Tooling tooling);

    /**
     *  修改工装信息
     * @param tooling  工装信息
     */
    void updateTooling(Tooling tooling);

    /**
     * 通过ID删除工装信息
     * @param ids 工装ID
     */
    void deleteToolingById(List<Integer> ids);
}
