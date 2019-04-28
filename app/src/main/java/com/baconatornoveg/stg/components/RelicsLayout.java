package com.baconatornoveg.stg.components;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

public class RelicsLayout extends LinearLayout {
    public RelicsLayout(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setShowDividers(SHOW_DIVIDER_MIDDLE);
    }
}
