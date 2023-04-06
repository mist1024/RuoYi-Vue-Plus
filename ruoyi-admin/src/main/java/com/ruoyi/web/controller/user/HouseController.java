package com.ruoyi.web.controller.user;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.DownloadGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.BuyHouses;
import com.ruoyi.system.domain.bo.BuyHousesBo;
import com.ruoyi.system.service.IBuyHousesService;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.GetProxy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
     * 购房申请添加
     */
    @Log(title = "【购房申请添加】", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BuyHousesBo bo) {
        return toAjax(iBuyHousesService.insertByBo(bo));
    }

    /**
     * 购房申请修改
     */
    @Log(title = "【购房申请修改】", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BuyHousesBo bo) {
        return toAjax(iBuyHousesService.updateByBo(bo));
    }

    /**
     * 购房获取详情
     */
    @Log(title = "【购房获取详情】", businessType = BusinessType.OTHER)
    @RepeatSubmit()
    @PostMapping("info")
    public R<?> getInfo(@RequestBody BuyHouses buyHouses) {
        return iBuyHousesService.getInfo(buyHouses);
    }


    /**
     * 下载人才认定申请表
     */
    @PostMapping("/download")
    public R downloadWord(@Validated(DownloadGroup.class) @RequestBody BuyHousesBo buyHousesBo){
        return iBuyHousesService.downloadWord(buyHousesBo);
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
    @PostMapping("/CandidatesInfo")
    public R getGaoXinCandidateInfoByCardId(@RequestBody BuyHouses buyHouses){
        String cardId = buyHouses.getCardId();
        if (ObjectUtil.isNull(cardId) || ObjectUtil.isEmpty(cardId)){
            return R.fail("key.not.exist");
        }
        return iBuyHousesService.getGaoXinCandidateInfoByCardId(cardId);
    }
}
