package com.chinamobile.xiaoyi.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.lib.utils.ToastManager;
import com.chinamobile.xiaoyi.R;

/**
 * Created by malijie on 2017/9/12.
 */

public abstract class BaseFragment extends Fragment{
    private ImageButton mButtonBack =  null;
    private LinearLayout mLayoutDevice = null;
    private TextView mTextTitle = null;
    private TextView mTextOption = null;

    public View onCreateView(View view) {
        mButtonBack = view.findViewById(R.id.id_title_bar_button_back);
        mLayoutDevice = view.findViewById(R.id.id_title_bar_layout_device);
        mTextTitle = view.findViewById(R.id.id_title_bar_text_title);
        mTextOption = view.findViewById(R.id.id_title_bar_text_option);

        return view;
    }

    public void showBackButton(){
        mButtonBack.setVisibility(View.VISIBLE);
    }

    public void hideBackButton(){
        mButtonBack.setVisibility(View.GONE);
    }

    public void showSubTitleLayout(){
        mLayoutDevice.setVisibility(View.VISIBLE);
    }

    public void hideSubTitleLayout(){
        mLayoutDevice.setVisibility(View.GONE);

    }

    public void showTitle(){
        mTextTitle.setVisibility(View.VISIBLE);
    }

    public void hideOption(){
        mTextOption.setVisibility(View.GONE);
    }

    public void showOption(){
        mTextOption.setVisibility(View.VISIBLE);
    }

    public void hideTitle(){
        mTextTitle.setVisibility(View.GONE);
    }

    public void setTitle(String title){
        mTextTitle.setText(title);
    }

    public abstract void initViews(View v);
    public abstract void initData();
}
