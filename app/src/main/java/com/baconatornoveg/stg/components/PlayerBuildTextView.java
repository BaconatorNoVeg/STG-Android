package com.baconatornoveg.stg.components;

import android.content.Context;
import android.widget.TextView;

public class PlayerBuildTextView extends android.support.v7.widget.AppCompatTextView {
    public PlayerBuildTextView(Context context, String text, int type) {
        super(context);
        setText(text);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        switch(type) {
            case 1:
                setTextSize(18f);
                break;
            default:
                break;
        }
        setPadding(50, 0, 50, 0);
    }
}
