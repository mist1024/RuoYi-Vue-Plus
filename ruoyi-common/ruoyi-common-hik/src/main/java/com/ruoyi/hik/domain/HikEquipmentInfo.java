package com.ruoyi.hik.domain;

import lombok.Data;

/**
 * 海康设备对象 hk_equipment
 *
 * @author zz
 * @date 2022-12-24
 */
@Data
public class HikEquipmentInfo {

    private static final long serialVersionUID=1L;

    /**
     * 设备id
     */
    private Long hkEquipmentId;
    /**
     * 设备类型0采集器1通道2门禁3考勤机
     */
    private String equipmentType;

    /**
     * 设备账号
     */
    private String username;
    /**
     * 设备密码
     */
    private String password;
    /**
     * 设备IP
     */
    private String equipmentIp;
    /**
     * 设备端口号
     */
    private Short equipmentPort;


    /**
     * 用户句柄：登录后的操作句柄
     */
    public int lUserID = -1;
    /**
     * 布防句柄：布防后的操作句柄
     */
    public int lAlarmHandle = -1;

    /**
     * 监听句柄：监听后的操作句柄
     */
    public int lListenHandle = -1;


}
