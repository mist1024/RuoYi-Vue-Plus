package com.ruoyi.common.excel.builder;


import com.ruoyi.common.excel.common.SheetModel;
import lombok.Getter;

/**
 * sheet页信息构建器
 *
 * @author liyang
 **/
@Getter
public abstract class  SheetModelBuilder {
    /**
     * sheet名称
     */
    protected final String sheetName;
    protected SheetModelBuilder(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * 具体构建方法
     * @return
     */
    abstract protected SheetModel build() ;
}
