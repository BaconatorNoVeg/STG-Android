package com.baconatornoveg.stg;

import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.baconatornoveg.stg.components.OptionsFragment;

public class OptionsActivity extends AppCompatActivity {

    public boolean isForcingOffensive;
    public boolean isForcingDefensive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new OptionsFragment())
                .commit();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }
}
