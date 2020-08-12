package com.ityouzi.framework.web.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ityouzi.project.system.domain.SysDept;
import com.ityouzi.project.system.domain.SysMenu;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: lizhen
 * @Date: 2020-06-29 08:41
 * @Description: TreeSelect树结构实体类
 */
@Getter
@Setter
public class TreeSelect implements Serializable
{
    private static final long serialVerSionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect(){}

    /**
     * 部门
     */
    public TreeSelect(SysDept dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 菜单
     */
    public TreeSelect(SysMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }
}
