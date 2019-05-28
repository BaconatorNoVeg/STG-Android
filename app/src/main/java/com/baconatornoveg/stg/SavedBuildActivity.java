package com.baconatornoveg.stg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.baconatornoveg.stg.components.PlayerBuildTextView;
import com.baconatornoveg.stg.database.DatabaseAccessObject;

public class SavedBuildActivity extends AppCompatActivity {

    private DatabaseAccessObject dao;
    private int buildKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = MainActivity.buildDatabase.dao();
        buildKey = getIntent().getIntExtra("build_key", 0);
        setContentView(R.layout.activity_saved_build);
        TextView buildName, godName, relicsList;
        LinearLayout buildLayout = findViewById(R.id.player_build);
        buildName = findViewById(R.id.build_name);
        godName = findViewById(R.id.god_name);
        relicsList = findViewById(R.id.relics_list);
        buildName.setText(dao.getBuild(buildKey).getBuildName());
        godName.setText(dao.getBuild(buildKey).getGod());
        String rawBuild = dao.getBuild(buildKey).getBuild();
        String rawRelics = dao.getBuild(buildKey).getRelics();
        String[] processedBuild = rawBuild.substring(1, rawBuild.length() - 1).split(",");
        String[] processedRelics = rawRelics.substring(1, rawRelics.length() - 1).split(",");
        for (int i = 0; i < 6; i++) {
            PlayerBuildTextView buildTextView = new PlayerBuildTextView(this, processedBuild[i], 0);
            buildLayout.addView(buildTextView);
        }
        String relicsString = "";
        for (int i = 0; i < 2; i++) {
            relicsString += processedRelics[i];
            if (i == 0) {
                relicsString += "\n";
            }
        }
        relicsList.setText(relicsString);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_saved_build, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_build_delete:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(R.string.delete_build_confirmation);
                alertDialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.delete(dao.getBuild(buildKey));
                        finish();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
