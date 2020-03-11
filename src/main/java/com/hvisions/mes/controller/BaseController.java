package com.hvisions.mes.controller;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.hvisions.mes.domain.Emp;
import com.hvisions.mes.service.IOperationLogService;
import springfox.documentation.annotations.ApiIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hvisions.mes.domain.OperationLog;
import com.hvisions.mes.service.IPageService;
import com.hvisions.mes.service.IUserService;
import com.hvisions.mes.util.CookieUtil;
@ApiIgnore
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    HttpServletRequest request;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOperationLogService operationLogService;
    @Autowired
    private IPageService pageService;

    /**
     * 获取当前登录用户ID
     */
    protected Integer getCurrentUserID() {
        String value=CookieUtil.getCookieValue(request, "emp_id");
        Integer empId=Integer.valueOf(value);
        return empId;
    }

    protected Emp getCurrentUser() {
        String value=CookieUtil.getCookieValue(request, "emp_id");
        Integer empId=Integer.valueOf(value);
        Emp user = userService.getUserByID(empId);
        return user;
    }

    /**
     * 获取当前登录用户ID
     */
//    protected Integer getCurrentUserID() {
//        return (Integer) request.getAttribute(Param.USER_ID);
//    }
//
//    protected Emp getCurrentUser() {
//        Integer userID = (Integer) request.getAttribute(Param.USER_ID);
//        Emp oEmp = userService.getUserByID(userID);
//        return oEmp;
//    }

    /**
     * 添加操作日志到数据库
     *
     * @param operationType
     *            日志的操作类型，值为以下三个常量之一:  0:添加、1:修改、2:删除、3：查看,4:密码
     *            <pre>
     *               添加：OperationLog.OPERATION_TYPE_ADD
     *               修改：OperationLog.OPERATION_TYPE_MODIFY
     *               删除：OperationLog.OPERATION_TYPE_REMOVE
     *               查看：OPERATION_TYPE_QUERY
     *            </pre>
     *
     * @param operationContent
     */
    protected void addPersonnelOperationLog(Integer operationType, String operationContent,String operContentEn) {
        OperationLog operationLog = new OperationLog();
        operationLog.setOperTime(new Date());
        operationLog.setOperUserId(getCurrentUserID());
        operationLog.setMenuId(pageService.queryMenuId(changeUrl(request.getRequestURI())));
        operationLog.setOperType(operationType);
        operationLog.setOperContentEn(operContentEn);
        operationLog.setOperContent(operationContent);
        operationLogService.addOperationLog(operationLog);
    }

    protected Date GetFirstTime(Date dDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dDate);
       //int a= calendar.get(Calendar.YEAR);
        //Date o=new Date(dDate. dDate.getMonth(), dDate.getDay(), 0, 0, 0);
       calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
       Date date=calendar.getTime();
       return date;
    }

    protected Date GetLastTime(Date dDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dDate);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Date date=calendar.getTime();
        return date;
    }
    protected Date GetFirstDayOfWeek(Date dDate)
    {
        // ---------------------周的第一天是星期一，使用以下程序---------------
        // 计算参数日期，与星期一相差几天
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        int d = 0;
        if(cal.get(Calendar.DAY_OF_WEEK)==1){
            d = -6;
        }else{
            d = 2-cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date date=cal.getTime();
        return date;

        // ---------------------周的第一天是星期日，使用以下程序---------------
        // 计算参数日期，与星期日相差几天
        //int iCnt = dDate.DayOfWeek - DayOfWeek.Sunday;

        //return dDate.Subtract(new TimeSpan(iCnt, 0, 0, 0));
    }
    protected Date GetLastDayOfWeek(Date dDate)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        int d = 0;
        if(cal.get(Calendar.DAY_OF_WEEK)==1){
            d = 0;
        }else{
            d = 8-cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),23, 59, 59);
        Date date=cal.getTime();
        return date;

    }

    protected Integer GetDayOfWeek(Date dDate)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        Integer week=0;
        if(cal.get(Calendar.DAY_OF_WEEK)==1){
            week = 7;
        }else{
            week = cal.get(Calendar.DAY_OF_WEEK)-1;
        }

        return week;
    }

    protected Date SetDateHour(Date dDate,Integer hour){
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        cal.add(Calendar.HOUR_OF_DAY, hour);
        Date date=cal.getTime();
        return date;

    }
    protected Integer GetHour(Date dDate)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        Integer hour=cal.get(Calendar.HOUR_OF_DAY);
        return hour;
    }




    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 解析
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 将指定byte数组以16进制的形式打印到控制台
     * @param b
     */
    public static void printHexString(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase());
        }

    }

    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
    }

    /**
     * 加密
     * @param str
     * @return
     */
    public static String encode(String str) {
        String strDigest = "";
        try {
            // 此 MessageDigest 类为应用程序提供信息摘要算法的功能，必须用try,catch捕获
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] data;
            data = md5.digest(str.getBytes("utf-8"));// 转换为MD5码
            strDigest = bytesToHexString(data);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return strDigest;
    }

    public String changeUrl(String url)
    {
        String[] str=url.split("/");
        String strUrl=str[1]+"/"+str[2]+"/";
        String urlList[]={"json/Users/","json/Role/","json/poManage/"};
        String urlPageList[]={"/page/settings/user.html","/page/settings/role.html","/page/poManage/poManage.html"};
        String result="";
        for(int i=0;i<urlList.length;i++)
        {
            String value=urlList[i];
            if(value.equals(strUrl))
            {
                result=urlPageList[i];
            }
        }
        return  result;
    }

}
