package org.dromara.common.oss.utils;

import cn.hutool.core.collection.CollUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.oss.constant.OssConstant;
import org.dromara.common.oss.core.OssClient;
import org.dromara.common.oss.entity.PartUploadInfo;
import org.dromara.common.oss.factory.OssFactory;
import org.dromara.common.redis.utils.RedisUtils;

import java.util.Collection;

/**
 * 分片上传缓存助手
 *
 * @author SunnyDeer0911
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PartUploadCacheHelper {

    /**
     * 获取分片上传信息cacheKey
     * @param uploadId 文件分片上传ID
     * @return 分片上传信息cacheKey
     */
    public static String getPartUploadInfoCacheKey(String uploadId){
        if (StringUtils.isBlank(uploadId)) {
            return null;
        }
        return OssConstant.PART_UPLOAD_INFO_CACHE_KEY + uploadId;
    }

    /**
     * 从缓存中获取分片上传信息对象
     * @param uploadId 文件分片上传ID
     * @return 获取分片上传信息对象
     */
    public static PartUploadInfo getCache(String uploadId){
        String cacheKey = getPartUploadInfoCacheKey(uploadId);
        if (cacheKey==null) {
            return null;
        }
        return RedisUtils.getCacheObject(cacheKey);
    }

    /**
     * 将分片上传信息对象放入缓存
     * @param partUploadInfo 分片上传信息对象
     */
    public static void putCache(PartUploadInfo partUploadInfo){
        String cacheKey = getPartUploadInfoCacheKey(partUploadInfo.getUploadId());
        if (cacheKey==null) {
            return;
        }
        RedisUtils.setCacheObject(cacheKey, partUploadInfo);
    }

    /**
     * 移除分片上传信息对象缓存
     * @param uploadId 文件分片上传ID
     */
    public static void removeCache(String uploadId){
        String cacheKey = getPartUploadInfoCacheKey(uploadId);
        if (cacheKey==null) {
            return;
        }
        removeCacheByKey(cacheKey);
    }

    /**
     * 移除分片上传信息对象缓存
     * @param cacheKey 缓存key
     */
    private static void removeCacheByKey(String cacheKey){
        PartUploadInfo cache = RedisUtils.getCacheObject(cacheKey);
        if (cache==null) {
            return;
        }
        // 如果未合并，啧调用OssClient终止分片上传任务的方法，让OSS删除已上传的分片
        if (!cache.isNeedMerge() && CollUtil.isNotEmpty(cache.getPartInfoList())) {
            OssClient instance = OssFactory.instance(cache.getService());
            // 终止分片上传任务
            instance.abortPartUpload(cache.getUploadId(),cache.getFileName());
        }
        RedisUtils.deleteObject(cacheKey);
    }

    /**
     * 移除所有分片上传信息对象缓存
     */
    public static void removeAllCache(){
        Collection<String> removeKeys = RedisUtils.keys(OssConstant.PART_UPLOAD_INFO_CACHE_KEY + "*");
        if (CollUtil.isEmpty(removeKeys)) {
            return;
        }
        removeKeys.forEach(PartUploadCacheHelper::removeCacheByKey);
    }

}
