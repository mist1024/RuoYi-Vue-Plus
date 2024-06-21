package org.dromara.liteflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import org.dromara.liteflow.domain.LiteflowChain;

import java.io.Serial;
import java.io.Serializable;


/**
 * 编排规则视图对象 liteflow_chain
 *
 * @author AprilWind
 * @date 2024-06-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LiteflowChain.class)
public class LiteflowChainVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 配置环境
     */
    @ExcelProperty(value = "配置环境")
    private String applicationName;

    /**
     * 编排规则ID
     */
    @ExcelProperty(value = "编排规则ID")
    private String chainName;

    /**
     * 状态（0未激活 1激活）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=未激活,1=激活")
    private Integer chainStatus;

    /**
     * EL表达式
     */
    @ExcelProperty(value = "EL表达式")
    private String elStr;

    /**
     * namespace（决策路由很多，想要判断某一组决策路由时在chain层面加入namespace参数）
     */
    @ExcelProperty(value = "namespace", converter = ExcelDictConvert.class)
    private String nameSpace;

    /**
     * 决策路由EL
     */
    @ExcelProperty(value = "决策路由EL")
    private String route;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}
