$(function () {
    var language;

    //登陆处理
    /*$.ajax({
        url : "/json/Users/login",
        method : "post",
        async:false,
        data : {
            account : 'admin',
            password : '123456',
            language : 'zh'
        },
        success : function(json, textStatus, jqXHR) {

        }
    });*/



    // 获取本系统登录选择的语言
    $.ajax({
        type:'post',
        url:'/json/Users/query',
        cache:false,
        dataType:'json',
        async:false,
        success:function(data){
            language=data.message;
        }
    });

    // 点击退出登录按钮 回到登录页
    $("#exitSys").on("click",function () {
        logout();
    });

    // 设置用户信息
    setCurrentUser();

    // 时间显示
    setCurrentTime();
    setInterval(setCurrentTime, 1000); // 每秒变更一次时间

    // 获取所有菜单
    $.ajax({
        url:'/json/Users/menus',
        dataType:'json',
        method:'post',
        async: false,
        success:function (ret) {
            let data = ret.data;
            let firstMenuName = "";
            let secondMenuName = "";
            let html = "";
            // console.log(data);
            if(data.menus.length !==0){
                // 遍历一级菜单
                $.each(data.menus,function (index,item) {
                    if (language === 'zh') {
                        firstMenuName = item.menuNameZh;
                    } else if (language === 'en') {
                        firstMenuName = item.menuNameEn;
                    }
                    if (item.secondMenus != null && item.secondMenus.length !== 0 && (item.url == null || item.url === '' || item.url === undefined)){
                        // 有二级菜单  并且 url 不存在     子菜单节点处理
                        html += "<li class='treeview'><a href='#'><i class='"+ item.menuIcon +"'></i><span>"+ firstMenuName +"</span><span class='pull-right-container'><i class='fa fa-angle-left pull-right'></i></span></a><ul class='treeview-menu'>";
                        // 遍历二级菜单
                        $.each(item.secondMenus,function (index,it) {
                            if (language === 'zh') {
                                secondMenuName = it.menuNameZh;
                            } else if (language === 'en') {
                                secondMenuName = it.menuNameEn;
                            }
                            html += "<li><a href='#' data-url='"+ it.url +"'>"+ secondMenuName +"</a></li>";
                        });
                        html += "</ul></li>";
                    }else if (item.url != null && item.url !== '' && item.url !== undefined && (item.secondMenus == null || item.secondMenus.length === 0)) {
                        // 没有二级菜单   有url      看板
                        html += "<li class= 'active'><a href='#' data-url='"+ item.url +"'><i class='"+ item.menuIcon +"'></i><span>"+ firstMenuName +"</span></a> </li>";
                    }
                });
                $("#menu-definition").html(html);
            }
        }
    });

    // 登录成功首页右侧显示页面
    let current = $(".sidebar-menu a[data-url]").first();
    let page = current.attr("data-url");

    $("#right_title").html(current.text());

    if(page !== "" && page !== undefined){
        let level = current.parents(".treeview").children(":first").text();
        if (level === "") {
            $(".breadcrumb").html("<li><a href=\"#\"><i class=\"fa fa-map-marker\"></i> <span id=\"level\">" + current.text() + "</span></a></li>");
        } else {
            $(".breadcrumb").html("<li><a href=\"#\"><i class=\"fa fa-map-marker\"></i> <span id=\"level\">" + level + "</span></a></li><li class=\"active\"><span id=\"here\">" + current.text() + "</span></li>");
        }
    }

    // $(".breadcrumb").html("<li><a href=\"#\"><i class=\"fa fa-map-marker\"></i> <span id=\"level\">" + current.text() + "</span></a></li>");

    jump(page)
});


// 退出登陆
function logout() {
    $.ajax({
        url : "/json/Users/logout",
        method : "post",
        success : function(json, textStatus, jqXHR) {
            // 返回登陆页
            window.location.href = "/";

        }
    });
}


// 设置用户信息
function setCurrentUser() {
    $.ajax({
        url : "/json/Users/currentUser",
        method : "post",
        success : function(json, textStatus, jqXHR) {
            // 设置用户名称
            $(".hidden-xs").html(json.data.empName);
            $('#imgName').html(json.data.empName);
            $('#imgRoleName').html(json.data.roleName);
        }
    });
}

// 设置当前时间
function setCurrentTime() {
    $(".navbar-time").html(dateFtt("yyyy-MM-dd hh:mm:ss",new Date()));
}


/**************************************时间格式化处理************************************/
function dateFtt(fmt,date)
{
    var o = {
        "M+" : date.getMonth()+1,                 //月份
        "d+" : date.getDate(),                    //日
        "h+" : date.getHours(),                   //小时
        "m+" : date.getMinutes(),                 //分
        "s+" : date.getSeconds(),                 //秒
        "q+" : Math.floor((date.getMonth()+3)/3), //季度
        "S"  : date.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}