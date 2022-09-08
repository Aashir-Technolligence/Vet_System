package com.aash.vetsystem.dialog;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aash.vetsystem.R;


public class ProgressBarDialog extends BaseAlertDialog implements DialogInterface.OnClickListener {
    private ProgressBar progressBar;
    private TextView message;
    private TextView noteTV;

    public ProgressBarDialog(Context context) {
        super(context);
        Context context1 = context;
        LayoutInflater factory = LayoutInflater.from(context);
        final View progressBarView = factory.inflate(R.layout.progresing_dilog, null);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setView(progressBarView);
      //  progressBar = progressBarView.findViewById(R.id.progressBar);
        message = progressBarView.findViewById(R.id.progressMessage);
        this.setCanceledOnTouchOutside(false);

    }

//    public void setTitle(String title){
//        super.setTitle(title);
//    }

    @Override
    public void setMessage(CharSequence message) {
        this.message.setText(message);
    }
    public void setButton(int type, String text){
        setButton(type,text, this);
    }
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void setNoteMessage(String message) {
        noteTV.setVisibility(View.VISIBLE);
        noteTV.setText("*Note: " + message);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {
            case BUTTON_POSITIVE:
                dismissManually();
                break;
            case BUTTON_NEGATIVE:
                boolean isSuccess = true;
                if(!isSuccess) {
                    dismissManually();
//					((StartupActivity)context).finish();
                }
                break;
            default:
                break;
        }
    }

    public void setWidth(int width){
        int width1 = width;
    }

    @Override
    public void show() {
        super.show();
        getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void dismissManually() {
        super.dismiss();
    }


}
