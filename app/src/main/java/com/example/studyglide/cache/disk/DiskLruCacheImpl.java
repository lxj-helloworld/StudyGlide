package com.example.studyglide.cache.disk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.studyglide.resource.Tool;
import com.example.studyglide.resource.Value;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 5:59 PM
 * 磁盘缓存的具体实现
 *
 * 关键方法依然是put和get方法
 *
 **/
public class DiskLruCacheImpl {
    private static final String TAG = DiskLruCacheImpl.class.getSimpleName();

    //磁盘缓存的目录
    private final String DISKLRU_CACHE_DIR = "disklru_cache_dir";

    private final int APP_VERSION = 1;//版本号，一旦修改，之前的缓存会失效
    private final int VALUE_COUNT = 1;//
    private final long MAX_SIZE = 1024 * 1024 * 10;

    private DiskLruCache diskLruCache;

    public DiskLruCacheImpl() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + DISKLRU_CACHE_DIR);
        try {
            diskLruCache = DiskLruCache.open(file,APP_VERSION,VALUE_COUNT,MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Author ： xiaojinli
     * Time ： 2020/8/30 6:04 PM
     * Describe： 添加一个缓存
    **/
    public void put(String key, Value value){
        Tool.checkNotEmpty(key);

        DiskLruCache.Editor editor = null;
        OutputStream outputStream = null;

        try {
            editor = diskLruCache.edit(key);
            outputStream = editor.newOutputStream(0);//参数不能大于VALUE_COUNT
            Bitmap bitmap = value.getmBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);//把Bitmap写入输出流
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //失败
            try {
                editor.abort();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.e(TAG,"添加失败，editor.abort异常" + ex.getMessage());
            }
        }finally {
            try {
                editor.commit(); //必须提交
                diskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG,"添加失败，editor.commit异常" + e.getMessage());
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG,"添加失败，outputStream.close异常" + e.getMessage());
                }
            }
        }
    }


    /**
     * Author ： xiaojinli
     * Time ： 2020/8/30 6:10 PM
     * Describe： 读取一个缓存
    **/
    public Value get(String key){
        Tool.checkNotEmpty(key);
        InputStream inputStream = null;
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            //如果快照不为空，去读取
            if(null != snapshot){
                Value value = Value.getInstance();
                inputStream = snapshot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                value.setmBitmap(bitmap);
                //保存key
                value.setKey(key);
                return value;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG,"读取失败，inputStream.close异常" + e.getMessage());
                }
            }
        }
        return null;
    }

}
