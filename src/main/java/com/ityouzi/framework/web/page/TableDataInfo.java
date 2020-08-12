package com.ityouzi.framework.web.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: lizhen
 * @Date: 2020-06-28 15:40
 * @Description: 表格分页数据对象
 */
@Getter
@Setter
public class TableDataInfo implements Serializable {

    private static final long serialVerSionUID = 1L;

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<?> rows;

    /** 消息状态码 */
    private int code;

    /** 消息内容 */
    private int msg;

    /** 表格数据对象 */
    public TableDataInfo(){}

    /**
     * 分页
     *
     * @param
     */
    public TableDataInfo(List<?> list, int code){
        this.rows = list;
        this.code = code;
    }

}
