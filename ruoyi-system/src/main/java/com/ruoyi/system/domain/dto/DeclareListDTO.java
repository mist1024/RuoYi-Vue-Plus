package com.ruoyi.system.domain.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.Data;

import java.util.Date;

@Data
@ExcelIgnoreUnannotated
public class DeclareListDTO {
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 提交时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private String processStatus;

    /**
     * 业务id
     */
    private String businessId;

    /**
     * 申请人
     */
    private String userName;

    /**
     * 流程key
     */
    private String processKey;

    private String cardId;

}
