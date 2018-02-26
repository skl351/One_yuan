package com.yiyuangou.android.one_yuan;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Admin on 2016/3/8.
 */
public class BannerApplication extends Application {
    private RequestQueue mQueue;
    public static BannerApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {

        mInstance = this;

        getRequestQueue();

        initUIL();
    }

    public RequestQueue getRequestQueue() {
        if (mQueue == null){
            mQueue = Volley.newRequestQueue(this);
        }
        return mQueue;
    }

    private void initUIL() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(this,"Banner/cache/image");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCache(new LruMemoryCache(2*1024*1024))
                .memoryCacheSize(2*1024*1024)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheSize(50*1024*1024)
                .imageDownloader(new BaseImageDownloader(this,20*60*60,30*60*60))
                .diskCacheFileCount(100)
                .denyCacheImageMultipleSizesInMemory()
                .build();
        ImageLoader.getInstance().init(config);
    }
}
