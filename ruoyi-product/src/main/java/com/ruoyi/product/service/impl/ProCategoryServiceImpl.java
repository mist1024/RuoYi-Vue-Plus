package com.ruoyi.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.product.bo.ProCategoryAddBo;
import com.ruoyi.product.bo.ProCategoryEditBo;
import com.ruoyi.product.bo.ProCategoryQueryBo;
import com.ruoyi.product.domain.ProCategory;
import com.ruoyi.product.mapper.ProCategoryMapper;
import com.ruoyi.product.service.IProCategoryService;
import com.ruoyi.product.vo.ProCategoryVo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 商品服务类别Service业务层处理
 *
 * @author ruoyi
 * @date 2021-03-14
 */
@Service
public class ProCategoryServiceImpl extends ServicePlusImpl<ProCategoryMapper, ProCategory> implements IProCategoryService {

	@Override
	public ProCategoryVo queryById(Long id) {
		return getVoById(id, ProCategoryVo.class);
	}

	@Override
	public TableDataInfo<ProCategoryVo> queryPageList(ProCategoryQueryBo bo) {
		PagePlus<ProCategory, ProCategoryVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), ProCategoryVo.class);
		return PageUtils.buildDataInfo(result);
	}

	@Override
	public List<ProCategoryVo> queryList(ProCategoryQueryBo bo) {
		return listVo(buildQueryWrapper(bo), ProCategoryVo.class);
	}

	private LambdaQueryWrapper<ProCategory> buildQueryWrapper(ProCategoryQueryBo bo) {
		Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<ProCategory> lqw = Wrappers.lambdaQuery();
		lqw.like(StrUtil.isNotBlank(bo.getCategoryName()), ProCategory::getCategoryName, bo.getCategoryName());
		lqw.eq(StrUtil.isNotBlank(bo.getIconUrl()), ProCategory::getIconUrl, bo.getIconUrl());
		return lqw;
	}

	@Override
	public Boolean insertByAddBo(ProCategoryAddBo bo) {
		ProCategory add = BeanUtil.toBean(bo, ProCategory.class);
		add.setId(IdUtils.nextId());
		validEntityBeforeSave(add);
		return save(add);
	}

	@Override
	public Boolean updateByEditBo(ProCategoryEditBo bo) {
		ProCategory update = BeanUtil.toBean(bo, ProCategory.class);
		validEntityBeforeSave(update);
		return updateById(update);
	}

	/**
	 * 保存前的数据校验
	 *
	 * @param entity 实体类数据
	 */
	private void validEntityBeforeSave(ProCategory entity) {
		//TODO 做一些数据校验,如唯一约束
	}

	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			//TODO 做一些业务上的校验,判断是否需要校验
		}
		return removeByIds(ids);
	}

}
