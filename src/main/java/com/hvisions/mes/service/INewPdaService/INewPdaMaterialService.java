package com.hvisions.mes.service.INewPdaService;

import com.hvisions.mes.domain.MaterialBatch;
import com.hvisions.mes.domain.MaterialStoreroomHistory;
import com.hvisions.mes.domain.PuArrivalvouch;
import com.hvisions.mes.domain.PuArrivalvouchs;

import java.util.List;

/**
 * @author dpeng
 * @create 2019-05-31 16:43
 */
public interface INewPdaMaterialService {

    /**
     *  原料入库列表历史记录
     * @param startTime
     * @param endTime
     * @return
     */
    List<MaterialStoreroomHistory> queryMaterialHistoryList(String startTime, String endTime);

    /**
     *  查询子表中是对应库房的主表信息
     * @param storeroomId   库房ID
     * @return  到货单主表信息
     */
    List<PuArrivalvouch> queryPuArrivalVouch(Integer storeroomId);

    /**
     *  通过主表Id查询对应库房的字表信息
     * @param storeroomId  库房ID
     * @param id   主表ID
     * @return  子表集合
     */
    List<PuArrivalvouchs> queryPuArrivalVouchs(Integer storeroomId, Integer id);


    /**
     * 原料入库
     * @param batch
     */
    void putMaterialStoreroom(MaterialBatch batch);

}
