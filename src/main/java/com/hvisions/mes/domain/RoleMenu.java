package com.hvisions.mes.domain;

public class RoleMenu {

    /**
     * 流水号
     */
    private Integer serialId;
    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

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

    private Integer menuType;



    /**
     * 是否可见（0：不可见、1：可见）
     */
    private Integer visible;




    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuNameZh() {
        return menuNameZh;
    }

    public void setMenuNameZh(String menuNameZh) {
        this.menuNameZh = menuNameZh;
    }

    public String getMenuNameEn() {
        return menuNameEn;
    }

    public void setMenuNameEn(String menuNameEn) {
        this.menuNameEn = menuNameEn;
    }

    public String getUpMenuId() {
        return upMenuId;
    }

    public void setUpMenuId(String upMenuId) {
        this.upMenuId = upMenuId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }




}

