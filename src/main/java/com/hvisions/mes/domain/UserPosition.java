package com.hvisions.mes.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wanghaiwei
 * @since 2017-05-25
 */
@TableName("user_position")
public class UserPosition extends Model<UserPosition> {

    private static final long serialVersionUID = 1L;

    @TableId("position_id")
    private Integer positionId;

    /**
     * 0:正常、1:删除
     *
     */
    @TableField("del_flag")
    private Integer delFlag;
    @TableField("dept_id")
    private Integer deptId;
    private String memo;

    @TableField("position_name")
    private String positionName;
    @TableField("position_name_en")
    private String positionNameEn;
    @TableField("up_position_id")
    private Integer upPositionId;

    private String upPosition;
    private String deptName;

    public String getUpPosition() {
        return upPosition;
    }

    public void setUpPosition(String upPosition) {
        this.upPosition = upPosition;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionNameEn() {
        return positionNameEn;
    }

    public void setPositionNameEn(String positionNameEn) {
        this.positionNameEn = positionNameEn;
    }

    public Integer getUpPositionId() {
        return upPositionId;
    }

    public void setUpPositionId(Integer upPositionId) {
        this.upPositionId = upPositionId;
    }

    public static final String DEL_FLAG = "del_flag";

    public static final String DEPT_ID = "dept_id";

    public static final String MEMO = "memo";

    public static final String POSITION_ID = "position_id";

    public static final String POSITION_NAME = "position_name";

    public static final String POSITION_NAME_EN = "position_name_en";

    @Override
    protected Serializable pkVal() {
        return this.positionId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserPosition [positionId=");
        builder.append(positionId);
        builder.append(", delFlag=");
        builder.append(delFlag);
        builder.append(", deptId=");
        builder.append(deptId);
        builder.append(", memo=");
        builder.append(memo);
        builder.append(", positionName=");
        builder.append(positionName);
        builder.append(", positionNameEn=");
        builder.append(positionNameEn);
        builder.append(", upPositionId=");
        builder.append(upPositionId);
        builder.append("]");
        return builder.toString();
    }

}
