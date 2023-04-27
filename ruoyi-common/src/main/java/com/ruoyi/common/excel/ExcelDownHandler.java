package com.ruoyi.common.excel;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.ruoyi.common.annotation.DropDown;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.spring.SpringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Excel表格下拉选
 *
 * @author Emil.Zhang
 */
@Slf4j
public class ExcelDownHandler implements SheetWriteHandler {

    /**
     * Excel表格中的列名
     */
    private static final String EXCEL_COLUMN_NAME = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 单选数据Sheet名
     */
    private static final String OPTIONS_SHEET_NAME = "options";
    /**
     * 联动选择数据Sheet名
     */
    private static final String LINKED_OPTIONS_SHEET_NAME = "linkedOptions";
    /**
     * 是否隐藏数据Sheet
     */
    private static final Boolean HIDDEN_FLAG = !"dev".equals(SpringUtils.getActiveProfile());
    /**
     * 下拉可选项
     */
    private final List<DropDownOptions> dropDownOptions;
    /**
     * 当前单选进度
     */
    private int currentOptionsColumnIndex;
    /**
     * 当前联动选择进度
     */
    private int currentLinkedOptionsSheetIndex;

    public ExcelDownHandler(List<DropDownOptions> options) {
        this.dropDownOptions = options;
        this.currentOptionsColumnIndex = 0;
        this.currentLinkedOptionsSheetIndex = 0;
    }

    /**
     * 开始创建下拉数据
     */
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        // 开始设置下拉框 HSSFWorkbook
        DataValidationHelper helper = sheet.getDataValidationHelper();
        Field[] fields = writeWorkbookHolder.getClazz().getDeclaredFields();
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        int length = fields.length;
        for (int i = 0; i < length; i++) {
            if (fields[i].isAnnotationPresent(DropDown.class)) {
                List<String> options = Arrays.asList(fields[i].getDeclaredAnnotation(DropDown.class).value());
                if (options.size() > 20) {
                    dropDownWithSheet(helper, workbook, sheet, i, options);
                } else {
                    dropDownWithSimple(helper, sheet, i, options);
                }
            }
        }
        dropDownOptions.forEach(everyOptions -> {
            // 如果传递了下拉框选择器参数
            if (!everyOptions.getNextOptions().isEmpty()) {
                // 当二级选项不为空时，使用额外关联表的形式
                dropDownLinkedOptions(helper, workbook, sheet, everyOptions);
            } else if (everyOptions.getOptions().size() > 10) {
                // 当一级选项参数个数大于10，使用额外表的形式
                dropDownWithSheet(helper, workbook, sheet, everyOptions.getIndex(), everyOptions.getOptions());
            } else if (everyOptions.getOptions().size() != 0) {
                // 当一级选项个数不为空，使用默认形式
                dropDownWithSimple(helper, sheet, everyOptions.getIndex(), everyOptions.getOptions());
            }
            // 否则不做处理
        });
    }

    /**
     * 简单下拉框
     */
    private void dropDownWithSimple(DataValidationHelper helper, Sheet sheet, Integer celIndex, List<String> value) {
        if (value.isEmpty()) {
            return;
        }
        this.markOptionsToSheet(helper, sheet, celIndex, helper.createExplicitListConstraint(value.toArray(new String[0])));
    }

    /**
     * 额外表格形式的级联下拉框
     */
    private void dropDownLinkedOptions(DataValidationHelper helper, Workbook workbook, Sheet sheet, DropDownOptions options) {
        String linkedOptionsSheetName = String.format("%s_%d", LINKED_OPTIONS_SHEET_NAME, currentLinkedOptionsSheetIndex);
        // 创建联动下拉数据表
        Sheet linkedOptionsDataSheet = workbook.createSheet(WorkbookUtil.createSafeSheetName(linkedOptionsSheetName));
        // 将下拉表隐藏
        workbook.setSheetHidden(workbook.getSheetIndex(linkedOptionsDataSheet), HIDDEN_FLAG);
        // 完善横向的一级选项数据表
        List<String> firstOptions = options.getOptions();
        Map<String, List<String>> secoundOptionsMap = options.getNextOptions();

        // 创建名称管理器
        Name name = workbook.createName();
        // 设置名称管理器的别名
        name.setNameName(linkedOptionsSheetName);
        // 以横向第一行创建一级下拉拼接引用位置
        String firstOptionsFunction = String.format("%s!$%s$1:$%s$1",
            linkedOptionsSheetName,
            getExcelColumnName(0),
            getExcelColumnName(firstOptions.size())
        );
        // 设置名称管理器的引用位置
        name.setRefersToFormula(firstOptionsFunction);
        // 设置数据校验为序列模式，引用的是名称管理器中的别名
        this.markOptionsToSheet(helper, sheet, options.getIndex(), helper.createFormulaListConstraint(linkedOptionsSheetName));

        for (int columIndex = 0; columIndex < firstOptions.size(); columIndex++) {
            // 先提取主表中一级下拉的列名
            String firstOptionsColumnName = getExcelColumnName(columIndex);
            // 一次循环是每一个一级选项
            int finalI = columIndex;
            // 本次循环的一级选项值
            String thisFirstOptionsValue = firstOptions.get(columIndex);
            // 创建第一行的数据
            Optional
                // 获取第一行
                .ofNullable(linkedOptionsDataSheet.getRow(0))
                // 如果不存在则创建第一行
                .orElseGet(() -> linkedOptionsDataSheet.createRow(finalI))
                // 第一行当前列
                .createCell(columIndex)
                // 设置值为当前一级选项值
                .setCellValue(thisFirstOptionsValue);

            // 第二行开始，设置第二级别选项参数
            List<String> secondOptions = secoundOptionsMap.get(thisFirstOptionsValue);
            if (ObjectUtil.isEmpty(secondOptions)) {
                // 必须保证至少有一个关联选项，否则将导致Excel解析错误
                secondOptions = Collections.singletonList("暂无_0");
            }

            // 以该一级选项值创建子名称管理器
            Name sonName = workbook.createName();
            // 设置名称管理器的别名
            sonName.setNameName(thisFirstOptionsValue);
            // 以第二行该列数据拼接引用位置
            String sonFunction = String.format("%s!$%s$2:$%s$%d",
                linkedOptionsSheetName,
                firstOptionsColumnName,
                firstOptionsColumnName,
                secondOptions.size() + 1
            );
            // 设置名称管理器的引用位置
            sonName.setRefersToFormula(sonFunction);
            // 数据验证为序列模式，引用到每一个主表中的二级选项位置
            // 创建子项的名称管理器，只是为了使得Excel可以识别到数据
            String mainSheetFirstOptionsColumnName = getExcelColumnName(options.getIndex());
            for (int i = 0; i < 100; i++) {
                // 以一级选项对应的主体所在位置创建二级下拉
                String secondOptionsFunction = String.format("=INDIRECT(%s%d)", mainSheetFirstOptionsColumnName, i + 1);
                // 二级只能主表每一行的每一列添加二级校验
                markLinkedOptionsToSheet(helper, sheet, i, options.getNextIndex(), helper.createFormulaListConstraint(secondOptionsFunction));
            }

            for (int rowIndex = 0; rowIndex < secondOptions.size(); rowIndex++) {
                // 从第二行开始填充二级选项
                int finalRowIndex = rowIndex + 1;
                int finalColumIndex = columIndex;

                Row row = Optional
                    .ofNullable(linkedOptionsDataSheet.getRow(finalRowIndex))
                    // 没有则创建
                    .orElseGet(() -> linkedOptionsDataSheet.createRow(finalRowIndex));
                Optional
                    // 在本级一级选项所在的列
                    .ofNullable(row.getCell(finalColumIndex))
                    // 不存在则创建
                    .orElseGet(() -> row.createCell(finalColumIndex))
                    // 设置二级选项值
                    .setCellValue(secondOptions.get(rowIndex));
            }
        }

        currentLinkedOptionsSheetIndex++;
    }

    /**
     * 额外表格形式的下拉框
     */
    private void dropDownWithSheet(DataValidationHelper helper, Workbook workbook, Sheet sheet, Integer celIndex, List<String> value) {
        // 创建下拉数据表
        Sheet simpleDataSheet = Optional.ofNullable(workbook.getSheet(WorkbookUtil.createSafeSheetName(OPTIONS_SHEET_NAME)))
            .orElseGet(() -> workbook.createSheet(WorkbookUtil.createSafeSheetName(OPTIONS_SHEET_NAME)));
        // 将下拉表隐藏
        workbook.setSheetHidden(workbook.getSheetIndex(simpleDataSheet), HIDDEN_FLAG);
        // 完善纵向的一级选项数据表
        for (int i = 0; i < value.size(); i++) {
            int finalI = i;
            // 获取每一选项行，如果没有则创建
            Row row = Optional.ofNullable(simpleDataSheet.getRow(i))
                .orElseGet(() -> simpleDataSheet.createRow(finalI));
            // 获取本级选项对应的选项列，如果没有则创建
            Cell cell = Optional.ofNullable(row.getCell(currentOptionsColumnIndex))
                .orElseGet(() -> row.createCell(currentOptionsColumnIndex));
            // 设置值
            cell.setCellValue(value.get(i));
        }

        // 创建名称管理器
        Name name = workbook.createName();
        // 设置名称管理器的别名
        String nameName = String.format("%s_%d", OPTIONS_SHEET_NAME, celIndex);
        name.setNameName(nameName);
        // 以纵向第一列创建一级下拉拼接引用位置
        String function = String.format("%s!$%s$1:$%s$%d",
            OPTIONS_SHEET_NAME,
            getExcelColumnName(currentOptionsColumnIndex),
            getExcelColumnName(currentOptionsColumnIndex),
            value.size());
        // 设置名称管理器的引用位置
        name.setRefersToFormula(function);
        // 设置数据校验为序列模式，引用的是名称管理器中的别名
        this.markOptionsToSheet(helper, sheet, celIndex, helper.createFormulaListConstraint(nameName));
        currentOptionsColumnIndex++;
    }

    /**
     * 挂载下拉的列，仅限一级选项
     */
    private void markOptionsToSheet(DataValidationHelper helper,
                                    Sheet sheet,
                                    Integer celIndex,
                                    DataValidationConstraint constraint) {
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, celIndex, celIndex);
        markDataValidationToSheet(helper, sheet, constraint, addressList);
    }

    /**
     * 挂载下拉的列，仅限二级选项
     */
    private void markLinkedOptionsToSheet(DataValidationHelper helper,
                                          Sheet sheet,
                                          Integer rowIndex,
                                          Integer celIndex,
                                          DataValidationConstraint constraint) {
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList addressList = new CellRangeAddressList(rowIndex, rowIndex, celIndex, celIndex);
        markDataValidationToSheet(helper, sheet, constraint, addressList);
    }

    /**
     * 应用数据校验
     */
    private void markDataValidationToSheet(DataValidationHelper helper,
                                           Sheet sheet,
                                           DataValidationConstraint constraint,
                                           CellRangeAddressList addressList) {
        // 数据有效性对象
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            //数据校验
            dataValidation.setSuppressDropDownArrow(true);
            //错误提示
            dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            dataValidation.createErrorBox("提示", "此值与单元格定义数据不一致");
            dataValidation.setShowErrorBox(true);
            //选定提示
            dataValidation.createPromptBox("填写说明：", "填写内容只能为下拉中数据，其他数据将导致导入失败");
            dataValidation.setShowPromptBox(true);
            sheet.addValidationData(dataValidation);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
    }

    /**
     * 获取栏位名
     */
    private String getExcelColumnName(int columnIndex) {
        // 26一循环的次数
        int columnCircleCount = columnIndex / 26;
        // 26一循环内的位置
        int thisCircleColumnIndex = columnIndex % 26;
        // 26一循环的次数大于0，则视为栏名至少两位
        String columnPrefix = columnCircleCount == 0
            ? ""
            : StrUtil.subWithLength(EXCEL_COLUMN_NAME, columnCircleCount, 1);
        // 从26一循环内取对应的栏位名
        String columnNext = StrUtil.subWithLength(EXCEL_COLUMN_NAME, thisCircleColumnIndex, 1);
        // 将二者拼接即为最终的栏位名
        return columnPrefix + columnNext;
    }

    /**
     * 下拉可选项
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DropDownOptions {
        /**
         * 一级下拉所在列index，从0开始算
         */
        private int index = 0;
        /**
         * 二级下拉所在的index，从0开始算，不能与一级相同
         */
        private int nextIndex = 0;
        /**
         * 一级下拉所包含的数据
         */
        private List<String> options = new ArrayList<>();
        /**
         * 二级下拉所包含的数据Map
         * <p>以每一个一级选项值为Key，每个一级选项对应的二级数据为Value</p>
         */
        private Map<String, List<String>> nextOptions = new HashMap<>();

        /**
         * 创建只有一级的下拉选
         */
        public DropDownOptions(int index, List<String> options) {
            this.index = index;
            this.options = options;
        }

        /**
         * 创建选项可选值
         * <p>注意：不能以数字，特殊符号开头，选项中不可以包含任何运算符号</p>
         *
         * @param vars 可选值内包含的参数
         * @return 合规的可选值
         */
        public static String createOptionValue(Object... vars) {
            StringBuilder stringBuffer = new StringBuilder();
            String regex = "^[\\S\\d\\u4e00-\\u9fa5]+$";
            for (int i = 0; i < vars.length; i++) {
                Object var = vars[i];
                if (!var.toString().matches(regex)) {
                    throw new ServiceException("选项数据不符合规则，仅允许使用中英文字符以及数字");
                }
                stringBuffer.append(StrUtil.trimToEmpty(var.toString()));
                if (i < vars.length - 1) {
                    // 直至最后一个前，都以_作为切割线
                    stringBuffer.append("_");
                }
            }
            if (stringBuffer.toString().matches("^\\d_*$")) {
                throw new ServiceException("禁止以数字开头");
            }
            return stringBuffer.toString();
        }
    }
}
