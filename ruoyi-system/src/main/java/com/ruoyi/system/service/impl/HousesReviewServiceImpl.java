package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.CreditCodeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.CardsUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.MyFileUtils;
import com.ruoyi.common.utils.poi.DeleteFileUtil;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.poi.ZipUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.bo.HousesReviewBo;
import com.ruoyi.system.domain.dto.HousesReviewEvent;
import com.ruoyi.system.domain.vo.HousesReviewVo;
import com.ruoyi.system.domain.vo.MaterialModuleVo;
import com.ruoyi.system.mapper.BuyHousesReviewMemberMapper;
import com.ruoyi.system.mapper.HousesReviewMapper;
import com.ruoyi.system.mapper.MaterialProofMapper;
import com.ruoyi.system.mapper.SubscribeExportMapper;
import com.ruoyi.system.service.IHousesReviewService;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.utils.WorkComplyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 购房复审登记Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-08
 */
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class HousesReviewServiceImpl implements IHousesReviewService {

    private final HousesReviewMapper baseMapper;

    private final MaterialModuleServiceImpl materialModuleService;

    private final MaterialProofMapper materialProofMapper;
    private final BuyHousesReviewMemberMapper buyHousesReviewMemberMapper;

    private final SubscribeExportMapper subscribeExportMapper;

    @Value("${file.template}")
    private String template;

    @Value("${file.path}")
    private String filePath;

    @Value("${file.domain}")
    private String download;

    @Value("${file.prefix}")
    private String prefix;


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
    public TableDataInfo<HousesReview> queryPageList(HousesReviewBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HousesReview> lqw = buildQueryWrapper2(bo);
        Page<HousesReview> result = baseMapper.selectPage(pageQuery.build(), lqw);
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

    private LambdaQueryWrapper<HousesReview> buildQueryWrapper3(HousesReviewBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HousesReview> lqw = Wrappers.lambdaQuery();
        if (ObjectUtil.isNotNull(bo.getIds()) && bo.getIds().length>0){
            lqw.in(HousesReview::getId,bo.getIds());
        }
        lqw.and(StringUtils.isNotBlank(bo.getName()),t ->
            t.like(HousesReview::getName,bo.getName())
                .or().like(HousesReview::getCard,bo.getName())
                .or().like(HousesReview::getProjectName,bo.getName()));
        lqw.orderByDesc(HousesReview::getUpdateTime);
        lqw.like(StringUtils.isNotBlank(bo.getCardType()), HousesReview::getCardType, bo.getCardType());
        lqw.eq(StringUtils.isNotBlank(bo.getQualification()), HousesReview::getQualification, bo.getQualification());
        lqw.eq(StringUtils.isNotBlank(bo.getAuditTime()), HousesReview::getAuditTime, bo.getAuditTime());
        lqw.like(StringUtils.isNotBlank(bo.getPresellCard()), HousesReview::getPresellCard, bo.getPresellCard());
        lqw.eq(StringUtils.isNotBlank(bo.getDealType()), HousesReview::getDealType, bo.getDealType());
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
        lqw.like(StringUtils.isNotBlank(bo.getTalentsType()), HousesReview::getTalentsType, bo.getTalentsType());
        lqw.eq(StringUtils.isNotBlank(bo.getCreditCode()), HousesReview::getCreditCode, bo.getCreditCode());
        lqw.like(StringUtils.isNotBlank(bo.getCompanyAddress()), HousesReview::getCompanyAddress, bo.getCompanyAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getSourceBy()), HousesReview::getSourceBy, bo.getSourceBy());
        lqw.eq(StringUtils.isNotBlank(bo.getProcessStatus()), HousesReview::getProcessStatus, bo.getProcessStatus());
        return lqw;
    }

    private LambdaQueryWrapper<HousesReview> buildQueryWrapper2(HousesReviewBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HousesReview> lqw = Wrappers.lambdaQuery();
        if (ObjectUtil.isNotNull(bo.getIds())){
            lqw.in(HousesReview::getId,bo.getIds());
        }
        lqw.orderByAsc(HousesReview::getProcessStatus);
        lqw.orderByDesc(HousesReview::getUpdateTime);
        lqw.eq(StringUtils.isNotBlank(bo.getCardType()), HousesReview::getCardType, bo.getCardType());
        lqw.eq(StringUtils.isNotBlank(bo.getCard()), HousesReview::getCard, bo.getCard());
        lqw.like(StringUtils.isNotBlank(bo.getName()), HousesReview::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getQualification()), HousesReview::getQualification, bo.getQualification());
        lqw.eq(StringUtils.isNotBlank(bo.getAuditTime()), HousesReview::getAuditTime, bo.getAuditTime());
        lqw.eq(StringUtils.isNotBlank(bo.getPresellCard()), HousesReview::getPresellCard, bo.getPresellCard());
        lqw.eq(StringUtils.isNotBlank(bo.getDealType()), HousesReview::getDealType, bo.getDealType());
        lqw.eq(StringUtils.isNotBlank(bo.getProjectName()), HousesReview::getProjectName, bo.getProjectName());
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
        lqw.eq(StringUtils.isNotBlank(bo.getProcessStatus()), HousesReview::getProcessStatus, bo.getProcessStatus());
        return lqw;
    }

    private LambdaQueryWrapper<HousesReview> buildQueryWrapper(HousesReviewBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HousesReview> lqw = Wrappers.lambdaQuery();
        if (ObjectUtil.isNotNull(bo.getIds())){
            lqw.in(HousesReview::getId,bo.getIds());
        }
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
        lqw.eq(StringUtils.isNotBlank(bo.getProcessStatus()), HousesReview::getProcessStatus, bo.getProcessStatus());
        return lqw;
    }

    /**
     * 新增购房复审登记
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(HousesReviewBo bo) {
        //判断该人才是否可提交
        HousesReview housesReview = baseMapper.selectById(bo.getId());
        if (ObjectUtil.isNotNull(housesReview.getProcessStatus())
            && (Constants.WAIT.equals(housesReview.getProcessStatus())
            || Constants.SUCCEED.equals(housesReview.getProcessStatus())
            || Constants.PUBLICS.equals(housesReview.getProcessStatus()))){
            throw new ServiceException("当前人才状态不允许提交");
        }
        bo.setProcessStatus(Constants.WAIT);

//     todo   注释原因:驳回重新提交资料后，以前的资料能否保存历史记录，可以查看
        //先删除存在的家庭情况关系表
        buyHousesReviewMemberMapper.delete( new LambdaQueryWrapper<BuyHousesReviewMember>()
            .eq(BuyHousesReviewMember::getBuyHousesId, bo.getId()));
        //删除补充材料
        materialProofMapper.delete(new LambdaQueryWrapper<MaterialProof>()
            .eq(MaterialProof::getHouseId, bo.getId())
            .eq(MaterialProof::getProcessKey,"house_review"));
        Integer number=1;
//        获取当前关系材料的最新一条数据
       /* Integer number=1;
        BuyHousesReviewMember buyHousesReviewMember = buyHousesReviewMemberMapper.selectOne(new LambdaQueryWrapper<>(BuyHousesReviewMember.class)
            .eq(BuyHousesReviewMember::getBuyHousesId, bo.getId())
            .orderByDesc(BuyHousesReviewMember::getUpdateTime));
        if (ObjectUtil.isNotNull(buyHousesReviewMember)){
             number+=buyHousesReviewMember.getNumber();
        }

        if (ObjectUtil.isNotNull(bo.getBuyHousesMemberList()) && bo.getBuyHousesMemberList().size()>0){
            Integer finalNumber = number;
            bo.getBuyHousesMemberList().stream().forEach(e ->{
                e.setBuyHousesId(bo.getId().toString());
                e.setNumber(finalNumber);
            });
            buyHousesReviewMemberMapper.insertBatch(bo.getBuyHousesMemberList());
        }*/
        if (ObjectUtil.isNotNull(bo.getBuyHousesMemberList()) && bo.getBuyHousesMemberList().size()>0) {
            Integer finalNumber = number;
            bo.getBuyHousesMemberList().forEach(e -> {
                e.setBuyHousesId(bo.getId().toString());
                e.setId(null);
                e.setNumber(finalNumber);
            });
            buyHousesReviewMemberMapper.insertBatch(bo.getBuyHousesMemberList());
        }
        //获取
        if(ObjectUtil.isNotNull(bo.getMaterialsList()) && bo.getMaterialsList().size()>0){
            ArrayList<MaterialProof> list = new ArrayList<>();
            bo.getMaterialsList().forEach(e ->{
                MaterialProof materialProof = BeanUtil.toBean(e, MaterialProof.class);
                materialProof.setHouseId(bo.getId().toString());
                materialProof.setStatus(0L);
                materialProof.setModulePathId(e.getId().toString());
                materialProof.setId(null);
                materialProof.setProcessKey("house_review");
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
            processVo.setCompanyName(bo.getCompanyName());
            processVo.setCardId(bo.getCard());
            WorkComplyUtils.comply(processVo);
        }
        return i>0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(HousesReview entity){
        if (!CardsUtil.isIDCard(entity.getCard())){
            throw new ServiceException("证件号码格式不正确");
        }
        if (!CreditCodeUtil.isCreditCode(entity.getCreditCode())){
            throw new ServiceException("社会统一社会信用代码格式不正确");
        }
        if (!"D".equals(entity.getTalentsType())){
            entity.setTypeExtend("");
        }
        //如果是区级没有企业类型且D类不区分学历和技能
        if (entity.getSourceBy().equals("1")){
            entity.setCompanyType("");
            if (entity.getTalentsType().equals("D")){
                entity.setTypeExtend("");
            }
        }
    }

    /**
     * 批量删除购房复审登记
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            List<HousesReview> housesReviews = baseMapper.selectBatchIds(ids);
            boolean b = housesReviews.stream().anyMatch(h -> h.getProcessStatus().equals(Constants.PUBLICS)
                || h.getProcessStatus().equals(Constants.WAIT)
                || h.getProcessStatus().equals(Constants.SUCCEED));
            if (b){
                throw new ServiceException("当前所选数据中存在审核的数据,请审核之后再删除");
            }
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
        //执行修改
        HousesReview housesReview = baseMapper.selectById(bo.getId());
        if (Constants.PUBLICS.equals(housesReview.getProcessStatus())
            || Constants.WAIT.equals(housesReview.getProcessStatus())
            || Constants.SUCCEED.equals(housesReview.getProcessStatus())){
        }else {
            HousesReview update = BeanUtil.toBean(bo, HousesReview.class);
            validEntityBeforeSave(update);
            baseMapper.updateById(update);
            Map<String, Object> map = BeanUtil.beanToMap(update);
            List<MaterialModuleVo> materialInfo = materialModuleService.getMaterialInfo(map);
            return R.ok(materialInfo);
        }
        Map<String, Object> map = BeanUtil.beanToMap(bo);
        List<MaterialModuleVo> materialInfo = materialModuleService.getMaterialInfo(map);
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

    /**
     * 获取导出列表
     * @param bo
     * @return
     */
    @Override
    public R subscribeExport(HousesReviewEvent bo){
        Long userId = LoginHelper.getUserId();
        //判断是否存在正在导出记录
        SubscribeExport subscribeExport1 = subscribeExportMapper.selectOne(new LambdaQueryWrapper<>(SubscribeExport.class)
            .eq(SubscribeExport::getUserId, userId)
            .eq(SubscribeExport::getExportStatus, "0"));
        if (ObjectUtil.isNotNull(subscribeExport1)){
            return R.fail("您已存在一条正在导出数据,请等待前一条导出之后再执行");
        }
        //添加导出记录
        SubscribeExport subscribeExport = new SubscribeExport();
        subscribeExport.setProcessKey(bo.getProcessKey());
        subscribeExport.setExportStatus("0");
        subscribeExport.setDescription(bo.getDescription());
        subscribeExport.setUserId(userId.toString());
        subscribeExportMapper.insert(subscribeExport);
        bo.setExcelId(subscribeExport.getId());
        SpringUtils.context().publishEvent(bo);
        return R.ok("预约成功");
    }


    /**
     * 下载excel
     * @param bo
     */
    @Override
    public void exportExcel(HousesReviewBo bo, HttpServletResponse response) {
        bo.setProcessStatus(Constants.SUCCEED);
        LambdaQueryWrapper<HousesReview> lqw = buildQueryWrapper(bo);
        List<HousesReviewVo> housesReviews = baseMapper.selectVoList(lqw);
        ExcelUtil.exportExcel(housesReviews,"人才列表", HousesReviewVo.class,response);
    }

    /**
     * 数据库统计
     * @param bo
     * @param pageQuery
     * @return
     */
    @Override
    public TableDataInfo<HousesReview> managerQueryPageList(HousesReviewBo bo, PageQuery pageQuery) {
        bo.setProcessStatus(Constants.SUCCEED);
        LambdaQueryWrapper<HousesReview> lqw = buildQueryWrapper3(bo);
        Page<HousesReview> result = baseMapper.selectPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Async
    @EventListener
    public void export(HousesReviewEvent event) throws IOException {
        HousesReviewBo bo = BeanUtil.toBean(event, HousesReviewBo.class);
        LambdaQueryWrapper<HousesReview> lqw = buildQueryWrapper3(bo);
        List<HousesReviewVo>  housesReviews = baseMapper.selectVoList(lqw);
        String title = "人才认定申请表";
        String separator = File.separator;
        String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String path = filePath + separator + format;
        if (housesReviews.size() > 0) {
            List<Long> collect = housesReviews.stream().map(HousesReviewVo::getId).collect(Collectors.toList());
            List<MaterialProof> materialProofList = materialProofMapper.selectList(new LambdaQueryWrapper<>(MaterialProof.class).in(MaterialProof::getHouseId, collect));
            //根据业务id进行分组
            Map<String, List<MaterialProof>> collectMap = materialProofList.stream().collect(Collectors.groupingBy(MaterialProof::getHouseId));
            housesReviews.stream().forEach(r -> {
                List<MaterialProof> materialProofs = collectMap.get(r.getId().toString());
                if (materialProofs.size()>0) {
                    materialProofs.stream().forEach(p -> {
                        String userNameFile = path + separator + r.getName();
                        File userFile = new File(userNameFile);
                        if (!userFile.exists() && !userFile.isDirectory()) {
                            userFile.mkdirs();
                        }
                        String s = userNameFile + separator + r.getName() + "--";
                        String file = p.getFile();
                        MyFileUtils.downLoadPic(file, s + p.getMaterialName() + file.substring(file.lastIndexOf(".")));
                        System.out.println("file = " + file);
                    });
                }
            });
            String p = path + separator + title + ".xlsx";
            String fo = filePath + separator + format + ".zip";
            File file = new File(p);
            //获取父目录
            File fileParent = file.getParentFile();
            //判断是否存在
            if (!fileParent.exists()) {
                //创建父目录文件
                fileParent.mkdirs();
            }
            file.createNewFile();
            OutputStream outXlsx = new FileOutputStream(p);
            ExcelUtil.exportExcel(housesReviews,"111",HousesReviewVo.class,outXlsx);
            outXlsx.close();
            ZipUtils.toZip(path, fo, true);
            System.out.println("path = " + path);
            System.out.println("fo = " + fo);
            //生成后删除文件
            DeleteFileUtil.delete(path);
            String zip = format+".zip";
            String url =download+prefix+"/"+zip;
            SubscribeExport subscribeExport = new SubscribeExport();
            subscribeExport.setPath(url);
            subscribeExport.setId(event.getExcelId());
            subscribeExport.setExportStatus("1");
            subscribeExportMapper.updateById(subscribeExport);
        }
    }
}
