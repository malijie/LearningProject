package com.politics.exam.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.politics.exam.R;
import com.politics.exam.util.Utils;


public class CustomDialog {
	private Dialog mDialog = null;
	private DialogButtonListener mListener;
	private View v = null;

	public CustomDialog(Context context, String title,String content){
		v = Utils.getView(R.layout.dialog_layout);

		mDialog = new AlertDialog.Builder(context, R.style.dialog)
				.setView(v)
				.setCancelable(false)
				.create();
		TextView textTitle = (TextView) v.findViewById(R.id.id_dialog_text_title);
		TextView textContent = (TextView) v.findViewById(R.id.id_dialog_text_content);
		textTitle.setText(title);
		textContent.setText(content);

	}

	public void show(){
		if(mDialog != null){
			mDialog.show();
		}
	}

	public void dissmiss(){
		if(mDialog != null){
			mDialog.dismiss();
		}
	}

	public void setButtonClickListener(DialogButtonListener listener){
		mListener = listener;

		Button confirmButton = (Button) v.findViewById(R.id.id_dialog_button_confirm);
		Button cancelButton = (Button) v.findViewById(R.id.id_dialog_button_cancel);

		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mListener.onConfirm();
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mListener.onCancel();
			}
		});
	}

	public interface DialogButtonListener{
		void onConfirm();
		void onCancel();
	}
	
}
