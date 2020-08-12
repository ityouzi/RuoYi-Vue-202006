package com.ityouzi.project.system.service;

import com.ityouzi.project.system.domain.SysDictData;

import java.util.List;

/**
 * 字典 业务层
 */
public interface ISysDictTypeService {

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType);
}
