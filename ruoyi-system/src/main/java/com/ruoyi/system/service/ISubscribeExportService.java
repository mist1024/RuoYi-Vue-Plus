package com.ruoyi.system.service;

import com.ruoyi.system.domain.SubscribeExport;
import com.ruoyi.system.domain.vo.SubscribeExportVo;
import com.ruoyi.system.domain.bo.SubscribeExportBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 预约导出Service接口
 *
 * @author ruoyi
 * @date 2023-04-20
 */
public interface ISubscribeExportService {

    /**
     * 查询预约导出
     */
    SubscribeExportVo queryById(Long id);

    /**
     * 查询预约导出列表
     */
    TableDataInfo<SubscribeExportVo> queryPageList(SubscribeExportBo bo, PageQuery pageQuery);

    /**
     * 查询预约导出列表
     */
    List<SubscribeExportVo> queryList(SubscribeExportBo bo);

    /**
     * 新增预约导出
     */
    Boolean insertByBo(SubscribeExportBo bo);

    /**
     * 修改预约导出
     */
    Boolean updateByBo(SubscribeExportBo bo);

    /**
     * 校验并批量删除预约导出信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
