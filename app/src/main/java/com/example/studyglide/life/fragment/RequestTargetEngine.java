package com.example.studyglide.life.fragment;

import android.util.Log;
import android.widget.ImageView;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 6:39 PM
 **/
public class RequestTargetEngine implements LifecycleCallback{
    private static final String TAG = RequestTargetEngine.class.getSimpleName();
    @Override
    public void glideInitAction() {
        Log.d(TAG,"Glide 的生命周期初始化开始了");
    }

    @Override
    public void glideStopAction() {
        Log.d(TAG,"Glide 的生命周期停止了");
    }

    @Override
    public void glideRecycleAction() {
        Log.d(TAG,"Glide 的生命周期销毁了，进行资源释放操作");
    }

    public void into(ImageView imageView) {

    }
}
