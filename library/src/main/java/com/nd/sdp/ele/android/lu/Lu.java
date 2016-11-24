package com.nd.sdp.ele.android.lu;

/**
 * @author Jackoder
 * @version 2016/11/24
 */
public class Lu {

    String mName;
    String mDescription;
    String mCmp;

    public Lu() {
    }

    public String getName() {
        return mName;
    }

    public Lu setName(String name) {
        mName = name;
        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public Lu setDescription(String description) {
        mDescription = description;
        return this;
    }

    public String getCmp() {
        return mCmp;
    }

    public Lu setCmp(String cmp) {
        mCmp = cmp;
        return this;
    }
}
