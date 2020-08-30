package com.example.studyglide.life;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.studyglide.life.fragment.ActivityFragmentManager;
import com.example.studyglide.life.fragment.FragmentActivityFragmentManager;
import com.example.studyglide.life.fragment.LifecycleCallback;
import com.example.studyglide.life.fragment.RequestTargetEngine;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 6:42 PM
 * 生命周期管理
 **/
public class RequestManager {
    private static final String TAG = RequestManager.class.getSimpleName();

    private final String FRAGMENT_ACTIVITY_NAME = "Fragment_Activity_NAME";
    private final String ACTIVITY_NAME = "Activity_NAME";

    //当前上下文
    private Context mContext;
    private static LifecycleCallback callback;
    private final int NEXT_HANDLER_MSG = 995465; //handler标记

    //构造代码块，不用在每个构造函数中去写了
    {
        if(callback == null){
            callback = new RequestTargetEngine();
        }
    }

    /**
     * Author ： xiaojinli
     * Time ： 2020/8/30 6:52 PM
     * Describe：管理FragmentActivity 生命周期
    **/
    FragmentActivity fragmentActivity;
    public RequestManager(FragmentActivity fragmentActivity){
        this.fragmentActivity = fragmentActivity;
        mContext = fragmentActivity;
        //开始绑定操作
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        //拿到Fragment
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);

        if(null == fragment){
            fragment = new FragmentActivityFragmentManager(callback);
            //添加到管理器中并提交
            fragmentManager.beginTransaction().add(fragment,FRAGMENT_ACTIVITY_NAME).commitAllowingStateLoss();
        }

        //添加进去提交之后，可能还处于排队状态，发送一个Handler
        mHandler.sendEmptyMessage(NEXT_HANDLER_MSG);
    }


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Fragment fragment = fragmentActivity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
            return false;
        }
    });


    public RequestManager(Activity activity){
        this.mContext = activity;
        //开始绑定操作
        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
        //拿到Fragment
        android.app.Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        if(fragment == null){
            fragment = new ActivityFragmentManager(callback);
            //添加到管理器
            fragmentManager.beginTransaction().add(fragment,FRAGMENT_ACTIVITY_NAME).commitAllowingStateLoss();
        }
        //添加进去提交之后，可能还处于排队状态，发送一个Handler
        mHandler.sendEmptyMessage(NEXT_HANDLER_MSG);
    }

    public RequestManager(Context mContext) {
        this.mContext = mContext;
    }


    public RequestTargetEngine load(String s){
        mHandler.removeMessages(NEXT_HANDLER_MSG);

        return (RequestTargetEngine)callback;
    }
}
