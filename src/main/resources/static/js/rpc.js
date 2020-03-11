/**
 *
 * @param {object}
 *            option 结构为:
 *
 * <pre>
 * {
 *    url:&quot;dashboard/kpi/getkpi&quot;, // 注意,不包括前面的 &quot;/json/&quot; 部分
 *    method: "GET",  //请求方法,默认GET
 *     param:&quot;&quot;, //可选.既可以包含一个查询字符串，比如 key1=value1&amp;key2=value2 ，
 *               //也可以是一个映射，比如 {key1: 'value1', key2: 'value2'} 。
 *               //如果使用了后者的形式，则数据再发送器会被转换成查询字符串。
 *     onSuccess: function (result, textStatus, jqXHR) {
 *         // 成功时的回调方法. 参数data是服务器返回的数据,只有这个是我们使用的,其他的可以不管.
 *         // data的结构为:
 *         {
 *             code: 0,
 *             message:&quot;ok&quot;,
 *             data:这里就是你的业务相关的核心数据了
 *         }
 *         所以你需要在此方法中,调用 result.data 获得你的数据.
 *     },
 *     onError: function (result, textStatus, jqXHR) {
 *        // 失败时的回调方法. 参数data是服务器返回的数据
 *
 *     }
 * }
 * </pre>
 *
 * @returns
 */
function ajax(option,isAsync) {
    if(isAsync==null||isAsync=="undefined"){
        isAsync=true;
    }
    var url = '/json/' + option.url;
    var method = option.method || "GET";
    var param = option.param;
    var onSuccess = option.onSuccess;
    var onError = option.onError;
    var contentType =option.contentType || "application/x-www-form-urlencoded"

    $.ajax({
        type : method,
        url : url,
        data : param,
        dataType : "json",
        contentType : contentType,
        cache:false,
        async:isAsync,
        success : function(result, textStatus, jqXHR) {
            //console.debug('result: %o', result);
            // console.info(result);
            //console.debug('textStatus: %o', textStatus);
            // console.info(textStatus);
            //console.debug('jqXHR: %o', jqXHR);
            // console.info(jqXHR);

            if (onSuccess) {
                onSuccess(result, textStatus, jqXHR);
            }
        },
        error : function(xhr, textStatus, errorThrown) {
            // 通常 textStatus 和 errorThrown 之中只有一个会包含信息
            // this; // 调用本次AJAX请求时传递的options参数
            //console.error('xhr: %o', xhr);
            // console.error(xhr);
            //console.error('textStatus: %o', textStatus);
            // console.error(textStatus);
            //console.error('errorThrown: %o', errorThrown);
            //console.error(errorThrown);
            if (onError) {
                onError(xhr, textStatus, errorThrown);
            }
        }
    });
}

/**
 * @description 公共ajax方法. 注意不要再任何地方再声明变量ajax了,以免产生冲突.</br>
 *              所有的人都应该使用这个方法获取请求的url.
 * @param {String}
 *            url 你的url,注意,不包括 /json/ 部分
 *
 * @returns {String} 会在你传递的url前面加上 /json/
 */
function getUrl(url) {
    return '/json/' + url;
}

if (window.onSuccess) {
    alert("onSuccess 变量或者方法已经存在！！！！！");
}

/**
 * 公共成功callback方法，适用于datagrid 增删改查。
 *
 * @param result
 *            服务器返回的结果数据
 * @param dialogId
 *            对话框组件的id，默认 '#dlg'
 * @param datagrId
 *            datagrid组件id 默认 "#tbl"
 * @param strType
 *            remove  删除   add 添加  edit  编辑
 */
function onResult(result, dialogId, datagrId, strType) {
    // var dlgId = '#dlg';
    //  var dgId = "#tbl";
    //  if (dialogId) {
    //  dlgId = dialogId;
    // }
    //  if (datagrId) {
    // dgId = datagrId;
    //  }
    if (strType == "add") {
        console.log(result);
        //result = "true";
        if (result == "true") {
            $(dialogId).dialog('close');

            $.messager.alert("提示", SuccessNotice.add, "info");
            $(datagrId).datagrid('reload');
        } else if (result == "2") {
            $.messager.alert("提示", "添加失败，记录已存在", "info");
            return;
        } else if (result == "false") {
            $.messager.alert("提示", FailedNotice.add, "info");
        }
    } else if (strType == "edit") {

        if (result == "true") {
            $(dialogId).dialog('close');

            $.messager.alert("提示", SuccessNotice.edit, "info");
            $(datagrId).datagrid('reload');
        } else if (result == "3") {
            $.messager.alert("提示", "编辑失败，记录已经存在", "info");
            return;
        } else if (result == "false") {
            $.messager.alert("提示", FailedNotice.edit, "info");
            return;
        }

    } else if (strType == "remove") {

        if (result == "true") {
            $(dialogId).dialog('close');

            $(datagrId).datagrid('clearSelections');
            $(datagrId).datagrid('clearChecked');

            $.messager.alert("提示", "删除成功", "info");

            $(datagrId).datagrid('reload');
        } else if (result == "1") {
            $.messager.alert("提示", "没有可删除的记录", "info");
            return;
        } else {
            $.messager.alert("提示", FailedNotice.remove, "info");
            return;
        }
    }

};


function queryDatagrid(datagrid, url, idField, params, isPageload, onLoadSuccess) {
    if (isPageload) {
        datagrid.datagrid({
            url: url,
            idfield: idField,
            queryParams: params,
            onLoadSuccess: function (data) {
                if (data.Result == failed) {
                    $.messager.alert("提示", "?", "info");
                }
                else {
                    if (typeof onLoadSuccess == "function") {
                        onLoadSuccess(data);
                    }
                }
            },


        });
    }
    else {
        datagrid.datagrid('load', params);
    }
}

//格式化时间
function ChangeDateFormat(cellval, isDateTime) {
    try {
        //  var date = new Date(parseInt(cellval.replace("/Date(", "").replace(")/", ""), 10));
        var date = new Date(cellval);
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1)
                : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date
                .getDate();
        if (!isDateTime)
            return date.getFullYear() + "-" + month + "-" + currentDate;
        else
            return date.getFullYear() + "-" + month + "-" + currentDate + " "
                    + date.getHours() + ":" + date.getMinutes();
    } catch (e) {
        return "";
    }
}


function formattertime(val, row) {
    if (val != null) {
        var str = ChangeDateFormat(val,true);
        return str;
    }
}

function formatterdate(val, row) {
    if (val != null) {
        var str = ChangeDateFormat(val);
        return str;
    }
}