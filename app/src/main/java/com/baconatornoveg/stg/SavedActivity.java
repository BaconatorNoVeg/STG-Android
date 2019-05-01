package com.baconatornoveg.stg;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.baconatornoveg.stg.components.PlayerTextView;
import com.baconatornoveg.stg.components.SavedBuildLayout;
import com.baconatornoveg.stg.components.SavedBuildTextView;
import com.baconatornoveg.stg.database.DBPlayer;
import com.baconatornoveg.stg.database.DatabaseAccessObject;

import org.apache.commons.math3.geometry.euclidean.twod.Line;

import java.util.List;

public class SavedActivity extends AppCompatActivity {

    private DatabaseAccessObject dao;
    private List<DBPlayer> playerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        dao = MainActivity.buildDatabase.dao();
        playerList = dao.loadAll();
        LinearLayout savedList = findViewById(R.id.saved_list);
        TextView noSaved = findViewById(R.id.empty_list);
        savedList.removeAllViews();
        if (playerList.size() < 1) {
            noSaved.setEnabled(true);
            noSaved.setVisibility(View.VISIBLE);
        } else {
            noSaved.setEnabled(false);
            noSaved.setVisibility(View.INVISIBLE);
        }
        for (DBPlayer i : playerList) {
            SavedBuildLayout savedBuildLayout = new SavedBuildLayout(this, i.getBuildName(), i.getId(), this);
            savedList.addView(savedBuildLayout);
        }
    }
}
