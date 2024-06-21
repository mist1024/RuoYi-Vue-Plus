package org.dromara.liteflow.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.liteflow.domain.bo.LiteflowScriptBo;
import org.dromara.liteflow.domain.vo.LiteflowScriptVo;

import java.util.Collection;
import java.util.List;

/**
 * 脚本Service接口
 *
 * @author AprilWind
 * @date 2024-06-21
 */
public interface ILiteflowScriptService {

    /**
     * 查询脚本
     *
     * @param id 主键
     * @return 脚本
     */
    LiteflowScriptVo queryById(Long id);

    /**
     * 分页查询脚本列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 脚本分页列表
     */
    TableDataInfo<LiteflowScriptVo> queryPageList(LiteflowScriptBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的脚本列表
     *
     * @param bo 查询条件
     * @return 脚本列表
     */
    List<LiteflowScriptVo> queryList(LiteflowScriptBo bo);

    /**
     * 校验脚本ID是否唯一
     *
     * @param script 脚本信息
     * @return 结果，true表示唯一，false表示不唯一
     */
    boolean checkScriptIdUnique(LiteflowScriptBo script);

    /**
     * 新增脚本
     *
     * @param bo 脚本
     * @return 是否新增成功
     */
    Boolean insertByBo(LiteflowScriptBo bo);

    /**
     * 修改脚本
     *
     * @param bo 脚本
     * @return 是否修改成功
     */
    Boolean updateByBo(LiteflowScriptBo bo);

    /**
     * 校验并批量删除脚本信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
