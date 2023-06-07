package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.RsaSecurity;
import com.ruoyi.system.domain.vo.RsaSecurityVo;
import com.ruoyi.system.domain.bo.RsaSecurityBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 请求RSA数据加解密Service接口
 *
 * @author ruoyi
 * @date 2023-05-17
 */
public interface IRsaSecurityService {

    /**
     * 查询请求RSA数据加解密
     */
    RsaSecurityVo queryById(Long id);

    /**
     * 查询请求RSA数据加解密列表
     */
    TableDataInfo<RsaSecurityVo> queryPageList(RsaSecurityBo bo, PageQuery pageQuery);

    /**
     * 查询请求RSA数据加解密列表
     */
    List<RsaSecurityVo> queryList(RsaSecurityBo bo);

    /**
     * 新增请求RSA数据加解密
     */
    RsaSecurity insertByBo(RsaSecurityBo bo);

    /**
     * 修改请求RSA数据加解密
     */
    RsaSecurity updateByBo(RsaSecurityBo bo);

    /**
     * 校验并批量删除请求RSA数据加解密信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 初始化获取加解密的接口
     */
   void loadingRsaSecurityCache();
}
