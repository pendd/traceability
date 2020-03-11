
$(function () {
    intiMonthBox("dataId");
    // grid初始化
    $("#dg").datagrid({
        url : "/json/empCheck/getAssembling",
        idField : "id",
        method : 'get',
        remoteSort:false,
        fitColumns : true,
        pagination: true,
        singleSelect:true,
        rownumbers:true, //显示行号
        columns:[[
            {
                field:'id',
                width:200,
                align:'center',
                title:'主键',
                hidden:true
            },
            {
                field:'empName',
                width:200,
                align:'center',
                title:'用户名'
            },
            {
                field:'attitude',
                width:200,
                align:'center',
                title:'工作态度/20分',
                editor:'text'
            },
            {
                field:'skill',
                width:200,
                align:'center',
                title:'工作技能/30分',
                editor:'text'
            },
            {
                field:'achievements',
                title:'工作绩效/5分',
                align:'center',
                width:200,
                editor:'text'
            },
            {
                field:'cooperation',
                title:'协同合作/5分',
                align:'center',
                width:200,
                editor:'text'
            },
            {
                field:'responsibility',
                title:'责任心/5分',
                align:'center',
                width:200,
                editor:'text'
            },
            {
                field:'character',
                title:'品德言行/5分',
                align:'center',
                width:200,
                editor:'text'
            },
            {
                field:'quality',
                title:'质量控制/20分',
                align:'center',
                width:200,
                editor:'text'
            },
            {
                field:'safeProduction',
                title:'安全生产/10分',
                align:'center',
                width:200,
                editor:'text'
            },
            {
                field:'manage',
                title:'管理能力/20分',
                align:'center',
                width:200,
                editor:'text'
            },
            {
                field:'lead',
                title:'领导能力/20分',
                align:'center',
                width:200,
                editor:'text'
            },
            {
                field:'result',
                title:'最终效果/60分',
                align:'center',
                width:200,
                editor:'text'
            },
            {
                field:'score',
                title:'得分汇总',
                align:'center',
                width:200,
                editor:'text'
            },
            {
                field:'assign',
                align:'center',
                width:200,
                formatter:formatterEdit
            }
        ]]
    });
    // 重新加载easyui样式
    // $.parser.parser($('#dulBtn').parent());
});


let intiMonthBox = function(id){
    let db = $('#'+id);
    db.datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); //触发click事件弹出月份层
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                        , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    db.datebox('hidePanel')//隐藏日期对象
                        .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0);
            yearIpt.unbind();//解绑年份输入框中任何事件
        },
        parser: function (s) {
            if (!s) return new Date();
            let arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function (d) {
            //解决月份小于10时，未加'0'问题
            let yearstr = d.getFullYear();
            let month = d.getMonth() + 1;
            let monthstr = month<10? "0"+ month : month;
            return yearstr+ '-'+ monthstr;
            // return d.getFullYear() + '-' + (d.getMonth() + 1);
        }
    });
    let p = db.datebox('panel'), //日期选择对象
    tds = false, //日期选择对象中月份
    yearIpt = p.find('input.calendar-menu-year'),//年份输入框
    span = p.find('span.calendar-text'); //显示月份层的触发控件

    let currTime=new Date();
    let strDate=currTime.getFullYear()+"-"+(currTime.getMonth())+"-01";

    //设置前当月
    db.datebox("setValue",strDate);
};

// 搜索按钮事件
function searchData() {
    let dataId = $("#dataId").datebox('getValue');
    $("#dg").datagrid('load', {
        dataId: dataId
    });
}

//编辑按钮点击事件
function editBtnClick(id) {
    // 清空表单
    $("#fm").form("clear");
    $("#dlg").removeAttr("disabled"); //解除Dialog禁用
    $("#dlg-buttons").show(); //显示按钮

    //获取当前页的数据
    let rowData = $("#dg").datagrid('getRows');

    let currentData;

    for (let i = 0; i < rowData.length; i++) {
        if (rowData[i].id == id) {
            currentData = rowData[i];
            break;
        }
    }

    $('#fm').form('load', {
        id: currentData.id,
        empName: currentData.empName,
        attitude: currentData.attitude,
        skill: currentData.skill,
        achievements: currentData.achievements,
        cooperation: currentData.cooperation,
        responsibility: currentData.responsibility,
        character: currentData.character,
        quality: currentData.quality,
        safeProduction: currentData.safeProduction,
        manage: currentData.manage,
        lead: currentData.lead,
        result: currentData.result
    });

    //显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', lang.getText("btn_edit"));
}

//编辑记录提交
function editRecord() {

    $('#fm').form('submit', {
        url: "/json/empCheck/modifyAssembling",//后台请求路径
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            if (result === "true") {
                $('#dlg').dialog('close');
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#dg').datagrid('reload');
            } else if (result === "false") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
            }
        }
    });
}

// Dialog取消按钮点击事件
function dlgCancelBtnClick() {
    $('#dlg').dialog('close');
}

function formatterEdit(val,row) {
    return "<a href='javascript:void(0)' id='dulBtn' class='easyui-linkbutton' data-options=\"iconCls:'icon-save'\" onclick='editBtnClick(\""+row.id+"\")'>编辑</a>";
}


