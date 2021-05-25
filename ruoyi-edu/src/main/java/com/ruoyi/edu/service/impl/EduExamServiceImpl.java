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
import com.ruoyi.edu.bo.EduExamAddBo;
import com.ruoyi.edu.bo.EduExamQueryBo;
import com.ruoyi.edu.bo.EduExamEditBo;
import com.ruoyi.edu.domain.EduExam;
import com.ruoyi.edu.mapper.EduExamMapper;
import com.ruoyi.edu.vo.EduExamVo;
import com.ruoyi.edu.service.IEduExamService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 考试信息Service业务层处理
 *
 * @author keyleaf
 * @date 2021-05-23
 */
@Service
public class EduExamServiceImpl extends ServiceImpl<EduExamMapper, EduExam> implements IEduExamService {

    @Override
    public EduExamVo queryById(Long id){
        return getVoById(id, EduExamVo.class);
    }

    @Override
    public TableDataInfo<EduExamVo> queryPageList(EduExamQueryBo bo) {
        PagePlus<EduExam, EduExamVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), EduExamVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<EduExamVo> queryList(EduExamQueryBo bo) {
        return listVo(buildQueryWrapper(bo), EduExamVo.class);
    }

    private LambdaQueryWrapper<EduExam> buildQueryWrapper(EduExamQueryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<EduExam> lqw = Wrappers.lambdaQuery();
        lqw.eq(StrUtil.isNotBlank(bo.getExamNo()), EduExam::getExamNo, bo.getExamNo());
        lqw.like(StrUtil.isNotBlank(bo.getExamName()), EduExam::getExamName, bo.getExamName());
        lqw.like(StrUtil.isNotBlank(bo.getExamShortName()), EduExam::getExamShortName, bo.getExamShortName());
        lqw.eq(bo.getExamDate() != null, EduExam::getExamDate, bo.getExamDate());
        return lqw;
    }

    @Override
    public Boolean insertByAddBo(EduExamAddBo bo) {
        EduExam add = BeanUtil.toBean(bo, EduExam.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByEditBo(EduExamEditBo bo) {
        EduExam update = BeanUtil.toBean(bo, EduExam.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(EduExam entity){
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
