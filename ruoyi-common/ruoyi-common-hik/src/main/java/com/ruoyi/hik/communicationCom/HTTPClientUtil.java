package com.ruoyi.hik.communicationCom;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ruoyi.hik.commom.HttpClientConfig;
import com.ruoyi.hik.domain.HikEquipmentInfo;
import com.ruoyi.hik.domain.HikSecurity;
import com.ruoyi.hik.domain.HikSecurityUserCheck;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

public class HTTPClientUtil {

    public static CopyOnWriteArraySet<HTTPClientUtil> copyOnWriteArraySet = new CopyOnWriteArraySet<HTTPClientUtil>();

    //创建XmlMapper对象，用于实体与json和xml之间的相互转换
    private static final XmlMapper xmlMapper = new XmlMapper();

    /**
     * 配置类
     */
    private static final HttpClientConfig HTTP_CLIENT_CONFIG = new HttpClientConfig();;

    // 使用工厂类 HttpClients 进行创建
    // 1、默认配置创建
    private CloseableHttpClient httpClient;
    private HikEquipmentInfo equipmentInfo;

    /**
     * 请求配置
     */
    private static RequestConfig requestConfig;

    // 设置要访问的HttpHost,即是目标站点的HttpHost
    private HttpHost target = null;

    public static HTTPClientUtil getHikEquipmentHTTPClient(HikEquipmentInfo equipment) {
        for (HTTPClientUtil util : HTTPClientUtil.copyOnWriteArraySet) {
            if (util.getEquipmentInfo().getEquipmentIp().equals(equipment.getEquipmentIp())) {
                return util;
            }
        }
        return null;
    }

    public HikSecurityUserCheck doLogin(HikEquipmentInfo equipment) throws IOException {
        requestConfig = RequestConfig.custom()
            .setAuthenticationEnabled(true)
            .setConnectTimeout(HTTP_CLIENT_CONFIG.getConnectTimeout())
            .setConnectionRequestTimeout(HTTP_CLIENT_CONFIG.getConnectionRequestTimeout())
            .setSocketTimeout(HTTP_CLIENT_CONFIG.getSocketTimeout())
            .build();
        this.setTarget( new HttpHost(equipment.getEquipmentIp(), 80));

        // 2、使用 builder来创建，可以添加自定义配置
        // 自定义 connectionManager 连接管理器
        // 配置连接池关联
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(HTTP_CLIENT_CONFIG.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(HTTP_CLIENT_CONFIG.getMaxPreRoute());

        // 配置登录权限相关
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(equipment.getUsername(), equipment.getPassword());
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,credentials);


        // 初始化客户端
        httpClient = HttpClients.custom()
            .setConnectionManager(connectionManager)
            .setDefaultRequestConfig(requestConfig)
            .setDefaultCredentialsProvider(credentialsProvider)
            // 重试机制
            .setRetryHandler(new DefaultHttpRequestRetryHandler(HTTP_CLIENT_CONFIG.getRetryCount(), HTTP_CLIENT_CONFIG.isRequestSentRetryEnabled()))
            // 开启后台线程清除过期的连接
            .evictExpiredConnections()
            // 开启后台线程清除闲置的连接
            .evictIdleConnections(HTTP_CLIENT_CONFIG.getConnectionTimeToLive(), TimeUnit.SECONDS)
            .build();


        /**
         * <userCheck xmlns="http://www.isapi.org/ver20/XMLSchema" version="2.0">
         * <!--ro, req, object, 用户名密码匹配, attr:version{req, string, 协议版本}-->
         * <statusValue>
         * <!--ro, req, enum, 校验结果状态, subType:int, [200#200,401#401]-->200
         * </statusValue>
         * <statusString>
         * <!--ro, opt, enum, 校验结果状态字符信息, subType:string, [OK#OK,Unauthorized#Unauthorized]-->OK
         * </statusString>
         * <isDefaultPassword>
         * <!--ro, opt, bool, 是否是默认密码-->true
         * </isDefaultPassword>
         * <isRiskPassword>
         * <!--ro, opt, bool, 是否是风险密码-->true
         * </isRiskPassword>
         * <isActivated>
         * <!--ro, opt, bool, 是否已激活-->true
         * </isActivated>
         * <residualValidity>
         * <!--ro, opt, int, 密码剩余有效天数, desc:返回负值,表示密码已经超期使用,例如"-3表示密码已经超期使用3天-->1
         * </residualValidity>
         * <lockStatus>
         * <!--ro, opt, enum, 锁定状态, subType:string, [unlock#未锁定,locked#已锁定]-->unlock
         * </lockStatus>
         * <unlockTime>
         * <!--ro, opt, int, 解锁剩余时间, unit:s, unitType:时间-->1
         * </unlockTime>
         * <retryLoginTime>
         * <!--ro, opt, int, 重试次数-->1
         * </retryLoginTime>
         * </userCheck>
         */
        String url = "/ISAPI/Security/userCheck";

        HikSecurity security = xmlMapper.readValue(this.doGet(url), HikSecurity.class);
        if(security.getUserCheck().getStatusValue() ==200 ){
            copyOnWriteArraySet.add(this);
            this.setEquipmentInfo(equipment);
        }

        return security.getUserCheck();

    }

    public String doGet(String url) throws IOException {
        String result = null;
        HttpGet method = new HttpGet(url);
        method.setConfig(requestConfig);
        //response 对象
        HttpResponse response = this.httpClient.execute(this.getTarget(), method);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(response);
        if (statusCode != 200) {
            method.abort();
            // throw new ServiceException("请求设备失败 错误码:" + statusCode);
        }

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        // Release the connection
        // method.reset();


        return result;
    }

    public HttpResponse doPut(String url, String inbound) throws Exception {

    	HttpPut method = new HttpPut(url);
        method.setConfig(requestConfig);

        // method.setRequestBody(inbound);
        StringEntity stringEntity = new StringEntity(inbound);
        method.setEntity(stringEntity);

        //response 对象
        HttpResponse response = null;
        response = this.httpClient.execute(this.getTarget(), method);
        // Release the connection
        // method.reset();
        return response;
    }

    public String doPost(String url, String inbound) throws Exception {
        String result = null;
    	HttpPost method = new HttpPost(url);
        method.setConfig(requestConfig);
        StringEntity stringEntity = new StringEntity(inbound);
        method.setEntity(stringEntity);

        //response 对象
        HttpResponse response = this.httpClient.execute(this.getTarget(), method);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            method.abort();
            // throw new ServiceException("请求设备失败 错误码:" + statusCode);
        }

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);

        // Release the connection
        // method.reset();
        return result;
    }

    public HttpResponse doDelete(String url) throws Exception {

  	    HttpDelete method = new HttpDelete(url);
        method.setConfig(requestConfig);

        //response 对象
        HttpResponse response = null;
        response = this.httpClient.execute(this.getTarget(), method);
        // Release the connection
        // method.reset();
        return response;
    }

    //With the binary form POst method
    public HttpResponse doPostwithBinaryData(String url, String json, String jsonName, String image, String imageName, String boundary) throws Exception {

        HttpPost method = new HttpPost(url);
        method.setConfig(requestConfig);

        method.setHeader("Accept", "text/html, application/xhtml+xml");
        method.setHeader("Accept-Language", "zh-CN");
        method.setHeader("Content-Type","multipart/form-data; boundary=" + boundary);
        method.setHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
        method.setHeader("Accept-Encoding","gzip, deflate");
        method.setHeader("Connection","Keep-Alive");
        method.setHeader("Cache-Control","no-cache");

        String bodyParam =
                "--" + boundary + "\r\n"
              + "Content-Disposition: form-data; name=\""+jsonName+"\";\r\n"
              + "Content-Type: text/json\r\n"
              + "Content-Length: " + Integer.toString(json.length()) + "\r\n\r\n"
              +  json + "\r\n"
              + "--" + boundary + "\r\n"
              + "Content-Disposition: form-data; name=\""+imageName+"\";\r\n"
              + "Content-Type: image/jpeg\r\n"
              + "Content-Length: " + Integer.toString(image.length()) + "\r\n\r\n"
              + image
              + "\r\n--" + boundary + "--\r\n";

        //method.setRequestBody(bodyParam);
        StringEntity stringEntity = new StringEntity(bodyParam);
        method.setEntity(stringEntity);


        //response 对象
        HttpResponse response = null;
        response = this.httpClient.execute(this.getTarget(), method);
        // Release the connection
        // method.reset();
        return response;
    }

    public HttpResponse doPostStorageCloud(String url, String json,String faceimage,String boundary) throws Exception {

        HttpPost method = new HttpPost(url);
        method.setConfig(requestConfig);

        method.setHeader("Accept", "text/html, application/xhtml+xml");
        method.setHeader("Accept-Language", "zh-CN");
        method.setHeader("Content-Type","multipart/form-data; boundary=" + boundary);
        method.setHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
        method.setHeader("Accept-Encoding","gzip, deflate");
        method.setHeader("Connection","Keep-Alive");
        method.setHeader("Cache-Control","no-cache");

        String bodyParam =
                "--" + boundary + "\r\n"
              + "Content-Disposition: form-data; name=\"uploadStorageCloud\";\r\n"
              + "Content-Type: text/json\r\n"
              + "Content-Length: " + Integer.toString(json.length()) + "\r\n\r\n"
              +  json + "\r\n"
              + "--" + boundary + "\r\n"
              + "Content-Disposition: form-data; name=\"imageData\";\r\n"
              + "Content-Type: image/jpeg\r\n"
              + "Content-Length: " + Integer.toString(faceimage.length()) + "\r\n\r\n"
              + faceimage
              + "\r\n--" + boundary + "--\r\n";

        // method.setRequestBody(bodyParam);
        StringEntity stringEntity = new StringEntity(bodyParam);
        method.setEntity(stringEntity);

        //response 对象
        HttpResponse response = null;
        response = this.httpClient.execute(this.getTarget(), method);
        // Release the connection
        // method.reset();
        return response;
    }


//    public CloseableHttpClient getHttpClient() {
//        return httpClient;
//    }
//
//    public void setHttpClient(CloseableHttpClient httpClient) {
//        this.httpClient = httpClient;
//    }


    public HikEquipmentInfo getEquipmentInfo() {
        return equipmentInfo;
    }

    public void setEquipmentInfo(HikEquipmentInfo equipmentInfo) {
        this.equipmentInfo = equipmentInfo;
    }

    public HttpHost getTarget() {
        return target;
    }

    public void setTarget(HttpHost target) {
        this.target = target;
    }

}
