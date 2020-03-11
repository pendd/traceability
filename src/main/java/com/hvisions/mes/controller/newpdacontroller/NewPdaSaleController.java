package com.hvisions.mes.controller.newpdacontroller;

import com.hvisions.mes.controller.vo.newpdavo.SaleOutStoreroomVo;
import com.hvisions.mes.controller.vo.newpdavo.SaleScanCodeVo;
import com.hvisions.mes.domain.DispatchList;
import com.hvisions.mes.domain.DispatchLists;
import com.hvisions.mes.domain.PdaDomain.PdaResponseData;
import com.hvisions.mes.domain.SaleCodeRef;
import com.hvisions.mes.dto.SaleCodeRefDTO;
import com.hvisions.mes.service.INewPdaService.INewPdaSaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dpeng
 * @description 销售出库控制器
 * @date 2019-08-12 17:00
 */
@RestController
@RequestMapping("/json/pda/sale")
@Api(description = "销售出库控制器")
public class NewPdaSaleController {

    @Autowired
    private INewPdaSaleService saleService;

    private Map<String,Object> data;
    private PdaResponseData pdaResponseData;

    @ModelAttribute
    public void init() {
        data = new HashMap<>(16);
        pdaResponseData = new PdaResponseData();
    }

    @ApiOperation(value = "删除销售出库扫码记录")
    @PostMapping("/removeSaleCodeRef")
    public PdaResponseData removeSaleCodeRef(@Validated @RequestBody SaleCodeRefDTO refDTO) {
        int count = saleService.cutSaleCodeRef(refDTO);
        if (count > 0) {
            data.put("state",0);
        }else {
            data.put("state",1);
        }
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "销售出库主表信息",notes = "ccode 单据编号 ddate 单据日期  参数storeroomId 给3 成品库")
    @GetMapping("/getDispatchList")
    public PdaResponseData getDispatchList(Integer storeroomId) {
        List<DispatchList> dispatchLists = saleService.queryDispatchList(storeroomId);
        data.put("list",dispatchLists);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "销售出库子表信息",notes = " 传入参数格式为'111111'  iquantity 要发货数量 cinvCode 物料编码 productName 产品名称 storeroomName 库房名称")
    @GetMapping("/getDispatchLists")
    public PdaResponseData getDispatchLists(String cCode,Integer storeroomId) {
        List<DispatchLists> dispatchLists = saleService.queryDispatchListsByCCode(storeroomId,cCode);
        data.put("list",dispatchLists);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "销售扫码逻辑 || 返回值 isMatch 是否匹配 0不匹配 1匹配  2空包装")
    @PostMapping("/saleScanCodeLogic")
    public PdaResponseData saleScanCodeLogic(@RequestBody SaleScanCodeVo vo) {
        SaleScanCodeVo codeVo = saleService.saleScanCodeLogic(vo.getQrCode(), vo.getParentId(), vo.getChildId());
        data.put("isMatch",codeVo.getIsMatch());
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "销售出库")
    @PostMapping("/saleOutStoreroom")
    public PdaResponseData saleOutStoreroom(@RequestBody SaleOutStoreroomVo vo) {
        saleService.saleOutStoreroom(vo.getParentId(),vo.getStoreroomId(),vo.getUserId(),vo.getTeamId());
        return pdaResponseData;
    }

    @ApiOperation(value = "新增销售扫码记录",notes = "入参 parentId childId amount storeroomId qrCode materialSignCode 出参 flag 1 操作成功 0 操作失败")
    @GetMapping("/addSaleCodeRef")
    public PdaResponseData addSaleCodeRef(SaleCodeRef ref) {
        String flag;
        try {
            saleService.appendSaleCodeRef(ref);
            flag = "1";
        } catch (Exception e) {
            e.printStackTrace();
            flag = "0";
        }
        data.put("flag",flag);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }
}
