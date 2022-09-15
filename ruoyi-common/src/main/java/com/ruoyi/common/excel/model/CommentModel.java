package com.ruoyi.common.excel.model;

import com.ruoyi.common.excel.common.SheetCellModel;
import lombok.Getter;

/**
 * 批注信息类
 *
 * @author liyang
 */
@Getter
public class CommentModel extends SheetCellModel {
    /**
     * 批注内容
     */
    private String commentContent;

    private CommentModel() {
    }

    /**
     * 生成批注信息
     *
     * @param sheetName      sheet页名称
     * @param rowIndex       行号
     * @param columnIndex    列号
     * @param commentContent 批注内容
     * @return
     */
    public static CommentModel createCommentModel(String sheetName, int rowIndex, int columnIndex, String commentContent) {
        CommentModel commentModel = new CommentModel();
        //sheet页名称
        commentModel.sheetName = sheetName;
        //行号
        commentModel.rowIndex = rowIndex;
        //列号
        commentModel.colIndex = columnIndex;
        //批注内容
        commentModel.commentContent = commentContent;
        return commentModel;
    }
}
