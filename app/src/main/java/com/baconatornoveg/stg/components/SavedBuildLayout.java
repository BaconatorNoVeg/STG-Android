package com.baconatornoveg.stg.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baconatornoveg.stg.MainActivity;
import com.baconatornoveg.stg.R;
import com.baconatornoveg.stg.SavedActivity;
import com.baconatornoveg.stg.database.DBPlayer;
import com.baconatornoveg.stg.database.DatabaseAccessObject;

public class SavedBuildLayout extends LinearLayout {

    public SavedBuildLayout(final Context context, String text, final int buildIndex, final SavedActivity savedActivity) {
        super(context);
        setOrientation(HORIZONTAL);
        SavedBuildTextView savedBuildTextView = new SavedBuildTextView(context, text, buildIndex);
        addView(savedBuildTextView);
        savedBuildTextView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Build Options");
                builder.setItems(new String[]{"Share", "Rename", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DatabaseAccessObject dao = MainActivity.buildDatabase.dao();
                        switch (which) {
                            case 0:
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(dao.getBuild(buildIndex).getBuildName());
                                stringBuilder.append("\n\n");
                                stringBuilder.append(dao.getBuild(buildIndex).getGod());
                                stringBuilder.append("\n");
                                stringBuilder.append(dao.getBuild(buildIndex).getRelics());
                                stringBuilder.append("\n\n");
                                String build = dao.getBuild(buildIndex).getBuild();
                                build = build.substring(1, build.length()-1);
                                String[] pBuild = build.split(",");
                                for (int i = 0; i < pBuild.length; i++) {
                                    stringBuilder.append(pBuild[i]);
                                    if (i < pBuild.length-1) {
                                        stringBuilder.append("\n");
                                    }
                                }
                                String shareString = stringBuilder.toString();
                                sendIntent.putExtra(Intent.EXTRA_TEXT, shareString);
                                sendIntent.setType("text/plain");
                                context.startActivity(sendIntent);
                                break;

                            case 1:
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                LayoutInflater li = LayoutInflater.from(context);
                                View saveView = li.inflate(R.layout.save_build_dialog, null);
                                alertDialogBuilder.setView(saveView);
                                final EditText userInput = saveView.findViewById(R.id.build_name_input);
                                alertDialogBuilder
                                        .setCancelable(true)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String buildName = userInput.getText().toString();
                                                if ("".equals(buildName)) {
                                                    buildName = dao.getBuild(buildIndex).getGod();
                                                }
                                                DBPlayer updatedPlayer = dao.getBuild(buildIndex);
                                                updatedPlayer.setBuildName(buildName);
                                                dao.update(updatedPlayer);
                                                Toast.makeText(context, "Build renamed", Toast.LENGTH_SHORT).show();
                                                savedActivity.refresh();
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

                            case 2:
                                AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(context);
                                deleteDialogBuilder.setTitle("Delete " + dao.getBuild(buildIndex).getBuildName() + "?");
                                deleteDialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                deleteDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dao.delete(dao.getBuild(buildIndex));
                                        Toast.makeText(context, "Build deleted", Toast.LENGTH_SHORT).show();
                                        savedActivity.refresh();
                                    }
                                });
                                AlertDialog deleteDialog = deleteDialogBuilder.create();
                                deleteDialog.show();
                                break;
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }
}
