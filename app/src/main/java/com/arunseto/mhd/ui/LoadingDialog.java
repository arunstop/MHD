package com.arunseto.mhd.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.arunseto.mhd.R;

public class LoadingDialog extends Dialog {

    private Context context;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void show() {
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.template_dialog_progress);
        super.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        super.setCancelable(false);
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
