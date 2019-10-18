package com.mifeng.mf.MFNavigation.Utils;

import android.support.annotation.Nullable;

import com.mifeng.mf.MFNavigation.Utils.Functions.Func1;
import com.mifeng.mf.MFNavigation.Utils.Functions.FuncR1;

public class ObjectUtils {
    public static <T> void perform(@Nullable T obj, Func1<T> action) {
        if (obj != null) action.run(obj);
    }

    public static <T, S> S perform(@Nullable T obj, S defaultValue, FuncR1<T, S> action) {
        return obj == null ? defaultValue : action.run(obj);
    }

    public static boolean notNull(Object o) {
        return o != null;
    }

    public static <T> boolean equalsNotNull(@Nullable T a, @Nullable T b) {
        return a != null && a.equals(b);
    }
}
