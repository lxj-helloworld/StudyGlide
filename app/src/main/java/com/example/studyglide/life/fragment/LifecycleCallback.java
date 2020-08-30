package com.example.studyglide.life.fragment;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 6:30 PM
 *  Fragment 生命周期监听
 **/
public interface LifecycleCallback {
    //生命周期开始初始化
    public void glideInitAction();
    //生命周期停止了
    public void glideStopAction();
    //生命周期 释放操作
    public void glideRecycleAction();
}
