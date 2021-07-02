package com.ruoyi.product.bo;

import com.ruoyi.product.domain.ProItem;
import com.ruoyi.product.dto.ProItemPictureDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品详情编辑对象 pro_item
 *
 * @author ruoyi
 * @date 2021-07-01
 */
@Data
@ApiModel("商品详情编辑对象")
public class ProItemEditBo extends ProItem {


	@ApiModelProperty("图片列表")
	private List<ProItemPictureDTO> proItemPictures;

	@ApiModelProperty("分类id集合")
	private List<String> proItemCategoryIds;
}
