/*解析页面参数
 *使用方法举例：var strProcessID = getUrlParam('iProcessID');
 *name即参数名
 */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); // 匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; // 返回参数值
}

function randomColor() {

    var r = Math.floor(Math.random() * 256);

    var g = Math.floor(Math.random() * 256);

    var b = Math.floor(Math.random() * 256);

    return "rgb(" + r + "," + g + "," + b + ")";

}

/*
 * 格式化日期 使用示例：var str = new Date().format("yyyy-MM-dd hh:mm:ss");
 */
Date.prototype.format = function(fmt) {
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };

    // 替换年部分
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    // 替换年以外部分
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }

    return fmt;
}

/*
 * 扩展combox验证，easyui原始只验证select text的值，不支持value验证zhongbs 2016-07-13
 * selectValueRequired ：使用方法 在控件的html 标签上增加
 * validType="selectValueRequired['控件id']" 若要自定义显示的错误消息在
 * 控件的data-options="invalidMessage:验证失败消息提示内容"
 */
$.extend($.fn.validatebox.defaults.rules, {
    selectValueRequired: { // 验证在下拉列表框中输入的值是否在下拉列表选项中
        validator: function(value, param) {
            // console.info(msg);
            if ($("#" + param).combobox('getValue') == $("#" + param).combobox('getText')) {
                return false;
            } else return true;

        },
        message: "请输入或者选择列表中存在的值"
    },
    // 整数数字
    Number: {
        validator: function(value) {
            var reg = /^[0-9]*$/;
            return reg.test(value);
        },
        message: '该输入项只能输入整数.'
    },
    // 整数或小数数字
    Float: {
        validator: function(value) {
            var reg = /^[0-9]+(\.[0-9]+)?$/;
            return reg.test(value);
        },
        message: '该输入项只能输入整数或小数.'
    }
});

/* 加载带CheckBoxes的下拉列表框 */
function CheckBoxComboboxData(obj, url) {
    obj.combobox({
        url: url,
        method: "get",
        valueField: 'id',
        textField: 'text',
        panelHeight: 'auto',
        multiple: true,
        formatter: function(row) {
            var opts = $(this).combobox('options');
            return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField]
        },
        onLoadSuccess: function() {
            var opts = $(this).combobox('options');
            var target = this;
            var values = $(target).combobox('getValues');
            $.map(values, function(value) {
                var el = opts.finder.getEl(target, value);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            })
        },
        onSelect: function(row) {
            var opts = $(this).combobox('options');
            var el = opts.finder.getEl(this, row[opts.valueField]);
            el.find('input.combobox-checkbox')._propAttr('checked', true);
        },
        onUnselect: function(row) {
            var opts = $(this).combobox('options');
            var el = opts.finder.getEl(this, row[opts.valueField]);
            el.find('input.combobox-checkbox')._propAttr('checked', false);
        }
    });
}

// datagrid无数据的json
var gridNoDataJson = JSON.parse('{"total":0,"rows":[]}');

// grid合并单元格 要在加载 grid的 onLoadSuccess: 事件中调用
$.extend($.fn.datagrid.methods, {
    autoMergeCells: function(jq, fields) {
        return jq.each(function() {
            var target = $(this);
            if (!fields) {
                fields = target.datagrid("getColumnFields");
            }
            var rows = target.datagrid("getRows");
            var i = 0,
                j = 0,
                temp = {};
            for (i; i < rows.length; i++) {
                var row = rows[i];
                j = 0;
                for (j; j < fields.length; j++) {
                    var field = fields[j];
                    var tf = temp[field];
                    if (!tf) {
                        tf = temp[field] = {};
                        tf[row[field]] = [i];
                    } else {
                        var tfv = tf[row[field]];
                        if (tfv) {
                            tfv.push(i);
                        } else {
                            tfv = tf[row[field]] = [i];
                        }
                    }
                }
            }
            $.each(temp, function(field, colunm) {
                $.each(colunm, function() {
                    var group = this;

                    if (group.length > 1) {
                        var before,
                            after,
                            megerIndex = group[0];
                        for (var i = 0; i < group.length; i++) {
                            before = group[i];
                            after = group[i + 1];
                            if (after && (after - before) == 1) {
                                continue;
                            }
                            var rowspan = before - megerIndex + 1;
                            if (rowspan > 1) {
                                target.datagrid('mergeCells', {
                                    index: megerIndex,
                                    field: field,
                                    rowspan: rowspan
                                });
                            }
                            if (after && (after - before) != 1) {
                                megerIndex = after;
                            }
                        }
                    }
                });
            });
        });
    }
});


/**
 *
 * Datagrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 *
 * 简单实现，如需高级功能，可以自由修改
 *
 * 使用说明:
 *
 * 在easyui.min.js之后导入本js
 *
 * 代码案例: 方法要在onLoadSuccess事件中填写
 *
 * $("#dg").datagrid({....}).datagrid('tooltip'); 所有列
 *
 * $("#dg").datagrid({....}).datagrid('tooltip',['productid','listprice']); 指定列
 *
 * @author ____′↘夏悸
 *
 */

$.extend($.fn.datagrid.methods, {

    tooltip: function(jq, fields) {

        return jq.each(function() {

            var panel = $(this).datagrid('getPanel');

            if (fields && typeof fields == 'object' && fields.sort) {

                $.each(fields, function() {

                    var field = this;

                    bindEvent($('.datagrid-body td[field=' + field + '] .datagrid-cell', panel));

                });

            } else {
                bindEvent($(".datagrid-body .datagrid-cell", panel));

            }

        });



        function bindEvent(jqs) {

            jqs.mouseover(function() {

                var content = '<pre>' + $(this).text() + '</pre>';

                $(this).tooltip({

                    content: content,
                    trackMouse: true,
                    onHide: function() {
                        $(this).tooltip('destroy');
                    },
                    onShow: function() {
                        $(this).tooltip('tip').css({
                            backgroundColor: '#FFFFE1'
                        })
                    }

                }).tooltip('show');

            });

        }

    }

});

/**
 * <pre>
 * 扩展easyUI的form组件, 添加3个方法:
 * 1. toObject(toMergeObj):
 *    把form中的所有字段组合成一个对象,参数toMergeObj是与结果合并的对象,可选,toMergeObj中的值会覆盖form中的值.
 *    使用方法:
 *        比如有id为formId的表单,包含文本框orderNo值为123和lotNo值为098
 *        var obj = $('formId').form('toObject');
 *        会得到 {orderNo:&quot;123&quot;,lotNo:&quot;098&quot;}
 *        另一个情况,我想把lotNo的值改为98,并且添加一个字段name值为abc
 *        var obj = $('formId').form('toObject',{lotNo:&quot;98&quot;,name:&quot;abc&quot;});
 *        会得到 {orderNo:&quot;123&quot;,lotNo:&quot;98&quot;,name:&quot;abc&quot;}
 * 2. toJson(toMergeObj):
 *    与toObject一样,只不过最后会得到一个json字符串
 * 3. getValue:
 *    获取表单中指定字段名称的值. 使用方法: var orderNo = $('formId').form('getValue','orderNo');
 * </pre>
 */
$.extend($.fn.form.methods, {
    toObject: function(jq,toMergeObj) {
        var arrayValue = $(jq[0]).serializeArray();
        var obj = {};
        $.each(arrayValue, function() {
            var item = this;
            if (obj[item["name"]]) {
                obj[item["name"]] = obj[item["name"]] + "," + item["value"];
            } else {
                obj[item["name"]] = item["value"];
            }
        });
        if(toMergeObj){
            $.extend(obj, toMergeObj);
        }
        return obj;
    },
    toJson: function(jq, toMergeObj) {
        var obj = $(jq[0]).form("toObject",toMergeObj);
        return JSON.stringify(obj);
    },
    getValue: function(jq, name) {
        var jsonValue = $(jq[0]).form("toObject");
        return jsonValue[name];
    }
    // setValue: function(jq, name, value) {
    //     return jq.each(function() {
    //         // _b(this, _29);
    //         var data = {};
    //         data[name] = value;
    //         $(this).form("load", data);
    //     });
    // }
});

$.ajaxSetup({cache:false});
$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {

    var originalError = options.error;
    options.error = function(xhr, textStatus, errorThrown){
        function toLoginPage(){
            var from = encodeURIComponent(self.location.pathname);
            top.location = '/page/login.html?from='+ from;
        }
        if(xhr.responseJSON){
            var code = xhr.responseJSON.code;
            var message = xhr.responseJSON.message;
            if(code == 401){ // 未登陆或者cookie超时
                $.messager.alert("提示", message, "info",toLoginPage);

                return;
            }

            originalError(xhr, textStatus, errorThrown);
        }else{

            originalError(xhr, textStatus, errorThrown);
        }
    }
});


