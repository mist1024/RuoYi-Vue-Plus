package com.ruoyi.hik.domain;

import lombok.Data;

@Data
public class HikSecurityUserCheck {
    // <!--ro, req, enum, 校验结果状态, subType:int, [200#200,401#401]-->200
    private Integer statusValue;
    // <!--ro, opt, enum, 校验结果状态字符信息, subType:string, [OK#OK,Unauthorized#Unauthorized]-->OK
    private String statusString;
    // <!--ro, opt, bool, 是否是默认密码-->true
    private Boolean isDefaultPassword;
    // <!--ro, opt, bool, 是否是风险密码-->true
    private Boolean isRiskPassword;
    // <!--ro, opt, bool, 是否已激活-->true
    private Boolean isActivated;
    // <!--ro, opt, int, 密码剩余有效天数, desc:返回负值,表示密码已经超期使用,例如"-3表示密码已经超期使用3天-->1
    private Integer residualValidity;
    // <!--ro, opt, enum, 锁定状态, subType:string, [unlock#未锁定,locked#已锁定]-->unlock
    private String lockStatus;
    // <!--ro, opt, int, 解锁剩余时间, unit:s, unitType:时间-->1
    private Integer unlockTime;
    // <!--ro, opt, int, 重试次数-->1
    private Integer retryLoginTime;
}
