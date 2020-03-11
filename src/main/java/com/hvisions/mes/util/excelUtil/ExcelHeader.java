package com.hvisions.mes.util.excelUtil;

import lombok.Data;

/**  excel头信息实体类
 *
 * @author dpeng
 * @create 2019-03-19 14:43
 */
@Data
public class ExcelHeader implements Comparable<ExcelHeader> {

    private String title;

    private int order;

    private String filed;

    @Override
    public int compareTo(ExcelHeader o) {
        return order - o.order;
    }

    public ExcelHeader(String title, int order, String filed) {
        this.title = title;
        this.order = order;
        this.filed = filed;
    }
}
