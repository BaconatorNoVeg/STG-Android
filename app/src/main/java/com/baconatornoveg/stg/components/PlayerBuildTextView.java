package com.baconatornoveg.stg.components;

import android.content.Context;

import androidx.appcompat.widget.AppCompatTextView;

public class PlayerBuildTextView extends AppCompatTextView {
    public PlayerBuildTextView(Context context, String text, int type) {
        super(context);
        setText(text);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        switch (type) {
            case 1:
                setTextSize(18f);
                break;
            default:
                break;
        }
        setPadding(50, 0, 50, 0);
    }
}
