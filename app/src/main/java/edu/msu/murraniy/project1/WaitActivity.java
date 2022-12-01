package edu.msu.murraniy.project1;

import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import edu.msu.murraniy.project1.Cloud.Cloud;

public class WaitActivity extends DialogFragment {

    // id for the game
    private int gameID;

    private AlertDialog dlg;

    private String player1;

    private volatile String player2 = null;

    private volatile boolean cancel = false;
    private volatile boolean start = false;

    public int getGameID() {
        return gameID;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.wait);

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                cancel = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cloud cloud = new Cloud();
                        final boolean result;
                        try {
                            result = cloud.deleteGame(gameID);

                            if (!result) {
                                Log.e("StopWait", "Game failed to be deleted!");
                            }

                        } catch (Exception e) {
                            // Error condition! Something went wrong
                            Log.e("StopWait", "Something went wrong canceling wait", e);
                        }
                    }
                }).start();
            }
        });

        // Destroy the game if the host decides to stop waiting
        /*builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                cancel = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cloud cloud = new Cloud();
                        final boolean result;
                        try {
                            result = cloud.deleteGame(gameID);

                            if (!result) {
                                Log.e("StopWait", "Game failed to be deleted!");
                            }

                        } catch (Exception e) {
                            // Error condition! Something went wrong
                            Log.e("StopWait", "Something went wrong canceling wait", e);
                        }
                    }
                }).start();

            }
        });*/


        // Thread to periodically check for if other player joined
        /*new Thread(new Runnable() {
            @Override
            public void run() {

                while (!cancel) {
                    SystemClock.sleep(2500);

                    // thread to perform cloud actions
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            // Create a cloud object and get the XML
                            Cloud cloud = new Cloud();
                            player2 = cloud.checkJoin(gameID);
                            if (player2 != null) {
                                cancel = true;
                                start = true;
                                // Starting process of moving into game
                            }
                        }
                    }).start();
                }
                // Starting process of moving into game
                if (start) {
                    ((MainActivity) getActivity()).startChess(player1, player2, gameID, 1);
                }

            }
        }).start();*/


        dlg = builder.create();
        return dlg;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

}
