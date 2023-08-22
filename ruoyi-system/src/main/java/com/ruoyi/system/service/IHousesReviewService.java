package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.HousesReview;
import com.ruoyi.system.domain.bo.HousesReviewBo;
import com.ruoyi.system.domain.dto.HousesReviewEvent;
import com.ruoyi.system.domain.vo.HousesReviewVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 购房复审登记Service接口
 *
 * @author ruoyi
 * @date 2023-03-08
 */
public interface IHousesReviewService {

    /**
     * 查询购房复审登记
     */
    HousesReviewVo queryById(Long id);

    /**
     * 查询购房复审登记列表
     */
    TableDataInfo<HousesReview> queryPageList(HousesReviewBo bo, PageQuery pageQuery);

    /**
     * 查询购房复审登记列表
     */
    List<HousesReviewVo> queryList(HousesReviewBo bo);

    /**
     * 新增购房复审登记
     */
    Boolean insertByBo(HousesReviewBo bo);

    /**
     * 修改购房复审登记
     */
    Boolean updateByBo(HousesReviewBo bo);

    /**
     * 校验并批量删除购房复审登记信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Boolean saveBatch(List<HousesReview> list);

    R<?> getMaterialInfo(HousesReviewBo bo);

    HousesReviewVo queryByIdOne(Long id);

    R<?> getMaterialByBusinessId(Long id);

    /**
     * 预约导出
     * @param bo
     * @return
     */
    R subscribeExport(HousesReviewEvent bo) throws IOException;

    /**
     *
     * @param bo
     */
    void exportExcel(HousesReviewBo bo, HttpServletResponse response);

    TableDataInfo<HousesReview> managerQueryPageList(HousesReviewBo bo, PageQuery pageQuery);

    List<HousesReview> selectListByCardList(List<String> collect, Set<String> projectName);
}
