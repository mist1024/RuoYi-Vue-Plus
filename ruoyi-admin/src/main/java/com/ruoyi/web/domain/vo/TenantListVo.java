package com.ruoyi.web.domain.vo;

import com.ruoyi.system.domain.vo.SysTenantVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 租户列表
 *
 * @author Lion Li
 */
@Data
@AutoMapper(target = SysTenantVo.class)
public class TenantListVo {

    private String tenantId;

    private String companyName;

    private String domain;

}
