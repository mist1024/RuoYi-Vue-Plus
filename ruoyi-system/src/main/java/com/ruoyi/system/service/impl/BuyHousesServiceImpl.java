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
import com.ruoyi.common.utils.BeanCopyUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExportWordUtil;
import com.ruoyi.system.domain.BuyHouses;
import com.ruoyi.system.domain.BuyHousesMember;
import com.ruoyi.system.domain.MaterialProof;
import com.ruoyi.system.domain.bo.BuyHousesBo;
import com.ruoyi.system.domain.dto.DeclareListDTO;
import com.ruoyi.system.domain.vo.BuyHousesVo;
import com.ruoyi.system.domain.vo.MaterialModuleVo;
import com.ruoyi.system.mapper.BuyHousesMapper;
import com.ruoyi.system.mapper.BuyHousesMemberMapper;
import com.ruoyi.system.mapper.MaterialProofMapper;
import com.ruoyi.system.service.IBuyHousesService;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.utils.WorkComplyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    private final String URL = "http://192.168.0.54:8010//candidates/getCardId";

    private final BuyHousesMapper baseMapper;

    private final BuyHousesMemberMapper buyHousesMemberMapper;

    private final MaterialProofMapper materialProofMapper;

    private final MaterialModuleServiceImpl materialModuleService;

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
        lqw.eq(bo.getAffTime() != null, BuyHouses::getAffTime, bo.getAffTime());
        lqw.eq(StringUtils.isNotBlank(bo.getPictureInformationUrl()), BuyHouses::getPictureInformationUrl, bo.getPictureInformationUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getWorkAddress()), BuyHouses::getWorkAddress, bo.getWorkAddress());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
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
        BuyHouses buyHouses = baseMapper.selectOne(new LambdaQueryWrapper<>(BuyHouses.class)
            .eq(BuyHouses::getCardId, entity.getCardId()));
        if (!Constants.SUBMIT.equals(buyHouses.getProcessStatus()) && !Constants.FAILD.equals(buyHouses.getProcessStatus())){
            throw new ServiceException("当前用户不可修改");
        }
        //删除家庭情况信息
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
     * @param buyHousesBo
     */
    @Override
    public R downloadWord(BuyHousesBo buyHousesBo) {
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
        //先判断本地数据库是否有值
        BuyHousesVo buyHousesVo = baseMapper.selectVoOne(new LambdaQueryWrapper<>(BuyHouses.class).eq(BuyHouses::getCardId,buyHouses.getCardId()));
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
        paramMap.put("cardId", buyHouses);
        String json = HttpUtil.get(URL, paramMap);
        JSONObject entrie = JSONUtil.parseObj(json);
        String data = String.valueOf(entrie.get("date"));
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
        buyHousesDto.setDistrict(String.valueOf(entries.get("district")));
        buyHousesDto.setSex(String.valueOf(entries.get("sex")));
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
}
