package com.ruoyi.test;

import cn.hutool.http.HttpUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpReq {

    //private  OkHttpClient client = new OkHttpClient();
    private final Request.Builder builder = new Request.Builder();
    private final OkHttpClient client = new OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build(); //设置各种超时时间

    /**
     @param  isAddXuexinHeader 是否加入学信网header到请求头
     */
    public HttpReq(boolean isAddXuexinHeader){
        if (isAddXuexinHeader == true){
            builder.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            builder.addHeader("Accept-Encoding","gzip, deflate, sdch, br");
            builder.addHeader("Accept-Language","zh-CN,zh;q=0.8");
            builder.addHeader("Cache-Control","max-age=0");
            builder.addHeader("Connection","keep-alive");
            builder.addHeader("Host","my.chsi.com.cn");
            builder.addHeader("If-Modified-Since","Tue, 13 Mar 2018 10:14:31 GMT");
            builder.addHeader("Upgrade-Insecure-Requests","1");
            builder.addHeader( "User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        }
    }

    /**
     @param cookie 加入cookie到请求头
     */
    public void addCookieToHeader(String cookie){
        builder.addHeader("cookie",cookie);
    }

    /**
     @param url get请求的url
     */
    public String get(String url)  {
        builder.url(url);
        final Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     @param  url 图片的地址
      * */
    public void savePicture(String url)  {
        builder.url(url);
        final Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            byte[] bt = response.body().bytes();
            byte2image(bt, "./xueji.png");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void byte2image(byte[] data,String path){
        try {
            File file = new File(path);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data, 0, data.length);
            fos.flush();
            fos.close();
            System.out.println("保存图片成功。。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void testBaidu(){
        HttpReq baiduHttp = new HttpReq(false);
        String respStr = baiduHttp.get("http://www.baidu.com");
        System.out.println(respStr);
    }

    public static void main(String[] args)  {
        // testBaidu();
//        HttpReq xuexinHttp = new HttpReq(true);
//        xuexinHttp.addCookieToHeader("aliyungf_tc=a44cd76725072e97c6625bc216dcf07870bb8f59efdab8e85142cd2903f45841; acw_tc=707c9f7216885291604143483e1ffdbde1d42ca14b99ec2b9ec8d2c48e0eb1; JSESSIONID=92648EE2F4820DEDD6FB627E7F9DFABE; CHSICC_CLIENTFLAGCHSI=d6d1556d12784a270db64720aa7652f7; goaYXsyEWlxdO=60.I6m8rZy8fEGYpPmjsxJzcQD7H_fstonBF3XyGvvyhDh1_7c_JIFFrAfHsmKk6KtOVWbM2ztenFjMrPRSWIm1a; Hm_lvt_9c8767bf2ffaff9d16e0e409bd28017b=1688529126; zg_did=%7B%22did%22%3A%20%22189242dc17d992-0d47d539a4c70c-3d267449-144000-189242dc17e9f3%22%7D; _gid=GA1.3.678250233.1688529126; CHSICC01=!bLuJc+pYd0Ah1bvzYxYLahOzddj6Y5Wo66QcNq34LuAZUVPejROt5X6Oa37p4b53SrFxCblP8+yMsQ==; zg_14e129856fe4458eb91a735923550aa6=%7B%22sid%22%3A%201688529125761%2C%22updated%22%3A%201688529387258%2C%22info%22%3A%201688529125764%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%7D%22%2C%22referrerDomain%22%3A%20%22%22%7D; Hm_lpvt_9c8767bf2ffaff9d16e0e409bd28017b=1688529387; _gat_gtag_UA_100524_1=1; _ga_8YMQD1TE48=GS1.1.1688529126.1.1.1688529387.0.0.0; _ga=GA1.1.2127309674.1688529126; goaYXsyEWlxdP=0jBX.aMEoAQAFi9Gmc9MppqIgDOlO8y_aQsmwyuvCdpbPOREblHy3rBYZ3akqvFSvw4wcAomCzMD_pFYl8MVkZ2D23rBjUNOxrqv.5iDjY66XvY1Pna6naF2xfKgvnvDTi5eOmrfSMgXiCW0EXcoGodkTJzbf6zmAqNcyw9aqpiBX980BsYj_Cgp5dDZvXxPK6yti7dHIQ_Oc7fWCOHPmjc7fRt.Xb0WxDqaQ7nQHckj9in8oSZf6uK6M5cjzp804QvVOiUoBq07KdCj_jVLIJndVclyF8iiT5Y5ytnCRogjhcgk_iGonLzXlugXQ7gfCbLlgqtmvJCIkhX99I0PwWYJ_Y5xuQuDhUM7kfwqBJSxm6MrozS7XHgvpKxTtg7pwg07awfezZWbh4ZCNidpXf5E90ppv4hFizfKH7ZUgQ0a");  //由webview执行js返回。
//        xuexinHttp.savePicture("https://www.chsi.com.cn/xlcx/bg.do?vcode=AA33TE9X16WA202D&srcid=bgcx"); //由webview执行js返回。
        String s = HttpUtil.get("https://www.chsi.com.cn/xlcx/bg.do?vcode=AA33TE9X16WA202D&srcid=bgcx");
        parseXueJi(s);

    }

    /**
     * 学籍解析
     */
    private static void parseXueJi(String strHtml) {
        Document doc = Jsoup.parse(strHtml, "UTF-8");
        Elements eleDiv2 = doc.getElementsByClass("report-info");
        if (eleDiv2 != null && !eleDiv2.isEmpty()) {
            Elements eleTd = eleDiv2.get(0).getElementsByTag("div");
            String text = eleTd.get(4).text();
           /* if (eleTd != null && !eleTd.isEmpty()) {
                StuInfo stuInfo = new StuInfo();
                // 姓名是图片，调用腾讯API实现ocr识别
                String nameImg = eleTd.get(1).getElementsByTag("img").get(0).attr("src");
                stuInfo.setName(aiOcr(nameImg));
                stuInfo.setGender(eleTd.get(4).text());
                stuInfo.setIdCard(eleTd.get(6).text());
                stuInfo.setNation(eleTd.get(8).text());
                stuInfo.setBirthDay(eleTd.get(10).text());
                stuInfo.setUniversity(eleTd.get(12).text());
                stuInfo.setLevel(eleTd.get(14).text());
                stuInfo.setDepartment(eleTd.get(16).text());
                stuInfo.setSClass(eleTd.get(18).text());
                stuInfo.setDomain(eleTd.get(20).text());
                stuInfo.setStuNum(eleTd.get(22).text());
                stuInfo.setForm(eleTd.get(24).text());
                stuInfo.setEntranceDate(eleTd.get(26).text());
                stuInfo.setLenOfSchooling(eleTd.get(28).text());
                stuInfo.setType(eleTd.get(30).text());
                String[] status = eleTd.get(32).text().split("\\(");
                stuInfo.setStatus(status[0]);
                stuInfo.setGraduationDate(status[1].substring(0, status[1].length() - 1));*/
//                return stuInfo;
//            }
        }
//        return null;
    }

    /**
     * 学历解析
     */
    /*private static StuInfo parseXueLi(String strHtml) {
        Document doc = Jsoup.parse(strHtml, "UTF-8");
        Elements eleDiv2 = doc.getElementsByClass("div2");
        if (eleDiv2 != null && !eleDiv2.isEmpty()) {
            Elements eleTd = eleDiv2.get(0).getElementsByTag("td");
            if (eleTd != null && !eleTd.isEmpty()) {
                StuInfo stuInfo = new StuInfo();
                // 姓名是图片，调用腾讯API实现ocr识别
                String nameImg = eleTd.get(0).getElementsByTag("img").get(0).attr("src");
                stuInfo.setName(aiOcr(nameImg));
                stuInfo.setGender(eleTd.get(2).text());
                stuInfo.setBirthDay(eleTd.get(3).text());
                stuInfo.setEntranceDate(eleTd.get(4).text());
                stuInfo.setGraduationDate(eleTd.get(5).text());
                stuInfo.setType(eleTd.get(6).text());
                stuInfo.setLevel(eleTd.get(7).text());
                stuInfo.setUniversity(eleTd.get(8).text());
                stuInfo.setLenOfSchooling(eleTd.get(9).text());
                stuInfo.setDomain(eleTd.get(10).text());
                stuInfo.setForm(eleTd.get(11).text());
                stuInfo.setCertificateNum(eleTd.get(12).text());
                // 状态是图片，调用腾讯API实现ocr识别
                String statusImg = eleTd.get(13).getElementsByTag("img").get(0).attr("src");
                stuInfo.setStatus(aiOcr(statusImg));
                stuInfo.setPresident(eleTd.get(14).text());
                return stuInfo;
            }
        }
        return null;
    }*/
}
