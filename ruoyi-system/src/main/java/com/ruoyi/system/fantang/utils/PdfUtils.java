package com.ruoyi.system.fantang.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PdfUtils {

    public static void fillTemplate(String templatePath, String outputPath, Map<String, Object> values) {

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

            Map<String, String> map = new HashMap<>();
            map.put("departName", "杜阮颐养院");
            map.put("payUnit", "苏镜泉");
            map.put("hospitalId", "R20081001");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            map.put("invoiceDate", sdf.format(date));
            map.put("projName1", "预付款 2020.10.06 至 2021.02.01");
            map.put("oneQian1", "1");
            map.put("oneBai1", "1");
            map.put("oneShi1", "1");
            map.put("oneWan", "1");
            map.put("oneQian2", "1");
            map.put("oneBai2", "1");
            map.put("oneShi2", "1");
            map.put("oneYuan", "1");
            map.put("oneJiao", "1");
            map.put("oneFen", "1");
            map.put("priceLite", "￥11111111.11");
            map.put("priceBig", "壹仟壹佰壹拾壹万壹仟壹佰壹拾壹元壹角壹分");
            map.put("remarks1", "测试收据样式");
            map.put("payee", "江门市第三人民医院");


            for (String name : form.getFields().keySet()) {
                form.setField(name, map.get(name));
            }
            //true代表生成的PDF文件不可编辑
            stamper.setFormFlattening(false);
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
}
