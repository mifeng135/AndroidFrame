package com.mifeng.mf.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.mifeng.mf.R;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2019/8/30.
 */

public class MFUtils {
    public enum Preferences {
        BOOLEAN,
        INTERGER,
        STRING,
        FLOAT
    }

    private Context context;
    private static volatile MFUtils mInstance;
    private String sharedPrefrencesName = "MF";

    public static MFUtils getInstance() {
        return mInstance;
    }

    public MFUtils(Context context) {
        this.context = context;
        mInstance = this;
    }

    public SharedPreferences getSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPrefrencesName, MODE_PRIVATE);
        return sharedPreferences;
    }

    public void saveLocalData(String key, Object value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        editor.commit();
    }

    public Object getLocalData(String key, Preferences preferences) {
        if (preferences == Preferences.BOOLEAN) {
            return getSharedPreferences().getBoolean(key, false);
        } else if (preferences == Preferences.INTERGER) {
            return getSharedPreferences().getInt(key, -1);
        } else if (preferences == Preferences.FLOAT) {
            return getSharedPreferences().getFloat(key, 0.0f);
        }
        return getSharedPreferences().getString(key, "");
    }

    public void loadImage(String url, ImageView view) {
        Glide.with(context).load(url).placeholder(R.drawable.place_image).into(view);
    }

    public void loadImageWithCircle(String url, ImageView view) {
        Glide.with(context).load(url).placeholder(R.drawable.place_image).apply(RequestOptions.bitmapTransform(new CropCircleWithBorderTransformation())).into(view);
    }

    public void loadImageWithRoundedCorners(String url, ImageView view, int radius) {
        Glide.with(context).load(url).placeholder(R.drawable.place_image).apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(radius, 0))).into(view);
    }
}
