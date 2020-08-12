package com.ityouzi.project.system.mapper;


import com.ityouzi.project.system.domain.SysDictData;

import java.util.List;

/**
 * 字典数据表
 */
public interface SysDictDataMapper {


    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<SysDictData> selectDictDataByType(String dictType);
}
