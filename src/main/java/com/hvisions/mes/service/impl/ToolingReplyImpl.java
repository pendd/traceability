package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.ToolingRepair;
import com.hvisions.mes.domain.ToolingReply;
import com.hvisions.mes.mapper.ToolingReplyMapper;
import com.hvisions.mes.service.IToolingReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dpeng
 * @description
 * @date 2019-08-30 14:10
 */
@Service
public class ToolingReplyImpl implements IToolingReply {

    @Autowired
    private ToolingReplyMapper replyMapper;

    @Override
    public Page<ToolingReply> queryToolingReplyByPage(Page<ToolingReply> page,Integer userId) {
        page.setRecords(replyMapper.selectToolingReplyByPage(page,userId));
        return page;
    }

    @Override
    public void updateToolingReply(ToolingReply toolingReply) {
        replyMapper.updateToolingReply(toolingReply);
    }

    @Override
    public Page<ToolingReply> queryAllToolingReply(Page<ToolingReply> page, String toolingName, String taskUserName, String principleName) {
        page.setRecords(replyMapper.selectAllToolingReply(page,toolingName,taskUserName,principleName));
        return page;
    }
}
