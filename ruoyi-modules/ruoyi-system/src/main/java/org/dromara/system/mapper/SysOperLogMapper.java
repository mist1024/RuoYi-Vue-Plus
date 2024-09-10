package org.dromara.system.mapper;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.system.domain.SysOperLog;
import org.dromara.system.domain.bo.SysOperLogBo;
import org.dromara.system.domain.vo.SysOperLogVo;

import java.util.Arrays;
import java.util.Map;

/**
 * 操作日志 数据层
 *
 * @author Lion Li
 */
public interface SysOperLogMapper extends BaseMapperPlus<SysOperLog, SysOperLogVo> {

    default LambdaQueryWrapper<SysOperLog> buildQuery(SysOperLogBo bo) {
        Map<String, Object> params = bo.getParams();
        return new LambdaQueryWrapper<SysOperLog>()
            .like(StringUtils.isNotBlank(bo.getOperIp()), SysOperLog::getOperIp, bo.getOperIp())
            .like(StringUtils.isNotBlank(bo.getTitle()), SysOperLog::getTitle, bo.getTitle())
            .eq(bo.getBusinessType() != null && bo.getBusinessType() > 0,
                SysOperLog::getBusinessType, bo.getBusinessType())
            .func(f -> {
                if (ArrayUtil.isNotEmpty(bo.getBusinessTypes())) {
                    f.in(SysOperLog::getBusinessType, Arrays.asList(bo.getBusinessTypes()));
                }
            })
            .eq(bo.getStatus() != null, SysOperLog::getStatus, bo.getStatus())
            .like(StringUtils.isNotBlank(bo.getOperName()), SysOperLog::getOperName, bo.getOperName())
            .between(params.get("beginTime") != null && params.get("endTime") != null,
                SysOperLog::getOperTime, params.get("beginTime"), params.get("endTime"));
    }
}
