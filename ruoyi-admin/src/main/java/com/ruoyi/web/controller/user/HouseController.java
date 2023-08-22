package com.ruoyi.web.controller.user;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.DownloadGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.BuyHouses;
import com.ruoyi.system.domain.bo.BuyHousesBo;
import com.ruoyi.system.service.IBuyHousesService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 客户端购房申请相关接口
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/house")
public class HouseController extends BaseController {
    private final IBuyHousesService iBuyHousesService;
    /**
     * 购房申请修改
     */
    @Log(title = "【购房申请修改】", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BuyHousesBo bo) {
        return toAjax(iBuyHousesService.updateByBo(bo));
    }

    /**
     * 购房获取详情
     */
    @Log(title = "【购房获取详情】", businessType = BusinessType.OTHER)
    @RepeatSubmit()
    @PostMapping("/info")
    public R<?> getInfo(@RequestBody BuyHouses buyHouses) {
        return iBuyHousesService.getInfo(buyHouses);
    }


    /**
     * 下载人才认定申请表
     */
    @Log(title = "下载人才认定申请表", businessType = BusinessType.OTHER)
    @PostMapping("/download")
    @RateLimiter(count = 1, time = 10)
    public R downloadWord(@Validated(DownloadGroup.class) @RequestBody BuyHousesBo buyHousesBo){
        return iBuyHousesService.downloadWord(buyHousesBo);
    }

    /**
     * 下载认定通知单
     */
    @GetMapping("/downloadInform")
    public R downloadInform(){
        return iBuyHousesService.downloadInform();
    }

    /**
     * 获取流程进度列表
     * @return
     */
    @GetMapping("/declareList")
    public R getDeclareList(){
        return R.ok(iBuyHousesService.getDeclareList());
    }


    /**
     * 通过调用高新人才判断该身份证是否具备人才资格
     */
    @Log(title = "通过调用高新人才判断该身份证是否具备人才资格", businessType = BusinessType.OTHER)
    @PostMapping("/CandidatesInfo")
    public R getGaoXinCandidateInfoByCardId(@RequestBody BuyHouses buyHouses){
        String cardId = buyHouses.getCardId();
        if (ObjectUtil.isNull(cardId) || ObjectUtil.isEmpty(cardId)){
            return R.fail("key.not.exist");
        }
        return iBuyHousesService.getGaoXinCandidateInfoByCardId(cardId);
    }


    @GetMapping("/checkStatus")
    public R checkStatus(){
        return iBuyHousesService.checkStatus();
    }

    /**
     * 获取审核日志
     * @return
     */
    @GetMapping("/buyHouseLogs")
    public R getBuyHouseLog(){
        return iBuyHousesService.getBuyHousesLogsByUserId();
    }

    /**
     * 外部推送接口
     * @param bo
     * @return
     * @throws InterruptedException
     */
    @Log(title = "【外部推送接口】", businessType = BusinessType.OTHER)
    @RepeatSubmit()
    @PostMapping("/insertOpenBuyHouses")
    public R<?> insertOpenBuyHouses(@Validated(EditGroup.class)@RequestBody BuyHousesBo bo){
        bo.setApiKey("gaoxingongyuanchengshiju");
        if (ObjectUtil.isNull(bo.getUserId())){
            return R.fail("userId不可为空");
        }
        String cardId = bo.getCardId();
        bo.setCardId(StringUtils.toUpperCase(cardId));
        return  iBuyHousesService.insertOpenBuyHouses(bo);
    }

    @SaIgnore
    @GetMapping("/excelZip")
    public void excelZip(String id){
        ThreadUtil.execAsync(() -> {
                iBuyHousesService.excelZip(id);
        });
    }
}
