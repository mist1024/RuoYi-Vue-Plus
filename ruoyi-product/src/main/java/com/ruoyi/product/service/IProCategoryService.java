package com.ruoyi.product.service;

import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.product.bo.ProCategoryAddBo;
import com.ruoyi.product.bo.ProCategoryEditBo;
import com.ruoyi.product.bo.ProCategoryQueryBo;
import com.ruoyi.product.domain.ProCategory;
import com.ruoyi.product.vo.ProCategoryVo;

import java.util.Collection;
import java.util.List;

/**
 * 商品服务类别Service接口
 *
 * @author ruoyi
 * @date 2021-07-01
 */
public interface IProCategoryService extends IServicePlus<ProCategory> {
	/**
	 * 查询单个
	 *
	 * @return
	 */
	ProCategoryVo queryById(Long id);

	/**
	 * 查询列表
	 */
	TableDataInfo<ProCategoryVo> queryPageList(ProCategoryQueryBo bo);

	/**
	 * 查询列表
	 */
	List<ProCategoryVo> queryList(ProCategoryQueryBo bo);

	/**
	 * 根据新增业务对象插入商品服务类别
	 *
	 * @param bo 商品服务类别新增业务对象
	 * @return
	 */
	Boolean insertByAddBo(ProCategoryAddBo bo);

	/**
	 * 根据编辑业务对象修改商品服务类别
	 *
	 * @param bo 商品服务类别编辑业务对象
	 * @return
	 */
	Boolean updateByEditBo(ProCategoryEditBo bo);

	/**
	 * 校验并删除数据
	 *
	 * @param ids     主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
