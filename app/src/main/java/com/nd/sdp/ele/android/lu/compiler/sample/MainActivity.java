package com.nd.sdp.ele.android.lu.compiler.sample;

import android.app.Activity;
import android.os.Bundle;

import com.nd.sdp.ele.android.lu.Lu;
import com.nd.sdp.ele.android.lu.mapper.LuMapper;

/**
 * @author Jackoder
 * @version 2016/11/24
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lu learnUnit = LuMapper.map(new Test());
        //do something
    }
}
