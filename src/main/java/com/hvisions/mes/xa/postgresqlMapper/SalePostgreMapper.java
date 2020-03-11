package com.hvisions.mes.xa.postgresqlMapper;

import com.hvisions.mes.domain.DispatchList;
import com.hvisions.mes.domain.DispatchLists;
import com.hvisions.mes.domain.Rdrecord32;
import com.hvisions.mes.domain.Rdrecords32;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author dpeng
 * @description 销售出库 ERP 多数据源事务
 * @date 2019-08-20 8:37
 */
public interface SalePostgreMapper {

    /**
     *  通过主表ID查询销售发货单主表信息
     * @param id             主表ID
     * @return               主表信息
     */
    DispatchList selectDispatchListById(Integer id);

    /**
     *  通过主表ID和库房ID查询销售发货单子表信息
     * @param id             主表ID
     * @param storeroomCode  库房编码
     * @return               子表信息
     */
    List<DispatchLists> selectDispatchListsByIdAndStoreroomId(@Param("id")Integer id, @Param("storeroomCode")String storeroomCode);

    /**
     *  新增销售出库单主表信息
     * @param rdrecord32  主表信息
     */
    void insertRdrecord32(Rdrecord32 rdrecord32);

    /**
     *  新增销售出库单子表信息
     * @param rdrecords32  子表信息
     */
    void insertRdrecords32(Rdrecords32 rdrecords32);

    /**
     * 更新销售入库单
     *
     * @param id
     */
    void updateRdrecord32ById(@Param("id")Integer id, @Param("rowNum")Integer rowNum);

    void updateDispatchListsState(@Param("parentId") Integer parentId, @Param("storeroomCode")String storeroomCode);

    int selectMaxId();
}
