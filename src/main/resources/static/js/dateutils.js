/**
 * 日期工具
 */
var dateutils = {};

/*
 * 格式化日期
 * 使用示例：dateutils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
 */
dateutils.format = function(d, fmt) {
    if (!d || !fmt) {
        return "";
    }

    var o = {
        "M+": d.getMonth() + 1, // 月份
        "d+": d.getDate(), // 日
        "h+": d.getHours(), // 小时
        "m+": d.getMinutes(), // 分
        "s+": d.getSeconds(), // 秒
        "q+": Math.floor((d.getMonth() + 3) / 3), // 季度
        "S": d.getMilliseconds() // 毫秒
    };

    // 替换年部分
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    // 替换年以外部分
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }

    return fmt;
};

/*
 * 计算月的第一天
 * 注：时间部分为00:00:00
 */
dateutils.firstDayOfMonth = function(d) {
    if (!d) {
        return null;
    }

    return new Date(d.getFullYear(), d.getMonth(), 1);
};

/*
 * 计算月的最后一天
 * 注：时间部分为00:00:00
 */
dateutils.lastDayOfMonth = function(d) {
    if (!d) {
        return null;
    }

    return new Date(d.getFullYear(), d.getMonth() + 1, 0);
};

/*
 * 格式化秒（返回格式：m's"）
 * 使用示例：dateutils.formatMinutes(90); 返回：1'30"
 */
dateutils.formatSeconds = function(seconds) {
    var ret = "";

    if (seconds == null) {
        return ret;
    }

    //四舍五入，避免出现小数
    seconds = Math.round(seconds);

    //计算分钟数、秒数


    var hour= Math.floor(seconds / 3600);
    var min =Math.floor((seconds % 3600)/60);
    var sec = (seconds % 3600)%60;
  //分钟字符串
    if (hour > 0) {
        ret = hour + ret + "'";
    }

    //分钟字符串
    if (min > 0) {
        ret = ret + min + "\"";
    }

    //秒字符串
    if (sec > 0) {
        ret = ret + sec + "\"'";
    }

    return ret;
};