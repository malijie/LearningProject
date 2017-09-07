package com.base.lib.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.lib.app.ActionImpl;
import com.base.lib.app.AppAction;

/**
 * Created by malijie on 2017/8/2.
 */

public class BaseActivity extends Activity {

    public AppAction action = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        action = new ActionImpl();
    }
}
