package org.dromara.system.service;

import org.dromara.common.core.domain.dto.UserOnlineDTO;
import org.dromara.system.domain.bo.SysOnlineUserBo;

import java.util.List;

/**
 * @Author warden
 */
public interface ISysUserOnlineService {
    List<UserOnlineDTO> getOnlineUsers(String tenantId, SysOnlineUserBo userBo);
}
