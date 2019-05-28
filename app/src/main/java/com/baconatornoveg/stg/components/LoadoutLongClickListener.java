package com.baconatornoveg.stg.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baconatornoveg.stg.BuildActivity;
import com.baconatornoveg.stg.MainActivity;
import com.baconatornoveg.stg.R;
import com.baconatornoveg.stg.database.DBPlayer;
import com.baconatornoveg.stglib.Player;
import com.baconatornoveg.stglib.Team;

public class LoadoutLongClickListener implements View.OnLongClickListener {

    private Player playerData;
    private Team teamData;
    private Context context;
    private int index;
    private BuildActivity buildActivity;
    private boolean relicsEnabled;

    public LoadoutLongClickListener(Context context, Player playerData, Team teamData, int index, BuildActivity buildActivity, boolean relicsEnabled) {
        this.playerData = playerData;
        this.context = context;
        this.teamData = teamData;
        this.index = index;
        this.buildActivity = buildActivity;
        this.relicsEnabled = relicsEnabled;
    }

    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Player Options");
        builder.setItems(new String[]{"Share", "Reroll Player", "Save Build"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        String[] build = playerData.getBuildAsStringArray();
                        String formattedPlayer = playerData.getGod() + "\n";
                        for (int i = 0; i < 6; i++) {
                            if (i != 5) { formattedPlayer += "- " + build[i] + "\n"; }
                        }
                        if (relicsEnabled) {
                            String[] relics = playerData.getRelicsAsStringArray();
                            formattedPlayer += "\n\nRelics: ";
                            for (int i = 0; i < 2; i++) {
                                formattedPlayer += relics[i];
                                if (i != 1) { formattedPlayer += "\n"; }
                            }
                        }
                        sendIntent.putExtra(Intent.EXTRA_TEXT, formattedPlayer);
                        sendIntent.setType("text/plain");
                        context.startActivity(sendIntent);
                        break;
                    case 1:
                        teamData.rerollPlayer(index);
                        MainActivity.prepareBuildActivity(teamData);
                        buildActivity.setTextViews();
                        break;
                    case 2:
                         AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        LayoutInflater li = LayoutInflater.from(context);
                        View saveView = li.inflate(R.layout.save_build_dialog, null);
                        alertDialogBuilder.setView(saveView);
                        final EditText userInput = saveView.findViewById(R.id.build_name_input);
                        final int assignedId;
                        if (MainActivity.buildDatabase.dao().loadAll().size() == 0) {
                            assignedId = 0;
                        } else {
                            assignedId = MainActivity.buildDatabase.dao().getLast().getId()+1;
                        }
                        alertDialogBuilder
                                .setCancelable(true)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String buildName = userInput.getText().toString();
                                        if ("".equals(buildName)) {
                                            buildName = playerData.getGod().getName();
                                        }
                                        DBPlayer player = new DBPlayer(assignedId, buildName, playerData.getGod().getName(), playerData.getBuild().toString(), playerData.getRelics().toString());
                                        MainActivity.buildDatabase.dao().insertAll(player);
                                        Toast.makeText(context, "Build saved", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }
}
