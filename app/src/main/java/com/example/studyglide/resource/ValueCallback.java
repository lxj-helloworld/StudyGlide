package com.example.studyglide.resource;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 5:01 PM
 * 当Value中的图片不再使用时的回调接口
 **/
public interface ValueCallback {
    //当Value中的图片不再使用的时候回调该方法
    void valueNonUseListener(String key,Value value);
}
