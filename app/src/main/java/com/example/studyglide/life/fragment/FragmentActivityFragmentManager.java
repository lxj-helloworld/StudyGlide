package com.example.studyglide.life.fragment;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 6:39 PM
 **/
public class FragmentActivityFragmentManager extends Fragment {
    public FragmentActivityFragmentManager() {
    }

    //生命周期监听接口
    public LifecycleCallback lifecycleCallback;


    @SuppressLint("ValidFragment")
    public FragmentActivityFragmentManager(LifecycleCallback callback) {
        this.lifecycleCallback = callback;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(null != lifecycleCallback){
            lifecycleCallback.glideInitAction();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(null != lifecycleCallback){
            lifecycleCallback.glideStopAction();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != lifecycleCallback){
            lifecycleCallback.glideRecycleAction();
        }
    }
}

