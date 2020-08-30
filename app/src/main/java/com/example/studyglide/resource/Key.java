package com.example.studyglide.resource;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 5:00 PM
 * 图片资源封装在一个map中，该类定义value对应的key值
 **/
public class Key {
    private String key; //通常使用图片的地址作为键值，但是地址中通常存在一些特殊符号，所以对其进行MD5加密

    public Key(String key){
        this.key = Tool.getSHA256StrJava(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
