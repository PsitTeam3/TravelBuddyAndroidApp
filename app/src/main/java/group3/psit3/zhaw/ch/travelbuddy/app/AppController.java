package group3.psit3.zhaw.ch.travelbuddy.app;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import group3.psit3.zhaw.ch.travelbuddy.util.LruBitmapCache;
import group3.psit3.zhaw.ch.travelbuddy.util.RequestQueuer;

/**
 * The AppController provides singletons across the app and helps
 * to maintain global state
 */
public class AppController extends Application {

    private static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private RequestQueuer mRequestQueuer;
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * @return Singleton.
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**
     * @return Singleton.
     */
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    /**
     * @return Singleton.
     */
    public RequestQueuer getRequestBuilder() {
        if (mRequestQueuer == null) {
            mRequestQueuer = new RequestQueuer();
        }
        return mRequestQueuer;
    }

    /**
     * @return Singleton.
     */
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Adds a request to the Android request queue that gets handled asynchronously.
     * @param req Request to queue.
     * @param tag TAG of the class or context.
     * @param <T> The type of parsed response this request expects.
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
}