package com.ityouzi.project.system.service;


import com.ityouzi.project.system.domain.SysUser;

import java.util.List;

/**
 * 用户 业务层
 */
public interface ISysUserService {
    /**
     * 通过用户名查询用户
     *
     * @param userName
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);


    /**
     * 根据用户ID查询用户所属的角色组
     *
     * @param username 用户名
     * @return 结果
     */
    String selectUserRoleGroup(String username);

    /**
     * 根据用户ID查询用户所属岗位
     *
     * @param username 用户名
     * @return 结果
     */
    String selectUserPostGroup(String username);

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUserProfile(SysUser user);

    /**
     * 重置用户密码
     *
     * @param username  用户名
     * @param encryptPassword 加密后的密码
     * @return 结果
     */
    int resetUserPwd(String username, String encryptPassword);

    /**
     * 修改用户头像
     *
     * @param username 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    boolean updateUserAvatar(String username, String avatar);

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> selectUserList(SysUser user);


    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);


    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    String importUser(List<SysUser> userList, boolean isUpdateSupport, String operName);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUser selectUserById(Long userId);

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    String checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    String checkPhoneUnique(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    String checkEmailUnique(SysUser user);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    void checkUserAllowed(SysUser user);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    int deleteUserByIds(Long[] userIds);

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    int resetPwd(SysUser user);

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUserStatus(SysUser user);
}
