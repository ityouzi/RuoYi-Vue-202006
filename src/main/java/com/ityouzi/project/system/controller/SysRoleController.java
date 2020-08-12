package com.ityouzi.project.system.controller;

import com.ityouzi.common.constant.UserConstants;
import com.ityouzi.common.utils.SecurityUtils;
import com.ityouzi.common.utils.poi.ExcelUtils;
import com.ityouzi.framework.aspectj.lang.annotation.Log;
import com.ityouzi.framework.aspectj.lang.enums.BusinessType;
import com.ityouzi.framework.web.controller.BaseController;
import com.ityouzi.framework.web.domain.AjaxResult;
import com.ityouzi.framework.web.page.TableDataInfo;
import com.ityouzi.project.system.domain.SysRole;
import com.ityouzi.project.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: lizhen
 * @Date: 2020-07-13 14:03
 * @Description: 角色管理
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService roleService;


    @PreAuthorize("@ss.hasPermi('system:role:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysRole role){
        startPage();
        List<SysRole> list = roleService.selectRoleList(role);
        return getDataTable(list);
    }

    /**
     * 导出
     */
    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:role:export')")
    @GetMapping("/export")
    public AjaxResult export(SysRole role){
        List<SysRole> list = roleService.selectRoleList(role);
        ExcelUtils<SysRole> utils = new ExcelUtils<>(SysRole.class);
        return utils.exportExcel(list, "角色数据");
    }

    /**
     * 根据角色编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:role:query')")
    @GetMapping(value = "/{roleId}")
    public AjaxResult getInfo(@PathVariable Long roleId){
        return AjaxResult.success(roleService.selectRoleById(roleId));
    }

    /**
     * 新增角色
     *
     * 2020-07-13 14:12
     */
    @PreAuthorize("@ss.hasPermi('system:role:add')")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysRole role){
        if(UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return AjaxResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role)))
        {
            return AjaxResult.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(SecurityUtils.getUserName());
        return toAjax(roleService.insertRole(role));
    }

    /**
     * 修改保存角色
     *
     * 2020/8/12 12:44
     */
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysRole role){
        // 校验角色是否允许操作
        roleService.checkRoleAllowed(role);
        if(UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))){
            return AjaxResult.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))){
            return AjaxResult.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(roleService.updateRole(role));
    }

    /**
     * 修改&保存角色数据权限
     *
     * 2020/8/12 13:02
     */
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/dataScope")
    public AjaxResult dataScope(@RequestBody SysRole role){
        // 校验角色是否允许操作
        roleService.checkRoleAllowed(role);
        return toAjax(roleService.authDataScope(role));
    }





}
