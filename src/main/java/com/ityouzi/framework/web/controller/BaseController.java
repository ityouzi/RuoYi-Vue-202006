package com.ityouzi.framework.web.controller;

import com.github.pagehelper.PageInfo;
import com.ityouzi.common.constant.HttpStatus;
import com.ityouzi.common.utils.DateUtils;
import com.ityouzi.framework.web.domain.AjaxResult;
import com.ityouzi.framework.web.page.TableDataInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * @Auther: lizhen
 * @Date: 2020-06-20 16:08
 * @Description:  web层通用数据处理
 */
@Slf4j
public class BaseController {

    /**
     * 将前台传递过来的日期格式的字符串， 自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder){
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
            public void setAsTest(String text){
                setValue(DateUtils.parseDate(text));    // 日期型字符串转化为日期 格式
            }
        });
    }


    /**
     * 设置请求分页数据
     */
    protected void startPage(){

    }


    /**
     * 响应请求分页数据
     */
    protected TableDataInfo getDataTable(List<?> list){
        TableDataInfo rspData = new TableDataInfo();
        // 状态码
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo<List>().getTotal());
        return rspData;
    }


    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows){
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

}
