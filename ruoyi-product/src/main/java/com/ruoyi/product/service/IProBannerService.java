package com.ruoyi.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.product.bo.ProBannerAddBo;
import com.ruoyi.product.bo.ProBannerEditBo;
import com.ruoyi.product.bo.ProBannerQueryBo;
import com.ruoyi.product.bo.ProItemRelationQueryBo;
import com.ruoyi.product.domain.ProBanner;
import com.ruoyi.product.vo.ProBannerVo;
import com.ruoyi.product.vo.ProItemRelationVo;

import java.util.Collection;
import java.util.List;

/**
 * banner管理Service接口
 *
 * @author ruoyi
 * @date 2021-03-14
 */
public interface IProBannerService extends IServicePlus<ProBanner> {
	/**
	 * 查询单个
	 *
	 * @return
	 */
	ProBannerVo queryById(Long id);

	/**
	 * 查询列表
	 */
	TableDataInfo<ProBannerVo> queryPageList(ProBannerQueryBo bo);

	/**
	 * 查询列表
	 */
	List<ProBannerVo> queryList(ProBannerQueryBo bo);

	/**
	 * 根据新增业务对象插入banner管理
	 *
	 * @param bo banner管理新增业务对象
	 * @return
	 */
	Boolean insertByAddBo(ProBannerAddBo bo);

	/**
	 * 根据编辑业务对象修改banner管理
	 *
	 * @param bo banner管理编辑业务对象
	 * @return
	 */
	Boolean updateByEditBo(ProBannerEditBo bo);

	/**
	 * 校验并删除数据
	 *
	 * @param ids     主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
