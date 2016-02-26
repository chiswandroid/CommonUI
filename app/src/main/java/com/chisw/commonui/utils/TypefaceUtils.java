package com.chisw.commonui.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public final class TypefaceUtils {

    public static final String ROBOTO_MEDIUM = "fonts/robotoMedium.ttf";

    private static final String TAG = TypefaceUtils.class.getSimpleName();
    private static final Map<String, SoftReference<Typeface>> cache = new ArrayMap<>();

    private TypefaceUtils() {
    }

    public static void setTypeface(TextView view, String typefacePath) {
        final AssetManager assetManager = view.getContext().getAssets();
        view.setTypeface(get(typefacePath, assetManager));
    }

    public static Typeface get(String typefacePath, AssetManager assetManager) {
        final SoftReference<Typeface> cachedTypefaceReference = cache.get(typefacePath);
        if (cachedTypefaceReference == null) {
            return createTypefaceAndPushToCache(typefacePath, assetManager);
        }
        final Typeface typeface = cachedTypefaceReference.get();
        if (typeface == null) {
            return createTypefaceAndPushToCache(typefacePath, assetManager);
        }
        return typeface;
    }

    public static Typeface getWithoutCache(String typefacePath, AssetManager assetManager) {
        return createTypefaceFromPath(typefacePath, assetManager);
    }

    public static void setTypefaceWithoutCache(TextView view, String typefacePath) {
        final AssetManager assetManager = view.getContext().getAssets();
        view.setTypeface(getWithoutCache(typefacePath, assetManager));
    }

    public static void clearCache() {
        cache.clear();
    }

    private static Typeface createTypefaceAndPushToCache(String typefacePath,
                                                         AssetManager assetManager) {
        final Typeface typeface = createTypefaceFromPath(typefacePath, assetManager);
        pushToCache(typefacePath, typeface);
        return typeface;
    }

    private static void pushToCache(String typefacePath, Typeface typeface) {
        if (typeface != null) {
            cache.put(typefacePath, new SoftReference<>(typeface));
        }
    }

    private static Typeface createTypefaceFromPath(String typefacePath, AssetManager assetManager) {
        Typeface typeface = null;
        try {
            typeface = Typeface.createFromAsset(assetManager, typefacePath);
        } catch (RuntimeException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        }
        return typeface;
    }
}
