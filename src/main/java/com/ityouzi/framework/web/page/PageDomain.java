package com.ityouzi.framework.web.page;

import com.ityouzi.common.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: lizhen
 * @Date: 2020-06-28 15:46
 * @Description: 分页数据
 */
@Getter
@Setter
public class PageDomain {
    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 排序列 */
    private String oderByColumn;

    /** 排序的方向 "desc" 或者 "asc" */
    private String isAsc;

    public String getOrderBy(){
        if(StringUtils.isEmpty(oderByColumn)) {
            return "";
        }
        return StringUtils.toUnderScoreCase(oderByColumn) + " " + isAsc;
    }


}
