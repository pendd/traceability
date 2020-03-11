$(function () {
    // ========================== 组件 ==========================
    //显示一级菜单
    var a=lang.language;
    showTopMenu();

    //页面尺寸变更
    window.onresize = function () {
        pagesizechange();
    };
    window.onload = function () {
        pagesizechange();
    };

    // ========================== 方法 ==========================
    /**
     * 显示一级菜单
     */
    function showTopMenu() {
        ajax({
            url : "Users/topMenu",
            method : "post",
            onSuccess : function(json, textStatus, jqXHR) {
                var data = json.data.menus;

                //没有菜单，显示空白
                if (data.length == 0) {
                    $("#mainDiv").html("");
                    return;
                }

                //将一级菜单数据存储在父页面
                parent.topMenuData = json.data;

                //制作菜单html
                var menuHTML = "";
                var menuStyle = "";
                var menuClick = "";
                $.each(data, function (index, item) {
                    if (item.disabled == 1) { //限制访问菜单
                        menuStyle = " style=\"background-color:#f2f2f2;\" "; //图标变为灰色
                        menuClick = ""; //不可点击
                    } else {
                        menuStyle = "";
                        menuClick = " onclick=\"parent.showMainMavi(" + index + ")\" ";
                    }
                    if(json.data.language=='zh')
                    {
                        menuHTML += "<div class=\"nav_ico_block\" " + menuStyle + menuClick + " >"
                        + "   <div class=\"nav_ico_border\" style=\"background:#f4b53c url('/image/Menu/nav_grey_" + item.menuId.toLowerCase() + ".png') no-repeat center 15px;\" ></div>"
                        + "   <div class=\"nav_ico_tit\" >" + item.menuNameZh + "</div>"
                        + " </div>";
                    }
                    else
                    {
                        menuHTML += "<div class=\"nav_ico_block\" " + menuStyle + menuClick + " >"
                        + "   <div class=\"nav_ico_border\" style=\"background:#f4b53c url('/image/Menu/nav_grey_" + item.menuId.toLowerCase() + ".png') no-repeat center 15px;\" ></div>"
                        + "   <div class=\"nav_ico_tit\" >" + item.menuNameEn + "</div>"
                        + " </div>";
                    }

                });

                $("#mainDiv").html(menuHTML);
            }
        });
    };

    //页面尺寸变更
    function pagesizechange() {
        $("#main_div_area").css("padding-bottom", ($(document.body).height() -440)/2 + 50);
        $("#main_div_area").css("padding-top", ($(document.body).height() - 440)/2 - 50);
    };

});


