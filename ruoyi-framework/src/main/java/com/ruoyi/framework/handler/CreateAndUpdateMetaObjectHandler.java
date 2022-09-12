package com.ruoyi.framework.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * MP注入处理器
 *
 * @author Lion Li
 * @author Charles7c
 * @date 2021/4/25
 */
@Slf4j
public class CreateAndUpdateMetaObjectHandler implements MetaObjectHandler {

    /** 创建人 */
    private static final String CREATE_BY = "createBy";
    /** 创建时间 */
    private static final String CREATE_TIME = "createTime";
    /** 更新人 */
    private static final String UPDATE_BY = "updateBy";
    /** 更新时间 */
    private static final String UPDATE_TIME = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            if (ObjectUtil.isNull(metaObject)) {
                return;
            }

            this.fillFieldValue(metaObject, CREATE_BY, this.getLoginUsername(), false);
            this.fillFieldValue(metaObject, CREATE_TIME, new Date(), false);
            this.fillFieldValue(metaObject, UPDATE_BY, this.getLoginUsername(), false);
            this.fillFieldValue(metaObject, UPDATE_TIME, new Date(), false);
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            if (ObjectUtil.isNull(metaObject)) {
                return;
            }

            this.fillFieldValue(metaObject, UPDATE_BY, this.getLoginUsername(), true);
            this.fillFieldValue(metaObject, UPDATE_TIME, new Date(), true);
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
        }
    }

    /**
     * 填充（注入）属性值
     *
     * @param metaObject     元数据对象
     * @param fieldName      要填充的属性名
     * @param fillFieldValue 要填充的属性值
     * @param isOverride     如果属性值不为空，是否覆盖（true 覆盖、false 不覆盖）
     */
    private void fillFieldValue(MetaObject metaObject, String fieldName, Object fillFieldValue, boolean isOverride) {
        if (metaObject.hasSetter(fieldName)) {
            Object fieldValue = metaObject.getValue(fieldName);
            setFieldValByName(fieldName, ObjectUtil.isNotNull(fieldValue) && !isOverride ? fieldValue : fillFieldValue, metaObject);
        }
    }

    /**
     * 获取登录用户名
     */
    private String getLoginUsername() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            log.warn("自动注入警告 => 用户未登录");
            return null;
        }
        return loginUser.getUsername();
    }
}
