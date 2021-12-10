package com.wild.system.service;

import com.wild.common.core.mybatisplus.core.IServicePlus;
import com.wild.common.core.page.TableDataInfo;
import com.wild.system.domain.SysOss;
import com.wild.system.domain.bo.SysOssBo;
import com.wild.system.domain.vo.SysOssVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/**
 * 文件上传 服务层
 *
 * @author Lion Li
 */
public interface ISysOssService extends IServicePlus<SysOss, SysOssVo> {

    TableDataInfo<SysOssVo> queryPageList(SysOssBo sysOss);

    SysOss upload(MultipartFile file);

    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
