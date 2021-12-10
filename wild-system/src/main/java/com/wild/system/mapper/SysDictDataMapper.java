package com.wild.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wild.common.core.domain.entity.SysDictData;
import com.wild.common.core.mybatisplus.core.BaseMapperPlus;

import java.util.List;

/**
 * 字典表 数据层
 *
 * @author Lion Li
 */
public interface SysDictDataMapper extends BaseMapperPlus<SysDictData> {

    default List<SysDictData> selectDictDataByType(String dictType) {
        return selectList(
                new LambdaQueryWrapper<SysDictData>()
                        .eq(SysDictData::getStatus, "0")
                        .eq(SysDictData::getDictType, dictType)
                        .orderByAsc(SysDictData::getDictSort));
    }
}
