package group3.psit3.zhaw.ch.travelbuddy.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * LruBitmapCache optimizes memory by capping the total memory used.
 * The total size is configurable, it will remove the oldest bitmaps.
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageCache {
    public LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }

    private LruBitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    private static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        return maxMemory / 8;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
