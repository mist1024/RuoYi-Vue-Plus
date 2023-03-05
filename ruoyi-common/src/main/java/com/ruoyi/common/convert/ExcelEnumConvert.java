package com.ruoyi.common.convert;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.ruoyi.common.annotation.ExcelEnumFormat;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 枚举格式化转换处理
 *
 * @author Liang
 */
@Slf4j
public class ExcelEnumConvert implements Converter<Object> {

    @Override
    public Class<Object> supportJavaTypeKey() {
        return Object.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public Object convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        beforeConverter(contentProperty);
        Object codeValue = cellData.getData();
        // 如果是空值
        if (ObjectUtil.isNull(codeValue)) {
            return null;
        }
        Map<Object, String> enumValueMap = beforeConverter(contentProperty);
        String textValue = enumValueMap.get(codeValue);
        return Convert.convert(contentProperty.getField().getType(), textValue);
    }

    @Override
    public WriteCellData<String> convertToExcelData(Object object, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (ObjectUtil.isNull(object)) {
            return new WriteCellData<>("");
        }
        Map<Object, String> enumValueMap = beforeConverter(contentProperty);
        String value = Convert.toStr(enumValueMap.get(object));
        return new WriteCellData<>(value);
    }

    private Map<Object, String> beforeConverter(ExcelContentProperty contentProperty) {
        ExcelEnumFormat anno = getAnnotation(contentProperty.getField());
        Map<Object, String> enumValueMap = new HashMap<>();
        Enum<?>[] enumConstants = anno.enumClass().getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            try {
                Field codeFieldObj = enumConstant.getClass().getDeclaredField(anno.codeField());
                codeFieldObj.setAccessible(true);
                Object codeValue = codeFieldObj.get(enumConstant);
                Field textFieldObj = enumConstant.getClass().getDeclaredField(anno.textField());
                textFieldObj.setAccessible(true);
                String textValue = (String) textFieldObj.get(enumConstant);
                enumValueMap.put(codeValue, textValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("枚举格式化转换处理异常", e);
                throw new RuntimeException("枚举格式化转换处理异常");
            }
        }
        return enumValueMap;
    }

    private ExcelEnumFormat getAnnotation(Field field) {
        return AnnotationUtil.getAnnotation(field, ExcelEnumFormat.class);
    }
}
