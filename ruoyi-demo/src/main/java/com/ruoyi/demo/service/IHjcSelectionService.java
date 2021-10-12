package com.ruoyi.demo.service;

import com.ruoyi.demo.domain.HjcSelection;
import com.ruoyi.demo.domain.vo.HjcSelectionVo;
import com.ruoyi.demo.domain.bo.HjcSelectionBo;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 图片选择参数Service接口
 *
 * @author qianlan
 * @date 2021-10-12
 */
public interface IHjcSelectionService extends IServicePlus<HjcSelection, HjcSelectionVo> {
	/**
	 * 查询单个
	 * @return
	 */
	HjcSelectionVo queryById(Long sid);

	/**
	 * 查询列表
	 */
    TableDataInfo<HjcSelectionVo> queryPageList(HjcSelectionBo bo);

	/**
	 * 查询列表
	 */
	List<HjcSelectionVo> queryList(HjcSelectionBo bo);

	/**
	 * 根据新增业务对象插入图片选择参数
	 * @param bo 图片选择参数新增业务对象
	 * @return
	 */
	Boolean insertByBo(HjcSelectionBo bo);

	/**
	 * 根据编辑业务对象修改图片选择参数
	 * @param bo 图片选择参数编辑业务对象
	 * @return
	 */
	Boolean updateByBo(HjcSelectionBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
