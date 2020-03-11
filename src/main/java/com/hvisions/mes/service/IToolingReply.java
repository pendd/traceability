package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.ToolingReply;

import java.util.List;

/**
 * @author dpeng
 * @description 工装反馈接口
 * @date 2019-08-30 14:09
 */
public interface IToolingReply {

    Page<ToolingReply> queryToolingReplyByPage(Page<ToolingReply> page,Integer userId);

    void updateToolingReply(ToolingReply toolingReply);

    Page<ToolingReply> queryAllToolingReply(Page<ToolingReply> page,String toolingName,String taskUserName,String principleName);
}
