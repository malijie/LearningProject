package com.politics.exam.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.common.lib.util.Utils;
import com.politics.exam.R;

/**
 * Created by malijie on 2018/10/29.
 */

public class PayTipDialog extends AlertDialog{
    public PayTipDialog(@NonNull Context context) {
        this(context,-1);
        View v = Utils.getView(R.layout.pay_tip_dialog);
        setView(v);

    }

    public PayTipDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
}
