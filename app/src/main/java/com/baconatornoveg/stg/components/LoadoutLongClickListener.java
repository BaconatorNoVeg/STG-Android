package com.baconatornoveg.stg.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.baconatornoveg.stg.BuildActivity;
import com.baconatornoveg.stg.MainActivity;
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
        builder.setItems(new String[]{"Share", "Reroll Player"}, new DialogInterface.OnClickListener() {
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
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }
}
