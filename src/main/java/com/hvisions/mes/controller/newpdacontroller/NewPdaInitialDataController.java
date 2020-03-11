package com.hvisions.mes.controller.newpdacontroller;

import com.hvisions.mes.dto.InitialDataDTO;
import com.hvisions.mes.service.INewPdaService.INewPdaInitialDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 旧库存初始化控制器
 * @author dpeng
 * @date 2019-11-18 18:32
 */
@RestController
@RequestMapping("/json/pda/initialData")
public class NewPdaInitialDataController {

    @Autowired
    private INewPdaInitialDataService initialDataService;

    @PostMapping("/addInitialData")
    public String addInitialData(@Validated InitialDataDTO initialDataDTO) {
        return initialDataService.addInitialData(initialDataDTO);
    }
}
