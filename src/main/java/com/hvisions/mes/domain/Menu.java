package com.hvisions.mes.domain;

import lombok.Data;

import java.util.List;

/**
 * 菜单实体类
 *
 * @author wenkb
 * @since 2018-01-10
 */
@Data
public class Menu {
    /**
     * 菜单ID
     */
    private String menuId;

    /**
     * 菜单名（中文）
     */
    private String menuNameZh;

    /**
     * 菜单名（英文）
     */
    private String menuNameEn;

    /**
     * 上级菜单ID
     */
    private String upMenuId;

    /**
     * 排序（同级内排序）
     */
    private Integer sort;

    /**
     * 页面地址
     */
    private String url;

    /**
     * 是否可见（0：不可见、1：可见）
     */
    private Integer visible;

    private String upMenuMameZh;

    private String upMenuMameEn;

    private String upUpMenuId;

    private String upUpMenuMameZh;

    private String upUpMenuMameEn;

    private Integer menuType;

    /**
     *  菜单图标
     */
    private String menuIcon;

    ///////////////////////  冗余字段  /////////////////////
    private List<Menu> secondMenus;

}
