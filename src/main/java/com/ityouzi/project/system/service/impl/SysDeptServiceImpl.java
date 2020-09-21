package com.ityouzi.project.system.service.impl;

import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.framework.web.domain.TreeSelect;
import com.ityouzi.project.system.domain.SysDept;
import com.ityouzi.project.system.mapper.SysDeptMapper;
import com.ityouzi.project.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: lizhen
 * @Date: 2020-06-28 20:42
 * @Description: 部门管理 服务实现
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {

    @Autowired
    private SysDeptMapper deptMapper;


    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<SysDept> selectDeptList(SysDept dept) {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> deptList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        for (SysDept dept : depts){
            tempList.add(dept.getDeptId());     // 部门ID
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext();){
            SysDept dept = iterator.next();
            if(!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                deptList.add(dept);
            }
        }
        if(deptList.isEmpty()) {
            deptList = depts;
        }
        return deptList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
        List<SysDept> deptTree = buildDeptTree(depts);
        return deptTree.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId) {
        return deptMapper.selectDeptListByRoleId(roleId);
    }


    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t){
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept dept:childList){
            if(hasChild(list, dept)) {
                // 判断是否有子节点
                Iterator<SysDept> it = childList.iterator();
                while (it.hasNext()){
                    SysDept n = it.next();
                    recursionFn(list,  n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t)
    {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext())
        {
            SysDept n = (SysDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
