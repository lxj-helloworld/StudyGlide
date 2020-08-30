package com.example.studyglide.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import androidx.annotation.RequiresApi;

import com.example.studyglide.resource.Value;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 5:43 PM
 * LRU内存缓存
 * 回收机制，LRU最近最少使用算法
 **/
public class MemoryCache extends LruCache<String, Value> {
    private MemoryCacheCallback memoryCacheCallback;
    private boolean manualRemove;

    public MemoryCache(int maxSize) {
        super(maxSize);
    }

    /**
     * Author ： xiaojinli
     * Time ： 2020/8/30 5:45 PM
     * Describe： 手动移除
    **/
    public Value manualRemove(String key){
        manualRemove = true;//被动移除失效
        Value value = remove(key);
        manualRemove = false;//被动移除恢复
        return value;
    }

    /**
     * Author ： xiaojinli
     * Time ： 2020/8/30 5:46 PM
     * Describe：  当我们容器中存储的元素个数，大于设置的最大容量时，最近最近被使用的一个元素被移除
    **/
    @Override
    protected void entryRemoved(boolean evicted, String key, Value oldValue, Value newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        if(memoryCacheCallback != null && !manualRemove){
            //如果回调接口不为空，并且不是手动手动移除的
            memoryCacheCallback.entryRemoveMemoryCache(key,oldValue);
        }
    }


    /**
     * Author ： xiaojinli
     * Time ： 2020/8/30 5:50 PM
     * Describe： 每一个元素的大小
    **/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected int sizeOf(String key, Value value) {
        Bitmap bitmap = value.getmBitmap();
        //API 19 4.4
        int result = bitmap.getAllocationByteCount();
        return result;
    }
}
