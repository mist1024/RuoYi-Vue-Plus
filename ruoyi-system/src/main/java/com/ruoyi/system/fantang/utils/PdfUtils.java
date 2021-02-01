package com.ruoyi.system.fantang.utils;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import com.ruoyi.system.fantang.common.NumberToList;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PdfUtils {

    public static void fillTemplate(String templatePath, String outputPath, Map<String, String> values) {

        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            out = new FileOutputStream(outputPath);
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

            BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            form.addSubstitutionFont(baseFont);

//            Map<String, String> map = new HashMap<>();
//            map.put("departName", "杜阮颐养院");
//            map.put("payUnit", "苏镜泉");
//            map.put("hospitalId", "R20081001");
//            Date date = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//            map.put("invoiceDate", sdf.format(date));
//            map.put("projName1", "预付款 2020.10.06 至 2021.02.01");
//            map.put("oneQian1", "1");
//            map.put("oneBai1", "1");
//            map.put("oneShi1", "1");
//            map.put("oneWan", "1");
//            map.put("oneQian2", "1");
//            map.put("oneBai2", "1");
//            map.put("oneShi2", "1");
//            map.put("oneYuan", "1");
//            map.put("oneJiao", "1");
//            map.put("oneFen", "1");
//            map.put("priceLite", "￥11111111.11");
//            map.put("priceBig", "壹仟壹佰壹拾壹万壹仟壹佰壹拾壹元壹角壹分");
//            map.put("remarks1", "测试收据样式");
//            map.put("payee", "江门市第三人民医院");

            for (String name : form.getFields().keySet()) {
                form.setField(name, values.get(name));
            }
            //true代表生成的PDF文件不可编辑
            stamper.setFormFlattening(true);
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void generatePrepaymentPdf(String templatePath, String outputPath, JSONObject params) {

        // 数据转换
        Map<String, String> values = new HashMap<>();
        values.put("departName", params.getString("departName"));
        values.put("payUnit", params.getString("name"));
        values.put("hospitalId", params.getString("hospitalId"));

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

        values.put("hospitalId", params.getString("hospitalId"));
        values.put("invoiceDate", sdf.format(date));
        values.put("projName1", "预付款");

        BigDecimal prepaid = params.getBigDecimal("prepaid");
        float prepaidFloat = prepaid.floatValue();
        Map<String, String> convert = NumberToList.convertTo(prepaidFloat, "1");
        System.out.println(convert);
        values.put("fen1", convert.get("fen1"));
        values.put("jiao1", convert.get("jiao1"));
        values.put("yuan1", convert.get("yuan1"));
        values.put("shi1", convert.get("shi1"));
        values.put("bai1", convert.get("bai1"));
        values.put("qian1", convert.get("qian1"));
        values.put("wan1", convert.get("wan1"));
        values.put("shiwan1", convert.get("shiwan1"));
        values.put("baiwan1", convert.get("baiwan1"));
        values.put("qianwan1", convert.get("qianwan1"));

        values.put("priceLite", "¥" + prepaid.toString());

        String convertToHan = NumberToList.convertToHan(prepaidFloat, "1");
        values.put("priceBig", convertToHan);

        values.put("payee", "江门市第三人民医院");

        fillTemplate(templatePath, outputPath, values);
    }
}
