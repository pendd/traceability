$(function() {
    // ========================== 组件 ==========================
    document.title = lang.language=="zh"?lang.zh.base_login:lang.en.base_login;
    $("#loginTit").text("生产线信息化管理系统");
    $("#txtUserName").prop('placeholder','账号');
    $("#txtPassword").prop('placeholder','密码');
    $("#page_login_id").text("登陆");
    $("#languageChange").combobox({

        onSelect:function(data){
            if(data.value=="zh")
            {
                    document.title = lang.zh.base_login;
                    $("#loginTit").text("生产线信息化管理系统");
                    $("#txtUserName").prop('placeholder','账号');
                    $("#txtPassword").prop('placeholder','密码');
                    $("#page_login_id").text("登陆");
            }
            else
                {

                    document.title = lang.en.base_login;
                    $("#loginTit").text("Production line management system");
                    $("#txtUserName").prop('placeholder','account');
                    $("#txtPassword").prop('placeholder','password');
                    $("#page_login_id").text('login');
                }

        }
    })





//	    $('#fm').form('submit', {
//	        url: "/json/Users/login",//后台请求路径
//	        onSubmit: function (param) {
//	        	param.account : username;
//	        	param.password : userPwd;
    //
//	            return $(this).form('validate');
//	        },
//	        success: function (result) {
//	            if (result == "true") {
//	               $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
//	               $("#faultTable").datagrid('reload');
//	               } else if (result == "false") {
//	               $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
//	                  return;
//	               }
//	        }
//	    });


    // ========================== 方法 ==========================

    //window.login = login;
});

function login() {
    //错误消息框隐藏
    $("#logintip").css("visibility", "hidden");

    //获取输入
    var userName = $("#txtUserName").val();
    var userPwd = $("#txtPassword").val();
    var language = $('#languageChange').combobox('getValue');
    console.log(language)

    //输入检查
    if (userName == "" || userPwd == "") {
        //显示错误
        $("#logintip").text(FailedNotice.logincheck);
        $("#logintip").css("visibility", "visible");

        return;
    }
    //登陆处理
    $.ajax({
            url : "/json/Users/login",
            method : "post",
            data : {
                account : userName,
                password : userPwd,
                language : language
            },
            success : function(json, textStatus, jqXHR) {
                //登陆成功，显示主界面
                window.location.href = "/page/starter.html";
            },
            error : function(xhr, textStatus, errorThrown) {
                //获取错误消息
                var err = $.parseJSON(xhr.responseText);

                //显示错误
                $("#logintip").text(err.message);
                $("#logintip").css("visibility", "visible");
            }
        });
    };
//回车登陆操作
document.onkeydown = function(event) {
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode == 13) {
        //错误消息框隐藏
        login();
    }
};


//登陆操作


