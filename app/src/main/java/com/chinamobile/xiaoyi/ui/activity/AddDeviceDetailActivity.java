package com.chinamobile.xiaoyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.http.app.ActionCallback;
import com.chinamobile.xiaoyi.http.entity.BindInfo;
import com.chinamobile.xiaoyi.http.http.RequestParams;
import com.chinamobile.xiaoyi.ui.base.AppBaseActivity;
import com.chinamobile.xiaoyi.util.CommonUtil;
import com.chinamobile.xiaoyi.util.Logger;
import com.chinamobile.xiaoyi.util.SharedPreferenceUtil;
import com.chinamobile.xiaoyi.util.ToastManager;
import com.chinamobile.xiaoyi.util.enums.ActiveEnum;

/**
 * Created by malijie on 2017/9/12.
 */

public class AddDeviceDetailActivity extends AppBaseActivity implements View.OnClickListener {
    private ImageView mButtonBack = null;
    private TextView mTextTitle = null;
    private TextView mTextOption = null;
    private LinearLayout mLayoutTitle = null;
    private TextView childTextView;
    private ImageView childImageView;
    private LinearLayout childLinerLayout;
    private TextView oldemanTextView;
    private ImageView oldemanImageView;
    private LinearLayout oldemanLinerLayout;
    private static final int OLD_MAN = 1;
    private static final int CHILD = 2;
    private static final int PET = 3;

    private static final int BIND_DEVICE = 0;
    private static final int MODIFY_DEVICE = 1;
    private static final int REBINDING = 2;
    private static final int ACTIVE = ActiveEnum.ACTIVE.getActive();//设备状态：激活
    private static final int UNACTIVE = ActiveEnum.UNACTIVE.getActive();//设备状态：未激活


    private int typeRequest;
    private int currentType;
    private String imei;
    private int active;
    private EditText deviceNameEditText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.add_device_detail;
    }

    @Override
    public void initViews() {
        setTitle();
        oldemanLinerLayout = findViewById(R.id.ll_add_device_oldman);
        oldemanImageView = findViewById(R.id.iv_add_device_oldman);
        oldemanTextView = findViewById(R.id.tv_add_device_oldman);
        childLinerLayout = findViewById(R.id.ll_add_device_child);
        childImageView = findViewById(R.id.iv_add_device_child);
        childTextView = findViewById(R.id.tv_add_device_child);


        deviceNameEditText = findViewById(R.id.id_add_device_text_input);
        deviceNameEditText.setText(getIntent().getExtras().getString("name"));
        setType(getIntent().getExtras().getInt("type",1));

        oldemanLinerLayout.setOnClickListener(this);
        childLinerLayout.setOnClickListener(this);
    }


    private void setTitle() {
        mButtonBack = findViewById(R.id.id_title_bar_button_back);
        mTextTitle = findViewById(R.id.id_title_bar_text_title);
        mTextOption = findViewById(R.id.id_title_bar_text_option);
        mLayoutTitle = findViewById(R.id.id_title_bar_layout_device);
        mLayoutTitle.setVisibility(View.GONE);

        mButtonBack.setOnClickListener(this);
        mTextOption.setVisibility(View.VISIBLE);
        mTextTitle.setText(TITLE_ADD_DEVICE);



        mTextOption.setOnClickListener(this);

        String title = getIntent().getExtras().getString("title_text");
        switch (title) {
            case "设备修改":
                typeRequest = MODIFY_DEVICE;
                break;
            case "设备添加":
                typeRequest = BIND_DEVICE;
                break;
            case "重新绑定":
                typeRequest =REBINDING;
                break;
        }
        mTextTitle.setText(title);
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        active  = bundle.getInt("active");
        imei = bundle.getString("imei");
        mTextOption.setText(active == UNACTIVE ? "下一步" : "完成");
    }

    public void setType(int type) {
        currentType = type;
        oldemanLinerLayout.setBackgroundColor(getResources().getColor(R.color.white));
        oldemanImageView.setImageResource(R.mipmap.type_oldman_unselected);
        oldemanTextView.setTextColor(getResources().getColor(R.color.font_grey));
        childLinerLayout.setBackgroundColor(getResources().getColor(R.color.white));
        childImageView.setImageResource(R.mipmap.type_children_unselected);
        childTextView.setTextColor(getResources().getColor(R.color.font_grey));

        switch (type) {
            case OLD_MAN:
                oldemanLinerLayout.setBackgroundColor(getResources().getColor(R.color.bg_light_orange));
                oldemanImageView.setImageResource(R.mipmap.type_oldman_selected);
                oldemanTextView.setTextColor(getResources().getColor(R.color.font_orange));
                break;
            case CHILD:
                childLinerLayout.setBackgroundColor(getResources().getColor(R.color.bg_light_orange));
                childImageView.setImageResource(R.mipmap.type_children_selected);
                childTextView.setTextColor(getResources().getColor(R.color.font_orange));
                break;
            case PET:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_add_device_oldman:
                setType(OLD_MAN);
                break;
            case R.id.ll_add_device_child:
                setType(CHILD);
                break;
            case R.id.id_title_bar_button_back:
                this.finish();
                break;
            case R.id.id_title_bar_text_option:
                bindOrModifyDevice();
                break;
        }
    }



    private void bindOrModifyDevice() {
        String deviceName = deviceNameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(deviceName)) {
            ToastManager.showShortMsg("请输入设备名称");
            return;
        }
        switch (typeRequest) {
            case MODIFY_DEVICE:
                modifyDeviceRequest(deviceName);
                break;
            case BIND_DEVICE:
                bindDeviceRequest(deviceName);

                break;
            case REBINDING:
                rebindingRequest(deviceName);
                break;
        }
    }

    private void rebindingRequest(String deviceName) {
        mProgressDialog = CommonUtil.createProgressDialog(this, "正在重绑...");
        mProgressDialog.show();
        Bundle bundle = getIntent().getExtras();
        String phone = SharedPreferenceUtil.loadPhone();
        imei = bundle.getString("imei");
        RequestParams params = new RequestParams();

        if (imei.length()==15){
            params.setParamsValue("codeType",1+"");
        }else if (imei.length()==10){
            params.setParamsValue("codeType",2+"");
        }

        params.setParamsValue("code", imei);
        params.setParamsValue("name", deviceName);
        params.setParamsValue("phone", phone);
        params.setParamsValue("type", currentType + "");
        Logger.hyw(params.toString());
        action.rebindingDevice(params, new ActionCallback<BindInfo>() {
            @Override
            public void onSuccess(BindInfo data) {
                mProgressDialog.dismiss();
                imei = data.getImei();
                doRebindOrActive();
            }

            @Override
            public void onFailed(String errorMsg) {
                mProgressDialog.dismiss();
                ToastManager.showShortMsg(errorMsg);
            }
        });
    }

    /**
     * 完成重新绑定或激活设备
     */
    private void doRebindOrActive() {
        if(active == ACTIVE){
            //已激活，重新绑定后显示
            startActivity(new Intent(this,MainActivity.class));
            finish(this);
        }else{
            //未激活，绑定后进入支付页面
            goPayActivePage();
        }
    }


    private void modifyDeviceRequest(final String deviceName) {
        mProgressDialog = CommonUtil.createProgressDialog(this, "正在修改...");
        mProgressDialog.show();
        Bundle bundle = getIntent().getExtras();
        String phone = SharedPreferenceUtil.loadPhone();
        String imei = bundle.getString("imei");
        RequestParams params = new RequestParams();
        params.setParamsValue("imei", imei);
        params.setParamsValue("name", deviceName);
        params.setParamsValue("phone", phone);
        params.setParamsValue("type", currentType + "");
        Logger.hyw(params.toString());
        action.modifyDevice(params, new ActionCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mProgressDialog.dismiss();
                ToastManager.showShortMsg(data);

                Intent i = new Intent();
                i.putExtra("device_name",deviceName);
                setResult(UserDetailActivity.REQUEST_CODE_MODIFY,i);
//                startActivity(new Intent(AddDeviceDetailActivity.this,MainActivity.class));
                mDeviceController.refresh();
                AddDeviceDetailActivity.this.finish();
            }

            @Override
            public void onFailed(String errorMsg) {
                mProgressDialog.dismiss();
                ToastManager.showShortMsg(errorMsg);
            }
        });
    }

    private void bindDeviceRequest(String deviceName) {
        mProgressDialog = CommonUtil.createProgressDialog(this, "正在绑定...");
        mProgressDialog.show();
        Bundle bundle = getIntent().getExtras();
//        String phone = bundle.getString("phone");
        String phone = SharedPreferenceUtil.loadPhone();
        String imei = bundle.getString("imei");
        RequestParams params = new RequestParams();
        params.setParamsValue("imei", imei);
        params.setParamsValue("phone", phone);
        params.setParamsValue("name", deviceName);
        params.setParamsValue("type", currentType + "");
        Logger.hyw(params.toString());
        action.addDevice(params, new ActionCallback<BindInfo>() {
            @Override
            public void onSuccess(BindInfo data) {
//                mDeviceController.refresh();
                mProgressDialog.dismiss();
                doRebindOrActive();

            }

            @Override
            public void onFailed(String errorMsg) {
                mProgressDialog.dismiss();
                ToastManager.showShortMsg(errorMsg);
            }
        });
    }



    /**
     * 跳转支付激活页面
     */
    private void goPayActivePage() {
        Intent intent = new Intent(AddDeviceDetailActivity.this, PayActiveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("imei",imei);
        intent.putExtras(bundle);
        startActivity(intent);
        AddDeviceDetailActivity.this.finish();
    }

}
