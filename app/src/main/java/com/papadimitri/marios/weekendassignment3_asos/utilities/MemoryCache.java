package com.papadimitri.marios.weekendassignment3_asos.utilities;

/**
 * Created by fatherjim on 08/04/2016.
 */
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

public class MemoryCache {
    private static final int MAX_CACHE_ENTRIES = 20;

    private LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(MAX_CACHE_ENTRIES) {
        @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            synchronized(MemoryCache.this) {
                if (oldValue != null && evicted) {
                    oldValue.recycle();
                }
            }
        }
    };

    public MemoryCache() {
    }

    public void evictAll() {
        mCache.evictAll();
    }

    public Bitmap get(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }

        return mCache.get(key);
    }

    public Bitmap put(String key, Bitmap value) {
        if (TextUtils.isEmpty(key) || value == null) {
            return null;
        }

        return mCache.put(key, value);
    }
}