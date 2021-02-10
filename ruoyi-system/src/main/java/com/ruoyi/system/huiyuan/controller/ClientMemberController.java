package com.ruoyi.system.huiyuan.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.huiyuan.service.IHyMemberDaoService;
import com.ruoyi.system.huiyuan.service.IHyProjDaoService;
import com.ruoyi.system.huiyuan.service.IHyProjTalentDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ryo
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/huiyuan")
public class ClientMemberController extends BaseController {

    private final IHyMemberDaoService iHyMemberDaoService;

    private final IHyProjDaoService iHyProjDaoService;

    private final IHyProjTalentDaoService iHyProjTalentDaoService;

    /**
     * 获取会员主页 URL
     *
     * @param memberId：会员 id
     * @author ryo
     */
    @GetMapping("/getMemberUrl/{memberId}")
    public AjaxResult getMemberUrl(@PathVariable Long memberId) {

        return AjaxResult.success(iHyMemberDaoService.getById(memberId).getMemberUrl());
    }

    /**
     * 获取会员积分
     *
     * @param memberId：会员 id
     * @author ryo
     */
    @GetMapping("/getMemberPoints/{memberId}")
    public AjaxResult getMemberPoints(@PathVariable Long memberId) {

        return AjaxResult.success(iHyMemberDaoService.getById(memberId).getPoints());
    }

    /**
     * 获取会员余额
     *
     * @param memberId：会员 id
     * @author ryo
     */
    @GetMapping("/getMemberBalance/{memberId}")
    public AjaxResult getMemberBalance(@PathVariable Long memberId) {

        return AjaxResult.success(iHyMemberDaoService.getById(memberId).getBalance());
    }

    /**
     * 获取会员代金券数量
     *
     * @param memberId：会员 id
     * @author ryo
     */
    @GetMapping("/getMemberVoucher/{memberId}")
    public AjaxResult getMemberVoucher(@PathVariable Long memberId) {

        return AjaxResult.success(iHyMemberDaoService.getById(memberId).getVoucher());
    }

    /**
     * 获取会员具体项目首页 URL
     *
     * @param memberId：会员 id
     * @author ryo
     */
    @GetMapping("/getMemberProjUrl/{memberId}")
    public AjaxResult getMemberProjUrl(@PathVariable Long memberId) {

        Long projId = iHyMemberDaoService.getById(memberId).getProjId();

        return AjaxResult.success(iHyProjDaoService.getById(projId).getHomeUrl());
    }


}
