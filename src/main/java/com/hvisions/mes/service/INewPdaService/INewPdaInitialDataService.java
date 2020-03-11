package com.hvisions.mes.service.INewPdaService;

import com.hvisions.mes.dto.InitialDataDTO;

/**
 * 旧库存初始化
 * @author dpeng
 * @date 2019-11-18 17:04
 */
public interface INewPdaInitialDataService {

    /**
     *  旧库存初始化
     * @param initialDataDTO
     * @return
     */
    String addInitialData(InitialDataDTO initialDataDTO);

}
