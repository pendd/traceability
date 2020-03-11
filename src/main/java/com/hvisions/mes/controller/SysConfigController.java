package com.hvisions.mes.controller;

import com.hvisions.mes.domain.SysConfig;
import com.hvisions.mes.service.ISysConfigService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanghaiwei
 * @since 2017-05-25
 */
@ApiIgnore
@RestController
@RequestMapping("/json/sysConfig")
public class SysConfigController extends BaseController {
    @Autowired
    private ISysConfigService sysConfigService;

    @PostMapping("addSysConfig")
    public void addSysConfig(@RequestParam("name") String mailSmtp,
            @RequestParam("address") String mailAddr, @RequestParam("MailUserName") String mailUser,
            @RequestParam("MailUserpassword") String mailPassword) {
        SysConfig sysConfig = new SysConfig();
        if(mailSmtp!=null && mailAddr!=null && mailUser!=null && mailPassword!=null)
        {
            sysConfig.setMailSmtp(mailSmtp);
            sysConfig.setMailAddr(mailAddr);
            sysConfig.setMailUser(mailUser);
            sysConfig.setMailPassword(mailPassword);
            sysConfigService.addSysConfig(sysConfig);
        }

        //addPersonnelOperationLog(OperationLog.OPERATION_TYPE_ADD, "添加邮件配置:" + mailUser);
    }

    @GetMapping("selectSysConfig")
    public List<SysConfig> selectSysConfig() {
        //addPersonnelOperationLog(OperationLog.OPERATION_TYPE_QUERY, "查看邮件配置");
        return sysConfigService.selectSysConfig();
    }

    @PostMapping("modifySysConfig")
    public void modifySysConfig(@RequestParam("name") String mailSmtp,
            @RequestParam("address") String mailAddr, @RequestParam("MailUserName") String mailUser,
            @RequestParam("MailUserpassword") String mailPassword) {
        sysConfigService.removeSysConfig();
        SysConfig sysConfig = new SysConfig();
        sysConfig.setMailSmtp(mailSmtp);
        sysConfig.setMailAddr(mailAddr);
        sysConfig.setMailUser(mailUser);
        sysConfig.setMailPassword(mailPassword);
        sysConfigService.addSysConfig(sysConfig);
        //addPersonnelOperationLog(OperationLog.OPERATION_TYPE_MODIFY, "修改邮件配置:" + mailUser);
    }
}
