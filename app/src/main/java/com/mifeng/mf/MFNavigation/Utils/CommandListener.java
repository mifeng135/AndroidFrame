package com.mifeng.mf.MFNavigation.Utils;

public interface CommandListener {
    void onSuccess(String childId);

    void onError(String message);
}
