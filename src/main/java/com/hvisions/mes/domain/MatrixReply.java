package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;

@Data
public class MatrixReply {
    /**
     *
     */
    private Integer replyId;

    /**
     *
     */
    private Integer matrixId;

    /**
     *
     */
    private String matrixName;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Integer repairState;

    /**
     *
     */
    private String repairDetail;

    /**
     *
     */
    private Integer taskUser;

    /**
     *
     */
    private Integer principle;

    /**
     *
     */
    private Date replyTime;
}

