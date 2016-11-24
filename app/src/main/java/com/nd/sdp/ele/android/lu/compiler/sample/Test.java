package com.nd.sdp.ele.android.lu.compiler.sample;

import com.nd.sdp.ele.android.lu.ann.Cmp;
import com.nd.sdp.ele.android.lu.ann.Description;
import com.nd.sdp.ele.android.lu.ann.Lu;
import com.nd.sdp.ele.android.lu.ann.Name;

/**
 * @author Jackoder
 * @version 2016/11/24
 */
@Lu
public class Test {

    @Name
    String mName;
    @Description
    String mDescription;

    @Cmp
    public String getCmp() {
        return "cmp";
    }

}
