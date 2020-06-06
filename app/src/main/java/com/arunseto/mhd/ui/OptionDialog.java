package com.arunseto.mhd.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arunseto.mhd.R;

public class OptionDialog extends AlertDialog {
    private Context context;
    private Dialog dialog;
    private String title, yLabel, nLabel;
    private Button btnYes, btnNo;
    private TextView tvTitle;

    public OptionDialog(Context context, String title, String yLabel, String nLabel) {
        super(context);
        this.context = context;
        this.title = title;
        this.yLabel = yLabel;
        this.nLabel = nLabel;
    }

    @Override
    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View layoutLogout = getLayoutInflater().inflate(R.layout.template_dialog_confirmation, null);
        // yes == log out
        // no == cancel
        btnYes = layoutLogout.findViewById(R.id.btnYes);
        btnNo = layoutLogout.findViewById(R.id.btnNo);
        tvTitle = layoutLogout.findViewById(R.id.tvTitle);
        builder.setView(layoutLogout);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();// to resize alert dialogMoreMenu, this command line should be below the alert.show()
        dialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);// here i have fragment height 30% of window's height you can set it as per your requirement

        tvTitle.setText(title);
        btnYes.setText(yLabel);
        btnNo.setText(nLabel);

        dialog.show();
    }

    public Button getBtnYes() {
        return btnYes;
    }

    public Button getBtnNo() {
        return btnNo;
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }
}
