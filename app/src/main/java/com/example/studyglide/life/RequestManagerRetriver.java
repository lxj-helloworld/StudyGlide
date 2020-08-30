package com.example.studyglide.life;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 6:56 PM
 * 管理RequestManager
 **/
public class RequestManagerRetriver {
    public RequestManager get(FragmentActivity fragmentActivity){
        return new RequestManager(fragmentActivity);
    }

    public RequestManager get(Activity activity){
        return new RequestManager(activity);
    }

    public RequestManager get(Context context){
        return new RequestManager(context);
    }
}
