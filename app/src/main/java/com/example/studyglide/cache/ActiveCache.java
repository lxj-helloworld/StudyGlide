package com.example.studyglide.cache;

import com.example.studyglide.resource.Tool;
import com.example.studyglide.resource.Value;
import com.example.studyglide.resource.ValueCallback;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称 StudyGlide
 * 创建人 xiaojinli
 * 创建时间 2020/8/30 5:15 PM
 * 活动缓存，存储APP正在被使用的图片资源，是Glide中的第一级缓存，使用弱引用的方式存储
 *
 * 关键方法： 添加一个资源、得到一个资源、移除一个资源
 * 关键技术：弱引用
 *
 **/
public class ActiveCache {
    //容器
    private Map<String, WeakReference<Value>> map = new HashMap<>();
    private ReferenceQueue<Value> queue;//弱引用监听
    private Thread mThread; //线程，死循环
    private boolean isCloseThread;// 是否关闭线程
    private boolean isManualRemove;//是否手动移除资源

    private ValueCallback mValueCallback;

    public ActiveCache(ValueCallback mValueCallback) {
        this.mValueCallback = mValueCallback;
    }

    /**
     * Author ： xiaojinli
     * time ： 2020/8/30 5:26 PM
     * 添加一个资源缓存
    **/
    public void put(String key,Value value){
        ///检查键值是否为空，如果为空，抛出异常
        Tool.checkNotEmpty(key);
        //为Value设置监听
        value.setmValueCallBack(mValueCallback);
        //真正的存储操作
        map.put(key,new CustomWeakReference(value,getQueue(),key));
    }

    /**
     * Author ： xiaojinli
     * Time ： 2020/8/30 5:39 PM
     * Describe：手动移除
    **/
    public Value manualRemove(String key){
        isManualRemove = true;//禁止被动移除
        WeakReference<Value> remove = map.get(key);//移除操作
        isManualRemove = false;// 恢复被动移除
        if(null != remove){
            return remove.get();
        }
        return null;
    }


    /**
     * Author ： xiaojinli
     * Time ： 2020/8/30 5:41 PM
     * Describe：关闭线程
    **/

    public void closeThread(){
        isCloseThread = true;
        map.clear();
        System.gc();
    }

    /**
     * Author ： xiaojinli
     * Time ： 2020/8/30 5:30 PM
     * Describe：  弱引用管理器，监听弱引用什么时候被回收了
    **/
    public class CustomWeakReference extends WeakReference<Value>{
        private String key;
        public CustomWeakReference(Value referent, ReferenceQueue<? super Value> q,String key) {
            super(referent, q);
            this.key = key;
        }
    }


    private ReferenceQueue<Value> getQueue(){
        if(queue == null){
            queue = new ReferenceQueue<>();
            //死循环不停的跑
            mThread = new Thread(){
                @Override
                public void run() {
                    super.run();
                    while (!isCloseThread){//线程中循环何时结束，即线程何时结束
                        try {
                            //阻塞式，等待什么时候被回收，就释放
                            Reference<? extends Value> remove = queue.remove();
                            //如果当前处于手动移除，则表示不需要当前的被动移除
                            if(!isManualRemove){
                                CustomWeakReference weakReference = (CustomWeakReference)remove;
                                if(map != null && !map.isEmpty()){
                                    map.remove(weakReference.key); //将弱引用里面的value移除
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            mThread.start();
        }
        return queue;
    }

}
