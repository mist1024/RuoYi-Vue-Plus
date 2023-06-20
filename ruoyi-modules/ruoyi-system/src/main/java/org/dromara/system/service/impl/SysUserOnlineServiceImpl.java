package org.dromara.system.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import org.dromara.common.core.constant.CacheConstants;
import org.dromara.common.core.domain.dto.UserOnlineDTO;
import org.dromara.common.core.utils.StreamUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.tenant.handle.TenantKeyPrefixHandler;
import org.dromara.system.domain.bo.SysOnlineUserBo;
import org.dromara.system.service.ISysUserOnlineService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserOnlineServiceImpl implements ISysUserOnlineService {

    @Override
    public List<UserOnlineDTO> getOnlineUsers(String tenantId, SysOnlineUserBo userBo) {
        String ipaddr = userBo == null ? null : userBo.getIpaddr();
        String userName = userBo == null ? null : userBo.getUserName();
        // 获取所有未过期的 token
        List<String> keys = StpUtil.searchTokenValue("", 0, -1, false);
        List<UserOnlineDTO> userOnlineDTOList = new ArrayList<>();
        for (String key : keys) {
            String token = StringUtils.substringAfterLast(key, ":");
            // 如果已经过期则跳过
            if (StpUtil.stpLogic.getTokenActivityTimeoutByToken(token) < -1) {
                continue;
            }

            TenantKeyPrefixHandler.setTenantId(tenantId);
            UserOnlineDTO online = RedisUtils.getCacheObject(CacheConstants.ONLINE_TOKEN_KEY + token);

            if (online != null) {
                userOnlineDTOList.add(online);
            }
        }
//        // 清空为空的数据
//        userOnlineDTOList.removeAll(Collections.singleton(null));
        if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
            userOnlineDTOList = StreamUtils.filter(userOnlineDTOList, userOnline ->
                StringUtils.equals(ipaddr, userOnline.getIpaddr()) &&
                    StringUtils.equals(userName, userOnline.getUserName())
            );
        } else if (StringUtils.isNotEmpty(ipaddr)) {
            userOnlineDTOList = StreamUtils.filter(userOnlineDTOList, userOnline ->
                StringUtils.equals(ipaddr, userOnline.getIpaddr())
            );
        } else if (StringUtils.isNotEmpty(userName)) {
            userOnlineDTOList = StreamUtils.filter(userOnlineDTOList, userOnline ->
                StringUtils.equals(userName, userOnline.getUserName())
            );
        }

        return userOnlineDTOList;
    }
}
