package com.ruoyi.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.BaseException;
import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.product.bo.ProItemAddBo;
import com.ruoyi.product.bo.ProItemEditBo;
import com.ruoyi.product.bo.ProItemQueryBo;
import com.ruoyi.product.domain.ProItem;
import com.ruoyi.product.domain.ProItemCategory;
import com.ruoyi.product.domain.ProItemPicture;
import com.ruoyi.product.dto.ProItemPictureDTO;
import com.ruoyi.product.enums.PictureTypeEnum;
import com.ruoyi.product.enums.PublishFlagEnum;
import com.ruoyi.product.mapper.ProItemMapper;
import com.ruoyi.product.service.IProItemCategoryService;
import com.ruoyi.product.service.IProItemPictureService;
import com.ruoyi.product.service.IProItemService;
import com.ruoyi.product.vo.ProItemVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品详情Service业务层处理
 *
 * @author ruoyi
 * @date 2021-03-13
 */
@Service
@RequiredArgsConstructor
public class ProItemServiceImpl extends ServicePlusImpl<ProItemMapper, ProItem> implements IProItemService {


	private final ProItemMapper proItemMapper;
	private final IProItemPictureService proItemPictureService;
	private final IProItemCategoryService proItemCategoryService;

	public boolean insertByAddBo(ProItemAddBo bo) {
		if (Objects.equals(bo.getPublishFlag(), PublishFlagEnum.YES.getValue())) {
			bo.setPublishTime(new Date());
		}
		ProItem proItem = new ProItem();
		BeanUtil.copyProperties(bo, proItem);
		proItem.setId(IdUtils.nextId());
		proItemMapper.insert(proItem);
		if (CollUtil.isNotEmpty(bo.getProItemPictures())) {
			List<ProItemPicture> pictures = getProItemPictures(bo.getProItemPictures(), proItem.getId());
			proItemPictureService.saveBatch(pictures);
		}
		if (CollUtil.isNotEmpty(bo.getProItemCategoryIds())) {
			List<ProItemCategory> proItemCategories = getProItemCategories(bo.getProItemCategoryIds(), proItem.getId());
			proItemCategoryService.saveBatch(proItemCategories);
		}
		return Boolean.TRUE;
	}

	private List<ProItemCategory> getProItemCategories(List<String> categoryIds, Long id) {
		return categoryIds.stream().map(item -> {
			ProItemCategory category = new ProItemCategory();
			category.setId(IdUtils.nextId());
			category.setCategoryId(Long.parseLong(item));
			category.setItemId(id);
			return category;
		}).collect(Collectors.toList());
	}

	private List<ProItemPicture> getProItemPictures(List<ProItemPictureDTO> pictureDTOS, Long id) {
		List<ProItemPicture> res = new ArrayList<>();
		for (ProItemPictureDTO proItemPicture : pictureDTOS) {
			ProItemPicture itemPicture = new ProItemPicture();
			itemPicture.setId(IdUtils.nextId());
			itemPicture.setItemId(id);
			itemPicture.setPicUrl(proItemPicture.getValue());
			itemPicture.setPicType(PictureTypeEnum.SWIPER.getValue());
			itemPicture.setPicSort(pictureDTOS.indexOf(proItemPicture));
			res.add(itemPicture);
		}
		return res;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateByEditBo(ProItemEditBo bo) {
		ProItem exist = baseMapper.selectById(bo.getId());
		Optional.ofNullable(exist).orElseThrow(() -> new BaseException("商品信息不存在!"));
		// 开始更新图片信息
		proItemPictureService.remove(Wrappers.<ProItemPicture>lambdaUpdate().eq(ProItemPicture::getItemId, exist.getId()));
		if (CollUtil.isNotEmpty(bo.getProItemPictures())) {
			List<ProItemPicture> pictures = getProItemPictures(bo.getProItemPictures(), bo.getId());
			proItemPictureService.saveBatch(pictures);
		}
		proItemCategoryService.remove(Wrappers.<ProItemCategory>lambdaUpdate().eq(ProItemCategory::getItemId, exist.getId()));
		if (CollUtil.isNotEmpty(bo.getProItemCategoryIds())) {
			List<ProItemCategory> proItemCategories = getProItemCategories(bo.getProItemCategoryIds(), bo.getId());
			proItemCategoryService.saveBatch(proItemCategories);
		}
		// 当库中未发布，参数中带着已发布，此时需要更新发布时间
		if ((Objects.equals(PublishFlagEnum.NO.getValue(), exist.getPublishFlag()) || Objects.isNull(exist.getPublishFlag()))
			&& Objects.equals(PublishFlagEnum.YES.getValue(), bo.getPublishFlag())) {
			bo.setPublishTime(new Date());
		}
		baseMapper.updateById(bo);
		return Boolean.TRUE;
	}

	@Override
	public ProItemVo getProItemById(Long id) {
		ProItem proItem = baseMapper.selectById(id);
		List<ProItemPicture> pictures = proItemPictureService.list(
			Wrappers.<ProItemPicture>lambdaQuery().eq(ProItemPicture::getItemId, id));
		List<ProItemCategory> categories = proItemCategoryService.list(
			Wrappers.<ProItemCategory>lambdaQuery().eq(ProItemCategory::getItemId, id));
		List<ProItemPictureDTO> pic = pictures.stream()
			.map(item -> ProItemPictureDTO.builder().url(item.getPicUrl()).value(item.getPicUrl()).build())
			.collect(Collectors.toList());
		List<String> categoryIds = categories.stream().map(item -> item.getCategoryId().toString()).collect(Collectors.toList());
		return proItem.toVo(pic, categoryIds);
	}

	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if(isValid){
			//TODO 做一些业务上的校验,判断是否需要校验
		}
		return removeByIds(ids);
	}

	@Override
	public TableDataInfo<ProItemVo> queryPageList(ProItemQueryBo proItemQueryBo) {
		PagePlus<ProItem, ProItemVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(proItemQueryBo), ProItemVo.class);
		return PageUtils.buildDataInfo(result);
	}

	@Override
	public List<ProItemVo> queryList(ProItemQueryBo bo) {
		return listVo(buildQueryWrapper(bo), ProItemVo.class);
	}

	private LambdaQueryWrapper<ProItem> buildQueryWrapper(ProItemQueryBo bo) {
//		Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<ProItem> lqw = Wrappers.lambdaQuery();
		lqw.like(StrUtil.isNotBlank(bo.getProName()), ProItem::getProName, bo.getProName());
		lqw.eq(bo.getSku() != null, ProItem::getSku, bo.getSku());
		lqw.eq(bo.getPublishFlag() != null, ProItem::getPublishFlag, bo.getPublishFlag());
//		lqw.eq(bo.getPublishTime() != null, ProItem::getPublishTime, bo.getPublishTime());
		return lqw;
	}
}
