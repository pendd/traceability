package com.hvisions.mes.xa.postgresqlMapper;

import com.hvisions.mes.domain.PuArrivalvouch;
import com.hvisions.mes.domain.PuArrivalvouchs;
import com.hvisions.mes.domain.Rdrecord01;
import com.hvisions.mes.domain.Rdrecords01;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dpeng
 * @description 采购(委外)入库单
 * @date 2019-09-02 0:33
 */
public interface MaterialPostgreMapper {

    /**
     *  通过主表ID查询主表信息
     * @param id
     * @return
     */
    PuArrivalvouch selectPuArrivalVouchById(Integer id);

    /**
     *  获取最大ID
     * @return
     */
    int selectMaxIdFromRdrecord01();

    /**
     *  通过主表ID和库房编码查询子表信息
     * @param parentId
     * @param storeroomCode
     * @return
     */
    List<PuArrivalvouchs> selectPuArrivalVouchsByIdAndStoreroomCode(@Param("parentId")Integer parentId, @Param("storeroomCode")String storeroomCode);

    /**
     *  新增入库单主表信息
     * @param rdrecord01
     */
    void insertRdrecord01(Rdrecord01 rdrecord01);

    /**
     *  新增子表信息
     * @param rdrecords01
     */
    void insertRdrecords01(Rdrecords01 rdrecords01);

    void updateMomMoallocate(@Param("allocateId")Integer allocateId, @Param("amount")Integer amount);

}
