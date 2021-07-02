package com.ruoyi.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.BaseException;
import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.product.bo.ProItemSpecificationAddBo;
import com.ruoyi.product.bo.ProItemSpecificationEditBo;
import com.ruoyi.product.bo.ProItemSpecificationQueryBo;
import com.ruoyi.product.domain.ProItemSpecification;
import com.ruoyi.product.enums.PublishFlagEnum;
import com.ruoyi.product.mapper.ProItemSpecificationMapper;
import com.ruoyi.product.service.IProItemSpecificationService;
import com.ruoyi.product.vo.ProItemSpecificationVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 商品规格Service业务层处理
 *
 * @author ruoyi
 * @date 2021-04-06
 */
@Service
public class ProItemSpecificationServiceImpl extends ServicePlusImpl<ProItemSpecificationMapper, ProItemSpecification> implements IProItemSpecificationService {

	@Override
	public ProItemSpecificationVo queryById(Long id) {
		return getVoById(id, ProItemSpecificationVo.class);
	}

	@Override
	public TableDataInfo<ProItemSpecificationVo> queryPageList(ProItemSpecificationQueryBo bo) {
		PagePlus<ProItemSpecification, ProItemSpecificationVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), ProItemSpecificationVo.class);
		return PageUtils.buildDataInfo(result);
	}

	@Override
	public List<ProItemSpecificationVo> queryList(ProItemSpecificationQueryBo bo) {
		return listVo(buildQueryWrapper(bo), ProItemSpecificationVo.class);
	}

	private LambdaQueryWrapper<ProItemSpecification> buildQueryWrapper(ProItemSpecificationQueryBo bo) {
		Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<ProItemSpecification> lqw = Wrappers.lambdaQuery();
		lqw.eq(bo.getItemId() != null, ProItemSpecification::getItemId, bo.getItemId());
		return lqw;
	}

	@Override
	public Boolean insertByAddBo(ProItemSpecificationAddBo bo) {
		ProItemSpecification add = BeanUtil.toBean(bo, ProItemSpecification.class);
		validEntityBeforeSave(add, Boolean.TRUE);
		add.setId(IdUtils.nextId());
		return this.save(add);
	}

	@Override
	public Boolean updateByEditBo(ProItemSpecificationEditBo bo) {
		ProItemSpecification update = BeanUtil.toBean(bo, ProItemSpecification.class);
		validEntityBeforeSave(update, Boolean.FALSE);
		return this.updateById(update);
	}

	/**
	 * 保存前的数据校验
	 *
	 * @param entity 实体类数据
	 */
	private void validEntityBeforeSave(ProItemSpecification entity, boolean beAdd) {
		if (beAdd && Objects.equals(PublishFlagEnum.YES.getValue(), entity.getPublishFlag())) {
			entity.setPublishTime(LocalDateTime.now());
		}
		if (!beAdd) {
			ProItemSpecification exist = baseMapper.selectById(entity.getId());
			Optional.ofNullable(exist).orElseThrow(() -> new BaseException("该规格不存在，请刷新重试！"));
			if (Objects.equals(PublishFlagEnum.YES.getValue(), entity.getPublishFlag())
				&& (Objects.isNull(exist.getPublishFlag()) || Objects.equals(PublishFlagEnum.NO.getValue(), exist.getPublishFlag()))) {
				entity.setPublishTime(LocalDateTime.now());
			}
		}
	}

	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			//TODO 做一些业务上的校验,判断是否需要校验
		}
		return this.removeByIds(ids);
	}
}
