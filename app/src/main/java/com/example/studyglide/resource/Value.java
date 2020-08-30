package com.example.studyglide.resource;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 5:00 PM
 * 对图片资源即BitMap 的封装
 **/
public class Value {
    private final String TAG = Value.class.getSimpleName();

    //单例模式
    private static Value value;

    //生成对象
    public static Value getInstance(){
        if(value == null){
            synchronized (Value.class){
                if(value == null){
                    value = new Value();
                }
            }
        }
        return value;
    }


    private Bitmap mBitmap; //封装的图片
    private int count = 0; //图片使用的次数
    private ValueCallback mValueCallBack;//当图片资源不再被程序使用时的回调接口
    private String key; //key唯一标记图片资源

    /**
     * 使用一次图片
     */
    public void useAction(){
        //检查图片是否为空，如果为空，抛出异常
        Tool.checkNotEmpty(mBitmap);
        //检查图片是否被回收
        if(mBitmap.isRecycled()){
            Log.d(TAG,"图片已经被回收");
            return;
        }
        count++;
        Log.d(TAG,"图片被使用，count加一");
    }


    /**
     * 不使用图片一次
     */
    public void nonUseAction(){
        Log.d(TAG,"图片没有被使用一次，对count进行减一操作");
        count--;
        if(count <= 0){ //图片目前已经没有被使用
            Log.d(TAG,"回调图片没有被使用的接口");
            mValueCallBack.valueNonUseListener(key,value);
        }
    }

    /**
     * 释放图片
     */
    public void recycleBitmap(){
        if(count > 0){ //表示图片正在被使用，不能释放
            Log.d(TAG,"资源正在被使用，不能释放该资源");
            return;
        }
        if(mBitmap.isRecycled()){
            Log.d(TAG,"资源已经被释放，不需要重复释放");
            return;
        }

        mBitmap.recycle();
        value = null;
        System.gc();
    }

    public static Value getValue() {
        return value;
    }

    public static void setValue(Value value) {
        Value.value = value;
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ValueCallback getmValueCallBack() {
        return mValueCallBack;
    }

    public void setmValueCallBack(ValueCallback mValueCallBack) {
        this.mValueCallBack = mValueCallBack;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
