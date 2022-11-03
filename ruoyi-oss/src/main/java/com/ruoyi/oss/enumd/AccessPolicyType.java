package com.ruoyi.oss.enumd;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 桶访问策略配置
 *
 * @author 陈賝
 */
@Getter
@AllArgsConstructor
public enum AccessPolicyType {

    /**
     * private
     */
    PRIVATE("0", CannedAccessControlList.Private),

    /**
     * public
     */
    PUBLIC("1", CannedAccessControlList.PublicRead),

    /**
     * constum
     */
    CONSTUM("2",CannedAccessControlList.PublicRead);

    /**
     * 桶 权限类型
     */
    private final String type;

    /**
     * 文件对象 权限类型
     */
    private final CannedAccessControlList acl;

}
