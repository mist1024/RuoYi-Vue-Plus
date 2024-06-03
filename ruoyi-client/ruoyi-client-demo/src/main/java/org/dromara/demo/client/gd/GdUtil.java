package org.dromara.demo.client.gd;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.CoordinateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.dtflys.forest.config.ForestConfiguration;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.demo.client.gd.vo.*;

import java.util.List;

/**
 * 高德地图API
 *
 * @author ye
 */
public class GdUtil {

    private static GdClient CLIENT;

    static {
        try {
            CLIENT = SpringUtils.getBean(GdClient.class);
        } catch (Exception ignored) {
        }
        //在springboot容器中拿不到就创建一个
        if (ObjectUtil.isNull(CLIENT)) {
            //创建默认forest配置，创建一个GdClient实例
            CLIENT = ForestConfiguration.createConfiguration().createInstance(GdClient.class);
        }
    }

    /**
     * 根据位置获取经纬度等信息 地理编码
     *
     * @param address 位置
     * @return 经纬度等信息
     */
    public static GeoVO geo(String address) {
        return CLIENT.geo(address);
    }

    /**
     * 根据位置获取经纬度
     *
     * @param address 位置
     * @return 经纬度
     */
    public static CoordinateUtil.Coordinate getLocationByAddress(String address) {
        GeoVO geo = CLIENT.geo(address);
        //要进行非空判断
        if (ObjectUtil.isNull(geo)) {
            return null;
        }
        List<GeoVO.GeocodesDTO> geocodes = geo.getGeocodes();
        if (geocodes.isEmpty()) {
            return null;
        }
        String location = geocodes.get(0).getLocation();
        String[] split = location.split(",");
        if (split.length != 2) {
            return null;
        }
        return new CoordinateUtil.Coordinate(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
    }

    /**
     * 根据经纬度获取位置等信息 地理逆编码
     *
     * @param coord 经纬度
     * @return 地理解析返回对象
     */
    public static RegeoVO regeo(CoordinateUtil.Coordinate coord) {
        if (ObjectUtil.isNull(coord.getLng()) || ObjectUtil.isNull(coord.getLat())) {
            return null;
        }
        return CLIENT.regeo(coord);
    }

    /**
     * 根据经纬度获取具体位置 地理逆编码
     *
     * @param coord 经纬度
     * @return 地址
     */
    public static String getAddressByCoord(CoordinateUtil.Coordinate coord) {
        RegeoVO regeo = regeo(coord);
        if (ObjectUtil.isNull(regeo)) {
            return null;
        }
        RegeoVO.RegeocodeDTO regeocode = regeo.getRegeocode();
        if (ObjectUtil.isNull(regeocode)) {
            return null;
        }
        return regeocode.getFormattedAddress();
    }

    /**
     * 根据经纬度获取adcode
     *
     * @param coord 经纬度
     * @return adcode
     */
    public static String getAdcodeByCoord(CoordinateUtil.Coordinate coord) {
        RegeoVO regeo = regeo(coord);
        if (ObjectUtil.isNull(regeo)) {
            return null;
        }
        RegeoVO.RegeocodeDTO regeocode = regeo.getRegeocode();
        if (ObjectUtil.isNull(regeocode)) {
            return null;
        }
        RegeoVO.RegeocodeDTO.AddressComponentDTO addressComponent = regeocode.getAddressComponent();
        if (ObjectUtil.isNull(addressComponent)) {
            return null;
        }
        return addressComponent.getAdcode();
    }

    /**
     * 根据位置获取adcode
     *
     * @param address 位置
     * @return adcode
     */
    public static String getAdcodeByAddress(String address) {
        GeoVO geo = geo(address);
        if (geo == null) {
            return null;
        }
        List<GeoVO.GeocodesDTO> geocodes = geo.getGeocodes();
        if (CollUtil.isEmpty(geocodes)) {
            return null;
        }
        return geocodes.get(0).getAdcode();
    }

    /**
     * 根据adcode获取天气等信息
     *
     * @param adcode adcode
     * @return 天气
     */
    public static Weather.LivesDTO getWeatherByAdcode(@NotBlank String adcode) {
        Weather weather = CLIENT.getWeatherByAdcode(adcode);
        if (ObjectUtil.isNull(weather)) {
            return null;
        }
        List<Weather.LivesDTO> lives = weather.getLives();
        if (CollUtil.isEmpty(lives)) {
            return null;
        }
        return lives.get(0);
    }

    /**
     * 根据经纬度获取天气
     *
     * @param coord 经纬度
     * @return 天气
     */
    public static Weather.LivesDTO getWeatherByCoord(CoordinateUtil.Coordinate coord) {
        String adcode = getAdcodeByCoord(coord);
        if (StringUtils.isBlank(adcode)) {
            return null;
        }
        return getWeatherByAdcode(adcode);
    }

    /**
     * 根据位置获取天气
     *
     * @param address 位置
     * @return 天气
     */
    public static Weather.LivesDTO getWeatherByAddress(String address) {
        String adcode = getAdcodeByAddress(address);
        if (StrUtil.isEmpty(adcode)) {
            return null;
        }
        return getWeatherByAdcode(adcode);
    }

    /**
     * 根据ip获取ip信息
     *
     * @param ip   ip地址
     * @param type 4代表ipv4
     * @return 地址信息
     */
    public static IPVO getLocationByIp(@NotBlank String ip, @NotBlank String type) {
        return CLIENT.getLocationByIp(ip, type);
    }

    /**
     * 根据ip获取地址
     *
     * @param ip   ip地址
     * @param type 4代表ipv4
     * @return 地址信息
     */
    public static String getAddressByIp(String ip, String type) {
        IPVO ipvo = getLocationByIp(ip, type);
        if (ObjectUtil.isNull(ipvo)) {
            return null;
        }
        String location = ipvo.getLocation();
        String[] split = location.split(",");
        if (split.length != 2) {
            return null;
        }
        CoordinateUtil.Coordinate coord = new CoordinateUtil.Coordinate(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
        return getAddressByCoord(coord);
    }

    /**
     * 获取起点和终点经纬度距离
     *
     * @param start 起点
     * @param end   终点
     */
    public static Long getDistance(@NotNull CoordinateUtil.Coordinate start, @NotNull CoordinateUtil.Coordinate end) {
        DistanceVo distanceVo = CLIENT.getDistance(start, end);
        List<DistanceVo.ResultsDTO> results = distanceVo.getResults();
        if (CollUtil.isEmpty(results)) {
            return null;
        }
        DistanceVo.ResultsDTO resultsDTO = results.get(0);
        return Long.valueOf(resultsDTO.getDistance());
    }
}
