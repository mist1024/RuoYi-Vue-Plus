package com.ruoyi.common.excel.common;

import com.ruoyi.common.excel.builder.CellModelBuilder;
import lombok.Getter;

/**
 * 单元格信息
 *
 * @author liyang
 */
@Getter
public class SheetCellModel extends SheetModel {
    /**
     * 列索引
     */
     protected int colIndex;
    /**
     * 行索引
     */
     protected int rowIndex;

    public SheetCellModel(CellModelBuilder cellModelBuilder) {
        super(cellModelBuilder);
        this.rowIndex = cellModelBuilder.getRowIndex();
        this.colIndex = cellModelBuilder.getColIndex();
    }
    protected SheetCellModel(){
    }
}
