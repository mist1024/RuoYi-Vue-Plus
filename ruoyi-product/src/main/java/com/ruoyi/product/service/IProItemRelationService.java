package com.ruoyi.product.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.product.domain.ProItemRelation;
import com.ruoyi.product.vo.ProItemRelationVo;
import com.ruoyi.product.bo.ProItemRelationQueryBo;
import com.ruoyi.product.bo.ProItemRelationAddBo;
import com.ruoyi.product.bo.ProItemRelationEditBo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * 商品关系（预约记录、优惠券）Service接口
 *
 * @author ruoyi
 * @date 2021-04-09
 */
public interface IProItemRelationService extends IService<ProItemRelation> {
	/**
	 * 查询单个
	 * @return
	 */
	ProItemRelationVo queryById(Long id);

	TableDataInfo<ProItemRelationVo> queryPageList(ProItemRelationQueryBo bo);

	/**
	 * 查询列表
	 */
	List<ProItemRelationVo> queryList(ProItemRelationQueryBo bo);

	/**
	 * 根据新增业务对象插入商品关系（预约记录、优惠券）
	 * @param bo 商品关系（预约记录、优惠券）新增业务对象
	 * @return
	 */
	Boolean insertByAddBo(ProItemRelationAddBo bo);

	/**
	 * 根据编辑业务对象修改商品关系（预约记录、优惠券）
	 * @param bo 商品关系（预约记录、优惠券）编辑业务对象
	 * @return
	 */
	Boolean updateByEditBo(ProItemRelationEditBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
