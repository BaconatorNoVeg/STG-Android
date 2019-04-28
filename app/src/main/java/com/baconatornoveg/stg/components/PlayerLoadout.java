package com.baconatornoveg.stg.components;

import android.content.Context;
import android.widget.LinearLayout;

import com.baconatornoveg.stg.R;

public class PlayerLoadout extends LinearLayout {
    public PlayerLoadout(Context context) {
        super(context);
        setOrientation(VERTICAL);
        setPadding(0, 0, 0, 50);
        setLongClickable(true);
    }
}
