$(function(){

   goodsCombobox();

    $("#import").on("click",function () {
        $("#uploadFile").form('submit');
    });

    $('#orderTable').datagrid({
        url : '/json/orderResourceFile/getAllOrderResourceFile',
        idField : 'id',
        fitColumns : true,
        // rownumbers : false,
        columns:[[
            {
                field : 'ck',
                width : 50,
                checkbox : true,
                align : 'center'
            },
            {
                field:'id',
                title:'主键',
                width:200,
                align:'center',
                hidden: true
            },
            {
                field:'orderName',
                title:'工序名称',
                width:200,
                align:'center'
            },
            {
                field:'fileName',
                title:'文件名',
                width:200,
                align:'center'
            },
            {
                field:'filePath',
                title:'文件路径',
                width:300,
                align:'center'
            }
        ]]
    });

    /* 配置导入框 */
    $("#uploadFile").form({
        url : '/json/orderResourceFile/uploadOrderFile',
        onSubmit: function() {
            let fileName= $('#excel').filebox('getValue');
            //对文件格式进行校验
            let suffix=/\.[^\.]+$/.exec(fileName);
            if (fileName == "") {
                $.messager.alert('工序资料导入', '请选择将要上传的文件!');
                return false;
            }else if(suffix[0] !== ".pdf"){
                $.messager.alert('提示','请选择pdf格式文件！','info');
                return false;
            }else{
                // 正常
                if ($("#searchOrderId").combobox('getValue') != null && $("#searchOrderId").combobox('getValue') != '') {
                    $("#import").linkbutton('disable');
                    return true;
                } else {
                    $.messager.alert('提示', '请选择工序!','info');
                    return false;
                }
            }
        },
        success : function(ret) {
            if (ret == "true") {
                $.messager.alert('提示!', '导入成功','info',
                    function() {
                        $("#import").linkbutton('enable');
                    });
                $('#orderTable').datagrid('reload');
            } else if (ret == "false") {
                $.messager.confirm('提示',"导入失败!");
                $("#import").linkbutton('enable');
            }
        }
    });

});

function goodsCombobox() {
    // 产品下拉列表
    $("#searchGoods").combobox({
        url : '/json/Goods/getGoodsByQ',
        editable : true,
        method : 'get',
        // mode: 'remote',
        valueField : 'goodsId',
        textField : 'goodsName',
        onSelect: function (ret) {
            console.log("ceshi。。。。。。。。。。")
            let goodsId = ret.goodsId;

            // 工序
            $("#searchOrderId").combobox('setValue',null);
            let url = '/json/OrderDetail/getOrderDetailByGoodsId?goodsId=' + goodsId;
            $("#searchOrderId").combobox({
                url : url,
                editable : true,
                method : 'get',
                valueField : 'orderId',
                textField : 'orderName',
                filter: function (q,row) {
                    let opts = $(this).combobox('options');
                    return row[opts.textField].indexOf(q) > -1;
                }
            });
        }
    });
}

// 删除按钮点击事件
function delBtnClick() {

    // 获取选中行数据
    let checkedRows = $("#orderTable").datagrid("getChecked");
    // 选中行检查
    if (checkedRows.length == 0) {
        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("promptNotice_del"), "info");
        return;
    }

    // 获取数据的主键名
    let idField = $("#orderTable").datagrid('options').idField;

    // 获取选中的ID（多选时，逗号分隔）
    let strIDList = [];

    for (let i = 0; i < checkedRows.length; i++) {
        strIDList.push(checkedRows[i][idField]);
    }

    $.messager.confirm(lang.getText("promptNotice_prompt"), lang.getText("promptNotice_delConfirm"), function(r) {
        if (r) {
            $.ajax({
                url : '/json/orderResourceFile/removeOrderResourceFileList',
                data : {'ids' : strIDList},
                type : 'post',
                traditional : true,
                success : function(result) {
                    if (result == "true") {

                        $("#orderTable").datagrid('clearSelections');
                        $("#orderTable").datagrid('clearChecked');

                        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");

                        $("#orderTable").datagrid('reload');

                    } else if (result == "false") {
                        $.messager.prompt(lang.getText("promptNotice_prompt"), lang.getText("base_operateFailed"), "info");
                    }
                }
            })
        }
    });
};
