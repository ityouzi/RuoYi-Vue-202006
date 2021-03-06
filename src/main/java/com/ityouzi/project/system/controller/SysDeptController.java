package com.ityouzi.project.system.controller;

import com.ityouzi.common.constant.UserConstants;
import com.ityouzi.common.utils.SecurityUtils;
import com.ityouzi.framework.aspectj.lang.annotation.Log;
import com.ityouzi.framework.aspectj.lang.enums.BusinessType;
import com.ityouzi.framework.web.controller.BaseController;
import com.ityouzi.framework.web.domain.AjaxResult;
import com.ityouzi.project.system.domain.SysDept;
import com.ityouzi.project.system.service.ISysDeptService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 根据部门编号获取详细信息
     * 
     * 2021/3/10 - 21:18
     */
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable Long deptId){
        return AjaxResult.success(deptService.selectDeptById(deptId));
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

    /**
     * 新增部门
     *
     * 2021/3/10 - 21:19
     */
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDept dept){ // 已验证
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))){
            return AjaxResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(SecurityUtils.getUserName());  // 获取创建人名称
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     *
     * 2021/3/10 - 21:36
     */
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDept dept){
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))){
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        else if (dept.getParentId().equals(dept.getDeptId())){
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())){
            return AjaxResult.error("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     *
     * 2021/3/13 - 14:02
     */
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable Long deptId){
        // 是否存在部门子节点、下级部门
        if (deptService.hasChildByDeptId(deptId)){
            return AjaxResult.error("存在下级部门，不允许删除");
        }
        // 查询部门是否存在用户、该部门是否还有用户在使用
        if (deptService.checkDeptExistUser(deptId)){
            return AjaxResult.error("部门存在用户，不允许删除");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }

}
