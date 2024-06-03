package org.dromara.guide.client;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;
import org.dromara.guide.domain.vo.TrafficVo;

/**
 * 高德地图API请求服务
 * <p>参考文档：<a href="https://lbs.amap.com/api/webservice/guide/api/traffic-incident">GuideTrafficClient</a></p>
 *
 * @author AprilWind
 */
@BaseRequest(baseURL = "https://et-api.amap.com", charset = "UTF-8")
public interface GuideTrafficClient {

    /**
     * 交通事件查询
     *
     * @param adcode       城市代码：授权城市ADCODE（城市级）
     * @param eventType    事件类型：仅获取对应类型事件，多个;分割
     * @param isExpressway 只获取高速：仅获取高速类事件。1-是   0-否
     * @return TrafficVo 事件信息
     */
    @Get(url = "/event/queryByAdcode", interceptor = GuideTrafficIntercepto.class)
    TrafficVo queryByAdcode(@Query("adcode") String adcode, @Query("eventType") String eventType, @Query("isExpressway") String isExpressway);

}
