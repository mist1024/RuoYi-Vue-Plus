package com.ruoyi.edu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.edu.bo.EduStudentExamAddBo;
import com.ruoyi.edu.bo.EduStudentExamQueryBo;
import com.ruoyi.edu.bo.EduStudentExamEditBo;
import com.ruoyi.edu.domain.EduStudentExam;
import com.ruoyi.edu.mapper.EduStudentExamMapper;
import com.ruoyi.edu.vo.EduStudentExamVo;
import com.ruoyi.edu.service.IEduStudentExamService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 学生考试信息Service业务层处理
 *
 * @author keyleaf
 * @date 2021-05-23
 */
@Service
public class EduStudentExamServiceImpl extends ServiceImpl<EduStudentExamMapper, EduStudentExam> implements IEduStudentExamService {

    @Override
    public EduStudentExamVo queryById(Long id){
        return getVoById(id, EduStudentExamVo.class);
    }

    @Override
    public TableDataInfo<EduStudentExamVo> queryPageList(EduStudentExamQueryBo bo) {
        PagePlus<EduStudentExam, EduStudentExamVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), EduStudentExamVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<EduStudentExamVo> queryList(EduStudentExamQueryBo bo) {
        return listVo(buildQueryWrapper(bo), EduStudentExamVo.class);
    }

    private Wrapper<EduStudentExam> buildQueryWrapper(EduStudentExamQueryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<EduStudentExam> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getStudentId() != null, EduStudentExam::getStudentId, bo.getStudentId());
        lqw.eq(bo.getExamId() != null, EduStudentExam::getExamId, bo.getExamId());

		QueryWrapper<EduStudentExam> qw = new QueryWrapper<>();
//		qw.eq("b.id", 2);
        return qw;
    }

    @Override
    public Boolean insertByAddBo(EduStudentExamAddBo bo) {
        EduStudentExam add = BeanUtil.toBean(bo, EduStudentExam.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByEditBo(EduStudentExamEditBo bo) {
        EduStudentExam update = BeanUtil.toBean(bo, EduStudentExam.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(EduStudentExam entity){
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
