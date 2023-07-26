package com.ruoyi.web.controller.common;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.file.service.ISysFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件请求处理
 *
 * @author ruoyi*/


@RestController
public class SysFileController
{
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Autowired
    private ISysFileService sysFileService;

 /*文件上传请求*/


    @PostMapping("upload")
    public R<Map> upload(MultipartFile file)
    {
        try{
            // 上传并返回访问地址
            String url = sysFileService.uploadFile(file);
            Map<String, String> map = new HashMap<>(2);
            map.put("url", url);
            map.put("fileName", FileUtils.getName(url));
            return R.ok(map);
        }
        catch (Exception e)
        {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
    }
}
