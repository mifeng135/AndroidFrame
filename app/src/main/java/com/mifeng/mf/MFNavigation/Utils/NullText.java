package com.mifeng.mf.MFNavigation.Utils;

public class NullText extends Text {
    public NullText() {
        super("");
    }

    @Override
    public boolean hasValue() {
        return false;
    }
}
