package com.ruoyi.winery.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.winery.domain.goods.GoodsMain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求退款对象
 *
 * @author ruoyi
 * @date 2021-01-18
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class AppRequestRefundDetailVo implements Serializable {

    /**
     * 明细ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;


    @Excel(name = "退款理由")
    private String refundReason;

}
