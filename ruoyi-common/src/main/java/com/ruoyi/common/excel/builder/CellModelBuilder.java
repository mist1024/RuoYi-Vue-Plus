package com.ruoyi.common.excel.builder;

import com.ruoyi.common.excel.common.SheetModel;
import lombok.Getter;

/**
 * 单元格信息构建器
 *
 * @author liyang
 **/
@Getter
public abstract class CellModelBuilder extends SheetModelBuilder {
    /**
     * 列索引
     */
    protected final int colIndex;
    /**
     * 行索引
     */
    protected final int rowIndex;

    protected CellModelBuilder(String sheetName, int rowIndex, int colIndex) {
        super(sheetName);
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }
    /**
     * 具体构建方法
     * @return
     */
    @Override
    abstract protected SheetModel build() ;
}
