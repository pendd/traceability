package com.hvisions.mes.util.excelUtil;


import com.hvisions.mes.util.UUIDGenerator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/** excel导入功能工具类
 *
 * @author dpeng
 * @create 2019-03-19 14:48
 */
public class ImportExcelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportExcelUtil.class);

    private final static String excel2003L =".xls";    //2003- 版本的excel
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel



    /**
     * 描述：获取IO流中的数据，组装成List<T>对象
     *      获取第0个sheet中,从0行到最后一行的数据,默认第0行为title
     * @param in    导入文件转换的流
     * @param fileName 文件名称
     * @param beanClass 转换成beanClass
     * @return
     * @throws Exception
     */
    public static <T> List<T> getDefaultDataListByExcel(InputStream in, String fileName, Class<T> beanClass) throws Exception{
        return getDataListByExcel(in,fileName,beanClass,0,0,Integer.MAX_VALUE);
    }



    /**
     * 描述：获取IO流中的数据，组装成List<T>对象
     *      获取第sheetIndex个sheet中,从0行到最后一行
     * @param in    导入文件转换的流
     * @param fileName 文件名称
     * @param beanClass 转换成beanClass
     * @param sheetIndex 要获取的sheet的index值
     * @return
     * @throws Exception
     */
    public static <T> List<T> getFullDataListByExcel(InputStream in,String fileName,Class<T> beanClass,int sheetIndex) throws Exception{
        return getDataListByExcel(in,fileName,beanClass,sheetIndex,0,Integer.MAX_VALUE);
    }

    /**
     * 描述：获取IO流中的数据，组装成List<T>对象
     * @param in
     * @param fileName 文件名称
     * @param beanClass 转换成beanClass
     * @param sheetIndex 要获取的sheet的index值
     * @return
     * @throws Exception
     */
    public static <T> List<T> getDataListByExcel(InputStream in,String fileName,Class<T> beanClass,int sheetIndex,int beginRowNum,int endRowNum) throws Exception{
        //创建Excel工作薄
        Workbook work = getWorkbook(in,fileName);
        if(null == work){
            throw new Exception("创建Excel工作薄为空！");
        }
        //获取响应的sheet
        Sheet sheet = getSheet(work,sheetIndex);
        //获取sheet的表头
        Row titleRow = getRow(sheet,beginRowNum);
        //返回的list<T>结果
        List<T> list = getPartDataListByExcel(titleRow,sheet,beanClass,beginRowNum,endRowNum);
        work.close();
        return list;
    }


    public static <T> List<T> getPartDataListByExcel(Row titleRow, Sheet sheet, Class<T> beanClass, int beginRowNum, int endRowNum) throws Exception{
        endRowNum = sheet.getLastRowNum();
        //返回的list<T>结果
        List<T> list = new ArrayList<T>();
        //获取导入文件对应的bean的标题map
        Map<Integer, ExcelHeader> maps = ImportExcelHeaderUtil.getHeaderMap(titleRow, beanClass);
        //遍历当前sheet中的所有行
        for (int j = beginRowNum+1; j <= endRowNum; j++) {
            Row row = sheet.getRow(j);
            //新建一个T类
            T bean = beanClass.newInstance();
            //遍历所有的列
            for (Cell cell : row) {
                int ci = cell.getColumnIndex();
                ExcelHeader header = maps.get(ci);
                if (null == header)
                    continue;
                String filed = header.getFiled();
                Object value = getCellValue(cell);
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString());
                    BeanUtils.copyProperty(bean,filed,date);
                }catch (ParseException e){
                    BeanUtils.copyProperty(bean, filed, value);
                }
            }
            list.add(bean);
        }
        //work.close();
        return list;
    }



    /**
     * 描述：根据sheetIndex,得到响应的sheet
     * @param work,fileName
     * @return Sheet
     * @throws Exception
     */
    public static Sheet getSheet(Workbook work, int sheetIndex) throws Exception{
        Sheet sheet = null;
        if(work.getNumberOfSheets() >= 0){
            sheet = work.getSheetAt(sheetIndex);
        }
        return sheet;
    }

    /**
     * 描述：根据rowIndex,得到响应的row
     * @param sheet,rowIndex
     * @return Sheet
     * @throws Exception
     */
    public static Row getRow(Sheet sheet, int rowIndex) throws Exception{
        return sheet.getRow(rowIndex);
    }


    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception{
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(excel2003L.equals(fileType)){
            wb = new HSSFWorkbook(inStr);  //2003-
        }else if(excel2007U.equals(fileType)){
            wb = new XSSFWorkbook(inStr);  //2007+
        }else{
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }


    /**
     * 描述：对表格中数值进行格式化
     * @param cell
     * @return
     */
    private static Object getCellValue(Cell cell){
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字

        switch (cell.getCellTypeEnum()) {
            case STRING://字符串型
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC://日期或数字
                if("General".equals(cell.getCellStyle().getDataFormatString())){
                    value = df.format(cell.getNumericCellValue());
                }else if(DateUtil.isCellDateFormatted(cell) || "m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                }else{
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN://布尔型
                value = cell.getBooleanCellValue();
                break;
            case BLANK://空字符串
                value = "";
                break;
            case FORMULA://公式
                CellStyle style = cell.getCellStyle();

                value = cell.getNumericCellValue();
                break;
            default:
                break;
        }
        return value;
    }

    /**
     *
     * @param mFile
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> List<T> insertExcel(MultipartFile mFile,Class<T> clazz) throws Exception {
        // 获取文件名
        String fileName = mFile.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        final File excelFile = File.createTempFile(UUIDGenerator.getUUID(), prefix);
        // MultipartFile to File
        mFile.transferTo(excelFile);
        // 获取文件输入流对象
        FileInputStream inStr = FileUtils.openInputStream(excelFile);

        return getDefaultDataListByExcel(inStr, fileName, clazz);
    }

}
