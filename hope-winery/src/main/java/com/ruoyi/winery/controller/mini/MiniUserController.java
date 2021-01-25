package com.ruoyi.winery.controller.mini;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.winery.component.MiniComponent;
import com.ruoyi.winery.domain.winery.WineryCompanyRecord;
import com.ruoyi.winery.enums.IrrigationTypeEnum;
import com.ruoyi.winery.enums.SoilTypeEnum;
import com.ruoyi.winery.enums.WineryStatusEnum;
import com.ruoyi.winery.service.IWineryCompanyRecordService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.ruoyi.winery.define.MiniDefine.MINI_USER_SYMBOL;

/**
 * @author tottimctj
 * @since 2020-11-10
 */
@RestController
@RequestMapping("/winery/mini/user")
@Slf4j
public class MiniUserController {


    @Autowired
    MiniComponent miniComponent;

    @Autowired
    IWineryCompanyRecordService recordWineryService;


    @Autowired
    private ISysUserService userService;

    /**
     * 通过微信api授权获取手机号
     *
     * @param json
     * @return
     */
    @Log(title = "发送小程序手机号码", businessType = BusinessType.OTHER)
    @PostMapping("/sendMobile")
    AjaxResult sendMobile(@RequestBody JSONObject json) {

        String mobile = miniComponent.getMobile(json);

        if (StrUtil.isBlank(mobile)) {
            return AjaxResult.error("获取失败!");
        }
        JSONObject rsp = new JSONObject();
        rsp.set("mobile", mobile);

        return AjaxResult.success("获取成功.", rsp);

    }

    /**
     * 小程序进行注册用户
     *
     * @param json
     * @return
     */
    @Log(title = "小程序进行注册用户", businessType = BusinessType.OTHER)
    @PostMapping("/registrationByMini")
    @RepeatSubmit
    AjaxResult postMobileRegistration(@RequestBody JSONObject json) {

        String openid = json.getStr("openid");
        String mobile = json.getStr("mobile");
        Long deptId = json.getLong("deptId");
        String nickName = json.getJSONObject("userInfo").getStr("nickName");
        String avatar = json.getJSONObject("userInfo").getStr("avatarUrl");
        return miniComponent.registration(openid, mobile, nickName, deptId, avatar);
    }


    @Log(title = "微信小程序登录换取openid", businessType = BusinessType.OTHER)
    @GetMapping("/getSession")
    public AjaxResult getSession(@RequestParam("code") String code, @RequestParam("deptId") Long deptId) throws WxErrorException {
        WxMaJscode2SessionResult sessionInfo = miniComponent.login(code, deptId);
        JSONObject json = new JSONObject();
        json.set("openid", sessionInfo.getOpenid());
        log.info("微信小程序获取openid信息成功:{}", sessionInfo.getOpenid());

        return AjaxResult.success(json);
    }


    @Log(title = "微信小程序登录系统", businessType = BusinessType.OTHER)
    @PostMapping("/loginByMini")
    public AjaxResult loginByMini(@RequestBody JSONObject json) {
        AjaxResult ajax = AjaxResult.success();
        String userAccount = MINI_USER_SYMBOL + json.getStr("openid") + "-" + json.getLong("deptId");
        // 生成令牌
        String token = miniComponent.loginByMini(userAccount);
        ajax.put(Constants.TOKEN, token);
        SysUser user =  userService.selectUserByUserName(userAccount);

        JSONObject userInfo = new JSONObject();
        userInfo.set("nickName",user.getNickName());
        userInfo.set("avatarUrl" ,user.getAvatar());
        userInfo.set("mobile" ,user.getPhonenumber());

        ajax.put("userInfo", userInfo);


        return ajax;
    }


    @PostMapping("/postForm")
    AjaxResult postForm(@RequestBody JSONObject json) {

        WineryCompanyRecord record = parseRecordWineryModel(json);
        WineryCompanyRecord old = recordWineryService.getOne(
                new LambdaQueryWrapper<WineryCompanyRecord>().
                        eq(WineryCompanyRecord::getOpenid, json.getStr("openid")));

        if (old != null) {
            record.setId(old.getId());
        }
        return AjaxResult.success(recordWineryService.saveOrUpdate(record));

    }

    @GetMapping("/getForm")
    AjaxResult getForm(@RequestParam String openid) {

        WineryCompanyRecord record = recordWineryService.getOne(
                new LambdaQueryWrapper<WineryCompanyRecord>().
                        eq(WineryCompanyRecord::getOpenid, openid));

        return AjaxResult.success(record);

    }

    @GetMapping("/getRealAuthJson")
    AjaxResult getRealAuthJson(@RequestParam String openid) {


        return AjaxResult.success(miniComponent.getRealAuthJson(openid));

    }

    private WineryCompanyRecord parseRecordWineryModel(JSONObject json) {

        WineryCompanyRecord record = new WineryCompanyRecord();
        record.setOpenid(json.getStr("openid"));
        record.setEmail(json.getStr("email"));
        record.setPersonName(json.getStr("personName"));
        record.setMobile(json.getStr("mobile"));
        record.setWineryName(json.getStr("wineryName"));
        record.setBuildTime(json.getStr("buildTime"));

        record.setRegion(parseArrayToStr(json, "region"));

        record.setAddress(json.getStr("address"));
        record.setWineryArea(json.getLong("wineryArea"));
        record.setBuildArea(json.getLong("buildArea"));

        WineryStatusEnum wineryStatus = WineryStatusEnum.parseEnum(json.getStr("wineryStatus"));
        record.setWineryStatus(wineryStatus);


        record.setPlantArea(json.getLong("plantArea"));
        record.setPlantWeight(json.getLong("plantWeight"));

        SoilTypeEnum soilType = SoilTypeEnum.parseEnum(json.getStr("soilType"));
        record.setSoilType(soilType);


        record.setRedVariety(json.getJSONObject("redVariety").toStringPretty());
        record.setWhiteVariety(json.getJSONObject("whiteVariety").toStringPretty());

        IrrigationTypeEnum irrigationType = IrrigationTypeEnum.parseEnum(json.getStr("irrigationType"));
        record.setIrrigationType(irrigationType);

        record.setFermentationProcess(parseArrayToStr(json, "fermentationProcess"));
        record.setContainer(parseArrayToStr(json, "container"));
        record.setClarificationMethod(parseArrayToStr(json, "clarificationMethod"));


        record.setAnnualOutput(json.getFloat("annualOutput"));
        record.setStock(json.getFloat("stock"));
        record.setBucketCount(json.getLong("bucketCount"));

        record.setMainPrice(parseArrayToStr(json, "mainPrice"));
        record.setSalesMode(parseArrayToStr(json, "salesMode"));


        record.setAwards(parseArrayToStr(json, "awards"));
        record.setAwardInformation(json.getStr("awardInformation"));
        record.setSlogan(json.getStr("slogan"));


        return record;

    }


    private String parseArrayToStr(JSONObject json, String key) {
        List<String> list = json.getJSONArray(key).stream().map(x -> (String) x).collect(Collectors.toList());
        return String.join(",", list);
    }

    private String[] parseStrtoArray(JSONObject json, String key) {
        return json.getStr(key).split(",");
    }

}
