package com.ruoyi.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.product.bo.ProBannerAddBo;
import com.ruoyi.product.bo.ProBannerEditBo;
import com.ruoyi.product.bo.ProBannerQueryBo;
import com.ruoyi.product.bo.ProItemRelationQueryBo;
import com.ruoyi.product.domain.ProBanner;
import com.ruoyi.product.mapper.ProBannerMapper;
import com.ruoyi.product.service.IProBannerService;
import com.ruoyi.product.vo.ProBannerVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * banner管理Service业务层处理
 *
 * @author ruoyi
 * @date 2021-03-14
 */
@Service
public class ProBannerServiceImpl extends ServicePlusImpl<ProBannerMapper, ProBanner> implements IProBannerService {


	@Override
	public ProBannerVo queryById(Long id){
		return getVoById(id, ProBannerVo.class);
	}

	@Override
	public TableDataInfo<ProBannerVo> queryPageList(ProBannerQueryBo bo) {
		PagePlus<ProBanner, ProBannerVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), ProBannerVo.class);
		return PageUtils.buildDataInfo(result);
	}

	@Override
	public List<ProBannerVo> queryList(ProBannerQueryBo bo) {
		return listVo(buildQueryWrapper(bo), ProBannerVo.class);
	}

	private LambdaQueryWrapper<ProBanner> buildQueryWrapper(ProBannerQueryBo bo) {
		Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<ProBanner> lqw = Wrappers.lambdaQuery();
		lqw.like(StrUtil.isNotBlank(bo.getTitle()), ProBanner::getTitle, bo.getTitle());
		lqw.eq(StrUtil.isNotBlank(bo.getUrl()), ProBanner::getUrl, bo.getUrl());
		lqw.eq(bo.getRedirectFlag() != null, ProBanner::getRedirectFlag, bo.getRedirectFlag());
		lqw.eq(bo.getBannerType() != null, ProBanner::getBannerType, bo.getBannerType());
		return lqw;
	}

	@Override
	public Boolean insertByAddBo(ProBannerAddBo bo) {
		ProBanner add = BeanUtil.toBean(bo, ProBanner.class);
		add.setId(IdUtils.nextId());
		validEntityBeforeSave(add);
		return save(add);
	}

	@Override
	public Boolean updateByEditBo(ProBannerEditBo bo) {
		ProBanner update = BeanUtil.toBean(bo, ProBanner.class);
		validEntityBeforeSave(update);
		return updateById(update);
	}

	/**
	 * 保存前的数据校验
	 *
	 * @param entity 实体类数据
	 */
	private void validEntityBeforeSave(ProBanner entity){
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
