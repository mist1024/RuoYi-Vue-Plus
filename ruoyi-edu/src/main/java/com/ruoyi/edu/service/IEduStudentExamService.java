package com.ruoyi.edu.service;

import com.ruoyi.edu.domain.EduStudentExam;
import com.ruoyi.edu.vo.EduStudentExamVo;
import com.ruoyi.edu.bo.EduStudentExamQueryBo;
import com.ruoyi.edu.bo.EduStudentExamAddBo;
import com.ruoyi.edu.bo.EduStudentExamEditBo;
import com.ruoyi.common.core.page.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 学生考试信息Service接口
 *
 * @author keyleaf
 * @date 2021-05-23
 */
public interface IEduStudentExamService extends IServicePlus<EduStudentExam> {
	/**
	 * 查询单个
	 * @return
	 */
	EduStudentExamVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<EduStudentExamVo> queryPageList(EduStudentExamQueryBo bo);
	/**
	 * 查询列表
	 */
	List<EduStudentExamVo> queryList(EduStudentExamQueryBo bo);

	/**
	 * 根据新增业务对象插入学生考试信息
	 * @param bo 学生考试信息新增业务对象
	 * @return
	 */
	Boolean insertByAddBo(EduStudentExamAddBo bo);

	/**
	 * 根据编辑业务对象修改学生考试信息
	 * @param bo 学生考试信息编辑业务对象
	 * @return
	 */
	Boolean updateByEditBo(EduStudentExamEditBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
