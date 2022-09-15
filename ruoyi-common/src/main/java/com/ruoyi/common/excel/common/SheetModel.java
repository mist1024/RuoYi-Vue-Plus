package com.ruoyi.common.excel.common;

import com.ruoyi.common.excel.builder.SheetModelBuilder;
import lombok.Getter;

/**
 * sheet页信息
 *
 * @author liyang
 */
@Getter
public class SheetModel {
    /**
     * sheet名称
     */
    protected String sheetName;

    public SheetModel(SheetModelBuilder sheetModelBuilder) {
        this.sheetName = sheetModelBuilder.getSheetName();
    }

    protected SheetModel() {

    }
}
