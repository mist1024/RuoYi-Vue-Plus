package com.ruoyi.winery.controller.mini;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.BackgroundImage;
import com.itextpdf.layout.property.TextAlignment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author tottimctj
 * @since 2020-11-10
 */
@RestController
@RequestMapping("/winery/test")
@Slf4j
public class TestController {

    public static final String REGULAR =
            "classpath:/fonts/PingFang_Heavy.ttf";


    public static void main(String[] args) throws Exception {

        FontProgram fontProgram =
                FontProgramFactory.createFont(REGULAR);


        String path = "/Users/tottimctj/Downloads/temp.pdf";

        PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        PdfFont font2 = PdfFontFactory.createFont(
                fontProgram, PdfEncodings.IDENTITY_H, true);

        PdfWriter writer = new PdfWriter(new FileOutputStream(new File(path)));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, new PageSize(500f,505f));
        document.setMargins(0, 0, 0, 0);
        Paragraph p = new Paragraph();
        p.setMarginTop(0);
        p.setHeight(500);
        p.setWidth(500);
        p.setFontSize(25);
//        Text text1 = new Text("字体1希望软件!").setFont(font);
//
//        text1.setTextAlignment(TextAlignment.CENTER);
//        text1.setRelativePosition(200, 200, 200, 200);
//        p.add(text1);

        Text text2 = new Text("字体2希望软件!").setFont(font2);
        text2.setTextAlignment(TextAlignment.CENTER);
        text2.setRelativePosition(0, 200, 0, 0);
        p.add(text2);

//        p.setBorder(new SolidBorder(DeviceGray.BLACK,0.5f));//边框
//        p.setBackgroundColor(ColorConstants.GREEN);//绿色你懂的


//        Image image = new Image(ImageDataFactory.create("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=28918940,1444141489&fm=26&gp=0.jpg"));
//        image.setHeight(300);
//        image.setWidth(200);
//        BackgroundSize backgroundSize = new BackgroundSize();


        PdfImageXObject imageXObject = new PdfImageXObject(ImageDataFactory.create("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=28918940,1444141489&fm=26&gp=0.jpg"));

        BackgroundImage backgroundImage = new BackgroundImage(imageXObject);
        p.setBackgroundImage(backgroundImage);


        document.add(p);


        document.close();
        writer.close();
        pdf.close();


    }

}
