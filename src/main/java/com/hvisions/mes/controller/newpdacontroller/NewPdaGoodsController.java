package com.hvisions.mes.controller.newpdacontroller;

import com.hvisions.mes.controller.vo.newpdavo.GoodsInStoreroomVo;
import com.hvisions.mes.domain.*;
import com.hvisions.mes.domain.PdaDomain.PdaResponseData;
import com.hvisions.mes.dto.GoodsStoreroomRefDTO;
import com.hvisions.mes.service.INewPdaService.INewPdaGoodsService;
import com.hvisions.mes.util.JsonStringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成品包装 入库控制器
 *
 * @author dpeng
 * @create 2019-05-30 17:04
 */
@Api(description = "成品入库控制器")
@RestController
@RequestMapping("/json/pda/goods")
public class NewPdaGoodsController {

    private PdaResponseData pdaResponseData;
    private Map<String, Object> data;

    @Autowired
    private INewPdaGoodsService goodsService;

    @ModelAttribute
    public void init() {
        pdaResponseData = new PdaResponseData();
        data = new HashMap<>(16);
    }

    @ApiOperation(value = "删除成品入库扫码记录")
    @PostMapping("removeGoodsStoreroomRef")
    public PdaResponseData removeGoodsStoreroomRef(@Validated @RequestBody GoodsStoreroomRefDTO refDTO) {
        int count = goodsService.cutGoodsStoreroomRef(refDTO);
        if (count > 0) {
            data.put("state",0);
        }else {
            data.put("state",1);
        }
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  成品包装信息批量提交
     * @param map
     * @return
     */
    @PostMapping("/addGoodsPackList")
    public PdaResponseData addGoodsPackList(@RequestBody Map<String,List<GoodsPack>> map) {

        try {
            goodsService.appendGoodsPackList(map.get("codes"));
        } catch (Exception e) {
            e.printStackTrace();
            pdaResponseData.setStatus(PdaResponseData.STATUS_FAIL);
            pdaResponseData.setErrorMessage(e.getMessage());
            return pdaResponseData;
        }

        return pdaResponseData;
    }

    @ApiOperation(value = "成品入库")
    @PostMapping("/addGoodStoreroomList")
    public PdaResponseData addGoodStoreroomList(@RequestBody GoodsInStoreroomVo vo) {
        goodsService.appendGoodsInStoreroom(vo.getCMoCode(),vo.getUserId(),vo.getTeamId(),vo.getStoreroomId());
        return pdaResponseData;
    }

    @ApiOperation(value = "主表信息",notes = "返回值 MoId 主表ID  cMoCode 生产订单编号")
    @GetMapping("/getMomOrder")
    public PdaResponseData getMomOrder() {
        List<MomOrder> momOrders = goodsService.queryMomOrder();
        data.put("list",momOrders);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "子表信息",notes = "参数只要cMoCode:DD20190626 这个是生产订单编号 scanAmount 扫码数量 返回值 ModId 子表ID unit 单位  cInvCode 物料编码 iModQty 生产数量 materialName 物料名称")
    @GetMapping("/getMomOrderDetail")
    public PdaResponseData getMomOrderDetail(String json) {
        List<MomOrderDetail> momOrders = goodsService.queryMomOrderDetailByCMoCode(JsonStringUtil.analyseJsonString(json,"cMoCode",String.class));
        data.put("list",momOrders);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "成品入库扫码记录",notes = "只有四个参数 cMoCode materialSignCode qrGoodsCode,amount  出参 flag  1 操作成功 0 操作失败")
    @PostMapping("/addGoodsStoreroomRef")
    public PdaResponseData addGoodsStoreroomRef(@RequestBody GoodsStoreroomRef ref) {
        String flag;
        try {
            goodsService.appendGoodsStoreroomRef(ref);
            flag = "1";
        } catch (Exception e) {
            flag = "0";
            e.printStackTrace();
        }
        data.put("flag",flag);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "扫码显示数量|| 参数qrGoodsCode")
    @GetMapping("/queryCountByQrCode")
    public PdaResponseData queryCountByQrCode(String json) {
        int count = goodsService.selectCountByQrCode(JsonStringUtil.analyseJsonString(json, "qrGoodsCode", String.class));
        data.put("amount",count);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "成品入库扫码逻辑",notes = "入参:qrCode 扫的二维码 cInvCode 子表那一行带的一个字段  出参 flag 0 不匹配  1 匹配  2 此包装码内没有包装成品码")
    @GetMapping("/scanGoodsCodeLogic")
    public PdaResponseData scanGoodsCodeLogic(String qrCode,String cInvCode) {
        String flag = goodsService.scanGoodsCodeLogic(qrCode, cInvCode);
        data.put("flag",flag);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

}
