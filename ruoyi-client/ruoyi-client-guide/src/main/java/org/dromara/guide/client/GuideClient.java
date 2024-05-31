package org.dromara.guide.client;

import cn.hutool.core.util.CoordinateUtil;
import com.dtflys.forest.annotation.*;
import org.dromara.guide.domain.bo.DrivingBo;
import org.dromara.guide.domain.vo.*;

import java.util.List;

/**
 * 高德地图API请求服务
 * <p>参考文档：<a href="https://lbs.amap.com/api/webservice">GuideClient</a></p>
 *
 * @author AprilWind
 */
@BaseRequest(baseURL = "https://restapi.amap.com", charset = "UTF-8", interceptor = GuideIntercepto.class)
public interface GuideClient {

    /**
     * IP定位：将IPv4地址定位到具体地理位置，不支持国外IP解析
     *
     * @param ip （必填）IPv4地址
     * @return 返回地理位置信息
     */
    @Get(url = "/v3/ip?output=json")
    IpLocationVo getLocationByIpV3(@Query("ip") String ip);

    /**
     * 地理编码：将详细的结构化地址转换为高德经纬度坐标，支持对地标性名胜景区、建筑物名称解析为高德经纬度坐标
     *
     * @param address （必填）结构化地址信息，规则遵循：国家、省份、城市、区县、城镇、乡村、街道、门牌号码、屋村、大厦，
     *                例如：北京市朝阳区阜通东大街6号。地标性建筑举例：天安门
     * @param city    （可选）城市信息：可选输入内容包括：指定城市的中文（如北京）、指定城市的中文全拼（beijing）、citycode（010）、adcode（110000），不支持县级市。当指定城市查询内容为空时，会进行全国范围内的地址转换检索
     * @return 返回转换后的经纬度坐标，格式为"经度,纬度"，例如：116.480881,39.989410
     */
    @Get(url = "/v3/geocode/geo?output=json" + "#{T(ForestUtils).parameter('city', #city)}")
    GeocodeVo geoV3(@Query("address") String address, String city);

    /**
     * 逆地理编码：将经纬度转换为详细结构化的地址，同时返回附近周边的POI、AOI信息
     * 例如：116.480881,39.989410 转换地址描述后：北京市朝阳区阜通东大街6号
     *
     * @param location   （必填）经纬度坐标，传入内容规则：经度在前，纬度在后，经纬度间以“,”分割，经纬度小数点后不要超过6位
     * @param poitype    （可选）返回附近POI类型：以下内容需要 extensions 参数为 all 时才生效。
     *                   <p>
     *                   逆地理编码在进行坐标解析之后不仅可以返回地址描述，也可以返回经纬度附近符合限定要求的POI内容（在 extensions 字段值为 all 时才会返回POI内容）。设置 POI 类型参数相当于为上述操作限定要求。参数仅支持传入POI TYPECODE，可以传入多个POI TYPECODE，相互之间用“|”分隔
     * @param radius     （可选）搜索半径：radius取值范围在0~3000，默认是1000。单位：米
     * @param extensions （可选）返回结果控制：extensions 参数默认取值是 base，也就是返回基本地址信息；
     *                   <p>
     *                   extensions 参数取值为 all 时会返回基本地址信息、附近 POI 内容、道路信息以及道路交叉口信息
     * @param roadlevel  （可选）道路等级：以下内容需要 extensions 参数为 all 时才生效。
     *                   <p>
     *                   可选值：0，1
     *                   当roadlevel=0时，显示所有道路
     *                   当roadlevel=1时，过滤非主干道路，仅输出主干道路数据
     * @param homeorcorp （可选）是否优化POI返回顺序：以下内容需要 extensions 参数为 all 时才生效。
     *                   <p>
     *                   homeorcorp 参数的设置可以影响召回 POI 内容的排序策略，目前提供三个可选参数：
     *                   <p>
     *                   0：不对召回的排序策略进行干扰。
     *                   <p>
     *                   1：综合大数据分析将居家相关的 POI 内容优先返回，即优化返回结果中 pois 字段的poi顺序。
     *                   <p>
     *                   2：综合大数据分析将公司相关的 POI 内容优先返回，即优化返回结果中 pois 字段的poi顺序。
     * @return 返回转换后的结构化地址和附近周边的POI、AOI信息
     */
    @Get(url = "/v3/geocode/regeo?output=json&location=${location.lng},${location.lat}"
        + "#{T(ForestUtils).parameter('poitype', #poitype)}"
        + "#{T(ForestUtils).parameter('radius', #radius)}"
        + "#{T(ForestUtils).parameter('extensions', #extensions)}"
        + "#{T(ForestUtils).parameter('roadlevel', #roadlevel)}"
        + "#{T(ForestUtils).parameter('homeorcorp', #homeorcorp)}"
    )
    ReverseGeocodingVo regeoV3(@Var("location") CoordinateUtil.Coordinate location, String poitype, String radius, String extensions, String roadlevel, String homeorcorp);

    /**
     * 坐标转换：将用户输入的非高德坐标（GPS坐标、mapbar坐标、baidu坐标）转换成高德坐标
     *
     * @param locations （必填）坐标点，包括经度和纬度
     * @param coordsys  （可选）原坐标系：可选值：
     *                  <p>
     *                  gps;
     *                  <p>
     *                  mapbar;
     *                  <p>
     *                  baidu;
     *                  <p>
     *                  autonavi(不进行转换)
     * @return 返回转换后的高德坐标
     */
    @Get(url = "/v3/assistant/coordinate/convert?output=json&locations=${locations.lng},${locations.lat}"
        + "#{T(ForestUtils).parameter('coordsys', #coordsys)}"
    )
    CoordinateConversionVo convertV3(@Var("locations") CoordinateUtil.Coordinate locations, String coordsys);

    /**
     * 多个坐标转换：将用户输入的非高德坐标（GPS坐标、mapbar坐标、baidu坐标）转换成高德坐标
     *
     * @param locations （必填）坐标点：经度和纬度用","分割，经度在前，纬度在后，经纬度小数点后不得超过6位。多个坐标对之间用”|”进行分隔最多支持40对坐标
     * @param coordsys  （可选）原坐标系：可选值：
     *                  <p>
     *                  gps;
     *                  <p>
     *                  mapbar;
     *                  <p>
     *                  baidu;
     *                  <p>
     *                  autonavi(不进行转换)
     * @return 返回转换后的高德坐标
     */
    @Get(url = "/v3/assistant/coordinate/convert?output=json" + "#{T(ForestUtils).parameter('coordsys', #coordsys)}")
    CoordinateConversionVo convertV3(@Query("locations") String locations, String coordsys);

    /**
     * 天气查询
     *
     * @param city       （必填）城市编码：输入城市的adcode
     * @param extensions （可选）气象类型：可选值：base/all
     *                   <p>
     *                   base:返回实况天气
     *                   <p>
     *                   all:返回预报天气
     * @return 实况天气每小时更新多次，预报天气每天更新3次，分别在8、11、18点左右更新。由于天气数据的特殊性以及数据更新的持续性，无法确定精确的更新时间，请以接口返回数据的reporttime字段为准
     */
    @Get(url = "/v3/weather/weatherInfo?output=json" + "#{T(ForestUtils).parameter('extensions', #extensions)}")
    WeatherQueryVo weatherInfoV3(@Query("city") String city, String extensions);

    /**
     * 距离测量
     *
     * @param origins     （必填）出发点坐标，最多支持100个坐标对，格式为经度和纬度用逗号分隔，多个坐标对之间用竖线分隔
     * @param destination （必填）目的地坐标，格式为经度和纬度用逗号分隔，如"117.500244,40.417801"
     * @param type        （可选）路径计算的方式和方法：
     *                    0：直线距离
     *                    <p>
     *                    1：驾车导航距离（仅支持国内坐标）。
     *                    <p>
     *                    必须指出，当为1时会考虑路况，故在不同时间请求返回结果可能不同。
     *                    <p>
     *                    此策略和驾车路径规划接口的 strategy=4策略基本一致，策略为“ 躲避拥堵的路线，但是可能会存在绕路的情况，耗时可能较长 ”
     *                    <p>
     *                    若需要实现高德地图客户端效果，可以考虑使用驾车路径规划接口
     *                    <p>
     *                    3：步行规划距离（仅支持5km之间的距离）
     * @return 距离信息
     */
    @Get(url = "/v3/distance?output=json" + "#{T(ForestUtils).parameter('type', #type)}")
    DistanceVo distanceV3(@Query("origins") String origins, @Query("destination") String destination, String type);

    /**
     * 步行路径规划：步行路径规划 API 可以规划100KM以内的步行通勤方案，并且返回通勤方案的数据。最大支持 100km 的步行路线规划
     *
     * @param origin        （必填）出发点：规则： lon，lat（经度，纬度）， “,”分割，如117.500244, 40.417801     经纬度小数点不超过6位
     * @param destination   （必填）目的地：规则： lon，lat（经度，纬度）， “,”分割，如117.500244, 40.417801     经纬度小数点不超过6位
     * @param originId      （选填）起点POI ID：起点为POI时，建议填充此值，可提升路线规划准确性
     * @param destinationId （选填）目的地POI ID：目的地为POI时，建议填充此值，可提升路线规划准确性
     * @return
     */
    @Get(url = "/v3/direction/walking?output=json&origin=${origin.lng},${origin.lat}&destination=${destination.lng},${destination.lat}"
        + "#{T(ForestUtils).parameter('origin_id', #originId)}"
        + "#{T(ForestUtils).parameter('destination_id', #destinationId)}"
    )
    WalkingVo walkingV3(@Var("origin") CoordinateUtil.Coordinate origin, @Var("destination") CoordinateUtil.Coordinate destination, String originId, String destinationId);

    /**
     * 公交路径规划：公交路径规划 API 可以规划综合各类公共（火车、公交、地铁）交通方式的通勤方案，并且返回通勤方案的数据
     *
     * @param origin      （必填）出发点：规则： lon，lat（经度，纬度）， “,”分割，如117.500244, 40.417801     经纬度小数点不超过6位
     * @param destination （必填）目的地：规则： lon，lat（经度，纬度）， “,”分割，如117.500244, 40.417801     经纬度小数点不超过6位
     * @param city        （必填）城市/跨城规划时的起点城市：目前支持市内公交换乘/跨城公交的起点城市。
     *                    <p>
     *                    可选值：城市名称/citycode
     * @param cityd       （选填）跨城公交规划时的终点城市：跨城公交规划必填参数。
     *                    <p>
     *                    可选值：城市名称/citycode
     * @param extensions  （选填）返回结果详略：可选值：base(default)/all
     *                    <p>
     *                    base:返回基本信息；all：返回全部信息
     * @param strategy    （选填）公交换乘策略：可选值：
     *                    <p>
     *                    0：最快捷模式
     *                    <p>
     *                    1：最经济模式
     *                    <p>
     *                    2：最少换乘模式
     *                    <p>
     *                    3：最少步行模式
     *                    <p>
     *                    5：不乘地铁模式
     * @param nightflag   （选填）是否计算夜班车：可选值：0：不计算夜班车
     *                    <p>
     *                    1：计算夜班车
     * @param date        （选填）出发日期：根据出发时间和日期，筛选可乘坐的公交路线，格式示例：date=2014-3-19。在无需设置预计出发时间时，请不要在请求之中携带此参数。
     * @param time        （选填）出发时间：根据出发时间和日期，筛选可乘坐的公交路线，格式示例：time=22:34。在无需设置预计出发时间时，请不要在请求之中携带此参数
     * @return
     */
    @Get(url = "/v3/direction/transit/integrated?output=json&origin=${origin.lng},${origin.lat}&destination=${destination.lng},${destination.lat}"
        + "#{T(ForestUtils).parameter('cityd', #cityd)}"
        + "#{T(ForestUtils).parameter('extensions', #extensions)}"
        + "#{T(ForestUtils).parameter('strategy', #strategy)}"
        + "#{T(ForestUtils).parameter('nightflag', #nightflag)}"
        + "#{T(ForestUtils).parameter('date', #date)}"
        + "#{T(ForestUtils).parameter('time', #time)}"
    )
    IntegratedVo integratedV3(@Var("origin") CoordinateUtil.Coordinate origin, @Var("destination") CoordinateUtil.Coordinate destination, @Query("city") String city, String cityd, String extensions, String strategy, String nightflag, String date, String time);

    /**
     * 驾车路径规划：驾车路径规划 API 可以规划以小客车、轿车通勤出行的方案，并且返回通勤方案的数据
     *
     * @param origin          （必填）出发点：经度在前，纬度在后，经度和纬度用","分割，经纬度小数点后不得超过6位。格式为x1,y1|x2,y2|x3,y3。
     *                        <p>
     *                        由于在实际使用过程中，存在定位飘点的情况。为了解决此类问题，允许传入多个起点用于计算车头角度。
     *                        <p>
     *                        最多允许传入3个坐标对，每对坐标之间距离必须超过2m。 虽然对每对坐标之间长度没有上限，但是如果超过4米会有概率性出现不准确的情况。使用三个点来判断距离和角度的有效性，如果两者都有效，使用第一个点和最后一个点计算的角度设置抓路的角度，规划路径时以最后一个坐标对进行规划。
     * @param destination     （必填）目的地：经度在前，纬度在后，经度和纬度用","分割，经纬度小数点后不得超过6位。
     * @param originid        （选填）出发点poiid：当起点为POI时，建议填充此值。
     * @param destinationid   （选填）目的地poiid：当终点为POI时，建议填充此值。
     * @param origintype      （选填）起点的poi类别：当用户知道起点POI的类别时候，建议填充此值
     * @param destinationtype （选填）终点的poi类别：当用户知道终点POI的类别时候，建议填充此值
     * @param strategy        （选填）驾车选择策略：下方10~20的策略，会返回多条路径规划结果。（高德地图APP策略也包含在内，强烈建议从此策略之中选择）
     *                        <p>
     *                        下方策略 0~9的策略，仅会返回一条路径规划结果。
     *                        <p>
     *                        <p>
     *                        <p>
     *                        <p>
     *                        <p>
     *                        下方策略返回多条路径规划结果
     *                        <p>
     *                        10，返回结果会躲避拥堵，路程较短，尽量缩短时间，与高德地图的默认策略也就是不进行任何勾选一致
     *                        <p>
     *                        11，返回三个结果包含：时间最短；距离最短；躲避拥堵 （由于有更优秀的算法，建议用10代替）
     *                        <p>
     *                        12，返回的结果考虑路况，尽量躲避拥堵而规划路径，与高德地图的“躲避拥堵”策略一致
     *                        <p>
     *                        13，返回的结果不走高速，与高德地图“不走高速”策略一致
     *                        <p>
     *                        14，返回的结果尽可能规划收费较低甚至免费的路径，与高德地图“避免收费”策略一致
     *                        <p>
     *                        15，返回的结果考虑路况，尽量躲避拥堵而规划路径，并且不走高速，与高德地图的“躲避拥堵&不走高速”策略一致
     *                        <p>
     *                        16，返回的结果尽量不走高速，并且尽量规划收费较低甚至免费的路径结果，与高德地图的“避免收费&不走高速”策略一致
     *                        <p>
     *                        17，返回路径规划结果会尽量的躲避拥堵，并且规划收费较低甚至免费的路径结果，与高德地图的“躲避拥堵&避免收费”策略一致
     *                        <p>
     *                        18，返回的结果尽量躲避拥堵，规划收费较低甚至免费的路径结果，并且尽量不走高速路，与高德地图的“避免拥堵&避免收费&不走高速”策略一致
     *                        <p>
     *                        19，返回的结果会优先选择高速路，与高德地图的“高速优先”策略一致
     *                        <p>
     *                        20，返回的结果会优先考虑高速路，并且会考虑路况躲避拥堵，与高德地图的“躲避拥堵&高速优先”策略一致
     *                        <p>
     *                        <p>
     *                        <p>
     *                        <p>
     *                        <p>
     *                        下方策略仅返回一条路径规划结果
     *                        <p>
     *                        0，速度优先，此路线不一定距离最短
     *                        <p>
     *                        1，费用优先，不走收费路段，且耗时最少的路线
     *                        <p>
     *                        2，距离优先，仅走距离最短的路线，但是可能存在穿越小路/小区的情况
     *                        <p>
     *                        3，速度优先，不走快速路，例如京通快速路（因为策略迭代，建议使用13）
     *                        <p>
     *                        4，躲避拥堵，但是可能会存在绕路的情况，耗时可能较长
     *                        <p>
     *                        5，多策略（同时使用速度优先、费用优先、距离优先三个策略计算路径）。
     *                        <p>
     *                        其中必须说明，就算使用三个策略算路，会根据路况不固定的返回一~三条路径规划信息。
     *                        <p>
     *                        6，速度优先，不走高速，但是不排除走其余收费路段
     *                        <p>
     *                        7，费用优先，不走高速且避免所有收费路段
     *                        <p>
     *                        8，躲避拥堵和收费，可能存在走高速的情况，并且考虑路况不走拥堵路线，但有可能存在绕路和时间较长
     *                        <p>
     *                        9，躲避拥堵和收费，不走高速
     * @param waypoints       （选填）途经点：经度和纬度用","分割，经度在前，纬度在后，小数点后不超过6位，坐标点之间用";"分隔
     *                        <p>
     *                        最大数目：16个坐标点。如果输入多个途径点，则按照用户输入的顺序进行路径规划
     * @param avoidpolygons   （选填）避让区域：区域避让，支持32个避让区域，每个区域最多可有16个顶点
     *                        <p>
     *                        经度和纬度用","分割，经度在前，纬度在后，小数点后不超过6位，坐标点之间用";"分隔，区域之间用"|"分隔。如果是四边形则有四个坐标点，如果是五边形则有五个坐标点；
     *                        <p>
     *                        同时传入避让区域及避让道路，仅支持避让道路；
     *                        <p>
     *                        避让区域不能超过81平方公里，否则避让区域会失效
     * @param avoidroad       （选填）避让道路名：只支持一条避让道路
     * @param province        （选填）用汉字填入车牌省份缩写，用于判断是否限行：例如：京
     * @param number          （选填）填入除省份及标点之外，车牌的字母和数字（需大写）。用于判断限行相关：例如:NH1N11
     *                        <p>
     *                        支持6位传统车牌和7位新能源车牌
     * @param cartype         （选填）车辆类型：0：普通汽车(默认值)
     *                        1：纯电动车
     *                        2：插电混动车
     * @param ferry           （选填）在路径规划中，是否使用轮渡：0:使用渡轮(默认)
     *                        1:不使用渡轮
     * @param roadaggregation （选填）是否返回路径聚合信息：false:不返回路径聚合信息
     *                        <p>
     *                        true:返回路径聚合信息，在steps上层增加roads做聚合
     * @param nosteps         （选填）是否返回steps字段内容：当取值为0时，steps字段内容正常返回；
     *                        <p>
     *                        当取值为1时，steps字段内容为空；
     * @param extensions      （选填）返回结果控制：可选值：base/all
     *                        <p>
     *                        base:返回基本信息；all：返回全部信息
     * @return 驾车路径规划
     */
    @Get(url = "/v3/direction/driving?output=json"
        + "#{T(ForestUtils).parameter('originid', #originid)}"
        + "#{T(ForestUtils).parameter('destinationid', #destinationid)}"
        + "#{T(ForestUtils).parameter('origintype', #origintype)}"
        + "#{T(ForestUtils).parameter('destinationtype', #destinationtype)}"
        + "#{T(ForestUtils).parameter('strategy', #strategy)}"
        + "#{T(ForestUtils).parameter('waypoints', #waypoints)}"
        + "#{T(ForestUtils).parameter('avoidpolygons', #avoidpolygons)}"
        + "#{T(ForestUtils).parameter('province', #province)}"
        + "#{T(ForestUtils).parameter('number', #number)}"
        + "#{T(ForestUtils).parameter('cartype', #cartype)}"
        + "#{T(ForestUtils).parameter('ferry', #ferry)}"
        + "#{T(ForestUtils).parameter('roadaggregation', #roadaggregation)}"
        + "#{T(ForestUtils).parameter('nosteps', #nosteps)}"
        + "#{T(ForestUtils).parameter('extensions', #extensions)}"
    )
    DrivingV3Vo drivingV3(@Query("origin") String origin, @Query("destination") String destination
        , String originid, String destinationid, String origintype, String destinationtype, String strategy, String waypoints, String avoidpolygons
        , String avoidroad, String province, String number, String cartype, String ferry, String roadaggregation, String nosteps, String extensions);

    /**
     * 骑行路径规划：骑行路径规划用于规划骑行通勤方案，规划时会考虑天桥、单行线、封路等情况。最大支持 500km 的骑行路线规划。
     *
     * @param origin      出发点经纬度
     * @param destination 目的地经纬度
     * @return 骑行路径规划
     */
    @Get(url = "/v4/direction/bicycling?output=json&origin=${origin.lng},${origin.lat}&destination=${destination.lng},${destination.lat}")
    BicyclingVo bicyclingV4(@Var("origin") CoordinateUtil.Coordinate origin, @Var("destination") CoordinateUtil.Coordinate destination);

    /**
     * 公交站 ID 查询
     *
     * @param id 公交站 id
     * @return 公交站信息
     */
    @Get(url = "/v3/bus/stopid?output=json")
    StopidVo stopidV3(@Query("id") String id);

    /**
     * 公交路线 ID 查询
     *
     * @param id         （必填）公交线路 id：有效的高德公交线路 id
     * @param extensions （可选）控制返回结果 可选：base，all
     *                   <p>
     *                   base：返回公交路线基本信息
     *                   <p>
     *                   all：返回基本+详细信息（详细信息包含途径站点，首末班车时间等）
     * @return 公交路线信息
     */
    @Get(url = "/v3/bus/lineid?output=json" + "#{T(ForestUtils).parameter('extensions', #extensions)}")
    LineidVo lineidV3(@Query("id") String id, String extensions);

    /**
     * 公交站关键字查询
     *
     * @param keywords （必填）查询关键字：只支持一个关键字
     * @param city     （可选）城市：支持adcode、citycode
     *                 <p>
     *                 adcode 信息可参考城市编码表获取
     * @param offset   （可选）每页记录数据：最大 100
     * @param page     （可选）当前页面：最大 100
     * @return 公交站信息
     */
    @Get(url = "/v3/bus/stopname?output=json"
        + "#{T(ForestUtils).parameter('city', #city)}"
        + "#{T(ForestUtils).parameter('offset', #offset)}"
        + "#{T(ForestUtils).parameter('page', #page)}")
    StopidVo stopnameV3(@Query("keywords") String keywords, String city, String offset, String page);

    /**
     * 公交站关键字查询
     *
     * @param keywords   （必填）查询关键字：只支持一个关键字
     * @param city       （必选）城市：支持adcode、citycode
     *                   <p>
     *                   adcode 信息可参考城市编码表获取
     * @param offset     （可选）每页记录数据：规则：大于 100 按默认值 默认值：20
     * @param page       （可选）当前页面：最大翻页数 10 默认值：1
     * @param extensions （可选）控制返回结果 可选：base，all
     *                   <p>
     *                   base：返回公交路线基本信息
     *                   <p>
     *                   all：返回基本+详细信息（详细信息包含途径站点，首末班车时间等）
     * @return 公交站信息
     */
    @Get(url = "/v3/bus/linename?output=json"
        + "#{T(ForestUtils).parameter('offset', #offset)}"
        + "#{T(ForestUtils).parameter('page', #page)}"
        + "#{T(ForestUtils).parameter('extensions', #extensions)}"
    )
    LinenameVo linenameV3(@Query("keywords") String keywords, @Query("city") String city, String offset, String page, String extensions);

    /**
     * 行政区域查询
     *
     * @param keywords    （可选）查询关键字，规则：只支持单个关键词语搜索关键词支持：行政区名称、citycode、adcode
     *                    <p>
     *                    例如，在subdistrict=2，搜索省份（例如山东），能够显示市（例如济南），区（例如历下区）
     * @param subdistrict （可选）子级行政区：规则：设置显示下级行政区级数（行政区级别包括：国家、省/直辖市、市、区/县、乡镇/街道多级数据）
     *                    <p>
     *                    可选值：0、1、2、3等数字，并以此类推
     *                    <p>
     *                    0：不返回下级行政区；
     *                    <p>
     *                    1：返回下一级行政区；
     *                    <p>
     *                    2：返回下两级行政区；
     *                    <p>
     *                    3：返回下三级行政区；
     *                    <p>
     *                    <p>
     *                    <p>
     *                    需要在此特殊说明，目前部分城市和省直辖县因为没有区县的概念，故在市级下方直接显示街道。
     *                    <p>
     *                    例如：广东-东莞、海南-文昌市
     * @param page        （可选）需要第几页数据：最外层的districts最多会返回20个数据，若超过限制，请用page请求下一页数据。
     *                    <p>
     *                    例如page=2；page=3。默认page=1
     * @param offset      （可选）最外层返回数据个数，默认为20
     * @param extensions  （可选）返回结果控制：此项控制行政区信息中返回行政区边界坐标点； 可选值：base、all;
     *                    <p>
     *                    base:不返回行政区边界坐标点；
     *                    <p>
     *                    all:只返回当前查询district的边界值，不返回子节点的边界值；
     *                    <p>
     *                    目前不能返回乡镇/街道级别的边界值
     * @param filter      （可选）根据区划过滤：按照指定行政区划进行过滤，填入后则只返回该省/直辖市信息
     *                    <p>
     *                    需填入adcode，为了保证数据的正确，强烈建议填入此参数
     * @return 行政区信息
     */
    @Get(url = "/v3/config/district?output=json"
        + "#{T(ForestUtils).parameter('keywords', #keywords)}"
        + "#{T(ForestUtils).parameter('subdistrict', #subdistrict)}"
        + "#{T(ForestUtils).parameter('page', #page)}"
        + "#{T(ForestUtils).parameter('offset', #offset)}"
        + "#{T(ForestUtils).parameter('extensions', #extensions)}"
        + "#{T(ForestUtils).parameter('filter', #filter)}"
    )
    DistrictVo districtV3(String keywords, String subdistrict, String page, String offset, String extensions, String filter);

    /**
     * 静态地图
     *
     * @param location （必填）地图中心点：中心点坐标
     *                 <p>
     *                 规则：经度和纬度用","分隔 经纬度小数点后不得超过6位。
     * @param zoom     （必填）地图级别：地图缩放级别:[1,17]
     * @param size     （可选）地图大小：图片宽度*图片高度。最大值为1024*1024
     * @param scale    （可选）普通/高清：
     *                 1:返回普通图；
     *                 <p>
     *                 2:调用高清图，图片高度和宽度都增加一倍，zoom也增加一倍（当zoom为最大值时，zoom不再改变）
     * @param markers  （可选）标注：使用规则见markers详细说明，标注最大数10个
     * @param labels   （可选）标签：使用规则见labels详细说明，标签最大数10个
     * @param paths    （可选）折线：使用规则见paths详细说明，折线和多边形最大数4个
     * @param traffic  （可选）交通路况标识：底图是否展现实时路况。 可选值： 0，不展现；1，展现。
     * @return 静态地图
     */
    @Get(url = "/v3/staticmap?output=json&location=${location.lng},${location.lat}"
        + "#{T(ForestUtils).parameter('size', #size)}"
        + "#{T(ForestUtils).parameter('scale', #scale)}"
        + "#{T(ForestUtils).parameter('markers', #markers)}"
        + "#{T(ForestUtils).parameter('labels', #labels)}"
        + "#{T(ForestUtils).parameter('paths', #paths)}"
        + "#{T(ForestUtils).parameter('traffic', #traffic)}"
    )
    String staticmapV3(@Var("location") CoordinateUtil.Coordinate location, @Query("zoom") String zoom, String size, String scale, String markers, String labels, String paths, String traffic);

    /**
     * 发起驾驶路线纠偏请求
     *
     * @param drivingBo 驾驶路线数据列表
     * @return 驾驶路线纠偏结果
     */

    @Post(url = "/v4/grasproad/driving")
    DrivingVo drivingV4(@Body List<DrivingBo> drivingBo);

    /**
     * 关键字搜索
     *
     * @param keywords   （必填(keywords和types两者至少必选其一)）查询关键字：规则：  只支持一个关键字
     *                   <p>
     *                   若不指定city，并且搜索的为泛词（例如“美食”）的情况下，返回的内容为城市列表以及此城市内有多少结果符合要求
     * @param types      （必填(keywords和types两者至少必选其一)）查询POI类型：可选值：分类代码 或 汉字（若用汉字，请严格按照附件之中的汉字填写）
     *                   <p>
     *                   规则： 多个关键字用“|”分割
     *                   <p>
     *                   分类代码由六位数字组成，一共分为三个部分，前两个数字代表大类；中间两个数字代表中类；最后两个数字代表小类。
     *                   <p>
     *                   若指定了某个大类，则所属的中类、小类都会被显示。
     *                   <p>
     *                   例如：010000为汽车服务（大类）
     *                   <p>
     *                   010100为加油站（中类）
     *                   <p>
     *                   010101为中国石化（小类）
     *                   <p>
     *                   010900为汽车租赁（中类）
     *                   <p>
     *                   010901为汽车租赁还车（小类）
     *                   <p>
     *                   当指定010000，则010100等中类、010101等小类都会被包含，当指定010900，则010901等小类都会被包含。
     *                   <p>
     *                   下载POI分类编码和城市编码表
     *                   <p>
     *                   若不指定city，返回的内容为城市列表以及此城市内有多少结果符合要求。
     *                   <p>
     *                   当您的keywords和types都是空时，默认指定types为120000（商务住宅）&150000（交通设施服务）
     * @param city       （可选）查询城市：可选值：城市中文、citycode、adcode
     *                   <p>
     *                   如：北京/010/110000
     *                   <p>
     *                   填入此参数后，会尽量优先返回此城市数据，但是不一定仅局限此城市结果，若仅需要某个城市数据请调用citylimit参数。
     *                   <p>
     *                   如：在深圳市搜天安门，返回北京天安门结果。
     * @param citylimit  （可选）仅返回指定城市数据：可选值：true/false
     * @param children   （可选）是否按照层级展示子POI数据：可选值：children=1
     *                   <p>
     *                   当为0的时候，子POI都会显示。
     *                   <p>
     *                   当为1的时候，子POI会归类到父POI之中。
     *                   <p>
     *                   在extensions=all 或者为空时生效
     * @param offset     （可选）每页记录数据：强烈建议不超过25，若超过25可能造成访问报错
     * @param page       （可选）当前页数
     * @param extensions （可选）返回结果控制：此项默认返回基本地址信息；取值为all返回地址信息、附近POI、道路以及道路交叉口信息
     * @return 关键字搜索结果
     */
    @Get(url = "/v3/place/text?output=json"
        + "#{T(ForestUtils).parameter('keywords', #keywords)}"
        + "#{T(ForestUtils).parameter('types', #types)}"
        + "#{T(ForestUtils).parameter('city', #city)}"
        + "#{T(ForestUtils).parameter('citylimit', #citylimit)}"
        + "#{T(ForestUtils).parameter('children', #children)}"
        + "#{T(ForestUtils).parameter('offset', #offset)}"
        + "#{T(ForestUtils).parameter('page', #page)}"
        + "#{T(ForestUtils).parameter('extensions', #extensions)}"
    )
    TextVo textV3(String keywords, String types, String city, String citylimit, String children, String offset, String page, String extensions);

    /**
     * 周边搜索
     *
     * @param location   （必填）中心点坐标
     * @param keywords   （可选）查询关键字：规则：  只支持一个关键字
     * @param types      （可选）查询POI类型：多个类型用“|”分割；
     *                   <p>
     *                   可选值：分类代码 或 汉字 （若用汉字，请严格按照附件之中的汉字填写）
     *                   <p>
     *                   分类代码由六位数字组成，一共分为三个部分，前两个数字代表大类；中间两个数字代表中类；最后两个数字代表小类。
     *                   <p>
     *                   若指定了某个大类，则所属的中类、小类都会被显示。
     *                   <p>
     *                   例如：010000为汽车服务（大类）
     *                   <p>
     *                   010100为加油站（中类）
     *                   <p>
     *                   010101为中国石化（小类）
     *                   <p>
     *                   010900为汽车租赁（中类）
     *                   <p>
     *                   010901为汽车租赁还车（小类）
     *                   <p>
     *                   当指定010000，则010100等中类、010101等小类都会被包含。
     *                   <p>
     *                   当指定010900，则010901等小类都会被包含
     *                   <p>
     *                   下载POI分类编码和城市编码表
     *                   <p>
     *                   当keywords和types均为空的时候，默认指定types为050000（餐饮服务）、070000（生活服务）、120000（商务住宅）
     * @param city       （可选）查询城市：可选值：城市中文、中文全拼、citycode、adcode
     *                   <p>
     *                   如：北京/beijing/010/110000
     *                   <p>
     *                   当用户指定的经纬度和city出现冲突，若范围内有用户指定city的数据，则返回相关数据，否则返回为空。
     *                   <p>
     *                   如：经纬度指定石家庄，而city却指定天津，若搜索范围内有天津的数据则返回相关数据，否则返回为空。
     * @param radius     （可选）查询半径：取值范围:0-50000。规则：大于50000按默认值，单位：米
     * @param sortrule   （可选）排序规则：规定返回结果的排序规则。按距离排序：distance；综合排序：weight
     * @param offset     （可选）每页记录数据：强烈建议不超过25，若超过25可能造成访问报错
     * @param page       （可选）当前页数
     * @param extensions （可选）返回结果控制：此项默认返回基本地址信息；取值为all返回地址信息、附近POI、道路以及道路交叉口信息
     * @return 周边搜索结果
     */
    @Get(url = "/v3/place/around?output=json&location=${location.lng},${location.lat}"
        + "#{T(ForestUtils).parameter('keywords', #keywords)}"
        + "#{T(ForestUtils).parameter('types', #types)}"
        + "#{T(ForestUtils).parameter('city', #city)}"
        + "#{T(ForestUtils).parameter('radius', #radius)}"
        + "#{T(ForestUtils).parameter('sortrule', #sortrule)}"
        + "#{T(ForestUtils).parameter('offset', #offset)}"
        + "#{T(ForestUtils).parameter('page', #page)}"
        + "#{T(ForestUtils).parameter('extensions', #extensions)}"
    )
    TextVo aroundV3(@Var("location") CoordinateUtil.Coordinate location, String keywords, String types, String city, String radius, String sortrule, String offset, String page, String extensions);

    /**
     * 多边形搜索
     *
     * @param polygon    （必填）经纬度坐标对：规则：经度和纬度用","分割，经度在前，纬度在后，坐标对用"|"分割。经纬度小数点后不得超过6位。多边形为矩形时，可传入左上右下两顶点坐标对；其他情况下首尾坐标对需相同
     * @param keywords   （可选）查询关键字：规则：  只支持一个关键字
     * @param types      （可选）查询POI类型：多个类型用“|”分割；
     *                   <p>
     *                   可选值：分类代码 或 汉字 （若用汉字，请严格按照附件之中的汉字填写）
     *                   <p>
     *                   分类代码由六位数字组成，一共分为三个部分，前两个数字代表大类；中间两个数字代表中类；最后两个数字代表小类。
     *                   <p>
     *                   若指定了某个大类，则所属的中类、小类都会被显示。
     *                   <p>
     *                   例如：010000为汽车服务（大类）
     *                   <p>
     *                   010100为加油站（中类）
     *                   <p>
     *                   010101为中国石化（小类）
     *                   <p>
     *                   010900为汽车租赁（中类）
     *                   <p>
     *                   010901为汽车租赁还车（小类）
     *                   <p>
     *                   当指定010000，则010100等中类、010101等小类都会被包含。
     *                   <p>
     *                   当指定010900，则010901等小类都会被包含
     *                   <p>
     *                   下载POI分类编码和城市编码表
     *                   <p>
     *                   当keywords和types为空的时候， 我们会默认指定types为120000（商务住宅）&150000（交通设施服务）
     * @param offset     （可选）每页记录数据：强烈建议不超过25，若超过25可能造成访问报错
     * @param page       （可选）当前页数
     * @param extensions （可选）返回结果控制：此项默认返回基本地址信息；取值为all返回地址信息、附近POI、道路以及道路交叉口信息
     * @return 多边形搜索结果
     */
    @Get(url = "/v3/place/polygon?output=json&polygon=${polygon.lng},${polygon.lat}"
        + "#{T(ForestUtils).parameter('keywords', #keywords)}"
        + "#{T(ForestUtils).parameter('types', #types)}"
        + "#{T(ForestUtils).parameter('offset', #offset)}"
        + "#{T(ForestUtils).parameter('page', #page)}"
        + "#{T(ForestUtils).parameter('extensions', #extensions)}"
    )
    TextVo polygonV3(@Var("polygon") CoordinateUtil.Coordinate polygon, String keywords, String types, String offset, String page, String extensions);

    /**
     * ID查询
     *
     * @param id AOI唯一标识：最多可以传入1个id，传入目标区域的poiid即可
     * @return ID查询结果
     */
    @Get(url = "/v3/place/detail?output=json")
    TextVo detailV3(@Query("id") String id);

    /**
     * AOI边界查询
     *
     * @param id AOI唯一标识：最多可以传入1个id，传入目标区域的poiid即可
     * @return ID查询结果
     */
    @Get(url = "/v5/aoi/polyline?output=json")
    PolylineVo polylineV5(@Query("id") String id);

    /**
     * 输入提示
     *
     * @param keywords  （必填）查询关键词
     * @param type      （可选）POI分类：服务可支持传入多个分类，多个类型剑用“|”分隔
     *                  <p>
     *                  可选值：POI分类名称、分类代码
     *                  <p>
     *                  此处强烈建议使用分类代码，否则可能会得到不符合预期的结果
     * @param location  （可选）坐标：格式：“X,Y”（经度,纬度），不可以包含空格
     *                  <p>
     *                  建议使用location参数，可在此location附近优先返回搜索关键词信息
     *                  <p>
     *                  在请求参数city不为空时生效
     * @param city      （可选）搜索城市：可选值：citycode、adcode，不支持县级市。
     *                  <p>
     *                  如：010/110000
     *                  <p>
     *                  adcode信息可参考城市编码表获取。
     *                  <p>
     *                  填入此参数后，会尽量优先返回此城市数据，但是不一定仅局限此城市结果，若仅需要某个城市数据请调用citylimit参数。
     *                  <p>
     *                  如：在深圳市搜天安门，返回北京天安门结果。
     * @param citylimit （可选）仅返回指定城市数据：可选值：true/false
     * @param datatype  （可选）返回的数据类型：多种数据类型用“|”分隔，可选值：all-返回所有数据类型、poi-返回POI数据类型、bus-返回公交站点数据类型、busline-返回公交线路数据类型
     * @return 提示信息
     */
    @Get(url = "/v3/assistant/inputtips?output=json"
        + "#{T(ForestUtils).parameter('type', #type)}"
        + "#{T(ForestUtils).parameter('location', #location)}"
        + "#{T(ForestUtils).parameter('city', #city)}"
        + "#{T(ForestUtils).parameter('citylimit', #citylimit)}"
        + "#{T(ForestUtils).parameter('datatype', #datatype)}"
    )
    InputtipsVo inputtipsV3(@Query("keywords") String keywords, String type, String location, String city, String citylimit, String datatype);

    /**
     * 智能硬件定位
     * 为了尽可能地保证您定位结果的精确性，在以上所有场景中，除了必须填写的参数外，其他可以获得的参数也请一并传入
     *
     * @param accesstype （必填）移动端接入网络方式 ：可选值：
     *                   <p>
     *                   移动接入网络：0
     *                   <p>
     *                   wifi 接入网络：1
     * @param imei       （必填）手机 imei 号
     * @param idfa       （可选）ios 手机的 idfa
     * @param smac       （可选）手机 mac 码
     * @param serverip   （可选）设备接入基站时对应的网关 IP
     * @param cdma       （可选）是否为 cdma ：是否为 cdma：
     *                   <p>
     *                   非 cdma：0
     *                   <p>
     *                   是 cdma：1
     * @param imsi       （可选）移动用户识别码 ：
     * @param network    （可选）无线网络类型 ：GSM/GPRS/EDGE/HSUPA/HSDPA/WCDMA
     * @param tel        （可选）手机号码 ：
     * @param bts        （可选）接入基站信息 ：接入基站信息，详见：表 1-2 内部参数说明
     *                   <p>
     *                   非 CDMA 格式为：mcc,mnc,lac,cellid,signal
     *                   <p>
     *                   CDMA 格式为：sid,nid,bid,lon,lat,signal
     *                   <p>
     *                   其中 lon,lat 可为空，格式为：sid,nid,bid,,,signa
     * @param nearbts    （可选）周边基站信息（不含接入基站信息） ：基站信息 1|基站信息 2|基站信息 3…..
     * @param mmac       （可选）已连热点 mac 信息 ：mac,signal,ssid
     *                   <p>
     *                   如：
     *                   <p>
     *                   mac：f0:7d:68:9e:7d:18
     *                   <p>
     *                   signal：-41
     *                   <p>
     *                   ssid：TPLink
     * @param macs       （可选）wifi 列表中 mac 信息 ：单 mac 信息同 mmac，mac 之间使用“|” 分隔。必须填写 2 个及 2 个以上,30 个以内的方可正常定位。请不要包含移动 wifi 信息
     * @return 智能硬件定位Vo
     */
    @Get(url = "https://apilocate.amap.com/position?output=json"
        + "#{T(ForestUtils).parameter('idfa', #idfa)}"
        + "#{T(ForestUtils).parameter('smac', #smac)}"
        + "#{T(ForestUtils).parameter('serverip', #serverip)}"
        + "#{T(ForestUtils).parameter('cdma', #cdma)}"
        + "#{T(ForestUtils).parameter('imsi', #imsi)}"
        + "#{T(ForestUtils).parameter('network', #network)}"
        + "#{T(ForestUtils).parameter('tel', #tel)}"
        + "#{T(ForestUtils).parameter('bts', #bts)}"
        + "#{T(ForestUtils).parameter('nearbts', #nearbts)}"
        + "#{T(ForestUtils).parameter('mmac', #mmac)}"
        + "#{T(ForestUtils).parameter('macs', #macs)}"
    )
    PositionVo position(@Query("accesstype") String accesstype, @Query("imei") String imei, String idfa, String smac, String serverip, String cdma, String imsi, String network, String tel, String bts, String nearbts, String mmac, String macs);

    /**
     * 智能硬件定位 2.0
     * 为了尽可能地保证您定位结果的精确性，在以上所有场景中，除了必须填写的参数外，其他可以获得的参数也请一并传入
     *
     * @param accesstype （必填）移动端接入网络方式 ：可选值：
     *                   <p>
     *                   移动接入网络：0
     *                   <p>
     *                   wifi 接入网络：1
     * @param diu        （可选）设备唯一编号
     * @param cdma       （可选）是否为 cdma ：是否为 cdma：
     *                   <p>
     *                   非 cdma：0
     *                   <p>
     *                   是 cdma：1
     * @param network    （可选）无线网络类型 ：GSM/GPRS/EDGE/HSUPA/HSDPA/WCDMA
     * @param bts        （可选）接入基站信息 ：接入基站信息，详见：表 1-2 内部参数说明
     *                   <p>
     *                   非 CDMA 格式为：mcc,mnc,lac,cellid,signal
     *                   <p>
     *                   CDMA 格式为：sid,nid,bid,lon,lat,signal
     *                   <p>
     *                   其中 lon,lat 可为空，格式为：sid,nid,bid,,,signa
     * @param nearbts    （可选）周边基站信息（不含接入基站信息） ：基站信息 1|基站信息 2|基站信息 3…..
     * @param historybts （可选）历史基站信息 ：历史定位基站信息，用于辅助定位。格式与周边基站信息相同
     * @param mmac       （可选）已连热点 mac 信息 ：mac,signal,ssid
     *                   <p>
     *                   如：
     *                   <p>
     *                   mac：f0:7d:68:9e:7d:18
     *                   <p>
     *                   signal：-41
     *                   <p>
     *                   ssid：TPLink
     * @param macs       （可选）wifi 列表中 mac 信息 ：单 mac 信息同 mmac，mac 之间使用“|” 分隔。必须填写 2 个及 2 个以上,30 个以内的方可正常定位。请不要包含移动 wifi 信息
     * @return 智能硬件定位Vo
     */
    @Post(url = "/v5/position/IoT?output=json"
        + "#{T(ForestUtils).parameter('diu', #diu)}"
        + "#{T(ForestUtils).parameter('cdma', #cdma)}"
        + "#{T(ForestUtils).parameter('network', #network)}"
        + "#{T(ForestUtils).parameter('bts', #bts)}"
        + "#{T(ForestUtils).parameter('nearbts', #nearbts)}"
        + "#{T(ForestUtils).parameter('historybts', #historybts)}"
        + "#{T(ForestUtils).parameter('mmac', #mmac)}"
        + "#{T(ForestUtils).parameter('macs', #macs)}"
    )
    Position2Vo positionV5(@Query("accesstype") String accesstype, String diu, String cdma, String network, String bts, String nearbts, String historybts, String mmac, String macs);

    /**
     * 指定线路交通态势查询
     *
     * @param level  （必填）道路等级：指定道路等级，下面各值代表的含义：  1：高速（京藏高速）  2：城市快速路、国道(西三环、103 国道)  3：高速辅路（G6 辅路）  4：主要道路（长安街、三环辅路）  5：一般道路（彩和坊路）  6：无名道路  注：以上值是包含关系，如 5 除自身道路以外还包含 1，2，3，4 的道路
     * @param name   （必填）街道名：道路名称
     * @param city   （可选）城市名：由于开发者可能对城市称呼和高德的称呼存在差异（例如开发者称呼为深圳，但高德仅识别深圳市）。  故强烈建议使用 adcode，不使用 city字段
     * @param adcode （可选）城市编码：由于开发者可能对城市称呼和高德的称呼存在差异（例如开发者称呼为深圳，但高德仅识别深圳市）。  故强烈建议使用 adcode，不使用 city字段
     *               <p>
     *               adcode信息可参考城市编码表获取
     * @return 交通态势信息
     */
    @Get(url = "/v3/traffic/status/road?output=json"
        + "#{T(ForestUtils).parameter('city', #city)}"
        + "#{T(ForestUtils).parameter('adcode', #adcode)}"
    )
    RoadVo roadV3(@Query("level") String level, @Query("name") String name, String city, String adcode);

    /**
     * 圆形区域内交通态势查询
     *
     * @param level      （必填）道路等级：指定道路等级，下面各值代表的含义：  1：高速（京藏高速）  2：城市快速路、国道(西三环、103 国道)  3：高速辅路（G6 辅路）  4：主要道路（长安街、三环辅路）  5：一般道路（彩和坊路）  6：无名道路  注：以上值是包含关系，如 5 除自身道路以外还包含 1，2，3，4 的道路
     * @param location   （必填）中心点坐标 ：经度在前，纬度在后。经度和纬度用","分割。经纬度小数点后不得超过6位
     * @param radius     （可选）半径 ：单位：米，最大取值 5000 米
     * @param extensions （可选）控制返回内容：可选：base，all
     *                   <p>
     *                   base：返回基本信息
     *                   <p>
     *                   all：返回基本+详细信息
     * @return 交通态势信息
     */
    @Get(url = "/v3/traffic/status/circle?output=json&location=${location.lng},${location.lat}"
        + "#{T(ForestUtils).parameter('radius', #radius)}"
        + "#{T(ForestUtils).parameter('extensions', #extensions)}"
    )
    CircleVo circleV3(@Query("level") String level, @Var("location") CoordinateUtil.Coordinate location, String radius, String extensions);

    /**
     * 矩形区域内交通态势查询
     *
     * @param level      （必填）道路等级：指定道路等级，下面各值代表的含义：  1：高速（京藏高速）  2：城市快速路、国道(西三环、103 国道)  3：高速辅路（G6 辅路）  4：主要道路（长安街、三环辅路）  5：一般道路（彩和坊路）  6：无名道路  注：以上值是包含关系，如 5 除自身道路以外还包含 1，2，3，4 的道路
     * @param rectangle  （必填）希望查询矩形坐标 ：左下右上顶点坐标对。矩形对角线不能超过 10 公里两个坐标对之间用”;”间隔。  xy 之间用”,”间隔最后格式为 x1,y1;x2,y2
     * @param extensions （可选）控制返回内容：可选：base，all
     *                   <p>
     *                   base：返回基本信息
     *                   <p>
     *                   all：返回基本+详细信息
     * @return 交通态势信息
     */
    @Get(url = "/v3/traffic/status/rectangle?output=json"
        + "#{T(ForestUtils).parameter('extensions', #extensions)}"
    )
    RectangleVo rectangleV3(@Query("level") String level, @Query("rectangle") String rectangle, String extensions);

    /**
     * 未来路径规划
     *
     * @param origin      （必填）出发点：
     * @param destination （必填）目的地：
     * @param firsttime   （必填）出发时间，第一个时间戳：unix时间戳，精确到秒，必须是未来时间，如果小于当前时间会报错。
     * @param interval    （必填）时间间隔：单位：秒
     * @param count       （必填）时间点个数：最多支持48个
     * @param strategy    （可选）规划策略：1.返回的结果考虑路况，尽量躲避拥堵而规划路径，与高德地图的“躲避拥堵”策略一致
     *                    <p>
     *                    2.返回的结果不走高速，与高德地图“不走高速”策略一致
     *                    <p>
     *                    3.返回的结果尽可能规划收费较低甚至免费的路径，与高德地图“避免收费”策略一致
     *                    <p>
     *                    4.返回的结果考虑路况，尽量躲避拥堵而规划路径，并且不走高速，与高德地图的“躲避拥堵&不走高速”策略一致
     *                    <p>
     *                    5.返回的结果尽量不走高速，并且尽量规划收费较低甚至免费的路径结果，与高德地图的“避免收费&不走高速”策略一致
     *                    <p>
     *                    6.返回路径规划结果会尽量的躲避拥堵，并且规划收费较低甚至免费的路径结果，与高德地图的“躲避拥堵&避免收费”策略一致
     *                    <p>
     *                    7.返回的结果尽量躲避拥堵，规划收费较低甚至免费的路径结果，并且尽量不走高速路，与高德地图的“避免拥堵&避免收费&不走高速”策略一致
     *                    <p>
     *                    8.返回的结果会优先选择高速路，与高德地图的“高速优先”策略一致
     *                    <p>
     *                    9.返回的结果会优先考虑高速路，并且会考虑路况躲避拥堵，与高德地图的“躲避拥堵&高速优先”策略一致
     *                    <p>
     *                    10.不考虑路况，返回速度最优、耗时最短的路线，但是此路线不一定距离最短
     *                    <p>
     *                    11.避让拥堵&速度优先&避免收费
     * @param province    （可选）用汉字填入车牌省份缩写，用于判断是否限行：例如：京
     * @param number      （可选）填入除省份及标点之外，车牌的字母和数字（需大写）。用于判断限行相关：例如:NH1N11
     *                    <p>
     *                    支持 6 位传统车牌和7位新能源车牌
     * @param cartype     （可选）车辆类型：0：普通汽车(默认值) 1：纯电动车 2：插电混动车
     * @return 未来路径规划结果
     */
    @Get(url = "/v4/etd/driving?output=json&origin=${origin.lng},${origin.lat}&destination=${destination.lng},${destination.lat}"
        + "#{T(ForestUtils).parameter('strategy', #strategy)}"
        + "#{T(ForestUtils).parameter('province', #province)}"
        + "#{T(ForestUtils).parameter('number', #number)}"
        + "#{T(ForestUtils).parameter('cartype', #cartype)}"
    )
    DrivingV4Vo drivingV4(@Var("origin") CoordinateUtil.Coordinate origin, @Var("destination") CoordinateUtil.Coordinate destination, @Query("firsttime") String firsttime, @Query("interval") String interval, @Query("count") String count, String strategy, String province, String number, String cartype);

    /**
     * 三方数据空间检索：关键字搜索
     *
     * @param datasetId （必填）数据集ID：暂时仅支持单个数据集
     * @param keywords  （必填）关键字：[string1]|[string2]|[string3]
     *                  <p>
     *                  所有字段类型均parse成string
     * @param field     （可选）属性字段：选择关键字所在的属性字段
     *                  <p>
     *                  例如：[属性1]｜[属性2]｜[属性3]
     * @param type      （可选）条件类型：0: 包含
     *                  <p>
     *                  1：等于
     * @param offset    （可选）每页记录数据：整型, 强烈建议不超过25，若超过25可能造成访问报错
     * @param page      （可选）当前页数：整型
     * @return 三方数据空间检索结果
     */
    @Get(url = "/rest/lbs/geohub/place/text?output=json"
        + "#{T(ForestUtils).parameter('properties_field', #field)}"
        + "#{T(ForestUtils).parameter('condition_type', #type)}"
        + "#{T(ForestUtils).parameter('offset', #offset)}"
        + "#{T(ForestUtils).parameter('page', #page)}"
    )
    GeohubVo text(@Query("dataset_id") String datasetId, @Query("keywords") String keywords, String field, String type, Integer offset, Integer page);

    /**
     * 三方数据空间检索：多边形搜索
     *
     * @param datasetId  （必填）数据集ID：暂时仅支持单个数据集
     * @param polygon    （必填）多边形区域：多个坐标对集合，坐标对用"|"分割。多边形为矩形时，可传入左上右下两顶点坐标对；其他情况下首尾坐标对需相同。
     * @param properties （可选）筛选条件：各属性类型支持的操作, JSON数据, 示例:
     *                   <p>
     *                   {
     *                   <p>
     *                   "relation": "and", // 各个属性间的或与操作, 可选and,&&,or,||
     *                   <p>
     *                   "conditions": [{
     *                   <p>
     *                   "field": "alias", // 属性名
     *                   <p>
     *                   "operation": "like", // 逻辑操作, 可选equal,=,not_equal,<>,greater,>,greater_equal,>=,less,<,less_equal,<=,in,like_any,like,not_like
     *                   <p>
     *                   "value": "别名" // 属性值
     *                   <p>
     *                   }]
     *                   <p>
     *                   }
     *                   <p>
     *                   两层逻辑（age字段>1且<10）示例：
     *                   <p>
     *                   {
     *                   <p>
     *                   "relation": "and",
     *                   <p>
     *                   "conditions": [{
     *                   <p>
     *                   "field": "age",
     *                   <p>
     *                   "relation": "and",
     *                   <p>
     *                   "conditions":[{
     *                   <p>
     *                   "operation": ">",
     *                   <p>
     *                   "value": 1
     *                   <p>
     *                   },{
     *                   <p>
     *                   "operation": "<",
     *                   <p>
     *                   "value": 10
     *                   <p>
     *                   }]
     *                   <p>
     *                   }]
     *                   <p>
     *                   }
     * @param offset     （可选）每页记录数据：整型, 强烈建议不超过25，若超过25可能造成访问报错
     * @param page       （可选）当前页数：整型
     * @return 三方数据空间检索结果
     */
    @Get(url = "/rest/lbs/geohub/place/polygon?output=json"
        + "#{T(ForestUtils).parameter('properties', #properties)}"
        + "#{T(ForestUtils).parameter('offset', #offset)}"
        + "#{T(ForestUtils).parameter('page', #page)}"
    )
    GeohubVo polygon(@Query("dataset_id") String datasetId, @Query("polygon") String polygon, String properties, Integer offset, Integer page);

    /**
     * 三方数据空间检索： 周边搜索
     *
     * @param datasetId  （必填）数据集ID：暂时仅支持单个数据集
     * @param location   （必填）中心点位置：多中心点经纬度坐标, 如: 120.165904,35.982862
     * @param radius     （必填）半径：以中心点为圆心的距离半径（单位：m）取值范围：(0,50000]
     * @param properties （可选）筛选条件：各属性类型支持的操作, JSON数据, 示例:
     *                   <p>
     *                   {
     *                   <p>
     *                   "relation": "and", // 各个属性间的或与操作, 可选and,&&,or,||
     *                   <p>
     *                   "conditions": [{
     *                   <p>
     *                   "field": "alias", // 属性名
     *                   <p>
     *                   "operation": "like", // 逻辑操作, 可选equal,=,not_equal,<>,greater,>,greater_equal,>=,less,<,less_equal,<=,in,like_any,like,not_like
     *                   <p>
     *                   "value": "别名" // 属性值
     *                   <p>
     *                   }]
     *                   <p>
     *                   }
     *                   <p>
     *                   两层逻辑（age字段>1且<10）示例：
     *                   <p>
     *                   {
     *                   <p>
     *                   "relation": "and",
     *                   <p>
     *                   "conditions": [{
     *                   <p>
     *                   "field": "age",
     *                   <p>
     *                   "relation": "and",
     *                   <p>
     *                   "conditions":[{
     *                   <p>
     *                   "operation": ">",
     *                   <p>
     *                   "value": 1
     *                   <p>
     *                   },{
     *                   <p>
     *                   "operation": "<",
     *                   <p>
     *                   "value": 10
     *                   <p>
     *                   }]
     *                   <p>
     *                   }]
     *                   <p>
     *                   }
     * @param offset     （可选）每页记录数据：整型, 强烈建议不超过25，若超过25可能造成访问报错
     * @param page       （可选）当前页数：整型
     * @return 三方数据空间检索结果
     */
    @Get(url = "/rest/lbs/geohub/place/around?output=json"
        + "#{T(ForestUtils).parameter('properties', #properties)}"
        + "#{T(ForestUtils).parameter('offset', #offset)}"
        + "#{T(ForestUtils).parameter('page', #page)}"
    )
    GeohubVo around(@Query("dataset_id") String datasetId, @Query("location") String location, @Query("radius") String radius, String properties, Integer offset, Integer page);

    /**
     * 三方数据空间检索： 属性筛选
     *
     * @param datasetId  （必填）数据集ID：暂时仅支持单个数据集
     * @param properties （必填）筛选条件：各属性类型支持的操作, JSON数据, 示例:
     *                   <p>
     *                   {
     *                   <p>
     *                   "relation": "and", // 各个属性间的或与操作, 可选and,&&,or,||
     *                   <p>
     *                   "conditions": [{
     *                   <p>
     *                   "field": "alias", // 属性名
     *                   <p>
     *                   "operation": "like", // 逻辑操作, 可选equal,=,not_equal,<>,greater,>,greater_equal,>=,less,<,less_equal,<=,in,like_any,like,not_like
     *                   <p>
     *                   "value": "别名" // 属性值
     *                   <p>
     *                   }]
     *                   <p>
     *                   }
     *                   <p>
     *                   两层逻辑（age字段>1且<10）示例：
     *                   <p>
     *                   {
     *                   <p>
     *                   "relation": "and",
     *                   <p>
     *                   "conditions": [{
     *                   <p>
     *                   "field": "age",
     *                   <p>
     *                   "relation": "and",
     *                   <p>
     *                   "conditions":[{
     *                   <p>
     *                   "operation": ">",
     *                   <p>
     *                   "value": 1
     *                   <p>
     *                   },{
     *                   <p>
     *                   "operation": "<",
     *                   <p>
     *                   "value": 10
     *                   <p>
     *                   }]
     *                   <p>
     *                   }]
     *                   <p>
     *                   }
     * @param offset     （可选）每页记录数据：整型, 强烈建议不超过25，若超过25可能造成访问报错
     * @param page       （可选）当前页数：整型
     * @return 三方数据空间检索结果
     */
    @Get(url = "/rest/lbs/geohub/place/properties?output=json"
        + "#{T(ForestUtils).parameter('offset', #offset)}"
        + "#{T(ForestUtils).parameter('page', #page)}"
    )
    GeohubVo properties(@Query("dataset_id") String datasetId, @Query("properties") String properties, Integer offset, Integer page);

}
