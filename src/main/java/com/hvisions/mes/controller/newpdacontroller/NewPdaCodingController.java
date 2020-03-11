package com.hvisions.mes.controller.newpdacontroller;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.domain.PdaDomain.PdaResponseData;
import com.hvisions.mes.dto.ArrivalOrderRefDTO;
import com.hvisions.mes.service.INewPdaService.INewPdaCodingService;
import com.hvisions.mes.util.JsonStringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author dpeng
 * @description 打码控制器
 * @date 2019-07-21 14:46
 */
@RestController
@RequestMapping("/json/pda/coding")
@Api(description = "NewPdaCodingController | 打码控制器")
public class NewPdaCodingController {

    private PdaResponseData pdaResponseData;
    private Map<String,Object> data;

    @Autowired
    private INewPdaCodingService codingService;

    @ModelAttribute
    public void init(){
        pdaResponseData = new PdaResponseData();
        data = new HashMap<>(16);
    }

    /**
     *  新增原料码表记录
     * @param materialCode 原料码对象
     * @return
     */
    @RequestMapping(value = "/addMaterialCode",method = RequestMethod.POST)
    public PdaResponseData addMaterialCode(@RequestBody MaterialCode materialCode){
        codingService.appendMaterialCode(materialCode);
        return pdaResponseData;
    }

    /**
     *  新增包装码表记录
     * @param packCode  包装码对象
     * @return
     */
    @RequestMapping(value = "/addPackCode",method = RequestMethod.POST)
    public PdaResponseData addPackCode(@RequestBody PackCode packCode) {
        codingService.appendPackCode(packCode);
        return pdaResponseData;
    }

    /**
     *  传进来一个二维码  判断是原料码、包装码、还是其他   0原料码  1包装码 2都不是
     * @param stock
     * @return
     */
    @PostMapping("/getCodeTypeByCode")
    public PdaResponseData getCodeTypeByCode(@RequestBody Stock stock) {
        int i = codingService.queryCodeTypeByCode(stock.getCode());
        data.put("type",i);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "原料入库扫二维码判断是否重复扫码,true 已扫过  false 没有扫过")
    @ApiImplicitParam(name = "json",value = "code物料二维码 parentId 主表ID,返回值 childId 子表ID 格式{'parentId':'1','childId':'2','code':'1%1565060520366'}",paramType = "query",dataType = "String")
    @RequestMapping(value = "/getMaterialSignCodeByCode",method = RequestMethod.GET)
    public PdaResponseData getMaterialSignCodeByCode(String json) {
        String code = JsonStringUtil.analyseJsonString(json, "code", String.class);
        Integer parentId = JsonStringUtil.analyseJsonString(json, "parentId", Integer.class);
        Integer childId = JsonStringUtil.analyseJsonString(json, "childId", Integer.class);
        String materialSignCode = JsonStringUtil.analyseJsonString(json, "materialSignCode", String.class);
        Map<String,Object> map = codingService.queryMaterialSignCodeByCode(parentId,childId,code,materialSignCode);
        pdaResponseData.setData(map);
        return pdaResponseData;
    }

    @ApiOperation(value = "新增到货关系表记录|")
    @RequestMapping(value = "/modifyArrivalOrderRef",method = RequestMethod.POST)
    public PdaResponseData modifyArrivalOrderRef(@RequestBody ArrivalOrderRef ref) {
        codingService.addArrivalOrderRef(ref);
        return pdaResponseData;
    }

    /**
     *  state 0 表示删除成功  1 表示不存在该扫码记录
     * @param refDTO
     * @return
     */
    @PostMapping("/removeArrivalOrderRef")
    @ApiOperation(value = "到货表扫码记录删除 state 0 删除成功  1 不存在该扫码记录")
    public PdaResponseData removeArrivalOrderRef(@Validated @RequestBody ArrivalOrderRefDTO refDTO) {
        int count = codingService.deleteArrivalOrderRef(refDTO);
        if (count <= 0) {
            data.put("state",1);
        }else {
            data.put("state",0);
        }
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "新增包装表记录")
    @RequestMapping(value = "/addMaterialPackOrRef",method = RequestMethod.POST)
    public PdaResponseData addMaterialPackOrRef(@RequestBody List<MaterialPack> packs) {
        codingService.putMaterialPackOrRef(packs);
        return pdaResponseData;
    }
}
