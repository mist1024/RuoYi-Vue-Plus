package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.BuyHousesReviewMember;
import com.ruoyi.system.domain.HousesReview;
import com.ruoyi.system.domain.MaterialModule;
import com.ruoyi.system.domain.MaterialProof;
import com.ruoyi.system.domain.bo.HousesReviewBo;
import com.ruoyi.system.domain.vo.HousesReviewVo;
import com.ruoyi.system.domain.vo.MaterialModuleVo;
import com.ruoyi.system.mapper.BuyHousesReviewMemberMapper;
import com.ruoyi.system.mapper.HousesReviewMapper;
import com.ruoyi.system.mapper.MaterialProofMapper;
import com.ruoyi.system.service.IHousesReviewService;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.utils.WorkComplyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 购房复审登记Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-08
 */
@RequiredArgsConstructor
@Service
@Transactional
public class HousesReviewServiceImpl implements IHousesReviewService {

    private final HousesReviewMapper baseMapper;

    private final MaterialModuleServiceImpl materialModuleService;

    private final MaterialProofMapper materialProofMapper;
    private final BuyHousesReviewMemberMapper buyHousesReviewMemberMapper;

    private final SysOssServiceImpl sysOssService;


    /**
     * 查询购房复审登记
     */
    @Override
    public HousesReviewVo queryById(Long id){
        HousesReviewVo housesReviewVo = baseMapper.selectVoById(id);
        LambdaQueryWrapper<BuyHousesReviewMember> queryWrapper = new LambdaQueryWrapper<BuyHousesReviewMember>()
            .eq(BuyHousesReviewMember::getBuyHousesId, housesReviewVo.getId());
        List<BuyHousesReviewMember> buyHousesReviewMembers = buyHousesReviewMemberMapper.selectList(queryWrapper);
        housesReviewVo.setBuyHousesMemberList(buyHousesReviewMembers);
        if (buyHousesReviewMembers.size()==0){
            housesReviewVo.setBuyHousesMemberList(new ArrayList<>());
        }
        return housesReviewVo;
    }

    /**
     * 查询购房复审登记列表
     */
    @Override
    public TableDataInfo<HousesReviewVo> queryPageList(HousesReviewBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HousesReview> lqw = buildQueryWrapper(bo);
        Page<HousesReviewVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询购房复审登记列表
     */
    @Override
    public List<HousesReviewVo> queryList(HousesReviewBo bo) {
        LambdaQueryWrapper<HousesReview> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<HousesReview> buildQueryWrapper(HousesReviewBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HousesReview> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getCardType()), HousesReview::getCardType, bo.getCardType());
        lqw.eq(StringUtils.isNotBlank(bo.getCard()), HousesReview::getCard, bo.getCard());
        lqw.like(StringUtils.isNotBlank(bo.getName()), HousesReview::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getQualification()), HousesReview::getQualification, bo.getQualification());
        lqw.eq(StringUtils.isNotBlank(bo.getAuditTime()), HousesReview::getAuditTime, bo.getAuditTime());
        lqw.eq(StringUtils.isNotBlank(bo.getPresellCard()), HousesReview::getPresellCard, bo.getPresellCard());
        lqw.eq(StringUtils.isNotBlank(bo.getDealType()), HousesReview::getDealType, bo.getDealType());
        lqw.like(StringUtils.isNotBlank(bo.getProjectName()), HousesReview::getProjectName, bo.getProjectName());
        lqw.eq(StringUtils.isNotBlank(bo.getProjectArea()), HousesReview::getProjectArea, bo.getProjectArea());
        lqw.eq(StringUtils.isNotBlank(bo.getQualificationConfirmTime()), HousesReview::getQualificationConfirmTime, bo.getQualificationConfirmTime());
        lqw.eq(StringUtils.isNotBlank(bo.getQualificationPreApplyTime()), HousesReview::getQualificationPreApplyTime, bo.getQualificationPreApplyTime());
        lqw.eq(StringUtils.isNotBlank(bo.getFamilyType()), HousesReview::getFamilyType, bo.getFamilyType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), HousesReview::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getRegisterFailureTime()), HousesReview::getRegisterFailureTime, bo.getRegisterFailureTime());
        lqw.eq(StringUtils.isNotBlank(bo.getNationality()), HousesReview::getNationality, bo.getNationality());
        lqw.eq(StringUtils.isNotBlank(bo.getMaritalStatus()), HousesReview::getMaritalStatus, bo.getMaritalStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getCompanyType()), HousesReview::getCompanyType, bo.getCompanyType());
        lqw.like(StringUtils.isNotBlank(bo.getCompanyName()), HousesReview::getCompanyName, bo.getCompanyName());
        lqw.eq(StringUtils.isNotBlank(bo.getTalentsType()), HousesReview::getTalentsType, bo.getTalentsType());
        lqw.eq(StringUtils.isNotBlank(bo.getCreditCode()), HousesReview::getCreditCode, bo.getCreditCode());
        lqw.eq(StringUtils.isNotBlank(bo.getCompanyAddress()), HousesReview::getCompanyAddress, bo.getCompanyAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getSourceBy()), HousesReview::getSourceBy, bo.getSourceBy());
        lqw.eq(StringUtils.isNotBlank(bo.getProcessKey()), HousesReview::getProcessKey, bo.getProcessKey());
        return lqw;
    }

    /**
     * 新增购房复审登记
     */
    @Override
    public Boolean insertByBo(HousesReviewBo bo) {
        //先判断是否存在数据
        HousesReview add = BeanUtil.toBean(bo, HousesReview.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            if (bo.getBuyHousesMemberList().size()>0){
                bo.getBuyHousesMemberList().stream().forEach( e ->{
                    e.setBuyHousesId(add.getId().toString());
                });

                buyHousesReviewMemberMapper.insertBatch(bo.getBuyHousesMemberList());
            }
            bo.setId(add.getId());
        }

        return flag;
    }

    /**
     * 修改购房复审登记
     */
    @Override
    public Boolean updateByBo(HousesReviewBo bo) {
        //先删除存在的关系表
        LambdaQueryWrapper<BuyHousesReviewMember> wrapper = new LambdaQueryWrapper<BuyHousesReviewMember>()
            .eq(BuyHousesReviewMember::getBuyHousesId, bo.getId());
        List<BuyHousesReviewMember> buyHousesReviewMembers = buyHousesReviewMemberMapper.selectList(wrapper);
        if (ObjectUtil.isNotNull(buyHousesReviewMembers) && buyHousesReviewMembers.size()>0) {
            List<BuyHousesReviewMember> buyHousesMemberList = bo.getBuyHousesMemberList();
            //先删除minio的图片
            List<Long> list = new ArrayList<>();
            if (ObjectUtil.isNotNull(buyHousesMemberList) && buyHousesMemberList.size()>0) {
                buyHousesReviewMembers.forEach(e -> {
                    /*boolean b1 = buyHousesMemberList.stream().noneMatch(b -> b.getFrontUrl().equals(e.getFrontUrl()));
                    boolean b1 = buyHousesMemberList.stream().noneMatch(b -> b.getInsidepageUrl().equals(e.getInsidepageUrl()));
                    boolean b1 = buyHousesMemberList.stream().noneMatch(b -> b.getHomeRecordUrl().equals(e.getHomeRecordUrl()));
                    boolean b1 = buyHousesMemberList.stream().noneMatch(b -> b.getReverseUrl().equals(e.getReverseUrl()));*/
                    if (ObjectUtil.isNotNull(e.getFrontUrl())){
                        if (!buyHousesMemberList.stream().anyMatch(b -> e.getFrontUrl().equals(b.getFrontUrl()))){
                            list.add(new Long(e.getFrontUrl()));
                        }
                    }
                    if (ObjectUtil.isNotNull(e.getInsidepageUrl())) {
                        if (!buyHousesMemberList.stream().anyMatch(b ->  e.getInsidepageUrl().equals(b.getInsidepageUrl()))) {
                            list.add(new Long(e.getFrontUrl()));
                        }
                    }
                    if (ObjectUtil.isNotNull(e.getHomeRecordUrl())) {
                        if (!buyHousesMemberList.stream().anyMatch(b -> ObjectUtil.isNotNull(e.getHomeRecordUrl()) && e.getHomeRecordUrl().equals(b.getHomeRecordUrl()))) {
                            list.add(new Long(e.getFrontUrl()));
                        }
                    }
                    if (ObjectUtil.isNotNull(e.getReverseUrl())) {
                        if (!buyHousesMemberList.stream().anyMatch(b -> ObjectUtil.isNotNull(e.getReverseUrl()) && e.getReverseUrl().equals(b.getReverseUrl()))) {
                            list.add(new Long(e.getFrontUrl()));
                        }
                    }
                });
            }else {
                List<Long> insidepageUrl = buyHousesReviewMembers.stream().filter(s ->ObjectUtil.isNotNull(s.getInsidepageUrl())).map(s -> Long.parseLong(s.getInsidepageUrl())).collect(Collectors.toList());
                if (ObjectUtil.isNotNull(insidepageUrl)&& insidepageUrl.size()>0){
                    list.addAll(insidepageUrl);
                }
                List<Long> homeRecordUrl = buyHousesReviewMembers.stream().filter(s ->ObjectUtil.isNotNull(s.getHomeRecordUrl())).map(s -> Long.parseLong(s.getHomeRecordUrl())).collect(Collectors.toList());
                if (ObjectUtil.isNotNull(homeRecordUrl)&& homeRecordUrl.size()>0){
                    list.addAll(homeRecordUrl);
                }
                List<Long> reverseUrl = buyHousesReviewMembers.stream().filter(s ->ObjectUtil.isNotNull(s.getReverseUrl())).map(s -> Long.parseLong(s.getReverseUrl())).collect(Collectors.toList());
                if (ObjectUtil.isNotNull(reverseUrl)&& reverseUrl.size()>0){
                    list.addAll(reverseUrl);
                }
                List<Long> frontUrl = buyHousesReviewMembers.stream().filter(s ->ObjectUtil.isNotNull(s.getFrontUrl())).map(s -> Long.parseLong(s.getFrontUrl())).collect(Collectors.toList());
                if (ObjectUtil.isNotNull(frontUrl)&& frontUrl.size()>0){
                    list.addAll(frontUrl);
                }
            }
            if (list.size()>0){
                sysOssService.deleteWithValidByIds(list,true);
            }
            List<Long> collect = buyHousesReviewMembers.stream().map(BuyHousesReviewMember::getId).collect(Collectors.toList());
            if (collect.size()>0) {
                buyHousesReviewMemberMapper.deleteBatchIds(collect);
            }
        }
        LambdaQueryWrapper<MaterialProof> queryWrapper = new LambdaQueryWrapper<MaterialProof>()
            .eq(MaterialProof::getHouseId, bo.getId());
        List<MaterialProof> materialProofs = materialProofMapper.selectList(queryWrapper);
        if (ObjectUtil.isNotNull(materialProofs) && materialProofs.size()>0) {
            List<Long> list = new ArrayList<>();
            List<MaterialModule> materialsList = bo.getMaterialsList();
            if (ObjectUtil.isNotNull(materialsList) && materialsList.size() > 0){
                materialProofs.forEach(mp ->{
                    boolean b = materialsList.stream().anyMatch(e -> ObjectUtil.isNotNull(mp.getFile()) && mp.getFile().equals(e.getFile()));
                    if (!b){
                        Long aLong = new Long(mp.getFile());
                        list.add(aLong);
                    }
                });
            }else {
                list.addAll(materialProofs.stream().filter(s ->ObjectUtil.isNotNull(s.getFile())).map(s -> Long.parseLong(s.getFile())).collect(Collectors.toList()));
            }
            if (list.size()>0) {
                sysOssService.deleteWithValidByIds(list, true);
            }
            List<Long> collect = materialProofs.stream().map(MaterialProof::getId).collect(Collectors.toList());
            if (collect.size()>0) {
                materialProofMapper.deleteBatchIds(collect);
            }
        }
        if (ObjectUtil.isNotNull(bo.getBuyHousesMemberList()) && bo.getBuyHousesMemberList().size()>0){
            bo.getBuyHousesMemberList().stream().forEach( e ->{
                e.setBuyHousesId(bo.getId().toString());
            });
            buyHousesReviewMemberMapper.insertBatch(bo.getBuyHousesMemberList());
        }
        if(ObjectUtil.isNotNull(bo.getMaterialsList()) && bo.getMaterialsList().size()>0){
            ArrayList<MaterialProof> list = new ArrayList<>();
            bo.getMaterialsList().forEach(e ->{
                MaterialProof materialProof = BeanUtil.toBean(e, MaterialProof.class);
                materialProof.setHouseId(bo.getId().toString());
                materialProof.setStatus(0L);
                materialProof.setModulePathId(e.getId().toString());
                materialProof.setId(null);
                list.add(materialProof);
            });
            materialProofMapper.insertBatch(list);
        }
        bo.setProcessKey("house_review");
        HousesReview update = BeanUtil.toBean(bo, HousesReview.class);
        validEntityBeforeSave(update);
        int i = baseMapper.updateById(update);
        if (i>0) {
            ProcessVo processVo = new ProcessVo();
            processVo.setProcessKey("house_review");
            processVo.setStep("1");
            Map<String, Object> map = BeanUtil.beanToMap(bo);
            processVo.setParams(map);
            processVo.setBusinessId(bo.getId().toString());
            processVo.setStartUser(bo.getName());
            WorkComplyUtils.comply(processVo);
        }
        return i>0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(HousesReview entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除购房复审登记
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean saveBatch(List<HousesReview> list) {

        return baseMapper.insertBatch(list);
    }

    /**
     * 获取材料接口
     * @param bo
     * @return
     */
    @Override
    public R<?> getMaterialInfo(HousesReviewBo bo) {
        List<MaterialModuleVo> materialInfo = materialModuleService.getMaterialInfo(bo);
        return R.ok(materialInfo);
    }

    @Override
    public HousesReviewVo queryByIdOne(Long id) {
        HousesReviewVo housesReviewVo = baseMapper.selectVoById(id);
        LambdaQueryWrapper<BuyHousesReviewMember> queryWrapper = new LambdaQueryWrapper<BuyHousesReviewMember>()
            .eq(BuyHousesReviewMember::getBuyHousesId, housesReviewVo.getId());
        List<BuyHousesReviewMember> buyHousesReviewMembers = buyHousesReviewMemberMapper.selectList(queryWrapper);
        housesReviewVo.setBuyHousesMemberList(buyHousesReviewMembers);
        if (buyHousesReviewMembers.size()==0){
            housesReviewVo.setBuyHousesMemberList(new ArrayList<>());
        }
        return housesReviewVo;

    }

    /**
     * 获取材料
     * @param id
     * @return
     */
    @Override
    public R<?> getMaterialByBusinessId(Long id) {
        LambdaQueryWrapper<MaterialProof> wrapper = new LambdaQueryWrapper<MaterialProof>()
            .eq(MaterialProof::getHouseId, id);
        return R.ok(materialProofMapper.selectList(wrapper));


    }
}
