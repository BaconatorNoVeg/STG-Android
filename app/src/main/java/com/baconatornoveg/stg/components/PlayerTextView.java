package com.baconatornoveg.stg.components;

import android.content.Context;
import android.graphics.Typeface;

public class PlayerTextView extends android.support.v7.widget.AppCompatTextView {
    public PlayerTextView(Context context, String text) {
        super(context);
        setText(text);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setTextSize(30f);
        setTypeface(null, Typeface.BOLD);
    }
}
