package com.ityouzi.project.system.controller;

import com.ityouzi.framework.web.controller.BaseController;
import com.ityouzi.framework.web.domain.AjaxResult;
import com.ityouzi.project.system.domain.SysDept;
import com.ityouzi.project.system.service.ISysDeptService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
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
     * 查询部门列表（排除节点）
     *
     * 2020/9/21 - 17:22
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list/exclude/{deptId}")
    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false ) Long deptId){
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        Iterator<SysDept> it = depts.iterator();
        while (it.hasNext()){
            SysDept d = it.next();
            if (d.getDeptId().intValue()==deptId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(),","),
                    deptId + "")){
                it.remove();
            }
        }
        return AjaxResult.success(depts);
    }

    /**
     * 
     * 
     * 2020/9/21 - 17:29
     */
    

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysDept dept){
        List<SysDept> depts = deptService.selectDeptList(dept);
        // 构建前端所需要下拉树结构
        return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
    }

    /**
     * 加载对应角色部门列表树
     *
     * 2020/9/21 - 16:06
     */
    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    public AjaxResult roleDeptTreeselect(@PathVariable("roleId") Long roleId){
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        ajax.put("depts", deptService.buildDeptTreeSelect(depts));
        return ajax;
    }

}
