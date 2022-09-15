package com.ruoyi.common.excel.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.ruoyi.common.excel.model.CommentModel;
import com.ruoyi.common.utils.poi.PoiExcelUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义批注处理器
 *
 * @author liyang
 */
public class CustomCommentWriteHandler implements RowWriteHandler {
    /**
     * sheet页名称列表
     */
    private List<String> sheetNameList;
    List<CommentModel> commentList = new ArrayList<>();

    /**
     * 自定义批注适配器构造方法
     *
     * @param commentList 批注信息
     * @param extension   文件后缀（xlsx、xls）
     */
    public CustomCommentWriteHandler(List<CommentModel> commentList, String extension) {
        if (CollUtil.isEmpty(commentList)) {
            return;
        }
        //文件不为指定的格式时，默认为Xlsx
        if (StrUtil.equals(extension, "xlsx") == false && StrUtil.equals(extension, "xls") == false) {
            extension = "xlsx";
        }
        this.commentList = commentList.stream().filter(x ->
                StrUtil.isNotBlank(x.getSheetName()) && x.getColIndex() >=0 && x.getRowIndex() >= 0 && StrUtil.isNotBlank(x.getCommentContent())
        ).collect(Collectors.toList());
        sheetNameList = this.commentList.stream().map(x -> x.getSheetName()).distinct().collect(Collectors.toList());
        this.extension = extension;
    }

    /**
     * 文档后缀名
     */
    private String extension;

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                                Integer relativeRowIndex, Boolean isHead) {
        Sheet sheet = writeSheetHolder.getSheet();
        //不需要添加批注，或者当前sheet页不需要添加批注
        if (CollUtil.isEmpty(commentList) || sheetNameList.contains(sheet.getSheetName()) == false) {
            return;
        }
        //获取当前行的批注信息
        List<CommentModel> rowCommentList = commentList.stream().filter(x ->
                StrUtil.equals(x.getSheetName(), sheet.getSheetName())
                        && StrUtil.equals(String.valueOf(relativeRowIndex), String.valueOf(x.getRowIndex()))).collect(Collectors.toList());
        //当前行没有批注信息
        if (CollUtil.isEmpty(rowCommentList)) {
            return;
        }
        List<Integer> colIndexList = rowCommentList.stream().map(x -> x.getColIndex()).distinct().collect(Collectors.toList());
        for (Integer colIndex : colIndexList) {
            //同一单元格的批注信息
            List<CommentModel> cellCommentList = rowCommentList.stream().filter(x ->
                    StrUtil.equals(String.valueOf(colIndex), String.valueOf(x.getColIndex()))).collect(Collectors.toList());
            if (CollUtil.isEmpty(cellCommentList)) {
                continue;
            }
            //批注内容拼成一条
            String commentContent = cellCommentList.stream().map(x -> x.getCommentContent()).collect(Collectors.joining());
            Cell cell = row.getCell(colIndex);
            PoiExcelUtil.addComment(cell, commentContent, extension);
        }
        //删除批注信息
        commentList.remove(rowCommentList);
        //重新获取要添加的sheet页姓名
        sheetNameList = commentList.stream().map(x -> x.getSheetName()).distinct().collect(Collectors.toList());
    }
}
