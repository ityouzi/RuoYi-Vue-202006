package com.ityouzi.project.system.controller;

import com.ityouzi.framework.web.controller.BaseController;
import com.ityouzi.framework.web.domain.AjaxResult;
import com.ityouzi.project.system.domain.SysDept;
import com.ityouzi.project.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: lizhen
 * @Date: 2020-06-28 20:40
 * @Description: 部门信息
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public AjaxResult list(SysDept dept){
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(depts);
    }


    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysDept dept){
        List<SysDept> depts = deptService.selectDeptList(dept);
        // 构建前端所需要下拉树结构
        return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
    }


}
