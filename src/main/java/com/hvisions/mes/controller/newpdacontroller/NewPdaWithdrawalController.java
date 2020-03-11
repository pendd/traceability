package com.hvisions.mes.controller.newpdacontroller;

import com.hvisions.mes.domain.Material;
import com.hvisions.mes.domain.MaterialStoreroomHistory;
import com.hvisions.mes.domain.PdaDomain.PdaResponseData;
import com.hvisions.mes.domain.Withdrawal;
import com.hvisions.mes.domain.WithdrawalCodeRef;
import com.hvisions.mes.dto.WithdrawalCodeRefDTO;
import com.hvisions.mes.service.INewPdaService.INewPdaWithdrawalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 退料控制器
 *
 * @author dpeng
 * @create 2019-06-17 9:30
 */
@RestController
@RequestMapping("/json/pda/withdrawal")
@Api(description = "NewPdaWithdrawalController | 退料控制器")
public class NewPdaWithdrawalController {

    private PdaResponseData pdaResponseData;
    private Map<String,Object> data;

    @Autowired
    private INewPdaWithdrawalService withdrawalService;

    @ModelAttribute
    public void init() {
        pdaResponseData = new PdaResponseData();
        data = new HashMap<>(16);
    }

    @ApiOperation(value = "删除退料扫码记录")
    @PostMapping("/removeWithdrawalCodeRef")
    public PdaResponseData removeWithdrawalCodeRef(@Validated @RequestBody WithdrawalCodeRefDTO refDTO) {
        int count = withdrawalService.cutWithdrawalCodeRef(refDTO);
        if (count > 0) {
            data.put("state",0);
        }else {
            data.put("state",1);
        }
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  库房退料任务列表
     * @param withdrawal
     * @return
     */
    @RequestMapping("/withdrawalTaskList")
    public PdaResponseData withdrawalTaskList(@RequestBody Withdrawal withdrawal) {
        List<Withdrawal> withdrawals = withdrawalService.selectWithdrawalList(withdrawal.getStoreroomId());
        data.put("produceNumbers",withdrawals);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     * 一个任务对应的具体的物料明细
     * @param withdrawal
     * @return
     */
    @RequestMapping("/withdrawalDetailList")
    public PdaResponseData withdrawalDetailList(@RequestBody Withdrawal withdrawal) {
        List<Material> materials = withdrawalService.selectWithdrawalDetailList(withdrawal.getWorkNumber(), withdrawal.getStoreroomId());
        data.put("withdrawalList",materials);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  扫二维码逻辑
     * @param withdrawal  materialId, code
     * @return 1 匹配 0 不匹配
     */
    @RequestMapping("/withdrawalLogic")
    public PdaResponseData getWithdrawalLogic(@RequestBody Withdrawal withdrawal) {
        String flag = withdrawalService.selectWithdrawalLogic(withdrawal.getCode(), withdrawal.getMaterialId());
        data.put("flag",flag);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  新增退料码表记录
     * @param ref
     * @return
     */
    @PostMapping("/addWithdrawalCodeRef")
    public PdaResponseData addWithdrawalCodeRef(@RequestBody WithdrawalCodeRef ref) {
        withdrawalService.appendWithdrawalCodeRef(ref);
        return pdaResponseData;
    }

    /**
     *  退料原料回库
     * @param history
     * @return
     */
    @PostMapping("/materialBackHistory")
    public PdaResponseData materialBackHistory(@RequestBody MaterialStoreroomHistory history) {
        withdrawalService.materialBackHistory(history.getProduceNumber(),history.getStoreroomId(),history.getUserId().intValue(),history.getTeamId().intValue());
        return pdaResponseData;
    }

    /**
     *  原料回库
     * @param map
     * @return
     */
    /*@RequestMapping("/materialBackHistory")
    public PdaResponseData materialBackHistory(@RequestBody Map<String,List<MaterialStoreroomHistory>> map) {
        boolean flag = withdrawalService.insertMaterialBackHistory(map.get("codes"));
        if (!flag) {
            // 存在箱子不是整箱子 但是 扫箱子码回库了
            pdaResponseData.setStatus(PdaResponseData.STATUS_FAIL);
            pdaResponseData.setErrorMessage("只有整箱才能扫箱码入库");
        }
        return pdaResponseData;
    }*/

}
