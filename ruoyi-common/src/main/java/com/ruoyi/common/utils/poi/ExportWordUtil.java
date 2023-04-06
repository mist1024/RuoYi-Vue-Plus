package com.ruoyi.common.utils.poi;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.util.PoitlIOUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * word模板导出
 * @author ljl
 */
public class ExportWordUtil {
    private ExportWordUtil() {
    }

    /**
     * 1.compile 编译模板
     * 2.render  渲染数据
     * 3.输出到流
     * @param hashMap
     */
    public static String createWord(String templatePath, String fileDir, String fileName,  Map<String,Object> hashMap ){
        Assert.notNull(templatePath, "word模板文件路径不能为空");
        Assert.notNull(fileDir, "生成的文件存放地址不能为空");
        Assert.notNull(fileName, "生成的文件名不能为空");

        // 生成的word格式
        String formatSuffix = ".docx";
        // 拼接后的文件名
        fileName = fileName + formatSuffix;

        // 生成的文件的存放路径
        if (!fileDir.endsWith("/")) {
            fileDir = fileDir + File.separator;
        }

        File dir = new File(fileDir);
        if (!dir.exists()) {
            System.out.println("生成word数据时存储文件目录{}不存在,为您创建文件夹!");
            dir.mkdirs();
        }

        String filePath = fileDir + fileName;
        // 读取模板templatePath并将paramMap的内容填充进模板，即编辑模板+渲染数据
        XWPFTemplate template = XWPFTemplate.compile(templatePath).render(hashMap);
        try {
            // 将填充之后的模板写入filePath
            template.writeToFile(filePath);
            template.close();
        } catch (Exception e) {
            System.out.println("生成word异常");
            e.printStackTrace();
            return "";
        }
        return filePath;
    }

    /**
     * 输出到网络中
     * @param response
     * @param templatePath
     * @param fileName
     * @param hashMap
     * @return
     */
    public static String createWord(HttpServletResponse response, String templatePath, String fileName, Map<String,Object> hashMap){
        Assert.notNull(templatePath, "word模板文件路径不能为空");
        //Assert.notNull(fileDir, "生成的文件存放地址不能为空");
        Assert.notNull(fileName, "生成的文件名不能为空");
        // 生成的word格式
        String formatSuffix = ".docx";
        // 拼接后的文件名
//        fileName = fileName + formatSuffix;
        // 读取模板templatePath并将paramMap的内容填充进模板，即编辑模板+渲染数据
        XWPFTemplate  template = XWPFTemplate.compile(templatePath).render(hashMap);
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/msword");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition","attachment;filename="+new String(fileName.getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1));
            OutputStream out = response.getOutputStream();
            // 将填充之后的模板写入filePath
//            template.write(out);
//            out.flush();
//            template.close();
            // HttpServletResponse response
            BufferedOutputStream bos = new BufferedOutputStream(out);

            template.write(bos);
            bos.flush();
            out.flush();
            PoitlIOUtils.closeQuietlyMulti(template, bos, out);
        } catch (Exception e) {
            System.out.println("生成word异常");
            e.printStackTrace();
            return "";
        }
        return fileName;
    }


    public static String createWord1(HttpServletResponse response, HttpServletRequest request, String templatePath, String fileName, Map<String,Object> hashMap, Configure configure){
        Assert.notNull(templatePath, "word模板文件路径不能为空");
        //Assert.notNull(fileDir, "生成的文件存放地址不能为空");
        Assert.notNull(fileName, "生成的文件名不能为空");
        // 生成的word格式
        String formatSuffix = ".docx";
        // 拼接后的文件名
        //fileName = fileName + formatSuffix;
        // 读取模板templatePath并将paramMap的内容填充进模板，即编辑模板+渲染数据
        XWPFTemplate template = XWPFTemplate.compile(templatePath,configure).render(hashMap);
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition","attachment;filename="+new String(fileName.getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1));
            //OutputStream out = response.getOutputStream();
            OutputStream out = response.getOutputStream();
            //BufferedOutputStream bos = new BufferedOutputStream(out);
            // 将填充之后的模板写入filePath
            template.write(out);
            template.close();
        } catch (Exception e) {
            System.out.println("生成word异常");
            e.printStackTrace();
            return "";
        }
        return fileName;
    }
}
