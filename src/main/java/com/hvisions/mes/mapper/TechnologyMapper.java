package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Technology;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 工艺mapper
 *
 * @author dpeng
 * @create 2019-07-03 21:41
 */
@Mapper
@Repository
public interface TechnologyMapper {

    /**
     *  查询所有工艺
     * @param page        分页对象
     * @param technology  工艺对象
     * @return
     */
    List<Technology> selectAllTechnology(Pagination page, Technology technology);

    /**
     *  新增工艺
     * @param technology   工艺对象
     */
    void insertTechnology(Technology technology);

    /**
     *  修改工艺
     * @param technology  工艺对象
     */
    void updateTechnology(Technology technology);

    /**
     *  删除工艺
     * @param ids  工艺主键数组
     */
    void deleteTechnology(Integer[] ids);
}
