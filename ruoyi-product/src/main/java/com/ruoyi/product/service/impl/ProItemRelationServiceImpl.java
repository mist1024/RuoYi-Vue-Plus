package com.ruoyi.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.product.bo.ProItemRelationAddBo;
import com.ruoyi.product.bo.ProItemRelationEditBo;
import com.ruoyi.product.bo.ProItemRelationQueryBo;
import com.ruoyi.product.domain.ProItemRelation;
import com.ruoyi.product.mapper.ProItemRelationMapper;
import com.ruoyi.product.service.IProItemRelationService;
import com.ruoyi.product.vo.ProItemRelationVo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 商品关系（预约记录、优惠券）Service业务层处理
 *
 * @author ruoyi
 * @date 2021-04-09
 */
@Service
public class ProItemRelationServiceImpl extends ServicePlusImpl<ProItemRelationMapper, ProItemRelation> implements IProItemRelationService {

	@Override
	public ProItemRelationVo queryById(Long id) {
		ProItemRelation db = this.baseMapper.selectById(id);
		return BeanUtil.toBean(db, ProItemRelationVo.class);
	}

	@Override
	public TableDataInfo<ProItemRelationVo> queryPageList(ProItemRelationQueryBo bo) {
		PagePlus<ProItemRelation, ProItemRelationVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), ProItemRelationVo.class);
		return PageUtils.buildDataInfo(result);
	}

	@Override
	public List<ProItemRelationVo> queryList(ProItemRelationQueryBo bo) {
		return listVo(buildQueryWrapper(bo), ProItemRelationVo.class);
	}

	private LambdaQueryWrapper<ProItemRelation> buildQueryWrapper(ProItemRelationQueryBo bo) {
		Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<ProItemRelation> lqw = Wrappers.lambdaQuery();
		lqw.eq(bo.getBusinessId() != null, ProItemRelation::getBusinessId, bo.getBusinessId());
		lqw.eq(StrUtil.isNotBlank(bo.getType()), ProItemRelation::getType, bo.getType());
		lqw.eq(bo.getProItemId() != null, ProItemRelation::getProItemId, bo.getProItemId());
		return lqw;
	}


	@Override
	public Boolean insertByAddBo(ProItemRelationAddBo bo) {
		ProItemRelation add = BeanUtil.toBean(bo, ProItemRelation.class);
		validEntityBeforeSave(add);
		return this.save(add);
	}

	@Override
	public Boolean updateByEditBo(ProItemRelationEditBo bo) {
		ProItemRelation update = BeanUtil.toBean(bo, ProItemRelation.class);
		validEntityBeforeSave(update);
		return this.updateById(update);
	}

	/**
	 * 保存前的数据校验
	 *
	 * @param entity 实体类数据
	 */
	private void validEntityBeforeSave(ProItemRelation entity) {
		//TODO 做一些数据校验,如唯一约束
	}

	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			//TODO 做一些业务上的校验,判断是否需要校验
		}
		return this.removeByIds(ids);
	}
}
