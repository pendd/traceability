package com.hvisions.mes.controller.newpdacontroller;

import com.hvisions.mes.controller.vo.newpdavo.AssignMaterialScanQrCodeVo;
import com.hvisions.mes.domain.*;
import com.hvisions.mes.domain.PdaDomain.PdaResponseData;
import com.hvisions.mes.dto.AssignMaterialQrRefDTO;
import com.hvisions.mes.service.INewPdaService.INewPdaAssignMaterialService;
import com.hvisions.mes.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配料控制器
 *
 * @author dpeng
 * @create 2019-06-10 16:34
 */
@RestController
@RequestMapping("/json/pda/assignMaterial")
@Api(description = "库房配料模块控制器")
public class NewPdaAssignMaterialController {

    private PdaResponseData pdaResponseData;
    private Map<String,Object> data;

    @Autowired
    private INewPdaAssignMaterialService assignMaterialService;

    @ModelAttribute
    public void init() {
        pdaResponseData = new PdaResponseData();
        data = new HashMap<>(16);
    }

    @ApiOperation(value = "库房备料任务列表",notes = "请求和返回和以前一样")
    @PostMapping("/taskList")
    public PdaResponseData taskList(@RequestBody(required = false) CallOff callOff) {

        List<CallOff> callOffs = assignMaterialService.selectCallOffStoreList(callOff.getStoreroomId());
        data.put("produceNumbers",callOffs);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "一个任务对应具体物料明细",notes = "返回结果比以前多了一个叫callOffId 这个是用来传给后台的,多了一个returnAmount这个是已扫码数量,请求参数没变")
    @PostMapping("/materialDetailList")
    public PdaResponseData materialDetailList(@RequestBody(required = false) CallOff callOff) {

        List<Material> materials = assignMaterialService.selectCallOffMaterialList(callOff.getWorkNumber(), callOff.getStoreroomId());
        data.put("materialList",materials);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "库房备料任务扫码返回逻辑",notes = "返回结果 status: 0 表示不是对应物料  1是物料二维码并且对应物料 type是否还存在比当前物料还早入库的物料 0不存在 1存在")
    @PostMapping(value = "/materialCodeLogic")
    public PdaResponseData materialCodeLogic(@RequestBody AssignMaterialScanQrCodeVo vo) {
        AssignMaterialScanQrCodeVo qrCodeVo = assignMaterialService.selectCallOffLogic(vo.getStoreroomId(),vo.getMaterialCode(), vo.getMaterialId());
        data.put("vo",qrCodeVo);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  产线配料任务列表
     * @param callOff
     * @return
     */
    @PostMapping("/getCallOffOrderTask")
    public PdaResponseData getCallOffOrderTask(@RequestBody CallOff callOff) {
        if (callOff.getUserId() == null) {
            pdaResponseData.setStatus(PdaResponseData.STATUS_FAIL);
            pdaResponseData.setErrorMessage("参数传入不正确");
            return pdaResponseData;
        }
        List<CallOff> callOffs = assignMaterialService.selectCallOffOrderList(callOff.getUserId().intValue());
        data.put("workNumbers",callOffs);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  配料出库列表 （工序人员）
     * @param callOff
     * @return
     */
    @PostMapping("/materialOutList")
    public PdaResponseData materialOutList(@RequestBody CallOff callOff) {
        if (callOff.getUserId() == null || StringUtil.isNull(callOff.getWorkNumber())) {
            pdaResponseData.setStatus(PdaResponseData.STATUS_FAIL);
            pdaResponseData.setErrorMessage("参数传入不正确");
            return pdaResponseData;
        }
        List<CallOff> list = assignMaterialService.selectCallOffList(callOff.getUserId().intValue(),callOff.getWorkNumber());
        data.put("materialList",list);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  更新配料状态
     * @param callOff
     * @return
     */
    @PostMapping("/updateMaterialState")
    public PdaResponseData updateMaterialState(@RequestBody CallOff callOff) {
        if (callOff.getState() == null || StringUtil.isNull(callOff.getWorkNumber())) {
            pdaResponseData.setStatus(PdaResponseData.STATUS_FAIL);
            pdaResponseData.setErrorMessage("参数传入不正确");
            return pdaResponseData;
        }

        try {
            assignMaterialService.updateCallOffState(callOff.getWorkNumber(),callOff.getState());
        } catch (Exception e) {
            e.printStackTrace();
            pdaResponseData.setStatus(PdaResponseData.STATUS_FAIL);
            pdaResponseData.setErrorMessage("更新失败");
            return pdaResponseData;
        }
        return pdaResponseData;
    }

    @ApiOperation(value = "新增配料任务原料二维码扫码记录")
    @PutMapping("/addAssignMaterialRef")
    public PdaResponseData addAssignMaterialRef(@RequestBody AssignMaterialQrRef ref) {
        if (ref.getQrCode() == null || "".equals(ref.getQrCode())) {
            pdaResponseData.setStatus(1);
            pdaResponseData.setErrorMessage("更新失败");
        }else {
            assignMaterialService.appendAssignMaterialQrRef(ref);
        }
        return pdaResponseData;
    }

    @ApiOperation(value = "备料出库",notes = "返回值type 0 出库成功  1 出库失败,没有发现材料出库单")
    @PostMapping("/assignMaterialOut")
    public PdaResponseData assignMaterialOut(@RequestBody CallOff callOff) {
        int type = assignMaterialService.assignMaterialOut(callOff.getWorkNumber(), callOff.getStoreroomId(), callOff.getUserId().intValue(), callOff.getTeamId().intValue());
        data.put("type",type);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "删除配料出库扫码历史")
    @PostMapping("/removeAssignMaterialQrRef")
    public PdaResponseData removeAssignMaterialQrRef(@Validated @RequestBody AssignMaterialQrRefDTO refDTO) {
        int count = assignMaterialService.cutAssignMaterialQrRef(refDTO);
        if (count > 0) {
            data.put("state",0);
        }else {
            data.put("state",1);
        }
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

}
