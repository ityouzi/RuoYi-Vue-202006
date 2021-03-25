package com.ityouzi.project.system.service.impl;

import com.ityouzi.common.constant.UserConstants;
import com.ityouzi.common.utils.SecurityUtils;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.common.utils.TreeUtils;
import com.ityouzi.framework.web.domain.TreeSelect;
import com.ityouzi.project.system.domain.SysMenu;
import com.ityouzi.project.system.domain.SysUser;
import com.ityouzi.project.system.domain.vo.MetaVo;
import com.ityouzi.project.system.domain.vo.RouterVo;
import com.ityouzi.project.system.mapper.SysMenuMapper;
import com.ityouzi.project.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther: lizhen
 * @Date: 2020-06-17 14:33
 * @Description: 菜单 业务处理层
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {
    @Autowired
    private SysMenuMapper menuMapper;


    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(Long userId) {
        return selectMenuList(new SysMenu(), userId);
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        // 读取 SysMenu 的权限标识数组
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        // 逐个，按照“逗号” 分隔
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms){
            if (StringUtils.isNotEmpty(perm)){
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = null;
        if(SecurityUtils.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
        } else {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return new TreeUtils().getChildPerms(menus, 0);
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<>();
        for (SysMenu menu : menus){
            RouterVo router = new RouterVo();
            router.setName(StringUtils.capitalize(menu.getPath()));
            router.setPath(getRouterPath(menu));
            router.setComponent(StringUtils.isEmpty(menu.getComponent()) ? "Layout" : menu.getComponent());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
            List<SysMenu> cMenus = menu.getChildren();
            if(!cMenus.isEmpty() && cMenus.size() > 0 && "M".equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        List<SysMenu> menuList = null;
        // 管理员显示所有菜单信息
        if (SysUser.isAdmin(userId)){
            menuList = menuMapper.selectMenuList(menu);
        }else {
            menu.getParams().put("userId", userId);
            // 根据用户查询系统菜单列表
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单详情
     */
    @Override
    public SysMenu selectMenuById(Long menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {

        return null;
    }

    /**
     * 构建菜单下拉树结构
     */
    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus){
        List<SysMenu> returnList = new ArrayList<>();
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext();){
            SysMenu sm = iterator.next();
            // 根据传入的某个父节点ID，遍历该父节点的所有子节点
            if (sm.getParentId() == 0){
                recursionFn(menus, sm);
                returnList.add(sm);
            }
        }
        if (returnList.isEmpty()){
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Integer> selectMenuListByRoleId(Long roleId) {
        return menuMapper.selectMenuListByRoleId(roleId);
    }


    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue())
        {
            return UserConstants.NOT_UNIQUE;    // 不唯一
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(SysMenu menu) {
        return menuMapper.iinsertMenu(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu) {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 递归列表
     * @author lizhen
     * 2021/3/24 - 15:52
     */
    private void recursionFn(List<SysMenu> list, SysMenu menu){
        // 得到子节点的列表
        List<SysMenu> childList = getChildList(list, menu);
        menu.setChildren(childList);
        for (SysMenu tChild : childList){
            if (hasChild(list, tChild)){
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext()){
                    SysMenu n = it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     * @author lizhen
     * 2021/3/25 - 10:11
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu menu)
    {
        List<SysMenu> list1 = new ArrayList<>();
        Iterator<SysMenu> iterator = list.iterator();
        while (iterator.hasNext()){
            SysMenu n = iterator.next();
            if (n.getParentId().longValue() == menu.getMenuId().longValue()){
                list1.add(n);
            }
        }
        return list1;
    }

    /**
     * 判断是否右子节点
     * @author lizhen
     * 2021/3/25 - 10:14
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t){
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 非外链并且是一级目录
        if (0 == menu.getParentId() && "1".equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        return routerPath;
    }

}
