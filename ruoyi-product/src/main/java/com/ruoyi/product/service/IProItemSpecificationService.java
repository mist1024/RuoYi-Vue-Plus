package com.ruoyi.product.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.product.domain.ProItemSpecification;
import com.ruoyi.product.vo.ProItemSpecificationVo;
import com.ruoyi.product.bo.ProItemSpecificationQueryBo;
import com.ruoyi.product.bo.ProItemSpecificationAddBo;
import com.ruoyi.product.bo.ProItemSpecificationEditBo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * 商品规格Service接口
 *
 * @author ruoyi
 * @date 2021-04-06
 */
public interface IProItemSpecificationService extends IService<ProItemSpecification> {
	/**
	 * 查询单个
	 * @return
	 */
	ProItemSpecificationVo queryById(Long id);

	TableDataInfo<ProItemSpecificationVo> queryPageList(ProItemSpecificationQueryBo bo);
	/**
	 * 查询列表
	 */
	List<ProItemSpecificationVo> queryList(ProItemSpecificationQueryBo bo);

	/**
	 * 根据新增业务对象插入商品规格
	 * @param bo 商品规格新增业务对象
	 * @return
	 */
	Boolean insertByAddBo(ProItemSpecificationAddBo bo);

	/**
	 * 根据编辑业务对象修改商品规格
	 * @param bo 商品规格编辑业务对象
	 * @return
	 */
	Boolean updateByEditBo(ProItemSpecificationEditBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
