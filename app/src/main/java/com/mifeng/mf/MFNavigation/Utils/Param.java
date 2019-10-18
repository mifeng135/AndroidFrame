package com.mifeng.mf.MFNavigation.Utils;

import static com.mifeng.mf.MFNavigation.Utils.ObjectUtils.equalsNotNull;

public abstract class Param<T> {
    protected T value;

    Param(T value) {
        this.value = value;
    }

    public T get() {
        if (hasValue()) {
            return value;
        }
        throw new RuntimeException("Tried to get null value!");
    }

    public T get(T defaultValue) {
        return hasValue() ? value : defaultValue;
    }

    public boolean hasValue() {
        return value != null;
    }

    public boolean equals(Param other) {
        return value == other.value || equalsNotNull(value, other.value);
    }
}
