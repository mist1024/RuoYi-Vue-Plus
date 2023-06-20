package org.dromara.common.mybatis.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author warden
 * @Date 2023/1/17 17:37
 */
public interface CommonMapper<T> extends BaseMapper<T> {
    /**
     * 全量插入,等价于insert
     * {@link com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn}
     *
     * @param entityList
     * @return
     */
    int insertBatchSomeColumn(List<T> entityList);

    /**
     * 全量更新，不忽略null字段，等价于update
     * 解决mybatis-plus会自动忽略null字段不更新
     * {@link com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById}
     *
     * @param entity
     * @return
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);
}
