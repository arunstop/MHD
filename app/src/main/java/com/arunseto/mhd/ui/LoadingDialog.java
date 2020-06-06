package com.arunseto.mhd.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.arunseto.mhd.R;

public class LoadingDialog extends Dialog {

    private Context context;
    private Dialog dialog;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void show() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_progress);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }
}
