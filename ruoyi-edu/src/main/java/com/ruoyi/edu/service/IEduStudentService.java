package com.ruoyi.edu.service;

import com.ruoyi.edu.domain.EduStudent;
import com.ruoyi.edu.vo.EduStudentVo;
import com.ruoyi.edu.bo.EduStudentQueryBo;
import com.ruoyi.edu.bo.EduStudentAddBo;
import com.ruoyi.edu.bo.EduStudentEditBo;
import com.ruoyi.common.core.page.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 学生信息Service接口
 *
 * @author keyleaf
 * @date 2021-05-22
 */
public interface IEduStudentService extends IServicePlus<EduStudent> {
	/**
	 * 查询单个
	 * @return
	 */
	EduStudentVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<EduStudentVo> queryPageList(EduStudentQueryBo bo);
	/**
	 * 查询列表
	 */
	List<EduStudentVo> queryList(EduStudentQueryBo bo);

	/**
	 * 根据新增业务对象插入学生信息
	 * @param bo 学生信息新增业务对象
	 * @return
	 */
	Boolean insertByAddBo(EduStudentAddBo bo);

	/**
	 * 根据编辑业务对象修改学生信息
	 * @param bo 学生信息编辑业务对象
	 * @return
	 */
	Boolean updateByEditBo(EduStudentEditBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
