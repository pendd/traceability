package com.hvisions.mes.controller.newpdacontroller;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.domain.PdaDomain.PdaResponseData;
import com.hvisions.mes.service.INewPdaService.INewPdaMaterialService;
import com.hvisions.mes.util.JsonStringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 原料入库控制器
 *
 * @author dpeng
 * @create 2019-05-31 16:40
 */
@RestController
@RequestMapping("/json/pda/materialHistory")
@Api(description = "NewPdaMaterialController | 原料入库控制器")
public class NewPdaMaterialController {

    private PdaResponseData pdaResponseData;
    private Map<String,Object> data;

    @Autowired
    private INewPdaMaterialService materialService;

    @ModelAttribute
    public void init() {
        pdaResponseData = new PdaResponseData();
        data = new HashMap<>(16);
    }

    /**
     *  原料入库列表历史记录
     * @param requestMap
     * @return
     */
    @PostMapping("/showMaterialHistoryList")
    public PdaResponseData showMaterialHistoryList(@RequestBody Map requestMap) {
        String startTime = requestMap.get("startTime").toString();
        String endTime = requestMap.get("endTime").toString();
        List<MaterialStoreroomHistory> list = materialService.queryMaterialHistoryList(startTime, endTime);
        data.put("materialHistorys",list);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "到货单主表信息")
    @ApiImplicitParam(name = "json",value = "库房ID 格式 {'storeroomId':'1'}  返回字段 id:主表ID,ccode:主表单据编号,ddate:单据日期,cvenname:供应商名称",paramType = "query",dataType = "Integer")
    @RequestMapping(value = "/getPuArrivalVouch",method = RequestMethod.GET)
    public PdaResponseData getPuArrivalVouch(String json) {
        Integer storeroomId = JsonStringUtil.analyseJsonString(json, "storeroomId", Integer.class);
        List<PuArrivalvouch> puArrivalVouches = materialService.queryPuArrivalVouch(storeroomId);
        data.put("list",puArrivalVouches);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "到货单子表信息")
    @ApiImplicitParam(name = "json",value = "库房ID 和主表ID 格式 {'storeroomId':'1','id':1} 返回字段 iarrsid:字表ID,ciinvname:物料名称,cinvcode:物料编码,iquantity:到货数量,amount:已扫码数量",paramType = "query",dataType = "Integer")
    @RequestMapping(value = "/getPuArrivalVouchs",method = RequestMethod.GET)
    public PdaResponseData getPuArrivalVouchs(String json) {
        Integer storeroomId = JsonStringUtil.analyseJsonString(json, "storeroomId", Integer.class);
        Integer id = JsonStringUtil.analyseJsonString(json, "id", Integer.class);
        List<PuArrivalvouchs> list = materialService.queryPuArrivalVouchs(storeroomId, id);
        data.put("list",list);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "原料入库")
    @RequestMapping(value = "/getMaterialStoreroom",method = RequestMethod.POST)
    public PdaResponseData getMaterialStoreroom(@RequestBody MaterialBatch materialBatch) {
        materialService.putMaterialStoreroom(materialBatch);
        return pdaResponseData;
    }

}
