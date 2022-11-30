package edu.msu.murraniy.project1;

import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.wait);

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Cancel just closes the dialog box
            }
        });

        // Destroy the game if the host decides to stop waiting
        DialogInterface.OnDismissListener onDismissListener;
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cloud cloud = new Cloud();
                        final boolean result;
                        try {
                            result = cloud.deleteGame(gameID);

                        } catch (Exception e) {
                            // Error condition! Something went wrong
                            Log.e("StopWait", "Something went wrong dismissing wait", e);
                        }
                    }
                }).start();

            }
        });

        dlg = builder.create();
        return dlg;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

}
