package com.hvisions.mes.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Tree extends Model<Tree>{

    private static final long serialVersionUID = 1L;


    public String id ;

    public String text;

    public Integer itype;

    public String upid ;

    public List<Tree> children ;

    public Integer eqId;

    public String title;

    public String content;

    private Boolean checked;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
