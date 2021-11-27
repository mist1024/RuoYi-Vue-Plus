package com.ruoyi.common.core.domain.model;

import com.ruoyi.common.enums.DeviceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 用户登录对象
 *
 * @author Lion Li
 */

@Data
@Accessors(chain = true)
@ApiModel("用户登录对象")
public class LoginBody {

    /**
     * 设备类型
     */
    @ApiModelProperty(value = "设备类型(默认pc): pc, app")
    private String deviceType = DeviceType.PC.getDevice();

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    /**
     * 用户密码
     */
    @NotNull(message = "用户密码不能为空")
    @ApiModelProperty(value = "用户密码", required = true)
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String code;

    /**
     * 唯一标识
     */
    @ApiModelProperty(value = "唯一标识")
    private String uuid = "";

}
