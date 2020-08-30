package com.example.studyglide.life;

import android.content.Context;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 7:00 PM
 **/
public class GlideBuilder {
    public GlideBuilder(Context context) {
    }

    public Glide build(){
        RequestManagerRetriver retriver = new RequestManagerRetriver();
        Glide glide = new Glide(retriver);
        return glide;
    }
}
