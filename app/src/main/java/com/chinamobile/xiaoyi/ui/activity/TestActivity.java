package com.chinamobile.xiaoyi.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.base.lib.base.AppBaseActivity;
import com.base.lib.entity.WelfareInfo;
import com.base.lib.http.RequestParams;
import com.base.lib.utils.Logger;
import com.base.lib.utils.Util;
import com.chinamobile.xiaoyi.R;

import java.util.List;

public class TestActivity extends AppBaseActivity {
    private static final String TAG = TestActivity.class.getSimpleName();
    private Button mButton = null;
    private Button mButtonSecond = null;
    private GridView mGridView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.id_get_picture);
        mButtonSecond = (Button) findViewById(R.id.id_go_second);
//        mGridView = (GridView) findViewById(R.id.id_gv);
        mButton.setOnClickListener(onClickGetPicture);
        mButtonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(TestActivity.this,SecondActivity.class));
            }
        });

    }

    @Override
    protected int getContentViewId() {
        return 0;
    }

    private View.OnClickListener onClickGetPicture = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mProgressDialog = Util.createProgressDialog(TestActivity.this,getResources().getString(R.string.loading_progress_tip));
            mProgressDialog.show();

            RequestParams params = new RequestParams();
            params.setParamsValue("groupId","10");
            params.setParamsValue("itemId","1");

            action.listWelfareInfo(params, new RequestCallback<List<WelfareInfo>>() {
                @Override
                public void onSuccess(List<WelfareInfo> data) {
                    mProgressDialog.dismiss();
                    Logger.d("MLJ","data 1==" + data);
                    Logger.d(TAG,"data 1==" + data);
                }

            });
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(TAG,"======onStop======");
        mThreadPool.removeAllTask();
        mThreadPool.waitTermination();
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }
}
