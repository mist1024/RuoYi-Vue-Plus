package com.ruoyi.winery.utils;

import cn.hutool.core.collection.CollUtil;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.select.Elements;
import com.ruoyi.common.utils.StringUtils;

/**
 * @author kino
 * @since 2021/01/19 9:49
 */
public class RichTextUtil {

    public static Document setImgStyle(String richText, String style) {
        Document doc = Jsoup.parse(richText);
        Elements img = doc.getElementsByTag("img");
        if (CollUtil.isNotEmpty(img)) {
            for (Element i : img) {
                String s = i.attr("style");
                if (StringUtils.isEmpty(s)) {
                    i.attr("style", style);
                }
            }
        }
        return doc;
    }
}
