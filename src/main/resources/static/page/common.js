$(function() {
    // ========================== 组件 ==========================

    function logins(value) {
        if(value=="cp")
        {
            $.ajax({
                url : "/json/Common/Users/login?interfaceUrl1='http://localhost:9199/'",
                method : "post",
                success : function(json, textStatus, jqXHR) {
                    //登陆成功，显示主界面
                    window.location.href = "/page/frame.html";
                },
                error : function(xhr, textStatus, errorThrown) {

                }
            });
        }
        //登陆处理

    };
    // ========================== 方法 ==========================

    window.login = login;
})





//登陆操作
