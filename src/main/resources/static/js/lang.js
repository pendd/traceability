/**
 * 多语言对象
 * 注意：本文件，依赖jQuery。并且要放在jQuery、easyUI的后面引用
 */
var aaa;

var lang = {
    //当前语言（英文:en 中文:zh）
    language : 'zh',

    //中文文本
    zh : {
       //时间
        time_3 : "小时",
        time_5 : "天",
        time_7 : "月",
        time_9 : "年",
        duration_5 : "日",
        duration_6 : "周",
        duration_7 : "月",
        duration_9 : "年",
        base_week_1:"星期一",
        base_week_2:"星期二",
        base_week_3:"星期三",
        base_week_4:"星期四",
        base_week_5:"星期五",
        base_week_6:"星期六",
        base_week_1:"星期日",
        //按钮
        btn_ok : "确定",
        btn_cancel :"取消",
        btn_search :"查询",
        btn_add:"增加",
        btn_edit:"编辑",
        btn_delete:"删除",

      //提示信息
        base_operateSuccessfully:"操作成功",
        base_operateFailed:"操作失败",
        base_operate_1: "包装名称已存在",
        base_operate_2: "名称已存在",
        base_operate_3: "一个人只能管理一条产线",
        base_operate_4: "该班组的排班已存在",
        base_operate_5: "一个产品只能配置一个维修工序",
        base_operate_6: "产品的工序顺序必须从1开始依次增加1",
        base_operate_7: "输入的不是正确的二维码",
        base_compareTime: "开始时间要早于结束时间",
        base_compareTime1: "登出时间要晚于登录时间",

        promptNotice_delConfirm:"操作后将不能再恢复，您确定真的要执行？",
        promptNotice_changeConfirm:"确定更换？如果确定则不能恢复，请仔细核对。",
        promptNotice_prompt:"提示",
        promptNotice_edit:"必须选择一项进行操作！",
        promptNotice_del:"请至少选择一项进行删除！",
        promptNotice_resetPsw:"请至少选择一项进行重置密码",
        promptNotice_del1:"存在不可删除的项,请重新选择。",
        //用户角色
        settings_account:"账户",
        settings_userName:"用户名",
        btn_resetPassword:"重置密码",
        settings_role:"角色",
        settings_email:"邮箱",
        settings_phone:"电话",
        settings_man:"男",
        settings_woman:"女",
        settings_sex:"性别",
        settings_roleName:"角色名称",
        base_unit:"单位",
        settings_memo:"备注",
        page_hello:"你好",
        page_cancel:"注销",
        settings_newPassword:"新密码",
        settings_originalPassword:"原密码",
        page_login:"登录",
        base_common:"加入常用组",
        page_userIn:"登录用户详细",
        page_prompt1:"修改成功！初始密码为123456",
        page_prompt2:"修改密码失败",
        page_prompt3:"原密码不能为空",
        page_prompt4:"只能输入6-20个字母、数字、下划线",
        page_prompt5:"原密码错误",
        page_prompt6:"新密码不能为空",
        page_prompt7:"原密码不能与新密码相同",
        base_checkRole:"无权限查看",
        roles:"分配角色",
        rolesmenu:"分配菜单",
        //菜单
        base_menuName:"菜单名称",
        base_visibleMenu:"可见菜单",
        base_menuNameLanguage:"menuNameZh",
        base_true:"是",
        base_false:"否",
        // 设备
        adjustPlan_planId: "ID",
        adjustPlan_planPart: "维保点",
        adjustPlan_planContent: "维保内容",
        adjustPlan_cycleDuration: "时间间隔",
        adjustPlan_isDelay: "是否延期",


        //登录显示
        base_login:"登录",
        base_warn:"网络异常 ",
        data_name:"资料名称",
        check_data:"查看资料",
        upload_file:"上传",
        bs:"商务支持：13901802096",
        ts:"技术支持：18624039877",
         task_manager:"任务提醒",
        alarm_manager:"报警提醒",
        top_title:"生产线信息化管理系统",
        logOut:"退出登录",
        hello:"您好!",
        back:"返回主导航"

    },

    //英文文本
    en : {
        //词语首字母小写，句子首字母大写

        //时间
        time_3 : "hour",
        time_5 : "day",
        time_7 : "month",
        time_9 : "year",
        duration_5 : "day",
        duration_6 : "week",
        duration_7 : "month",
        duration_9 : "year",
        base_week_1:"Monday",
        base_week_2:"Tuesday",
        base_week_3:"Wednesday",
        base_week_4:"Thursday",
        base_week_5:"FriDay",
        base_week_6:"Saturday",
        base_week_1:"Sunday",
        //按钮

        btn_ok : "OK",
        btn_cancel : "Cancel",
        btn_search : "Search",
        btn_add:"Add",
        btn_edit:"Edit",
        btn_delete:"Delete",
      //提示信息
        base_operateSuccessfully:"Operate successfully",
        base_operateFailed:"Operate failed",
        base_compareTime: "The begin time should before the end time",
        promptNotice_delConfirm:"After deleting, you will not be able to recover again, you are sure you really want to delete ? ",
        promptNotice_changeConfirm:"Confirm the replacement? If confirmed, it can not be restored. Please check it carefully.",
        promptNotice_prompt:"Prompt",
        promptNotice_edit:"You must choose one for operation!",
        promptNotice_del:"Please choose at least one item to delete!",
        promptNotice_resetPsw:"Please choose at least one item to reset password!",
        promptNotice_del1:"There is an item that can not be deleted, please rechoose.",

        //用户角色

        settings_account:"account",
        settings_userName:"user name",
        btn_resetPassword:"reset password",
        settings_role:"role",
        settings_email:"email",
        settings_phone:"phone",
        settings_man:"man",
        settings_woman:"woman",
        settings_sex:"sex",
        settings_roleName:"role name",
        settings_role:"distribute role",
        base_check:"check",
        base_checkPoint:"check point",
        base_checkContent:"check content",
        settings_roleOperate:"Operation permissions",
        settings_roleSearch:"Browse menus",
        settings_memo:"memo",
        settings_password:"password",
        page_hello:"Hello",
        page_cancel:"Cancel",
        settings_newPassword:"New password",
        settings_originalPassword:"Original password",
        page_login:"Login",
        base_common:"add to common group",
        page_prompt1:"The modification is successful! The initial password is 123456",
        page_prompt2:"Modifying the password failed ",
        page_prompt3:"The original password can not be empty",
        page_prompt4:"Only 6-20 letters, numbers, underlines can only be entered ",
        page_prompt5:"Original password error ",
        page_prompt6:"New password can not be empty",
        page_prompt7:"The original password can not be the same as the new password ",
        base_checkRole:"No permission view",
        page_userIn:"user infromation",
        roles:"Assign roles",
        rolesmenu:"Assign menu",

        //菜单
        base_menuName:"menu name",
        base_visibleMenu:"visible menu",
        base_menuNameLanguage:"menuNameEn",
        base_true:"true",
        base_false:"false",

        order_eqpt:"equipment",
        order_number:"order number",
        order_accept:"Accpet",
        order_commit:"Common",
        order_back:"Back",
        order_close:"Close",
        order_state:"state",
        base_unit:"unit",
        // equipment
        adjustPlan_planId: "ID",
        adjustPlan_planPart: "Plan part",
        adjustPlan_planContent: "Plan Content",
        adjustPlan_cycleDuration: "Cycle Duration",
        adjustPlan_isDelay: "Is Delay",

        //登录显示
        base_login:"Login",
        base_warn:"Network anomaly",
        data_name:"datum name",
        check_data:"check datum",
        upload_file:"upload file",
        bs:"Business Support:13901802096",
        ts:"Technical Support:18624039877",
        task_manager:"task manager",
        alarm_manager:"alarm manager",
        top_title:"Production line management system",
        logOut:"Logout",
        hello:"Hello!",
        back:"Back"
       },

    //获取key对应的文本
    //可以使用占位符，比如：lang.getText("key", "repalce1", "repalce2")
    //文本中，占位符使用{1}、{2}的形式
    getText : function (key) {
        //获取文本
        let txt = this.language == 'en' ? this.en[key] : this.zh[key];

        //替换占位符
        if (arguments.length > 1) {
            for(let i = 1; i < arguments.length; i++) {
                txt = txt.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
            }
        }

        return txt;
    },

    isInArray: function (arr,value){
        for(let i = 0; i < arr.length; i++){
            if(value === arr[i].url){
                return true;
            }
        }
        return false;
       },
    //替换页面文本
    //需要替换的文本，需要用<span>标签包裹，并设置lang属性（值为key）。
    //比如：<span lang="eqpt_sts_run">运行中</span>
    replacePageText : function () {
        $("span[lang]").each(function (index, domElement) {
            $(domElement).text(lang.getText(domElement.lang));
        });
    },

    getOperate : function(path){

        $.ajax({
                url : '/json/Users/operation_menu',
                method : "get",
                cache:false,
                async:false,
               success : function(json) {
                        menuso = json.data;

                //let path=window.location.pathname;
                if(!lang.isInArray(menuso,path))
                {
                    $(".easyui-linkbutton").linkbutton({disabled:true});
                }
                else
                {
                    $(".easyui-linkbutton").linkbutton({disabled:false});
                }
             }

            });
    }
};

$(function() {

    $.ajax({
            type:'post',
            url:'/json/Users/query',
            cache:false,
            dataType:'json',
            success:function(data){
                let language="";

                        language=data.message;
                        lang.language=language;
                            lang.replacePageText();





                }
            });





});
//自动替换页面文本
//$(function() {
//    lang.replacePageText();
//});




//加载easyUI语言文件
// if ($.easyui) {
//
//     $.ajax({
//         type:'post',
//         url:'/json/Users/query',
//         cache:false,
//         async:false,
//         dataType:'json',
//         success:function(data){
//             let language="";
//
//
//                     language=data.message;
//                     lang.language=language;
//
//
//
//
//             }
//         });
//     if (lang.language == 'en') {
//         document.writeln("<script type='text/javascript' src='/js/easyui/locale/easyui-lang-en.js'></script>");
//     } else {
//         document.writeln("<script type='text/javascript' src='/js/easyui/locale/easyui-lang-zh_CN.js'></script>");
//     }
// }
