package com.baconatornoveg.stg.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.baconatornoveg.stg.R;
import com.baconatornoveg.stg.SavedBuildActivity;

public class SavedBuildTextView extends PlayerTextView {

    public SavedBuildTextView(Context context, String text, int id) {
        super(context, text);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));
        setPadding(0, 50, 0, 50);
        setBackgroundResource(R.drawable.list_clickable);
        setOnClickListener(new SavedBuildOnClickListener(context, id));
    }
}

class SavedBuildOnClickListener implements View.OnClickListener {

    private Context context;
    private Activity activity;
    private int id;
    private String idString;
    public SavedBuildOnClickListener(Context context, int id) {
        this.context = context;
        this.id = id;
        idString = "";
        idString += id;
    }
    @Override
    public void onClick(View v) {
        Intent viewSavedBuildIntent = new Intent(context, SavedBuildActivity.class);
        viewSavedBuildIntent.putExtra("build_key", id);
        context.startActivity(viewSavedBuildIntent);
    }
}
