package org.dromara.demo.client.gd;

import cn.hutool.core.util.CoordinateUtil;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Var;
import org.dromara.demo.client.gd.vo.*;

/**
 * 高德请求工具
 *
 * @author bkywksj
 */
@BaseRequest(baseURL = "https://restapi.amap.com", interceptor = GdInterceptor.class)
public interface GdClient {


    /**
     * 根据ip获取ip信息
     *
     * @param ip   ip地址
     * @param type 4代表ipv4
     * @return 地址信息
     */
    @Get(url = "/v3/ip")
    IPVO getLocationByIp(@Query("ip") String ip, @Query("type") String type);

    /**
     * 根据位置获取经纬度信息 地理编码
     *
     * @param address 位置
     * @return 地理解析返回对象
     */
    @Get(url = "/v3/geocode/geo?output=json")
    GeoVO geo(@Query("address") String address);

    /**
     * 根据经纬度获取位置等信息 地理逆编码
     *
     * @param coord 经纬度
     * @return 地理解析返回对象
     */
    @Get(url = "/v3/geocode/regeo?output=json&location=${coord.lng},${coord.lat}")
    RegeoVO regeo(@Var("coord") CoordinateUtil.Coordinate coord);

    /**
     * 根据位置获取经纬度信息 地理编码
     *
     * @param adcode adcode
     * @return 地理解析返回对象
     */
    @Get(url = "/v3/weather/weatherInfo")
    Weather getWeatherByAdcode(@Query("city") String adcode);

    /**
     * 根据位置获取经纬度信息 地理编码
     *
     * @param start 起始经纬度
     * @param end   终止经纬度
     * @return 距离信息
     */
    @Get(url = "/v3/distance?type=0&origins=${origins.lng},${origins.lat}&destination=${destination.lng},${destination.lat}")
    DistanceVo getDistance(@Var("origins") CoordinateUtil.Coordinate start, @Query("destination") CoordinateUtil.Coordinate end);

}
