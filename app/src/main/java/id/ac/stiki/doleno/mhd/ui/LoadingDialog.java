package id.ac.stiki.doleno.mhd.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import id.ac.stiki.doleno.mhd.R;

public class LoadingDialog extends Dialog {

    private Context context;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
        super.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.template_dialog_progress);
        super.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        super.setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
