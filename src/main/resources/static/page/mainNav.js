$(function () {
    // ========================== 组件 ==========================
    //加载一级菜单（页面上半部分）
    loadTopMenu();

    //加载二级菜单（页面下半部分）
    loadSubMenu(parent.topMenuData.menus[parent.currTopMenuIdx].menuId)

    var urlParam="none";
    var menuIdParam="";
    var upMenuIdParam="";
    var upUpMenuIdParam="";
    // ========================== 方法 ==========================
    //加载一级菜单（页面上半部分）
    function loadTopMenu() {
        if (parent.topMenuData.length == 0) {
            $("#mainDiv").html("");

            return;
        }

        var menuHTML = "";
        var menuClass = "";
        var menuImage = "";
        var menuClick = "";
        $.each(parent.topMenuData.menus, function (index, item) {
            //设置选中一级菜单样式
            if (parent.currTopMenuIdx == index) { //选中
                menuClass = " class=\"top_nav_sel\" ";
                menuImage = "/image/Menu/nav_" + item.menuId.toLowerCase() + ".png";
            } else { //未选中
                menuClass = "";
                menuImage = "/image/Menu/nav_grey_" + item.menuId.toLowerCase() + ".png";
            }

            //限制访问的菜单，不能点击
            if (item.disabled == 1) { //限制访问
                menuClick = "";
            } else {
                menuClick = " onclick=\"parent.showMainMavi(" + index + ")\" ";
            }
            if(parent.topMenuData.language=='zh')
            {
                menuHTML += "<div " + menuClick + menuClass + " style=\"float: left; "
                + "                                    text-align: center;"
                + "                                    margin-top: 40px; "
                + "                                    width: 79px;"
                + "                                    padding-top: 89px; "
                + "                                    cursor: pointer;"
                + "                                    height: 21px; "
                + "                                    margin-left: 20px; "
                + "                                    margin-right: 0px; "
                + "                                    background:url('" + menuImage + "') top center no-repeat \" >"
                + "   <div class=\"menu_name\" >" + item.menuNameZh + "</div>"
                + " </div>";
            }
            else
                {

                menuHTML += "<div " + menuClick + menuClass + " style=\"float: left; "
                 + "                                    text-align: center;"
                 + "                                    margin-top: 40px; "
                 + "                                    width: 79px;"
                 + "                                    padding-top: 89px; "
                 + "                                    cursor: pointer;"
                 + "                                    height: 21px; "
                 + "                                    margin-left: 20px; "
                 + "                                    margin-right: 0px; "
                 + "                                    background:url('" + menuImage + "') top center no-repeat \" >"
                 + "   <div class=\"menu_name\" >" + item.menuNameEn + "</div>"
                 + " </div>";
                }

        });

        $("#mainDiv").html(menuHTML);
    };

    //加载二级菜单（页面下半部分）
    function loadSubMenu(menuID) {

        ajax({
            url : "Users/subMenu",
            method : "post",
            param : {
                topMenuID : menuID
            },
            onSuccess : function(json, textStatus, jqXHR) {
                var data = json.data.menus;

                //没有菜单，显示空白
                if (data.length == 0) {
                    $("#main").html("");
                    return;
                }

                //制作菜单html
                var menuHTML = "";
                var menuStyle = "";
                var menuClick = "";
                $.each(data, function (index, item) {
                    if(item.url!="")
                    {

                        if(json.data.language=='zh')
                        {
                            if (item.disabled == 1) { //限制访问菜单
                                menuStyle = " style=\"background-color:#f2f2f2;\" "; //图标变为灰色
                                menuClick = ""; //不可点击
                            } else {
                                menuStyle = "";
                                menuClick = " onclick=\"showsecondMainPage('" + item.url + "', '"+item.upMenuId+"', '"+item.upUpMenuId+"', '"+item.upUpMenuMameZh + "', '" + item.upMenuMameZh + "', '" + item.menuId + "', '" + item.menuNameZh + "')\" ";
                            }
                            menuHTML += "<div class=\"nav_ico_block\" " + menuStyle + menuClick + ">"
                            + "   <div class=\"nav_ico_border\">"
                            + "     <img class=\"nav_ico_image\"  src=\"/image/Menu/nav_" + item.menuId.toLowerCase() + ".png\"/>"
                            + "   </div>"
                            + "   <div class=\"nav_ico_tit\">" + item.menuNameZh + "</div>"
                            + " </div>";
                        $("#btnBack").text("返回");
                        }
                        else
                        {
                            if (item.disabled == 1) { //限制访问菜单
                                menuStyle = " style=\"background-color:#f2f2f2;\" "; //图标变为灰色
                                menuClick = ""; //不可点击
                            } else {
                                menuStyle = "";
                                menuClick = " onclick=\"showsecondMainPage('" + item.url + "', '" +item.upMenuId+ "', '"+item.upUpMenuId+"', '"+item.upUpMenuMameEn+ "', '"+ item.upMenuMameEn + "', '" + item.menuId + "', '" + item.menuNameEn + "')\" ";
                            }
                            menuHTML += "<div class=\"nav_ico_block\" " + menuStyle + menuClick + ">"
                            + "   <div class=\"nav_ico_border\">"
                            + "     <img class=\"nav_ico_image\"  src=\"/image/Menu/nav_" + item.menuId.toLowerCase() + ".png\"/>"
                            + "   </div>"
                            + "   <div class=\"nav_ico_tit\">" + item.menuNameEn + "</div>"
                            + " </div>";
                            $("#btnBack").text("Back");
                        }
                    }
                    else
                    {

                        if(json.data.language=='zh')
                        {
                            if (item.disabled == 1) { //限制访问菜单
                                menuStyle = " style=\"background-color:#f2f2f2;\" "; //图标变为灰色
                                menuClick = ""; //不可点击
                            } else {
                                menuStyle = "";
                                menuClick = " onclick=\"showsecondMainPage('" + item.url + "', '"+item.upMenuId+"', '"+item.upUpMenuId+"', '"+item.upUpMenuMameZh+"', '" + item.upMenuMameZh + "', '" + item.menuId + "', '" + item.menuNameZh + "')\" ";
                            }
                            menuHTML += "<div class=\"nav_ico_block\" " + menuStyle + menuClick + ">"
                            + "   <div class=\"nav_ico_border\">"
                            + "     <img class=\"nav_ico_image\"  src=\"/image/Menu/nav_min_" + item.menuId.toLowerCase() + ".png\"/>"
                            + "   </div>"
                            + "   <div class=\"nav_ico_tit\">" + item.menuNameZh + "</div>"
                            + " </div>";
                        $("#btnBack").text("返回");
                        }
                        else
                        {
                            if (item.disabled == 1) { //限制访问菜单
                                menuStyle = " style=\"background-color:#f2f2f2;\" "; //图标变为灰色
                                menuClick = ""; //不可点击
                            } else {
                                menuStyle = "";
                                menuClick = " onclick=\"showsecondMainPage('" + item.url + "', '" +item.upMenuId+ "', '"+item.upUpMenuId+"', '"+item.upUpMenuMameEn+"', '" + item.upMenuMameEn + "', '" + item.menuId + "', '" + item.menuNameEn + "')\" ";
                            }
                            menuHTML += "<div class=\"nav_ico_block\" " + menuStyle + menuClick + ">"
                            + "   <div class=\"nav_ico_border\">"
                            + "     <img class=\"nav_ico_image\"  src=\"/image/Menu/nav_grey_" + item.menuId.toLowerCase() + ".png\"/>"
                            + "   </div>"
                            + "   <div class=\"nav_ico_tit\">" + item.menuNameEn + "</div>"
                            + " </div>";
                            $("#btnBack").text("Back");
                        }
                    }


                });

                $("#main").html(menuHTML);
            }
        });
    };
    function showsecondMainPage(url,upMenuId,upUpMenuId,upUpMenuMame,upMenuMameZh,menuId,menuNameZh)
    {
        if(url=="")
       {
           parent.$("#dropdown").hide();//;
           parent.showMain(url,upMenuMameZh,menuId,menuNameZh);
           $("#main").html("");
           $("#mainDiv").html("");
           urlParam=url;
           menuIdParam=menuId;
           upMenuIdParam=upMenuId;
           upUpMenuIdParam=upUpMenuId;
           loadTopMenus(upMenuId,menuId);
           loadSubMenu(menuId);
        }
       else
        {

          parent.$("#dropdown").show();
          parent.showMainPage(url,upUpMenuMame,upMenuMameZh,menuId,menuNameZh);//showMainMavi
        }

    };
    function showHome()
    {
        if(urlParam=="")
        {
            loadTopMenu(upUpMenuIdParam,upMenuIdParam);
            loadSubMenu(upMenuIdParam);
            urlParam="none";
            menuIdParam="";
            upMenuIdParam="";
            upUpMenuIdParam="";
        }
        else
        {
            parent.showHome();
        }
    };

    function loadSubMenus(menuId,upMenuMameZh,menuMameZh,menuID)
    {
        loadTopMenus(menuID,menuId);
        loadSubMenu(menuId);
        parent.showMain("", upMenuMameZh, menuID, menuMameZh);
    }


    function loadTopMenus(menuID,selectId) {
        if (menuID == "") {
            $("#mainDiv").html("");

            return;
        }

        var menuHTML = "";
        var menuClass = "";
        var menuImage = "";
        var menuClick = "";
        ajax({
            url : "Users/subMenu",
            method : "post",
            param : {
                topMenuID : menuID
            },
            onSuccess : function(json, textStatus, jqXHR) {
                var data = json.data.menus;
                $.each(data, function (index, item) {
                    //设置选中一级菜单样式
                    if (item.menuId == selectId) { //选中
                        menuClass = " class=\"top_nav_sel\" ";
                        menuImage = "/image/Menu/nav_" + item.menuId.toLowerCase() + ".png";
                    } else { //未选中
                        menuClass = "";
                        menuImage = "/image/Menu/nav_grey_" + item.menuId.toLowerCase() + ".png";
                    }

                    //限制访问的菜单，不能点击
                    if (item.disabled == 1) { //限制访问
                        menuClick = "";
                    } else {
                        if(parent.topMenuData.language=='zh'){
                            menuClick = " onclick=\"loadSubMenus('" + item.menuId+"', '"+ item.upMenuMameZh+"', '"+ item.menuNameZh+"', '"+menuID+"')\" ";
                        }
                        else
                        {
                            menuClick = " onclick=\"loadSubMenus('" + item.menuId+"', '"+ item.upMenuMameEn+"', '"+ item.menuNameEn+"', '"+menuID+"')\" ";
                        }


                    }
                    if(item.url=="")
                    {
                        if(parent.topMenuData.language=='zh')
                         {
                             menuHTML += "<div " + menuClick + menuClass + " style=\"float: left; "
                             + "                                    text-align: center;"
                             + "                                    margin-top: 40px; "
                             + "                                    width: 79px;"
                             + "                                    padding-top: 89px; "
                             + "                                    cursor: pointer;"
                             + "                                    height: 21px; "
                             + "                                    margin-left: 20px; "
                             + "                                    margin-right: 0px; "
                             + "                                    background:url('" + menuImage + "') top center no-repeat \" >"
                             + "   <div class=\"menu_name\" >" + item.menuNameZh + "</div>"
                             + " </div>";
                         }
                         else
                             {

                             menuHTML += "<div " + menuClick + menuClass + " style=\"float: left; "
                              + "                                    text-align: center;"
                              + "                                    margin-top: 40px; "
                              + "                                    width: 79px;"
                              + "                                    padding-top: 89px; "
                              + "                                    cursor: pointer;"
                              + "                                    height: 21px; "
                              + "                                    margin-left: 20px; "
                              + "                                    margin-right: 0px; "
                              + "                                    background:url('" + menuImage + "') top center no-repeat \" >"
                              + "   <div class=\"menu_name\" >" + item.menuNameEn + "</div>"
                              + " </div>";
                             }

                    }

                });

                $("#mainDiv").html(menuHTML);




               }
            });

    };

    window.loadSubMenu = loadSubMenu;
    window.loadTopMenu=loadTopMenu;
    window.loadTopMenus=loadTopMenus;
    window.loadSubMenus=loadSubMenus;
    window.showHome=showHome;
    window.showsecondMainPage=showsecondMainPage;
});















