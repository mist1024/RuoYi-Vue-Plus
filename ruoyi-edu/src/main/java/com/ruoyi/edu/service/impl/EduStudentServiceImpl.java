package com.ruoyi.edu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.edu.bo.EduStudentAddBo;
import com.ruoyi.edu.bo.EduStudentQueryBo;
import com.ruoyi.edu.bo.EduStudentEditBo;
import com.ruoyi.edu.domain.EduStudent;
import com.ruoyi.edu.mapper.EduStudentMapper;
import com.ruoyi.edu.vo.EduStudentVo;
import com.ruoyi.edu.service.IEduStudentService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 学生信息Service业务层处理
 *
 * @author keyleaf
 * @date 2021-05-22
 */
@Service
public class EduStudentServiceImpl extends ServiceImpl<EduStudentMapper, EduStudent> implements IEduStudentService {

    @Override
    public EduStudentVo queryById(Long id){
        return getVoById(id, EduStudentVo.class);
    }

    @Override
    public TableDataInfo<EduStudentVo> queryPageList(EduStudentQueryBo bo) {
        PagePlus<EduStudent, EduStudentVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), EduStudentVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<EduStudentVo> queryList(EduStudentQueryBo bo) {
        return listVo(buildQueryWrapper(bo), EduStudentVo.class);
    }

    private LambdaQueryWrapper<EduStudent> buildQueryWrapper(EduStudentQueryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<EduStudent> lqw = Wrappers.lambdaQuery();
        lqw.eq(StrUtil.isNotBlank(bo.getIdCardNo()), EduStudent::getIdCardNo, bo.getIdCardNo());
        lqw.like(StrUtil.isNotBlank(bo.getStudentName()), EduStudent::getStudentName, bo.getStudentName());
        lqw.eq(StrUtil.isNotBlank(bo.getCurrentGrade()), EduStudent::getCurrentGrade, bo.getCurrentGrade());
        lqw.eq(StrUtil.isNotBlank(bo.getCurrentClass()), EduStudent::getCurrentClass, bo.getCurrentClass());
        return lqw;
    }

    @Override
    public Boolean insertByAddBo(EduStudentAddBo bo) {
        EduStudent add = BeanUtil.toBean(bo, EduStudent.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByEditBo(EduStudentEditBo bo) {
        EduStudent update = BeanUtil.toBean(bo, EduStudent.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(EduStudent entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }
}
