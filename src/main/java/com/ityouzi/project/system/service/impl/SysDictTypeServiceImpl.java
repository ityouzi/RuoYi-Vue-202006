package com.ityouzi.project.system.service.impl;

import com.ityouzi.common.utils.DictUtils;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.project.system.domain.SysDictData;
import com.ityouzi.project.system.mapper.SysDictDataMapper;
import com.ityouzi.project.system.mapper.SysDictTypeMapper;
import com.ityouzi.project.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: lizhen
 * @Date: 2020-06-29 08:54
 * @Description: 字典业务处理层
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {
    
    /** 字典类型 */
    @Autowired
    private SysDictTypeMapper dictTypeMapper;
    
    /** 字典数据 */
    @Autowired
    private SysDictDataMapper dictDataMapper;


    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        // 获取字典缓存
        List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
        if(StringUtils.isNotNull(dictDatas)) {
            return dictDatas;
        }
        // 依据字典类型获取字典数据
        dictDatas = dictDataMapper.selectDictDataByType(dictType);
        if(StringUtils.isNotNull(dictDatas)) {
            // 设置字典数据缓存
            DictUtils.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return null;
    }
}
