package com.ityouzi.project.system.controller;

import com.ityouzi.common.constant.UserConstants;
import com.ityouzi.common.utils.SecurityUtils;
import com.ityouzi.common.utils.ServletUtils;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.common.utils.poi.ExcelUtils;
import com.ityouzi.framework.aspectj.lang.annotation.Log;
import com.ityouzi.framework.aspectj.lang.enums.BusinessType;
import com.ityouzi.framework.security.LoginUser;
import com.ityouzi.framework.security.service.TokenService;
import com.ityouzi.framework.web.controller.BaseController;
import com.ityouzi.framework.web.domain.AjaxResult;
import com.ityouzi.framework.web.page.TableDataInfo;
import com.ityouzi.project.system.domain.SysRole;
import com.ityouzi.project.system.domain.SysUser;
import com.ityouzi.project.system.service.ISysPostService;
import com.ityouzi.project.system.service.ISysRoleService;
import com.ityouzi.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: lizhen
 * @Date: 2020-06-28 15:08
 * @Description: 用户信息
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private TokenService tokenService;




    /**
     * 获取用户列表
     *
     * @return 分页数据
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user){
        startPage();    // 开始分页
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }






    /**
     * 导出用户信息
     */
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/export")
    public AjaxResult export(SysUser user){
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtils<SysUser> utils = new ExcelUtils<>(SysUser.class);
        return utils.exportExcel(list,"用户数据");
    }
    
    /**
     * 导入用户信息
     */
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception{
        ExcelUtils<SysUser> utils = new ExcelUtils<>(SysUser.class);
        // 把Excel转集合
        List<SysUser> userList = utils.importExcel(file.getInputStream());
        // 当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();  // 操作用户
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * 导入模板
     */
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(){
        ExcelUtils<SysUser> util = new ExcelUtils<>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }
    
    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId){
        AjaxResult ajax= AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if(StringUtils.isNotNull(userId)) {
            ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return ajax;
    }

    /**
     * 新增用户
     *
     * 2020-07-13 12:41
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user){
        if(UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(SecurityUtils.getUserName());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     *
     * 2020-07-13 12:58
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user){
        userService.checkUserAllowed(user); // 校验用户是否允许被操作
        if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     *
     * 2020-07-13 13:04
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds){
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     *
     * 2020-07-13 13:10
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user){
        userService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 修改状态
     *
     * 2020-07-13 13:14
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user){
        userService.checkUserAllowed(user);
        user.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(userService.updateUserStatus(user));
    }


}
