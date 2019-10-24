package com.mifeng.mf.MsgBean;

import java.util.List;

public class TestBean {

    public List<String> getChild() {
        return child;
    }

    private List<String> child;

    public String getParent() {
        return parent;
    }

    private String parent;

    public TestBean(String parentString,List<String> child) {
        parent = parentString;
        this.child = child;
    }


}
