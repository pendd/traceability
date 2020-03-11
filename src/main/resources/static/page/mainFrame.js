var topMenuData = []; // 一级菜单数据(主页显示时赋值)
var currTopMenuIdx = -1; // 当前选择的一级菜单索引
var currTopMenuName = ""; // 当前选择的一级菜单名
var currUpUpMenuName="";
var currSubMenuName = ""; // 当前选择的二级菜单名
var clearFlag = null;

$(function() {
     var language=lang.language;
    // ========================== 组件 ==========================
    $("#logoutBtn").click(logout);
    $("#dropdown").click(slideToggle);

    // 设置用户信息
    setCurrentUser();

    // 时间显示
    setCurrentTime();
    setInterval(setCurrentTime, 1000); // 每秒变更一次时间

    // 显示主页
    showHome();

    // 页面尺寸变更
    window.onresize = function() {
        pagesizechange();
    };
    window.onload = function() {
        pagesizechange();
    };

    // ========================== 方法 ==========================
    // 退出登陆
    function logout() {
        ajax({
            url : "Users/logout",
            method : "post",
            onSuccess : function(json, textStatus, jqXHR) {
                // 返回登陆页
                window.location.href = "/";
            }
        });
    }
    ;

    // 设置用户信息
    function setCurrentUser() {
        ajax({
            url : "Users/currentUser",
            method : "post",
            onSuccess : function(json, textStatus, jqXHR) {
                // 设置用户名称
                $("#logName").text(json.data.empName);
            }
        });
    }
    ;

    // 设置当前时间
    function setCurrentTime() {
        $("#currentTime").html(new Date().format("yyyy-MM-dd hh:mm:ss"));
    }
    ;

    // 菜单显示切换
    function slideToggle() {
        if ($("#NaviPage").is(":hidden")) {
            // 显示当前位置
            if(language=="zh"){

                if (currUpUpMenuName) {
                     setLocation("当前位置：" + currUpUpMenuName+" > "+currTopMenuName );
                 } else {
                     setLocation("当前位置："+currTopMenuName);
                 }
            }
            else
            {
                if (currUpUpMenuName) {
                     setLocation("Location：" + currUpUpMenuName+" > "+currTopMenuName );
                 } else {
                     setLocation("Location："+currTopMenuName );
                 }
            }

            // 显示菜单页面
            $("#NaviPage").slideDown("slow");
            $("#dropdown").hide();
        }

        else {
//            // 显示当前位置
//            if(language=="zh"){
//                if (currSubMenuName) {
//                     setLocation("当前位置：" + currTopMenuName + " > " + currSubMenuName);
//                 } else {
//                     setLocation("当前位置：");
//                 }
//            }
//            else
//            {
//                if (currSubMenuName) {
//                     setLocation("Location：" + currTopMenuName + " > " + currSubMenuName);
//                 } else {
//                     setLocation("Location：");
//                 }
//            }
//

            // 隐藏菜单页面
            $("#NaviPage").slideUp("slow");

        }
    }
    ;

    // 页面尺寸变更
    function pagesizechange() {
        $("#NaviPage").css("height", $(document.body).height() - 90);
        $("#MainPage").css("height", $(document.body).height() - 100);
    }
    ;

    // 清空保存的数据
    function clearCurrData() {
        topMenuData = [];
        currTopMenuIdx = -1;
        currTopMenuName = "";
        currSubMenuName = "";
        currUpUpMenuName="";

        $("#MainPage").attr("src", "");
    }
    ;

    // 显示主页（主页只显示一级菜单）
    function showHome() {
        if(language=="zh")
        {
            setLocation("当前位置：主页");
        }
        else
        {
            setLocation("Location：main");
        }


        // 清空保存的数据
        clearCurrData();

        $("#NaviPage").attr("src", "/page/home.html");
        $("#NaviPage").slideDown("slow");

        // 隐藏菜单图标
        $("#dropdown").hide();
    }
    ;

    // 显示系统导航页（系统导航页显示一级菜单、二级菜单）
    function showMainMavi(index) {
        currTopMenuIdx = index;
        if(language=="zh")
        {
            setLocation("当前位置：系统导航");
        }
        else
        {
            setLocation("Location：navigation system");
        }

        $("#NaviPage").attr("src", "/page/mainNav.html");
        $("#NaviPage").slideDown("slow");

        // 显示菜单图标

        //$("#dropdown").show();
    }
    ;

    function showNaviPage(url, topMenuName, subMenuId, subMenuName)
    {
        currTopMenuName = topMenuName;
        currSubMenuName = subMenuName;
        if(language=="zh"){
            setLocation("当前位置：" + topMenuName + " > " + subMenuName);
        }
        else
        {
            setLocation("Location：" + topMenuName + " > " + subMenuName);
        }

    }



    // 显示主体页
    function showMainPage(url, upUpMenuMame,topMenuName, subMenuId, subMenuName) {
        //$("#dropdown").hide();
        if(upUpMenuMame!="")
        {
            if(language=="zh"){
                setLocation("当前位置：" + upUpMenuMame+" > "+topMenuName + " > " + subMenuName);
            }
            else
            {
                setLocation("Location：" +upUpMenuMame+" > "+ topMenuName + " > " + subMenuName);
            }
        }
        else
        {
            if(language=="zh"){
                    setLocation("当前位置：" + topMenuName + " > " + subMenuName);
                }
                else
                {
                    setLocation("Location：" + topMenuName + " > " + subMenuName);
                }

        }
        currUpUpMenuName=upUpMenuMame
        currTopMenuName = topMenuName;
        currSubMenuName = subMenuName;



        // 显示主体页
        if (url.indexOf("?") > -1) {
            //URL中已经有参数
            $("#MainPage").attr("src", url + "&menuID=" + subMenuId);
        } else {
            //URL中没有参数
            $("#MainPage").attr("src", url + "?menuID=" + subMenuId);
        }


        // 隐藏菜单
        $("#NaviPage").slideUp("slow");
    }
    ;

    // 显示主体页
    function showMain(url, topMenuName, subMenuId, subMenuName) {
        currTopMenuName = topMenuName;
        currSubMenuName = subMenuName;
        if(language=="zh"){
            setLocation("当前位置：" + topMenuName + " > " + subMenuName);
        }
        else
        {
            setLocation("Location：" + topMenuName + " > " + subMenuName);
        }

  }
    ;

    // 设置导航地址文本
    function setLocation(text) {
        $("#pagenav").html(text);
    }
    ;

    // ========================== 暴露给其他页面的方法 ==========================
    window.showHome = showHome;
    window.showMainMavi = showMainMavi;
    window.showMainPage = showMainPage;
    window.showMain=showMain;

    if (clearFlag != null) {
        clearInterval(clearFlag);
    }
    Message();
   //Message();
//    setInterval("Message()", 60 * 1000);

});

function Message() {
   // setTimeout("Message()", 60000);
    ajax({
        url : 'equipment/repairOrder/messageReminder',
        onSuccess : function(res) {
            $("#reminderValue").text(res.length);
            $("#reminderValue").val(res.length);

            if (frequency == 1) {
                start();
                frequency++;
            }

        }
    })

}
var frequency = 1;
var flag = 0;

function start() {
    var rows = $("#reminderValue").val();
    if(rows!=0){
        if (!flag) {
            $("#reminderValue").css('color', '#3a4b6b');
            flag = 1;
        } else {
            $("#reminderValue").css('color', 'red');
            flag = 0;
        }
    }else{
        $("#reminderValue").css('color', '#red');
        flag = 0;
    }

    setTimeout("start()", 500);


}

// 消息提醒
function reminderValues() {
    $('#reminderList').datagrid({
        url : '/json/equipment/repairOrder/messageReminder',
        method : 'get',
        pagination : false,
        pageSize : 10,
        singleSelect : false,
        selectOnCheck : true,
        checkOnSelect : true,
        fit : true,
        columns : [ [ {
            field : 'category',
            title : '分类',
            width : 200,
            align : 'center'
        }, {
            field : 'values',
            title : '内容',
            width : 600,
            align : 'center'
        } ] ],
        onLoadSuccess : function(res) {
            console.log(res);
        }
    });
}

function reminderValue() {
    reminderValues();
    $('#reminder').dialog({
        title : '任务提醒',
        width : 858,
        height : 600,
        closed : false,
        cache : false,
        modal : true
    });
}


//function Message() {
//    setTimeout("Message()", 60000);
//    ajax({
//        url : 'alarm/warning/search',
//        onSuccess : function(res) {
//            $("#reminderValue").text(res.length);
//            $("#reminderValue").val(res.length);
//
//            if (frequency == 1) {
//                start();
//                frequency++;
//            }
//
//        }
//    });
//
//
//}
//function Messages() {
//    //setTimeout("Messages()", 60000);
//    ajax({
//        url : 'equipment/repairOrder/messageReminder',
//        onSuccess : function(res) {
//            $("#reminder").text(res.length);
//            $("#reminder").val(res.length);
//
//            if (frequencys == 1) {
//                starts();
//                frequencys++;
//            }
//
//        }
//    })
//
//
//}
//
//var frequency = 1;
//var flag = 0;
//var frequencys = 1;
//var flags = 0;
//function start() {
//    //var rows = $("#reminderValue").val();
//    var rows = 3;
//    if(rows!=0){
//        if (!flag) {
//            $("#reminderValue").css('color', '#3a4b6b');
//            flag = 1;
//        } else {
//            $("#reminderValue").css('color', 'red');
//            flag = 0;
//        }
//    }else{
//        $("#reminderValue").css('color', '#red');
//        flag = 0;
//    }
//
//    setTimeout("start()", 500);
//
//
//}
//function starts() {
//    //var rows = $("#reminderValue").val();
//    var rowss = 3;
//    if(rowss!=0){
//        if (!flags) {
//             $("#reminder").css('color', '#3a4b6b');
//            flags = 1;
//        } else {
//            $("#reminder").css('color', 'red');
//            flags = 0;
//        }
//    }else{
//        $("#reminder").css('color', '#red');
//        flags = 0;
//    }
//
//   // setTimeout("starts()", 500);
//
//
//}
//
//
//function reminders() {
//    $('#reminderList').datagrid({
//        url : '/json/equipment/repairOrder/messageReminder',
//        method : 'get',
//        pagination : false,
//        pageSize : 10,
//        singleSelect : false,
//        selectOnCheck : true,
//        checkOnSelect : true,
//        fit : true,
//        columns : [ [ {
//            field : 'category',
//            title : '分类',
//            width : 200,
//            align : 'center'
//        }, {
//            field : 'values',
//            title : '内容',
//            width : 600,
//            align : 'center'
//        } ] ],
//        onLoadSuccess : function(res) {
//            console.log(res);
//        }
//    });
//}
//
//
//
//
//
//
//// 消息提醒
//function reminderValues() {
//    $('#reminderList').datagrid({
//        url : '/json/alarm/warning/search',
//        method : 'get',
//        pagination : false,
//        pageSize : 10,
//        singleSelect : false,
//        selectOnCheck : true,
//        checkOnSelect : true,
//        fit : true,
//        columns : [ [ {
//            field : 'alarm_id',
//            title : '报警编号',
//            width : 100,
//            align : 'center'
//        }, {
//            field : 'alarm_name',
//            title : '报警名称',
//            width : 300,
//            align : 'center'
//        },{
//            field : 'begin_time',
//            title : '开始时间',
//            width : 200,
//            align : 'center',
//            formatter:function(value,row,index){
//
//                var a=formatDatebox(value);
//                return a;
//            }
//        },{
//            field : 'type',
//            title : '设备名称',
//            width : 200,
//            align : 'center'
//        }
//
//        ] ],
//        onLoadSuccess : function(res) {
//            console.log(res);
//        }
//    });
//}
//
//function reminder() {
//    reminders();
//    $('#reminder').dialog({
//        title : '任务提醒',
//        width : 858,
//        height : 600,
//        closed : false,
//        cache : false,
//        modal : true
//    });
//}
//
//function reminderValue() {
//    reminderValues();
//    $('#reminder').dialog({
//        title : '报警提醒',
//        width : 858,
//        height : 600,
//        closed : false,
//        cache : false,
//        modal : true
//    });
//}




function formatDatebox(value) {
    if (value == null || value == '') {
                return '';
            }
            var dt;
    if (value instanceof Date) {
                dt = value;
        } else {
                dt = new Date(value);
            }

            return dt.format("yyyy-MM-dd hh:mm:ss"); //扩展的Date的format方法(上述插件实现)
        }

    function formatDate(value) {
        if (value == null || value == '') {
                    return '';
                }
                var dt;
        if (value instanceof Date) {
                    dt = value;
            } else {
                    dt = new Date(value);
                }

            return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)

            }