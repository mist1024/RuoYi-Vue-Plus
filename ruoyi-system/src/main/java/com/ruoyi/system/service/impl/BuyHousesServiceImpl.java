package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CreditCodeUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.CardsUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.MyFileUtils;
import com.ruoyi.common.utils.poi.DeleteFileUtil;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.poi.ExportWordUtil;
import com.ruoyi.common.utils.poi.ZipUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.bo.BuyHousesBo;
import com.ruoyi.system.domain.dto.BuyHousesEvent;
import com.ruoyi.system.domain.dto.DeclareListDTO;
import com.ruoyi.system.domain.vo.BuyHousesVo;
import com.ruoyi.system.domain.vo.MaterialModuleVo;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.IBuyHousesService;
import com.ruoyi.work.domain.AuditLog;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.dto.HousingConstructionBureauPushDto;
import com.ruoyi.work.mapper.AuditLogMapper;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-24
 */
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class BuyHousesServiceImpl implements IBuyHousesService {

    @Value("${file.template}")
    private String fileUpload;

    @Value("${file.path}")
    private String filePath;

    @Value("${file.domain}")
    private String download;

    @Value("${file.prefix}")
    private String prefix;

    //正式地址
        private final String URL = "https://gx.chengdutalent.cn:8010/candidates/getCardId";
    //测试地址
//    private final String URL = "https://mihuatang.xyz/gaoxin-api/candidates/getCardId";
    //本地地址
//    private final String URL = "http://127.0.0.1:8010/candidates/getCardId";

    private final BuyHousesMapper baseMapper;

    private final BuyHousesMemberMapper buyHousesMemberMapper;

    private final MaterialProofMapper materialProofMapper;

    private final MaterialModuleServiceImpl materialModuleService;

    private final SubscribeExportMapper subscribeExportMapper;

    private final AuditLogMapper auditLogMapper;

    private  final HousingConstructionBureauPushDto housingConstructionBureauPushDto;

    private final HousesReviewMapper housesReviewMapper;


    /**
     * 查询【请填写功能名称】
     */
    @Override
    public BuyHousesVo queryById(Long id){
        BuyHousesVo buyHousesVo = baseMapper.selectVoById(id);
        if (ObjectUtil.isNull(buyHousesVo)){
            throw new ServiceException("数据查询为空");
        }
        LambdaQueryWrapper<MaterialProof> wrapper = new LambdaQueryWrapper<MaterialProof>()
            .eq(MaterialProof::getHouseId, buyHousesVo.getId())
            .eq(MaterialProof::getProcessKey, buyHousesVo.getProcessKey());
        List<MaterialProof> materialProofs = materialProofMapper.selectList(wrapper);
        buyHousesVo.setMaterialProofList(materialProofs);
        LambdaQueryWrapper<BuyHousesMember> queryWrapper = new LambdaQueryWrapper<BuyHousesMember>()
            .eq(BuyHousesMember::getBuyHousesId, id);
        List<BuyHousesMember> buyHousesMembers = buyHousesMemberMapper.selectList(queryWrapper);
        buyHousesVo.setBuyHousesMemberList(buyHousesMembers);
        return buyHousesVo;
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<BuyHousesVo> queryPageList(BuyHousesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BuyHouses> lqw = buildQueryWrapper2(bo);
        Page<BuyHousesVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<BuyHousesVo> queryList(BuyHousesBo bo) {
        LambdaQueryWrapper<BuyHouses> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BuyHouses> buildQueryWrapper(BuyHousesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BuyHouses> lqw = Wrappers.lambdaQuery();
        if (ObjectUtil.isNotNull(bo.getIds())){
            lqw.in(BuyHouses::getId,bo.getIds());
        }
        lqw.eq(ObjectUtil.isNotNull(bo.getId()), BuyHouses::getId, bo.getId());
        lqw.eq(StringUtils.isNotBlank(bo.getInsidepageUrl()), BuyHouses::getInsidepageUrl, bo.getInsidepageUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getCardId()), BuyHouses::getCardId, bo.getCardId());
        lqw.eq(StringUtils.isNotBlank(bo.getCommitmentUrl()), BuyHouses::getCommitmentUrl, bo.getCommitmentUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getCompanyAddress()), BuyHouses::getCompanyAddress, bo.getCompanyAddress());
        lqw.like(StringUtils.isNotBlank(bo.getCompanyName()), BuyHouses::getCompanyName, bo.getCompanyName());
        lqw.eq(bo.getCreateTime() != null, BuyHouses::getCreateTime, bo.getCreateTime());
        lqw.eq(StringUtils.isNotBlank(bo.getDeclarationUrl()), BuyHouses::getDeclarationUrl, bo.getDeclarationUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getDistrict()), BuyHouses::getDistrict, bo.getDistrict());
        lqw.eq(StringUtils.isNotBlank(bo.getEducation()), BuyHouses::getEducation, bo.getEducation());
        lqw.eq(StringUtils.isNotBlank(bo.getFrontUrl()), BuyHouses::getFrontUrl, bo.getFrontUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getGyStatus()), BuyHouses::getGyStatus, bo.getGyStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getHomeRecordUrl()), BuyHouses::getHomeRecordUrl, bo.getHomeRecordUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getHomepageUrl()), BuyHouses::getHomepageUrl, bo.getHomepageUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getLaborContractUrl()), BuyHouses::getLaborContractUrl, bo.getLaborContractUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getLicenseUrl()), BuyHouses::getLicenseUrl, bo.getLicenseUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getMaritalStatus()), BuyHouses::getMaritalStatus, bo.getMaritalStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getMaritalUrl()), BuyHouses::getMaritalUrl, bo.getMaritalUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getNationality()), BuyHouses::getNationality, bo.getNationality());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), BuyHouses::getPhone, bo.getPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getQyStatus()), BuyHouses::getQyStatus, bo.getQyStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getReverseUrl()), BuyHouses::getReverseUrl, bo.getReverseUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getSex()), BuyHouses::getSex, bo.getSex());
        lqw.eq(StringUtils.isNotBlank(bo.getShStatus()), BuyHouses::getShStatus, bo.getShStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getSocialCode()), BuyHouses::getSocialCode, bo.getSocialCode());
        lqw.eq(StringUtils.isNotBlank(bo.getSocialSecurityUrl()), BuyHouses::getSocialSecurityUrl, bo.getSocialSecurityUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), BuyHouses::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), BuyHouses::getType, bo.getType());
        lqw.eq(bo.getUserId() != null, BuyHouses::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), BuyHouses::getUserName, bo.getUserName());
        lqw.eq(bo.getPassTime() != null, BuyHouses::getPassTime, bo.getPassTime());
        lqw.eq(StringUtils.isNotBlank(bo.getPictureInformationUrl()), BuyHouses::getPictureInformationUrl, bo.getPictureInformationUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getWorkAddress()), BuyHouses::getWorkAddress, bo.getWorkAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getProcessStatus()), BuyHouses::getProcessStatus, bo.getProcessStatus());
        return lqw;
    }

    private LambdaQueryWrapper<BuyHouses> buildQueryWrapper2(BuyHousesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BuyHouses> lqw = Wrappers.lambdaQuery();
        if (ObjectUtil.isNotNull(bo.getIds())){
            lqw.in(BuyHouses::getId,bo.getIds());
        }
        lqw.eq(StringUtils.isNotBlank(bo.getInsidepageUrl()), BuyHouses::getInsidepageUrl, bo.getInsidepageUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getCardId()), BuyHouses::getCardId, bo.getCardId());
        lqw.eq(StringUtils.isNotBlank(bo.getCommitmentUrl()), BuyHouses::getCommitmentUrl, bo.getCommitmentUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getCompanyAddress()), BuyHouses::getCompanyAddress, bo.getCompanyAddress());
        lqw.like(StringUtils.isNotBlank(bo.getCompanyName()), BuyHouses::getCompanyName, bo.getCompanyName());
        lqw.eq(bo.getCreateTime() != null, BuyHouses::getCreateTime, bo.getCreateTime());
        lqw.eq(StringUtils.isNotBlank(bo.getDeclarationUrl()), BuyHouses::getDeclarationUrl, bo.getDeclarationUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getEducation()), BuyHouses::getEducation, bo.getEducation());
        lqw.eq(StringUtils.isNotBlank(bo.getFrontUrl()), BuyHouses::getFrontUrl, bo.getFrontUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getGyStatus()), BuyHouses::getGyStatus, bo.getGyStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getHomeRecordUrl()), BuyHouses::getHomeRecordUrl, bo.getHomeRecordUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getHomepageUrl()), BuyHouses::getHomepageUrl, bo.getHomepageUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getLaborContractUrl()), BuyHouses::getLaborContractUrl, bo.getLaborContractUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getLicenseUrl()), BuyHouses::getLicenseUrl, bo.getLicenseUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getMaritalStatus()), BuyHouses::getMaritalStatus, bo.getMaritalStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getMaritalUrl()), BuyHouses::getMaritalUrl, bo.getMaritalUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getNationality()), BuyHouses::getNationality, bo.getNationality());
        lqw.eq(StringUtils.isNotBlank(bo.getQyStatus()), BuyHouses::getQyStatus, bo.getQyStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getReverseUrl()), BuyHouses::getReverseUrl, bo.getReverseUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getSex()), BuyHouses::getSex, bo.getSex());
        lqw.eq(StringUtils.isNotBlank(bo.getShStatus()), BuyHouses::getShStatus, bo.getShStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getSocialCode()), BuyHouses::getSocialCode, bo.getSocialCode());
        lqw.eq(StringUtils.isNotBlank(bo.getSocialSecurityUrl()), BuyHouses::getSocialSecurityUrl, bo.getSocialSecurityUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), BuyHouses::getStatus, bo.getStatus());
        lqw.eq(bo.getUserId() != null, BuyHouses::getUserId, bo.getUserId());

        lqw.eq(StringUtils.isNotBlank(bo.getProcessStatus()),BuyHouses::getProcessStatus,bo.getProcessStatus());
        lqw.ge(StringUtils.isBlank(bo.getProcessStatus()),BuyHouses::getProcessStatus,Constants.SUCCEED);
        lqw.and(StringUtils.isNotBlank(bo.getUserName()),t ->t.like(BuyHouses::getUserName,bo.getUserName())
            .or().like(BuyHouses::getPhone,bo.getUserName())
            .or().like(BuyHouses::getCardId,bo.getUserName()));
        lqw.eq(StringUtils.isNotBlank(bo.getDistrict()),BuyHouses::getDistrict,bo.getDistrict());
        lqw.like(StringUtils.isNotBlank(bo.getType()),BuyHouses::getType,bo.getType());

        lqw.eq(bo.getPassTime() != null, BuyHouses::getPassTime, bo.getPassTime());
        lqw.eq(StringUtils.isNotBlank(bo.getPictureInformationUrl()), BuyHouses::getPictureInformationUrl, bo.getPictureInformationUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getWorkAddress()), BuyHouses::getWorkAddress, bo.getWorkAddress());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(BuyHousesBo bo) {
        Long userId = LoginHelper.getUserId();
        bo.setUserId(userId);
        //验证这个身份证是否提交过
        List<BuyHouses> buyHouses = baseMapper.selectList(new LambdaQueryWrapper<>(BuyHouses.class)
            .eq(BuyHouses::getCardId, bo.getCardId()));
        if (buyHouses.size()>0){
            throw new ServiceException("当前人才已提交申请");
        }
        bo.setProcessStatus(Constants.WAIT);
        BuyHouses add = BeanUtil.toBean(bo, BuyHouses.class);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
            //先删除家庭信息表
            if (bo.getBuyHousesMemberList().size()>0) {
                buyHousesMemberMapper.delete( new LambdaQueryWrapper<>(BuyHousesMember.class).eq(BuyHousesMember::getBuyHousesId, add.getId()));
                //添加家庭信息
                bo.getBuyHousesMemberList().stream().forEach(e ->e.setBuyHousesId(String.valueOf(add.getId())));
                buyHousesMemberMapper.insertBatch(bo.getBuyHousesMemberList());
            }
            //将数据添加到流程中
            ProcessVo processVo = new ProcessVo();
            processVo.setProcessKey("apply_house");
            processVo.setStep("1");
            Map<String, Object> map = BeanUtil.beanToMap(bo);
            processVo.setParams(map);
            processVo.setBusinessId(bo.getId().toString());
            processVo.setStartUser(bo.getUserName());
            processVo.setCardId(bo.getCardId());
            processVo.setCompanyName(bo.getCompanyName());
            WorkComplyUtils.comply(processVo);
        }
        return flag;
    }

    /**
     * 修改【请填写功能名称】
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(BuyHousesBo bo) {
        Long userId = LoginHelper.getUserId();
        BuyHouses buyHouses = baseMapper.selectOne(new LambdaQueryWrapper<>(BuyHouses.class).eq(BuyHouses::getUserId, userId));
        if (ObjectUtil.isNull(buyHouses)){
            throw new ServiceException("请先下载申请表");
        }
        bo.setUserId(userId);
        BuyHouses update = BeanUtil.toBean(bo, BuyHouses.class);
        validEntityBeforeSave(update);
        update.setProcessStatus(Constants.WAIT);
        update.setStep("1");
        Boolean flag =  baseMapper.updateById(update) > 0;
        if (flag){
            //将数据添加到流程中
            ProcessVo processVo = new ProcessVo();
            processVo.setProcessKey("apply_house");
            processVo.setStep("1");
            Map<String, Object> map = BeanUtil.beanToMap(bo);
            processVo.setParams(map);
            processVo.setBusinessId(bo.getId().toString());
            processVo.setStartUser(bo.getUserName());
            processVo.setCardId(bo.getCardId());
            processVo.setCompanyName(bo.getCompanyName());
            WorkComplyUtils.comply(processVo);
        }
        return flag;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BuyHouses entity){
        //TODO 做一些数据校验,如唯一约束
        //数据校验
        if (!CardsUtil.isIDCard(entity.getCardId())){
            throw new ServiceException("证件号码格式不正确");
        }
        if (!CreditCodeUtil.isCreditCode(entity.getSocialCode())){
            throw new ServiceException("社会统一社会信用代码格式不正确");
        }
        //验证当前状态是否可以修改
        //判断数据库中是否存在该人才通过身份证去验证
        Long userId = LoginHelper.getUserId();
        BuyHouses buyHouses = baseMapper.selectOne(new LambdaQueryWrapper<>(BuyHouses.class).eq(BuyHouses::getUserId, userId));
        if (ObjectUtil.isNotNull(buyHouses)) {
            if (!Constants.SUBMIT.equals(buyHouses.getProcessStatus())
                && !Constants.FAILD.equals(buyHouses.getProcessStatus())
//                && !Constants.CANCEL.equals(buyHouses.getProcessStatus())
            ) {
                throw new ServiceException("当前用户不可修改");
            }
            //先删除家庭信息表
            if (entity.getBuyHousesMemberList().size() > 0) {
                buyHousesMemberMapper.delete(new LambdaQueryWrapper<>(BuyHousesMember.class).eq(BuyHousesMember::getBuyHousesId, entity.getId()));
                //添加家庭信息
                entity.getBuyHousesMemberList().stream().forEach(e -> {
                    e.setBuyHousesId(String.valueOf(entity.getId()));
                    e.setId(null);
                });
                buyHousesMemberMapper.insertBatch(entity.getBuyHousesMemberList());
            }
        }
    }

    /**
     * 批量删除【请填写功能名称】
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public R<?> getMaterialInfo(BuyHousesBo bo) {
        Map<String, Object> map = BeanUtil.beanToMap(bo);
        List<MaterialModuleVo> materialInfo = materialModuleService.getMaterialInfo(map);
        return R.ok(materialInfo);
    }

    @Override
    public BuyHouses getBuyHousesByCardId(String cardId) {
        //验证当前人是否哦申请过购房信息
        List<BuyHouses> buyHouses = baseMapper.selectList(
            new LambdaQueryWrapper<BuyHouses>()
                .eq(BuyHouses::getCardId, cardId));

        if (buyHouses.size()>0){
            BuyHouses buyHouses1 = buyHouses.get(0);
            List<BuyHousesMember> buyHousesMembers = buyHousesMemberMapper.selectList(new LambdaQueryWrapper<>(BuyHousesMember.class)
                .eq(BuyHousesMember::getBuyHousesId, buyHouses1.getId()));
            buyHouses1.setBuyHousesMemberList(buyHousesMembers);
            return buyHouses1;
        }
        return null;
    }

    /**
     * 先保存再执行下载
     * @param bo
     */
    @Override
    public R downloadWord(BuyHousesBo bo) {
        bo.setUserId(LoginHelper.getUserId());
        BuyHouses buyHousesBo = BeanUtil.toBean(bo, BuyHouses.class);
        buyHousesBo.setStep("1");
        validEntityBeforeSave(buyHousesBo);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        //判断数据库中是否存在该人才通过身份证去验证
        BuyHouses buyHouses = baseMapper.selectOne(new LambdaQueryWrapper<>(BuyHouses.class)
            .eq(BuyHouses::getCardId, buyHousesBo.getCardId()));
        //当前用户没提交过
        if (ObjectUtil.isNull(buyHouses)){
            buyHousesBo.setProcessStatus(Constants.SUBMIT);
            buyHousesBo.setUpdateTime(new Date());
            buyHousesBo.setCreateTime(new Date());
            BuyHouses toBean = BeanUtil.toBean(buyHousesBo, BuyHouses.class);
            baseMapper.insert(toBean);
            map.put("buyHouses",toBean);
        }else {
            //如果不是待提交或者
            if (Constants.SUBMIT.equals(buyHouses.getProcessStatus()) || Constants.FAILD.equals(buyHouses.getProcessStatus())){
                //执行修改操作
                buyHousesBo.setUpdateTime(new Date());
                buyHousesBo.setId(buyHouses.getId());
                BuyHouses toBean = BeanUtil.toBean(buyHousesBo, BuyHouses.class);
                baseMapper.updateById(toBean);
                map.put("buyHouses",toBean);
            }
        }
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("companyName",buyHousesBo.getCompanyName());
        hashMap.put("socialCode",buyHousesBo.getSocialCode());
        hashMap.put("phone",buyHousesBo.getPhone());
        hashMap.put("companyAddress",buyHousesBo.getCompanyAddress());
        hashMap.put("name",buyHousesBo.getUserName());
        hashMap.put("cardId",buyHousesBo.getCardId());
        hashMap.put("education",buyHousesBo.getEducation());
        hashMap.put("type",buyHousesBo.getType());
        String fileName = UUID.randomUUID().toString();
        String templatePath = fileUpload + "Houses_template.docx";
        String word = ExportWordUtil.createWord(templatePath, filePath, fileName, hashMap);
        System.out.println("word = " + word);
        String file= download + prefix + "/" + fileName + ".docx";
        map.put("file",file);
        return R.ok(map);
    }

    /**
     * 获取进度列表
     * @return
     */
    @Override
    public List<DeclareListDTO> getDeclareList() {
        ArrayList<DeclareListDTO> list = new ArrayList<>();
        //查询购房信息
        Long userId = LoginHelper.getUserId();
        List<BuyHouses> buyHouses = baseMapper.selectList(new LambdaQueryWrapper<>(BuyHouses.class).eq(BuyHouses::getUserId, userId));
        if (buyHouses.size()>0) {
            buyHouses.stream().forEach(e -> {
                DeclareListDTO declareListDTO = new DeclareListDTO();
                declareListDTO.setProjectName("人才安居");
                declareListDTO.setProcessStatus(e.getProcessStatus());
                declareListDTO.setCreateTime(e.getCreateTime());
                declareListDTO.setUserName(e.getUserName());
                declareListDTO.setProcessKey(e.getProcessKey());
                declareListDTO.setBusinessId(e.getId().toString());
                declareListDTO.setCardId(e.getCardId());
                list.add(declareListDTO);
            });
        }
        return list;
    }

    /**
     * 根据身份证或者用户信息
     * @param buyHouses
     * @return
     */
    @Override
    public R<?> getInfo(BuyHouses buyHouses) {
        Long userId = LoginHelper.getUserId();
        //先判断本地数据库是否有值
        BuyHousesVo buyHousesVo = baseMapper.selectVoOne(new LambdaQueryWrapper<>(BuyHouses.class).eq(BuyHouses::getUserId,userId));
        if (ObjectUtil.isNotNull(buyHousesVo)){
            LambdaQueryWrapper<MaterialProof> wrapper = new LambdaQueryWrapper<MaterialProof>()
                .eq(MaterialProof::getHouseId, buyHousesVo.getId())
                .eq(MaterialProof::getProcessKey, buyHousesVo.getProcessKey());
            List<MaterialProof> materialProofs = materialProofMapper.selectList(wrapper);
            buyHousesVo.setMaterialProofList(materialProofs);
            LambdaQueryWrapper<BuyHousesMember> queryWrapper = new LambdaQueryWrapper<BuyHousesMember>()
                .eq(BuyHousesMember::getBuyHousesId, buyHousesVo.getId());
            List<BuyHousesMember> buyHousesMembers = buyHousesMemberMapper.selectList(queryWrapper);
            buyHousesVo.setBuyHousesMemberList(buyHousesMembers);
            return R.ok(buyHousesVo);
        }
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("cardId", buyHouses.getCardId());
        String json = HttpUtil.get(URL, paramMap);
        JSONObject entrie = JSONUtil.parseObj(json);
        String data = String.valueOf(entrie.get("data"));
        if (ObjectUtil.isNull(data)){
            return R.fail("获取失败");
        }
        JSONObject entries = JSONUtil.parseObj(data);
        BuyHouses buyHousesDto = new BuyHouses();
        buyHousesDto.setCardId(String.valueOf(entries.get("cardId")));
        String nationality = String.valueOf(entries.get("nationality"));
        if ("中国".contains(nationality)){
            buyHousesDto.setNationality("中国籍");
        }else {
            buyHousesDto.setNationality("外籍");
        }
        buyHousesDto.setUserName(String.valueOf(entries.get("name")));
        buyHousesDto.setPhone(String.valueOf(entries.get("phone")));
        buyHousesDto.setCompanyName(String.valueOf(entries.get("companyName")));
        buyHousesDto.setSex(String.valueOf(entries.get("sex")));
        buyHousesDto.setEducation(String.valueOf(entries.get("education")));
        buyHousesDto.setDistrict("1");
        buyHousesDto.setProcessStatus(Constants.SUBMIT);
        buyHousesDto.setType(String.valueOf(entries.get("type"))+"类");
        buyHousesDto.setWorkAddress(String.valueOf(entries.get("district")));
        buyHousesDto.setProcessKey("apply_house");
        return R.ok(buyHousesDto);
    }

    /**
     * 验证是否具有人才资格
     * @param cardId
     * @return
     */
    @Override
    public R getGaoXinCandidateInfoByCardId(String cardId) {
        HttpResponse execute = HttpRequest.get(URL)
            .form("cardId",cardId)
            .timeout(5000)
            .execute();
        JSONObject entries = JSONUtil.parseObj(execute.body());
        String code = String.valueOf(entries.get("code"));
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        if (ObjectUtil.isNull(code)){
            return R.fail("获取人才信息失败");
        }else {
            if ("666666".equals(code)){
                BuyHouses buyHouses = baseMapper.selectOne(new LambdaQueryWrapper<>(BuyHouses.class).eq(BuyHouses::getCardId, cardId));
                if (ObjectUtil.isNotNull(buyHouses)){
                    hashMap.put("status",buyHouses.getProcessStatus());
                    hashMap.put("processKey",buyHouses.getProcessKey());
                    hashMap.put("businessId",buyHouses.getId());
                    return R.ok("获取成功",hashMap);
                }
                hashMap.put("status",Constants.SUBMIT);
                hashMap.put("processKey","apply_house");
                hashMap.put("businessId",null);
                return R.ok("获取成功",hashMap);
            }else {
                return R.fail("暂无资格");
            }
        }
    }

    /**
     * 预约导出列表
     * @param bo
     * @return
     */
    @Override
    public R subscribeExport(BuyHousesEvent bo) {
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
        subscribeExport.setDescription(bo.getDescription());
        subscribeExport.setUserId(userId.toString());
        subscribeExportMapper.insert(subscribeExport);
        bo.setExcelId(subscribeExport.getId());
        SpringUtils.context().publishEvent(bo);
        return R.ok("预约成功");
    }


    /**
     *导出excel
     * @param bo
     * @param response
     */

    @Override
    public void exportExcel(BuyHousesBo bo, HttpServletResponse response) {
        bo.setProcessStatus(Constants.SUCCEED);
        LambdaQueryWrapper<BuyHouses> lqw = buildQueryWrapper(bo);
        List<BuyHousesVo> buyHousesVoList = baseMapper.selectVoList(lqw);
        ExcelUtil.exportExcel(buyHousesVoList,"人才列表", BuyHousesVo.class,response);
    }

    /**
     * 判断是否申请过
     * @return
     */
    @Override
    public R checkStatus() {
        Long userId = LoginHelper.getUserId();
        List<BuyHouses> buyHousesList = baseMapper.selectList(new LambdaQueryWrapper<>(BuyHouses.class).eq(BuyHouses::getUserId, userId));
        if (buyHousesList.size()>0){
            return R.ok();
        }
        return R.ok(201,"未申请过",null);
    }

    /**
     * 获取当前登录用户日志
     * @return
     */
    @Override
    public R getBuyHousesLogsByUserId() {
        Long userId = LoginHelper.getUserId();
        HashMap<String, Object> map = new HashMap<>();
        BuyHouses buyHouses = baseMapper.selectOne(new LambdaQueryWrapper<>(BuyHouses.class).eq(BuyHouses::getUserId, userId));
        if (ObjectUtil.isNotNull(buyHouses)) {
            List<AuditLog> auditLogList = auditLogMapper.selectList(new LambdaQueryWrapper<>(AuditLog.class).eq(AuditLog::getOtherId, buyHouses.getId()).orderByAsc(AuditLog::getCreateTime));
            if (auditLogList.size() > 0) {
                auditLogList.stream().forEach(a -> {
                    if (ObjectUtil.isNull(a.getProcessKey())) {
//                    1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6.初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过  11.审定退件,12.资格取消
                        switch (a.getStatus()) {
                            case "1":
                                a.setStatus("待提交");
                                break;
                            case "2":
                                a.setStatus("受理中");
                                break;
                            case "3":
                                a.setStatus("受理退件");
                                break;
                            case "4":
                                a.setStatus("受理驳回");
                                break;
                            case "5":
                                a.setStatus("初审中");
                                break;
                            case "6":
                                a.setStatus("初审不通过");
                                break;
                            case "7":
                                a.setStatus("初审退件");
                                break;
                            case "8":
                                a.setStatus("审定中");
                                break;
                            case "9":
                                a.setStatus("审定不通过");
                                break;
                            case "10":
                                a.setStatus("审定通过");
                                break;
                            case "11":
                                a.setStatus("审定退件");
                                break;
                            case "12":
                                a.setStatus("资格取消");
                                break;
                        }
                    } else {
                        switch (a.getStatus()) {
                            case "1":
                                a.setStatus("审核失败");
                                break;
                            case "2":
                                a.setStatus("审核成功");
                                break;
                        }
                    }
                });
            }

            map.put("auditLog", auditLogList);
            map.put("status", buyHouses.getProcessStatus());
            map.put("id", buyHouses.getId());
            return R.ok("查询成功",map);
        }
        return R.fail("当前身份证与登录信息不匹配");

    }

    @Override
    public R downloadInform() {
        Long userId = LoginHelper.getUserId();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        //判断数据库中是否存在该人才通过身份证去验证
        BuyHouses buyHouses = baseMapper.selectOne(new LambdaQueryWrapper<>(BuyHouses.class)
            .eq(BuyHouses::getUserId,userId)
            .eq(BuyHouses::getProcessStatus,Constants.SUCCEED));
        if (ObjectUtil.isNull(buyHouses)){
            return R.fail("没有查询到该人才,请确保该人才已通过审核");
        }
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("companyName",buyHouses.getCompanyName());
        hashMap.put("companyAddress",buyHouses.getCompanyAddress());
        hashMap.put("name",buyHouses.getUserName());
        hashMap.put("cardId",buyHouses.getCardId());
        hashMap.put("type",buyHouses.getType());
        hashMap.put("nationality", "中国籍".equals(buyHouses.getNationality()) ? "身份证" : "护照");
        String fileName = UUID.randomUUID().toString();
        String templatePath = fileUpload + "inform.docx";
        String word = ExportWordUtil.createWord(templatePath, filePath, fileName, hashMap);
        System.out.println("word = " + word);
        String file= download + prefix + "/" + fileName + ".docx";
        System.out.println("file = " + file);
        map.put("file",file);
        return R.ok(map);

    }

    @Override
    public R<?> updateBuyHouses(BuyHouses buyHouses) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        buyHouses.setProcessStatus(Constants.CANCEL);
        int i = baseMapper.updateById(buyHouses);
        //添加取消资格日志
        AuditLog auditLog = new AuditLog();
        auditLog.setOtherId(String.valueOf(buyHouses.getId()));//业务id
        auditLog.setProcessKey("apply_house");//流程key
        auditLog.setAuditId(loginUser.getUserId().toString());//审核人id
        auditLog.setReply(buyHouses.getReply());
        auditLog.setAuditType("2");//审核类型
        auditLog.setStatus("1");//审核状态
        auditLog.setAudit(loginUser.getUserId().toString());
        auditLog.setAdminUserName(loginUser.getUsername());
        auditLog.setCreateTime(new Date());
        auditLog.setUpdateTime(new Date());
        auditLog.setStep("3");
        auditLogMapper.insert(auditLog);
        if (i>0){
            //todo 推送
            Map<String, Object> map = new HashMap<>();
            map.put("id",buyHouses.getId());
            map.put("reason",buyHouses.getReply());//原因
            map.put("userName",buyHouses.getUserName());
            map.put("cardId",buyHouses.getCardId());
            map.put("cancelTime", DateUtils.dateTime("yyyy-MM-dd HH:mm:ss"));
            map.put("note","人才信息有误");//备注
            map.put("status", "00N");
            System.out.println("JSONUtil.toJsonPrettyStr(map) = " + JSONUtil.toJsonPrettyStr(map));
//            housingConstructionBureauPushDto.openUrl("https://jcfw.cdzjryb.com//CCSRegistryCenter/rest",map,"254");

//            housingConstructionBureauPushDto.openUrl("https://www.cdhtrct.com/route/open/api/anju/openBuyHousesCallback",map,"001");
            return R.ok();
        }
        return R.fail();
    }

    /**
     * 对外推送接口
     * @param bo
     * @return
     */
    @Override
    public R<?> insertOpenBuyHouses(BuyHousesBo bo) {
        BuyHouses buyHouses = BeanUtil.toBean(bo, BuyHouses.class);
        buyHouses.setStep("1");
        BuyHouses buyHouses1 = baseMapper.selectOne(new LambdaQueryWrapper<>(BuyHouses.class).eq(BuyHouses::getCardId, buyHouses.getCardId()));
        //判断该人才是否存在
        if (ObjectUtil.isEmpty(buyHouses1.getApiKey()) || !buyHouses1.getUserId().equals(buyHouses.getUserId())){
            throw new ServiceException("当前用户已在系统存在");
//            return R.fail("当前用户已在系统存在");
        }
        if (ObjectUtil.isNull(buyHouses1)){
            //新增
            buyHouses.setProcessStatus(Constants.WAIT);
            buyHouses.setProcessKey("apply_house");
            buyHouses.setCreateTime(new Date());
            buyHouses.setUpdateTime(new Date());
            int insert = baseMapper.insert(buyHouses);
            if (insert==0){
                throw new ServiceException("新增失败");
//                return R.fail("新增失败");
            }
            //创建关系材料表
            List<BuyHousesMember> buyHousesMemberList = buyHouses.getBuyHousesMemberList();
            if (buyHousesMemberList.size()>0){
                buyHousesMemberList.stream().forEach(b ->{
                    b.setBuyHousesId(String.valueOf(buyHouses.getId()));
                });
            }
            buyHousesMemberMapper.insertBatch(buyHousesMemberList);
        }else {
            //判断状态是否在可执行范围
            if (!Constants.WAIT.equals(buyHouses.getProcessStatus())
                && !Constants.SUBMIT.equals(buyHouses.getProcessStatus())
                && ! Constants.FAILD.equals(buyHouses.getProcessStatus())){
                throw new ServiceException("当前状态不在修改范围");
//                return R.fail("当前状态不在修改范围");
            }
            buyHouses.setId(buyHouses1.getId());

            //判断当前是否可以提交
            if (Constants.WAIT.equals(buyHouses1.getProcessStatus())
                ||Constants.SUCCEED.equals(buyHouses1.getProcessStatus())){
                throw new ServiceException("当前状态不允许修改");
//                return R.fail("当前状态不允许修改");
            }
            buyHouses.setUpdateTime(new Date());
            //删除关系表中得数据
            buyHousesMemberMapper.delete(new LambdaQueryWrapper<>(BuyHousesMember.class).eq(BuyHousesMember::getBuyHousesId, buyHouses.getId()));
            //修改数据
            int i = baseMapper.updateById(buyHouses);
            if (Constants.WAIT.equals(buyHouses.getProcessStatus()) && i==0){
                throw new ServiceException("修改失败");
//                return R.fail("修改失败");
            }
            //修改材料表中得数据
            List<BuyHousesMember> buyHousesMemberList = buyHouses.getBuyHousesMemberList();
            if (buyHousesMemberList.size()>0){
                buyHousesMemberList.stream().forEach(b ->{
                    b.setBuyHousesId(buyHouses.getId().toString());
                    b.setId(null);
                });
            }
            buyHousesMemberMapper.insertBatch(buyHousesMemberList);
        }
        if (Constants.WAIT.equals(buyHouses.getProcessStatus())){
            //创建流程
            //将数据添加到流程中
            ProcessVo processVo = new ProcessVo();
            processVo.setProcessKey("apply_house");
            processVo.setStep("1");
            Map<String, Object> map = BeanUtil.beanToMap(buyHouses);
            processVo.setParams(map);
            processVo.setBusinessId(buyHouses.getId().toString());
            processVo.setStartUser(buyHouses.getUserName());
            processVo.setCardId(buyHouses.getCardId());
            processVo.setCompanyName(buyHouses.getCompanyName());
            WorkComplyUtils.comply(processVo);
        }
        return R.ok(buyHouses.getId());
    }

    @Override
    public R<?> getIndexType() {
        HashMap<String, Object> map = new HashMap<>();
        List<Map> mapList =  baseMapper.getIndexType();
        map.put("type",mapList);
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        mapList.stream().forEach(m ->{
            Object num = m.get("value");
            if (ObjectUtil.isNotNull(num)){
                sum.updateAndGet(v -> v + Integer.valueOf(String.valueOf(num)));
            }
        });
        map.put("sum",sum);
        List<Map>  actMapList = baseMapper.getActProcessList();
        map.put("step",actMapList);
        return R.ok(map);
    }

    /**
     * 首页企业所在地展示
     * @return
     */
    @Override
    public R<?> getCompanyDistrict() {
        HashMap<String, Object> map = new HashMap<>();
        List<Map> list = baseMapper.getCompanyDistrict();
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        list.stream().forEach(m ->{
            Object num = m.get("value");
            if (ObjectUtil.isNotNull(num)){
                sum.updateAndGet(v -> v + Integer.valueOf(String.valueOf(num)));
            }
        });
        map.put("sum",sum);
        map.put("district",list);
        return R.ok(map);
    }

    @Override
    public R<?> getNationalityAndMarital() {
        HashMap<String, Object> map = new HashMap<>();
        List<Map> mapList=   baseMapper.getNationality();
        AtomicReference<Integer> nationalitySum = new AtomicReference<>(0);
        mapList.stream().forEach(m ->{
            Object num = m.get("value");
            if (ObjectUtil.isNotNull(num)){
                nationalitySum.updateAndGet(v -> v + Integer.valueOf(String.valueOf(num)));
            }
        });
        map.put("nationalitySum",nationalitySum);
        map.put("nationality",mapList);
        List<Map> maritalList = baseMapper.getMaritalStatus();
        AtomicReference<Integer> maritalSum = new AtomicReference<>(0);
        maritalList.stream().forEach(m ->{
            Object num = m.get("value");
            if (ObjectUtil.isNotNull(num)){
                maritalSum.updateAndGet(v -> v + Integer.valueOf(String.valueOf(num)));
            }
        });
        map.put("maritalSum",maritalSum);
        map.put("marital",maritalList);
        return R.ok(map);
    }

    /**
     * 首页第一排基础数据展示
     * @return
     */
    @Override
    public R getBasicData() {
        //获取一期认定通过人才数
        HashMap<String, Object> map = new HashMap<>();
        Long aLong = baseMapper.selectCount(new LambdaQueryWrapper<>(BuyHouses.class)
            .eq(BuyHouses::getProcessStatus, Constants.SUCCEED));
        map.put("oneSum",aLong);
        //获取二期认定通过人才数
        List<HousesReview> housesReviewList = housesReviewMapper.selectList(new LambdaQueryWrapper<>(HousesReview.class)
            .eq(HousesReview::getProcessStatus, Constants.SUCCEED));
        map.put("twoSum",housesReviewList.size());
        //获取二期市级和区级比例
        //市级
        int twoMunicipal = housesReviewList.stream().filter(f -> f.getSourceBy().equals("2")).collect(Collectors.toList()).size();
        double div2 = NumberUtil.div(twoMunicipal,housesReviewList.size());
        String twoMunicipalDecimalFormat = NumberUtil.decimalFormat("#.##%", div2);
        map.put("twoMunicipal",twoMunicipalDecimalFormat);
        //区级
        int twoDistrict = housesReviewList.stream().filter(f -> f.getSourceBy().equals("1")).collect(Collectors.toList()).size();
        double div1 = NumberUtil.div(twoDistrict,housesReviewList.size());
        String twoDistrictDecimalFormat = NumberUtil.decimalFormat("#.##%", div1);
        map.put("twoDistrict",twoDistrictDecimalFormat);
        //获取本月复审通过数
        Date date = DateUtil.date();
        //获得月份，从0开始计数
        int month = DateUtil.month(date);
        List<HousesReview> housesReviewStream = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == month).collect(Collectors.toList());
        map.put("monthSum",housesReviewStream.size());
        //筛选区级人才数
        int size = housesReviewList.stream().filter(h -> h.getSourceBy().equals("1")).collect(Collectors.toList()).size();
        double div = NumberUtil.div(size,aLong.intValue());
        String decimalFormat = NumberUtil.decimalFormat("#.##%", div);
        //获取复审占比
        map.put("decimalFormat",decimalFormat);
        return R.ok(map);
    }


    /**
     * 复审柱状图统计图
     * @param date
     * @return
     */
    @Override
    public R getHistogram(String date) {
        //查询出所有的数据
        QueryWrapper<HousesReview> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNull(date)){
            queryWrapper.eq(ObjectUtil.isNotNull(date),"DATE_FORMAT(create_time,'%Y')", DateUtils.getDate());
        }else {
            queryWrapper.eq("DATE_FORMAT(create_time,'%Y')", date);
        }
        queryWrapper.eq("process_status",Constants.SUCCEED);
        List<HousesReview> housesReviewList = housesReviewMapper.selectList(queryWrapper);
        //区级
        List<Integer> districtList = new ArrayList<>();
        //市级
        List<Integer> municipalList = new ArrayList<>();
        //获取第一个月的数据
        List<HousesReview> collect = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == 0).collect(Collectors.toList());
        if (collect.size()>0){
            //区分区级还是市级
            int size = collect.stream().filter(c -> c.getSourceBy().equals("1")).collect(Collectors.toList()).size();
            int size1 = collect.stream().filter(c -> c.getSourceBy().equals("2")).collect(Collectors.toList()).size();
            districtList.add(size);
            municipalList.add(size1);
        }else {
            districtList.add(0);
            municipalList.add(0);
        }
        //获取第一个月的数据
        List<HousesReview> collect1 = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == 1).collect(Collectors.toList());
        if (collect1.size()>0){
            //区分区级还是市级
            int size = collect1.stream().filter(c -> c.getSourceBy().equals("1")).collect(Collectors.toList()).size();
            int size1 = collect1.stream().filter(c -> c.getSourceBy().equals("2")).collect(Collectors.toList()).size();
            districtList.add(size);
            municipalList.add(size1);
        }else {
            districtList.add(0);
            municipalList.add(0);
        }
        //获取第一个月的数据
        List<HousesReview> collect2 = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == 2).collect(Collectors.toList());
        if (collect2.size()>0){
            //区分区级还是市级
            int size = collect2.stream().filter(c -> c.getSourceBy().equals("1")).collect(Collectors.toList()).size();
            int size1 = collect2.stream().filter(c -> c.getSourceBy().equals("2")).collect(Collectors.toList()).size();
            districtList.add(size);
            municipalList.add(size1);
        }else {
            districtList.add(0);
            municipalList.add(0);
        }
        //获取第一个月的数据
        List<HousesReview> collect3 = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == 3).collect(Collectors.toList());
        if (collect3.size()>0){
            //区分区级还是市级
            int size = collect3.stream().filter(c -> c.getSourceBy().equals("1")).collect(Collectors.toList()).size();
            int size1 = collect3.stream().filter(c -> c.getSourceBy().equals("2")).collect(Collectors.toList()).size();
            districtList.add(size);
            municipalList.add(size1);
        }else {
            districtList.add(0);
            municipalList.add(0);
        }
        //获取第一个月的数据
        List<HousesReview> collect4 = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == 4).collect(Collectors.toList());
        if (collect4.size()>0){
            //区分区级还是市级
            int size = collect4.stream().filter(c -> c.getSourceBy().equals("1")).collect(Collectors.toList()).size();
            int size1 = collect4.stream().filter(c -> c.getSourceBy().equals("2")).collect(Collectors.toList()).size();
            districtList.add(size);
            municipalList.add(size1);
        }else {
            districtList.add(0);
            municipalList.add(0);
        }
        //获取第一个月的数据
        List<HousesReview> collect5 = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == 5).collect(Collectors.toList());
        if (collect5.size()>0){
            //区分区级还是市级
            int size = collect5.stream().filter(c -> c.getSourceBy().equals("1")).collect(Collectors.toList()).size();
            int size1 = collect5.stream().filter(c -> c.getSourceBy().equals("2")).collect(Collectors.toList()).size();
            districtList.add(size);
            municipalList.add(size1);
        }else {
            districtList.add(0);
            municipalList.add(0);
        }
        //获取第一个月的数据
        List<HousesReview> collect6 = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == 6).collect(Collectors.toList());
        if (collect6.size()>0){
            //区分区级还是市级
            int size = collect6.stream().filter(c -> c.getSourceBy().equals("1")).collect(Collectors.toList()).size();
            int size1 = collect6.stream().filter(c -> c.getSourceBy().equals("2")).collect(Collectors.toList()).size();
            districtList.add(size);
            municipalList.add(size1);
        }else {
            districtList.add(0);
            municipalList.add(0);
        }//获取第一个月的数据
        List<HousesReview> collect7 = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == 7).collect(Collectors.toList());
        if (collect7.size()>0){
            //区分区级还是市级
            int size = collect7.stream().filter(c -> c.getSourceBy().equals("1")).collect(Collectors.toList()).size();
            int size1 = collect7.stream().filter(c -> c.getSourceBy().equals("2")).collect(Collectors.toList()).size();
            districtList.add(size);
            municipalList.add(size1);
        }else {
            districtList.add(0);
            municipalList.add(0);
        }//获取第一个月的数据
        List<HousesReview> collect8 = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == 8).collect(Collectors.toList());
        if (collect8.size()>0){
            //区分区级还是市级
            int size = collect8.stream().filter(c -> c.getSourceBy().equals("1")).collect(Collectors.toList()).size();
            int size1 = collect8.stream().filter(c -> c.getSourceBy().equals("2")).collect(Collectors.toList()).size();
            districtList.add(size);
            municipalList.add(size1);
        }else {
            districtList.add(0);
            municipalList.add(0);
        }
        //获取第一个月的数据
        List<HousesReview> collect9 = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == 9).collect(Collectors.toList());
        if (collect9.size()>0){
            //区分区级还是市级
            int size = collect9.stream().filter(c -> c.getSourceBy().equals("1")).collect(Collectors.toList()).size();
            int size1 = collect9.stream().filter(c -> c.getSourceBy().equals("2")).collect(Collectors.toList()).size();
            districtList.add(size);
            municipalList.add(size1);
        }else {
            districtList.add(0);
            municipalList.add(0);
        }
        //获取第一个月的数据
        List<HousesReview> collect10 = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == 10).collect(Collectors.toList());
        if (collect10.size()>0){
            //区分区级还是市级
            int size = collect10.stream().filter(c -> c.getSourceBy().equals("1")).collect(Collectors.toList()).size();
            int size1 = collect10.stream().filter(c -> c.getSourceBy().equals("2")).collect(Collectors.toList()).size();
            districtList.add(size);
            municipalList.add(size1);
        }else {
            districtList.add(0);
            municipalList.add(0);
        }
        //获取第一个月的数据
        List<HousesReview> collect11 = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == 11).collect(Collectors.toList());
        if (collect11.size()>0){
            //区分区级还是市级
            int size = collect11.stream().filter(c -> c.getSourceBy().equals("1")).collect(Collectors.toList()).size();
            int size1 = collect11.stream().filter(c -> c.getSourceBy().equals("2")).collect(Collectors.toList()).size();
            districtList.add(size);
            municipalList.add(size1);
        }else {
            districtList.add(0);
            municipalList.add(0);
        }
        HashMap<Object, Object> map = new HashMap<>();
        map.put("district",districtList);
        map.put("municipal",municipalList);
        map.put("sum",housesReviewList.size());
        return R.ok(map);


    }

    /**
     * 起步执行导出zip
     * @param event
     * @throws IOException
     */
    @Async
    @EventListener
    public void export(BuyHousesEvent event) throws IOException {
        BuyHousesBo bo = BeanUtil.toBean(event, BuyHousesBo.class);
        bo.setProcessStatus(Constants.SUCCEED);
        LambdaQueryWrapper<BuyHouses> lqw = buildQueryWrapper(bo);
        List<BuyHousesVo> buyHousesVoList = baseMapper.selectVoList(lqw);
        String title = "人才认定申请表";
        String separator = File.separator;
        String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String path = filePath + separator + format;
        if (buyHousesVoList.size() > 0) {
            //查询家庭附件
            List<Long> collect = buyHousesVoList.stream().map(BuyHousesVo::getId).collect(Collectors.toList());
            List<BuyHousesMember> buyHousesMemberList = buyHousesMemberMapper.selectList(new LambdaQueryWrapper<>(BuyHousesMember.class).in(BuyHousesMember::getBuyHousesId, collect));
            //对每一个HouseId分组
            Map<String, List<BuyHousesMember>> buyHousesMemberMap = buyHousesMemberList.stream().collect(Collectors.groupingBy(BuyHousesMember::getBuyHousesId));
            buyHousesVoList.stream().forEach(r -> {
                String userNameFile = path + separator + r.getUserName();
                System.out.println("userNameFile = " + userNameFile);
                try {
                    Files.createDirectories(Paths.get(userNameFile));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String s = userNameFile + separator + r.getUserName() + "--";
                //护照或者身份证
                if (ObjectUtil.isNotEmpty(r.getInsidepageUrl())){
                    String insidepageUrl = r.getInsidepageUrl();
                    String fileName=null;
                    if ("中国籍".equals(r.getNationality())){
                        fileName = s  + "户口簿内页" + insidepageUrl.substring(insidepageUrl.lastIndexOf("."));
                    }else {
                        fileName = s  + "护照内页" + insidepageUrl.substring(insidepageUrl.lastIndexOf("."));
                    }
                    MyFileUtils.downLoadPic(insidepageUrl, fileName);
                }
                //申请表
                if (ObjectUtil.isNotEmpty(r.getCommitmentUrl())){
                    String commitmentUrl = r.getCommitmentUrl();
                    String fileName= s  + "申请表" + commitmentUrl.substring(commitmentUrl.lastIndexOf("."));
                    MyFileUtils.downLoadPic(commitmentUrl, fileName);
                }

                //申明书
                if (ObjectUtil.isNotEmpty(r.getDeclarationUrl())){
                    String declarationUrl = r.getDeclarationUrl();
                    String fileName= s  + "申明书" + declarationUrl.substring(declarationUrl.lastIndexOf("."));
                    MyFileUtils.downLoadPic(declarationUrl, fileName);
                }

                //身份证正面
                if (ObjectUtil.isNotEmpty(r.getFrontUrl())){
                    String frontUrl = r.getFrontUrl();
                    String fileName= s  + "身份证正面" + frontUrl.substring(frontUrl.lastIndexOf("."));
                    MyFileUtils.downLoadPic(frontUrl, fileName);
                }

                //房屋记录
                if (ObjectUtil.isNotEmpty(r.getHomeRecordUrl())){
                    String homeRecordUrl = r.getHomeRecordUrl();
                    String fileName= s  + "房屋记录" + homeRecordUrl.substring(homeRecordUrl.lastIndexOf("."));
                    MyFileUtils.downLoadPic(homeRecordUrl, fileName);
                }


                //户口簿主页
                if (ObjectUtil.isNotEmpty(r.getHomepageUrl())){
                    String homepageUrl = r.getHomepageUrl();
                    String fileName= s  + "户口簿主页" + homepageUrl.substring(homepageUrl.lastIndexOf("."));
                    MyFileUtils.downLoadPic(homepageUrl, fileName);
                }

                //劳动合同
                if (ObjectUtil.isNotEmpty(r.getLaborContractUrl())){
                    String laborContractUrl = r.getLaborContractUrl();
                    String fileName= s  + "劳动合同" + laborContractUrl.substring(laborContractUrl.lastIndexOf("."));
                    MyFileUtils.downLoadPic(laborContractUrl, fileName);
                }

                //企业营业执照
                if (ObjectUtil.isNotEmpty(r.getLicenseUrl())){
                    String licenseUrl = r.getLicenseUrl();
                    String fileName= s  + "企业营业执照" + licenseUrl.substring(licenseUrl.lastIndexOf("."));
                    MyFileUtils.downLoadPic(licenseUrl, fileName);
                }

                //户口簿主页
                if (ObjectUtil.isNotEmpty(r.getHomepageUrl())){
                    String homepageUrl = r.getHomepageUrl();
                    String fileName= s  + "户口簿主页" + homepageUrl.substring(homepageUrl.lastIndexOf("."));
                    MyFileUtils.downLoadPic(homepageUrl, fileName);
                }

                //婚姻证明材料
                if (ObjectUtil.isNotEmpty(r.getMaritalUrl())){
                    String maritalUrl = r.getMaritalUrl();
                    String fileName= s  + "婚姻证明材料" + maritalUrl.substring(maritalUrl.lastIndexOf("."));
                    MyFileUtils.downLoadPic(maritalUrl, fileName);
                }

                //身份证背面
                if (ObjectUtil.isNotEmpty(r.getReverseUrl())){
                    String reverseUrl = r.getReverseUrl();
                    String fileName= s  + "身份证背面" + reverseUrl.substring(reverseUrl.lastIndexOf("."));
                    MyFileUtils.downLoadPic(reverseUrl, fileName);
                }

                //社保证明
                if (ObjectUtil.isNotEmpty(r.getSocialSecurityUrl())){
                    String socialSecurityUrl = r.getSocialSecurityUrl();
                    String fileName= s  + "社保证明" + socialSecurityUrl.substring(socialSecurityUrl.lastIndexOf("."));
                    MyFileUtils.downLoadPic(socialSecurityUrl, fileName);
                }

                //人才影像卡
                if (ObjectUtil.isNotEmpty(r.getPictureInformationUrl())){
                    String pictureInformationUrl = r.getPictureInformationUrl();
                    String fileName= s  + "人才影像卡" + pictureInformationUrl.substring(pictureInformationUrl.lastIndexOf("."));
                    MyFileUtils.downLoadPic(pictureInformationUrl, fileName);
                }
                //家庭信息
                List<BuyHousesMember> buyHousesMembers = buyHousesMemberMap.get(r.getId().toString());
                if (ObjectUtil.isNotNull(buyHousesMembers)) {
                    buyHousesMembers.stream().forEach(m -> {
                        System.out.println("m = " + m);
//                        String userNameFile1 = path + separator + m.getName();
                        if (ObjectUtil.isNotEmpty(m.getFrontUrl())) {
                            String frontUrl = m.getFrontUrl();
                            String fileName = s + m.getRelation() + "身份证正面" + frontUrl.substring(frontUrl.lastIndexOf("."), frontUrl.length());
                            MyFileUtils.downLoadPic(frontUrl, fileName);
                        }
                        if (ObjectUtil.isNotEmpty(m.getInsidepageUrl())) {
                            String insidepageUrl = m.getInsidepageUrl();
                            String fileName = s + m.getRelation() + "户口簿内页" + insidepageUrl.substring(insidepageUrl.lastIndexOf("."));
                            MyFileUtils.downLoadPic(insidepageUrl, fileName);
                        }
                        if (ObjectUtil.isNotEmpty(m.getReverseUrl())) {
                            String reverseUrl = m.getReverseUrl();
                            String fileName = s + m.getRelation() + "身份证正面" + reverseUrl.substring(reverseUrl.lastIndexOf("."), reverseUrl.length());
                            MyFileUtils.downLoadPic(reverseUrl, fileName);
                        }
                        if (ObjectUtil.isNotEmpty(m.getHomeRecordUrl())) {
                            String homeRecordUrl = m.getHomeRecordUrl();
                            String fileName = s + m.getRelation() + "身份证正面" + homeRecordUrl.substring(homeRecordUrl.lastIndexOf("."), homeRecordUrl.length());
                            MyFileUtils.downLoadPic(homeRecordUrl, fileName);
                        }

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
            ExcelUtil.exportExcel(buyHousesVoList,"111",BuyHousesVo.class,outXlsx);
            outXlsx.close();
            ZipUtils.toZip(path, fo, true);
            System.out.println("path = " + path);
            System.out.println("fo = " + fo);
            //生成后删除文件
            DeleteFileUtil.delete(path);
            String zip = format+".zip";
            String url =download+prefix+"/"+zip;
            System.out.println("url = " + url);
            SubscribeExport subscribeExport = new SubscribeExport();
            subscribeExport.setPath(url);
            subscribeExport.setId(event.getExcelId());
            subscribeExport.setExportStatus("1");
            subscribeExportMapper.updateById(subscribeExport);
        }
    }

    public void  excelZip(){
        BuyHousesBo bo =new BuyHousesBo();
        bo.setId(3L);
        bo.setProcessStatus(Constants.SUCCEED);
        LambdaQueryWrapper<BuyHouses> lqw = buildQueryWrapper(bo);
        List<BuyHousesVo> buyHousesVoList = baseMapper.selectVoList(lqw);
        if (buyHousesVoList.size() > 0) {
            //查询家庭附件
            List<Long> collect = buyHousesVoList.stream().map(BuyHousesVo::getId).collect(Collectors.toList());
            List<BuyHousesMember> buyHousesMemberList = buyHousesMemberMapper.selectList(new LambdaQueryWrapper<>(BuyHousesMember.class).in(BuyHousesMember::getBuyHousesId, collect));
            //对每一个HouseId分组
            Map<String, List<BuyHousesMember>> buyHousesMemberMap = buyHousesMemberList.stream().collect(Collectors.groupingBy(BuyHousesMember::getBuyHousesId));
            buyHousesVoList.stream().forEach(r -> {
                String dir="/usr/local/images/2023/07/01";
                //护照或者身份证
                if (ObjectUtil.isNotEmpty(r.getInsidepageUrl())){
                    String insidepageUrl = r.getInsidepageUrl();
                    String fileName =dir+insidepageUrl.substring(insidepageUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(insidepageUrl, fileName);
                }
                //申请表
                if (ObjectUtil.isNotEmpty(r.getCommitmentUrl())){
                    String commitmentUrl = r.getCommitmentUrl();
                    String fileName= dir+commitmentUrl.substring(commitmentUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(commitmentUrl, fileName);
                }
                //申明书
                if (ObjectUtil.isNotEmpty(r.getDeclarationUrl())){
                    String declarationUrl = r.getDeclarationUrl();
                    String fileName= dir+declarationUrl.substring(declarationUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(declarationUrl, fileName);
                }

                //身份证正面
                if (ObjectUtil.isNotEmpty(r.getFrontUrl())){
                    String frontUrl = r.getFrontUrl();
                    String fileName= dir+frontUrl.substring(frontUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(frontUrl, fileName);
                }

                //房屋记录
                if (ObjectUtil.isNotEmpty(r.getHomeRecordUrl())){
                    String homeRecordUrl = r.getHomeRecordUrl();
                    String fileName= dir+homeRecordUrl.substring(homeRecordUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(homeRecordUrl, fileName);
                }


                //户口簿主页
                if (ObjectUtil.isNotEmpty(r.getHomepageUrl())){
                    String homepageUrl = r.getHomepageUrl();
                    String fileName= dir+homepageUrl.substring(homepageUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(homepageUrl, fileName);
                }

                //劳动合同
                if (ObjectUtil.isNotEmpty(r.getLaborContractUrl())){
                    String laborContractUrl = r.getLaborContractUrl();
                    String fileName= dir+laborContractUrl.substring(laborContractUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(laborContractUrl, fileName);
                }

                //企业营业执照
                if (ObjectUtil.isNotEmpty(r.getLicenseUrl())){
                    String licenseUrl = r.getLicenseUrl();
                    String fileName= dir+licenseUrl.substring(licenseUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(licenseUrl, fileName);
                }

                //户口簿主页
                if (ObjectUtil.isNotEmpty(r.getHomepageUrl())){
                    String homepageUrl = r.getHomepageUrl();
                    String fileName= dir+homepageUrl.substring(homepageUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(homepageUrl, fileName);
                }

                //婚姻证明材料
                if (ObjectUtil.isNotEmpty(r.getMaritalUrl())){
                    String maritalUrl = r.getMaritalUrl();
                    String fileName= dir+maritalUrl.substring(maritalUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(maritalUrl, fileName);
                }

                //身份证背面
                if (ObjectUtil.isNotEmpty(r.getReverseUrl())){
                    String reverseUrl = r.getReverseUrl();
                    String fileName=dir+reverseUrl.substring(reverseUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(reverseUrl, fileName);
                }

                //社保证明
                if (ObjectUtil.isNotEmpty(r.getSocialSecurityUrl())){
                    String socialSecurityUrl = r.getSocialSecurityUrl();
                    String fileName= dir+socialSecurityUrl.substring(socialSecurityUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(socialSecurityUrl, fileName);
                }

                //人才影像卡
                if (ObjectUtil.isNotEmpty(r.getPictureInformationUrl())){
                    String pictureInformationUrl = r.getPictureInformationUrl();
                    String fileName= dir+pictureInformationUrl.substring(pictureInformationUrl.lastIndexOf("/") + 1);
                    MyFileUtils.downLoadPic(pictureInformationUrl, fileName);
                }
                //家庭信息
                List<BuyHousesMember> buyHousesMembers = buyHousesMemberMap.get(r.getId().toString());
                if (ObjectUtil.isNotNull(buyHousesMembers)) {
                    buyHousesMembers.stream().forEach(m -> {
                        if (ObjectUtil.isNotEmpty(m.getFrontUrl())) {
                            String frontUrl = m.getFrontUrl();
                            String fileName = dir+frontUrl.substring(frontUrl.lastIndexOf("/") + 1);
                            MyFileUtils.downLoadPic(frontUrl, fileName);
                        }
                        if (ObjectUtil.isNotEmpty(m.getInsidepageUrl())) {
                            String insidepageUrl = m.getInsidepageUrl();
                            String fileName =dir+ insidepageUrl.substring(insidepageUrl.lastIndexOf("/") + 1);
                            MyFileUtils.downLoadPic(insidepageUrl, fileName);
                        }
                        if (ObjectUtil.isNotEmpty(m.getReverseUrl())) {
                            String reverseUrl = m.getReverseUrl();
                            String fileName =dir+ reverseUrl.substring(reverseUrl.lastIndexOf("/") + 1);
                            MyFileUtils.downLoadPic(reverseUrl, fileName);
                        }
                        if (ObjectUtil.isNotEmpty(m.getHomeRecordUrl())) {
                            String homeRecordUrl = m.getHomeRecordUrl();
                            String fileName = dir+homeRecordUrl.substring(homeRecordUrl.lastIndexOf("/") + 1);
                            MyFileUtils.downLoadPic(homeRecordUrl, fileName);
                        }
                    });
                }
            });
        }
    }
}
