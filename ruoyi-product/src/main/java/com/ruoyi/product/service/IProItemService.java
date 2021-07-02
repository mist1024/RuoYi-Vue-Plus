package com.ruoyi.product.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.product.bo.ProItemAddBo;
import com.ruoyi.product.bo.ProItemEditBo;
import com.ruoyi.product.bo.ProItemQueryBo;
import com.ruoyi.product.domain.ProItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.product.vo.ProItemVo;

import java.util.Collection;
import java.util.List;

/**
 * 商品详情Service接口
 *
 * @author ruoyi
 * @date 2021-03-13
 */
public interface IProItemService extends IService<ProItem> {

	/**
	 * 查询列表
	 */
	TableDataInfo<ProItemVo> queryPageList(ProItemQueryBo bo);

	/**
	 * 查询列表
	 */
	List<ProItemVo> queryList(ProItemQueryBo bo);

    /**
     * 新增商品
     * @param bo
     * @return
     */
    boolean insertByAddBo(ProItemAddBo bo);

    /**
     * 根据id更新商品详情
     * @param bo
     * @return
     */
    boolean updateByEditBo(ProItemEditBo bo);

    /**
     * 根据id获取详情
     * @param id
     * @return
     */
    ProItemVo getProItemById(Long id);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
