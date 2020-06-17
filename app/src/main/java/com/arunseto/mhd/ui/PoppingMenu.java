package com.arunseto.mhd.ui;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;

import com.arunseto.mhd.R;

public class PoppingMenu extends PopupMenu {
    private Context context;
    private View anchor;
    private int order;

    public PoppingMenu(@NonNull Context context, @NonNull View anchor) {
        super(context, anchor);
        this.context = context;
        this.anchor = anchor;
        this.order = 0;
    }

    @Override
    public void show() {
        super.show();
    }

    public void addItem(String title){
        //add(groupId, itemId, order, title);
        int itemId = order;
        super.getMenu().add(0, itemId, order,title);
        order++;
    }
}
