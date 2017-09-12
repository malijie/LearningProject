package com.chinamobile.xiaoyi.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chinamobile.xiaoyi.R;


/**
 * Created by malijie on 2016/12/8.
 */

public class MessageFragment extends BaseFragment {
    private static final String TITLE_TEXT = "我的消息";

    private FragmentManager mSupportFragmentManager;
    private ImageButton mButtonBack = null;
    private TextView mTextTitle = null;

    public MessageFragment(FragmentManager supportFragmentManager) {
        this.mSupportFragmentManager = supportFragmentManager;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_layout, container, false);
        super.onCreateView(v);

        initViews(v);
        initData();

        return v;
    }

    @Override
    public void initViews(View v) {
        hideBackButton();
        hideOption();
        hideSubTitleLayout();
        showTitle();
        setTitle(TITLE_TEXT);
    }

    @Override
    public void initData() {

    }
}