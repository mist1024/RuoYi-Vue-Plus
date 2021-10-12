package com.ruoyi.demo.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.demo.domain.PictureDisplay;
import com.ruoyi.demo.domain.vo.PictureDisplayVo;
import com.ruoyi.demo.domain.bo.PictureDisplayBo;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.demo.domain.vo.PictureListVo;

import java.util.Collection;
import java.util.List;

/**
 * 图片展示Service接口
 *
 * @author qianlan
 * @date 2021-10-10
 */
public interface IPictureDisplayService extends IServicePlus<PictureDisplay, PictureDisplayVo> {
	/**
	 * 查询单个
	 *
	 * @return
	 */
	PictureDisplayVo queryById(Long picId);

	/**
	 * 查询列表
	 */
	TableDataInfo<PictureDisplayVo> queryPageList(PictureDisplayBo bo);

	/**
	 * 查询列表
	 */
	List<PictureDisplayVo> queryList(PictureDisplayBo bo);

	/**
	 * 根据新增业务对象插入图片展示
	 *
	 * @param bo 图片展示新增业务对象
	 * @return
	 */
	Boolean insertByBo(PictureDisplayBo bo);

	/**
	 * 根据编辑业务对象修改图片展示
	 *
	 * @param bo 图片展示编辑业务对象
	 * @return
	 */
	Boolean updateByBo(PictureDisplayBo bo);

	/**
	 * 校验并删除数据
	 *
	 * @param ids     主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

	/**
	 * 获取图片，每十个为一组
	 *
	 * @return
	 */
	List<PictureListVo> queryListWithCondition();


	/**
	 * 提交要修改的图片选项
	 * @return
	 */
	AjaxResult commitPic(List<PictureDisplayVo> lists);

}
