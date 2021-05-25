package com.ruoyi.edu.service;

import com.ruoyi.edu.domain.EduExam;
import com.ruoyi.edu.vo.EduExamVo;
import com.ruoyi.edu.bo.EduExamQueryBo;
import com.ruoyi.edu.bo.EduExamAddBo;
import com.ruoyi.edu.bo.EduExamEditBo;
import com.ruoyi.common.core.page.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 考试信息Service接口
 *
 * @author keyleaf
 * @date 2021-05-23
 */
public interface IEduExamService extends IServicePlus<EduExam> {
	/**
	 * 查询单个
	 * @return
	 */
	EduExamVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<EduExamVo> queryPageList(EduExamQueryBo bo);
	/**
	 * 查询列表
	 */
	List<EduExamVo> queryList(EduExamQueryBo bo);

	/**
	 * 根据新增业务对象插入考试信息
	 * @param bo 考试信息新增业务对象
	 * @return
	 */
	Boolean insertByAddBo(EduExamAddBo bo);

	/**
	 * 根据编辑业务对象修改考试信息
	 * @param bo 考试信息编辑业务对象
	 * @return
	 */
	Boolean updateByEditBo(EduExamEditBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
