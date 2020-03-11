package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.ToolingReply;import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dpeng
 * @description 工装反馈
 * @date 2019-08-29 14:20
 */
public interface ToolingReplyMapper {

    void insertToolingReply(ToolingReply reply);

    List<ToolingReply> selectToolingReplyByPage(@Param("page")Pagination page, @Param("userId")Integer userId);

    void updateToolingReply(ToolingReply toolingReply);

    List<ToolingReply> selectAllToolingReply(@Param("page")Pagination page, @Param("toolingName")String toolingName, @Param("taskUserName")String taskUserName, @Param("principleName")String principleName);


}
