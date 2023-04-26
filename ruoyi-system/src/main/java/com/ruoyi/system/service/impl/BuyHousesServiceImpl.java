package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.helper.LoginHelper;
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
import com.ruoyi.system.mapper.BuyHousesMapper;
import com.ruoyi.system.mapper.BuyHousesMemberMapper;
import com.ruoyi.system.mapper.MaterialProofMapper;
import com.ruoyi.system.mapper.SubscribeExportMapper;
import com.ruoyi.system.service.IBuyHousesService;
import com.ruoyi.work.domain.AuditLog;
import com.ruoyi.work.domain.vo.ProcessVo;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-24
 */
@RequiredArgsConstructor
@Service
public class BuyHousesServiceImpl implements IBuyHousesService {

    @Value("${file.template}")
    private String fileUpload;

    @Value("${file.path}")
    private String filePath;

    @Value("${file.domain}")
    private String download;

    @Value("${file.prefix}")
    private String prefix;

    private final String URL = "http://192.168.0.54:8010//candidates/getCardId";

    private final BuyHousesMapper baseMapper;

    private final BuyHousesMemberMapper buyHousesMemberMapper;

    private final MaterialProofMapper materialProofMapper;

    private final MaterialModuleServiceImpl materialModuleService;

    private final SubscribeExportMapper subscribeExportMapper;

    private final AuditLogMapper auditLogMapper;


    /**
     * 查询【请填写功能名称】
     */
    @Override
    public BuyHousesVo queryById(Long id){
        BuyHousesVo buyHousesVo = baseMapper.selectVoById(id);
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
        LambdaQueryWrapper<BuyHouses> lqw = buildQueryWrapper(bo);
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

    /**
     * 新增【请填写功能名称】
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(BuyHousesBo bo) {
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
        BuyHouses update = BeanUtil.toBean(bo, BuyHouses.class);
        validEntityBeforeSave(update);
        update.setProcessStatus(Constants.WAIT);
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
            WorkComplyUtils.comply(processVo);
        }
        return flag;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BuyHouses entity){
        //TODO 做一些数据校验,如唯一约束
        //验证当前状态是否可以修改
        //判断数据库中是否存在该人才通过身份证去验证
        Long userId = LoginHelper.getUserId();
        BuyHouses buyHouses = baseMapper.selectOne(new LambdaQueryWrapper<>(BuyHouses.class).eq(BuyHouses::getUserId, userId));
        if (!Constants.SUBMIT.equals(buyHouses.getProcessStatus()) && !Constants.FAILD.equals(buyHouses.getProcessStatus())){
            throw new ServiceException("当前用户不可修改");
        }
        //删除家庭情况信息
        buyHousesMemberMapper.delete(new LambdaQueryWrapper<>(BuyHousesMember.class).eq(BuyHousesMember::getBuyHousesId,buyHouses.getId()));
        //先删除家庭信息表
        if (entity.getBuyHousesMemberList().size()>0) {
            buyHousesMemberMapper.delete( new LambdaQueryWrapper<>(BuyHousesMember.class).eq(BuyHousesMember::getBuyHousesId, entity.getId()));
            //添加家庭信息
            entity.getBuyHousesMemberList().stream().forEach(e ->e.setBuyHousesId(String.valueOf(entity.getId())));
            buyHousesMemberMapper.insertBatch(entity.getBuyHousesMemberList());
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
        BuyHouses buyHousesBo = BeanUtil.toBean(bo, BuyHouses.class);
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
        buyHousesDto.setNationality(String.valueOf(entries.get("nationality")));
        buyHousesDto.setUserName(String.valueOf(entries.get("name")));
        buyHousesDto.setPhone(String.valueOf(entries.get("phone")));
        buyHousesDto.setCompanyName(String.valueOf(entries.get("companyName")));
        buyHousesDto.setSex(String.valueOf(entries.get("sex")));
        buyHousesDto.setDistrict("1");
        buyHousesDto.setProcessStatus(Constants.SUBMIT);
        buyHousesDto.setType(String.valueOf(entries.get("type")));
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
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("cardId", cardId);
        String json = HttpUtil.get(URL, paramMap);
        JSONObject entries = JSONUtil.parseObj(json);
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
     * 导出列表
     * @param bo
     * @return
     */
    @Override
    public R subscribeExport(BuyHousesEvent bo) {
        Long userId = LoginHelper.getUserId();
        bo.setExcelUserId(userId.toString());
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
        BuyHouses buyHouses = baseMapper.selectOne(new LambdaQueryWrapper<>(BuyHouses.class).eq(BuyHouses::getUserId, userId));
        List<AuditLog> auditLogList = auditLogMapper.selectList(new LambdaQueryWrapper<>(AuditLog.class).eq(AuditLog::getOtherId, buyHouses.getId()).orderByAsc(AuditLog::getCreateTime));
        if (auditLogList.size()>0){
            auditLogList.stream().forEach(a ->{
                if (ObjectUtil.isNull(a.getProcessKey())){
//                    1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6.初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过  11.审定退件,12.资格取消
                    switch (a.getStatus()){
                        case "1":a.setStatus("待提交");
                            break;
                        case "2":a.setStatus("受理中");
                            break;
                        case "3":a.setStatus("受理退件");
                            break;
                        case "4":a.setStatus("受理驳回");
                            break;
                        case "5":a.setStatus("初审中");
                            break;
                        case "6":a.setStatus("初审不通过");
                            break;
                        case "7":a.setStatus("初审退件");
                            break;
                        case "8":a.setStatus("审定中");
                            break;
                        case "9":a.setStatus("审定不通过");
                            break;
                        case "10":a.setStatus("审定通过");
                            break;
                        case "11":a.setStatus("审定退件");
                            break;
                        case "12":a.setStatus("资格取消");
                            break;
                    }
                }else {
                    switch (a.getStatus()){
                        case "1":a.setStatus("审核失败");
                        break;
                        case "2":a.setStatus("审核成功");
                        break;
                    }
                }
            });
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("auditLog",auditLogList);
        map.put("status",buyHouses.getProcessStatus());
        map.put("id",buyHouses.getId());
        return R.ok("查询成功",map);
    }

    @Override
    public R downloadInform() {
        Long userId = LoginHelper.getUserId();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        //判断数据库中是否存在该人才通过身份证去验证
        BuyHouses buyHouses = baseMapper.selectOne(new LambdaQueryWrapper<>(BuyHouses.class)
            .eq(BuyHouses::getUserId,userId)
            .eq(BuyHouses::getProcessStatus,Constants.SUCCEED));
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
                /*if (ObjectUtil.isNotEmpty(r.getCommitmentUrl())){
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
                }*/
                //家庭信息
               /* List<BuyHousesMember> buyHousesMembers = buyHousesMemberMap.get(r.getId().toString());
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
                }*/
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
            subscribeExport.setProcessKey(event.getProcessKey());
            subscribeExport.setDescription(event.getDescription());
            subscribeExport.setUserId(event.getExcelUserId());
            subscribeExportMapper.insert(subscribeExport);
        }
    }
}
