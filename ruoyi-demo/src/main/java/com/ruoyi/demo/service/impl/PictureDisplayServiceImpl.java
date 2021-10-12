package com.ruoyi.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.demo.domain.HjcSelection;
import com.ruoyi.demo.domain.vo.PictureListVo;
import com.ruoyi.demo.service.IHjcSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.demo.domain.bo.PictureDisplayBo;
import com.ruoyi.demo.domain.vo.PictureDisplayVo;
import com.ruoyi.demo.domain.PictureDisplay;
import com.ruoyi.demo.mapper.PictureDisplayMapper;
import com.ruoyi.demo.service.IPictureDisplayService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 图片展示Service业务层处理
 *
 * @author qianlan
 * @date 2021-10-10
 */
@Service
@Transactional
public class PictureDisplayServiceImpl extends ServicePlusImpl<PictureDisplayMapper, PictureDisplay, PictureDisplayVo> implements IPictureDisplayService {


	@Autowired
	private IPictureDisplayService pictureDisplayService;

	@Autowired
	private IHjcSelectionService selectionService;

	@Override
	public PictureDisplayVo queryById(Long picId) {
		return getVoById(picId);
	}

	@Override
	public TableDataInfo<PictureDisplayVo> queryPageList(PictureDisplayBo bo) {
		PagePlus<PictureDisplay, PictureDisplayVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
		return PageUtils.buildDataInfo(result);
	}

	@Override
	public List<PictureDisplayVo> queryList(PictureDisplayBo bo) {
		return listVo(buildQueryWrapper(bo));
	}

	private LambdaQueryWrapper<PictureDisplay> buildQueryWrapper(PictureDisplayBo bo) {
		Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<PictureDisplay> lqw = Wrappers.lambdaQuery();
		lqw.eq(bo.getPicId() != null, PictureDisplay::getPicId, bo.getPicId());
		lqw.eq(StrUtil.isNotBlank(bo.getPicUrl()), PictureDisplay::getPicUrl, bo.getPicUrl());
		lqw.eq(bo.getPicSelection() != null, PictureDisplay::getPicSelection, bo.getPicSelection());
		lqw.eq(bo.getPicBool() != null, PictureDisplay::getPicBool, bo.getPicBool());
		return lqw;
	}

	@Override
	public Boolean insertByBo(PictureDisplayBo bo) {
		PictureDisplay add = BeanUtil.toBean(bo, PictureDisplay.class);
		validEntityBeforeSave(add);
		return save(add);
	}

	@Override
	public Boolean updateByBo(PictureDisplayBo bo) {
		PictureDisplay update = BeanUtil.toBean(bo, PictureDisplay.class);
		validEntityBeforeSave(update);
		return updateById(update);
	}

	/**
	 * 保存前的数据校验
	 *
	 * @param entity 实体类数据
	 */
	private void validEntityBeforeSave(PictureDisplay entity) {
		//TODO 做一些数据校验,如唯一约束
	}

	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			//TODO 做一些业务上的校验,判断是否需要校验
		}
		return removeByIds(ids);
	}

	@Override
	public List<PictureListVo> queryListWithCondition() {
		QueryWrapper<PictureDisplay> wrapper = new QueryWrapper<>();
		wrapper.last(" ORDER BY RAND() LIMIT 20");
		List<PictureDisplay> pictureDisplays = baseMapper.selectList(wrapper);
		List<PictureListVo> lists = pictureDisplays.stream().map((list) -> {
			PictureListVo listVo = new PictureListVo();
			BeanUtil.copyProperties(list, listVo);
			return listVo;
		}).collect(Collectors.toList());
		return lists;
	}

	@Override
	public AjaxResult commitPic(List<PictureDisplayVo> lists) {
		int len = lists.size();
		HashSet<Long> ids = new HashSet<>();
		for (int i = len - 1; i >= 0; i--) {
			Long picId = lists.get(i).getPicId();
			int selection = lists.get(i).getPicSelection();
			if (!ids.contains(picId)) {

				PictureDisplay pictureDisplay = new PictureDisplay();
				Integer picBool = pictureDisplayService.getOne(new QueryWrapper<PictureDisplay>().eq("pic_id", picId)).getPicBool();
				pictureDisplay.setPicId(picId);
				pictureDisplay.setPicBool(picBool + 1);
				pictureDisplayService.updateById(pictureDisplay);

				HjcSelection hjcSelection = new HjcSelection();
				hjcSelection.setPid(picId);
				hjcSelection.setSelection(selection);
				selectionService.save(hjcSelection);

			}
			ids.add(picId);
		}
		return AjaxResult.success();
	}

}
