package com.ruoyi.gateway.utils.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 网关 规则信息(服务对应AK处理规则)
 * @author Wechao Gong
 * @date 2021-10-16
 */
@Getter
@Setter
public class IscRule implements Serializable
{
    @JsonIgnore
    private String id;
    private Date expire;
    private Long daysLimit;
    private Long hoursLimit;
    private Long minutesLimit;
    private Long secondsLimit;
}
