package com.example.studyglide.cache;

import com.example.studyglide.resource.Value;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 5:42 PM
 *  内存缓存中，LUR移除时的回调接口
 **/
public interface MemoryCacheCallback {
    /**
     * Author ： xiaojinli
     * Time ： 2020/8/30 5:43 PM
     * Describe：  从内存缓存中移除图片资源时回调该方法
    **/
    void entryRemoveMemoryCache(String key, Value value);
}
