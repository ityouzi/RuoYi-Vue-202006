package com.ityouzi.project.system.controller;

import com.ityouzi.common.utils.SecurityUtils;
import com.ityouzi.framework.aspectj.lang.annotation.Log;
import com.ityouzi.framework.aspectj.lang.enums.BusinessType;
import com.ityouzi.framework.web.controller.BaseController;
import com.ityouzi.framework.web.domain.AjaxResult;
import com.ityouzi.framework.web.page.TableDataInfo;
import com.ityouzi.project.system.domain.SysNotice;
import com.ityouzi.project.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 公告 信息操作处理
 * @author: lizhen created on 2021-03-26 10:47
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    @Autowired
    private ISysNoticeService noticeService;

    /**
     * 获取公告停止列表
     * @author lizhen
     * 2021/3/26 - 10:50
     */
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNotice notice){
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     * @author lizhen
     * 2021/3/26 - 13:43
     */
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping("/{noticeId}")
    public AjaxResult getInfo(@PathVariable("noticeId") Long noticeId){
        return AjaxResult.success(noticeService.selectNoticeById(noticeId));
    }

    /**
     * 新增通知公告
     * @author lizhen
     * 2021/3/26 - 13:47
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysNotice notice){
        notice.setCreateBy(SecurityUtils.getUserName());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     * @author lizhen
     * 2021/3/26 - 13:52
     */
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysNotice notice){
        notice.setCreateBy(SecurityUtils.getUserName());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除公告
     * @author lizhen
     * 2021/3/26 - 13:56
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds){
        return toAjax(noticeService.deleteNoticeByIds(noticeIds));
    }
}
