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

public class AvailableGamesActivity extends DialogFragment {

    private AlertDialog dlg;

    private volatile String username;
    private volatile String password;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.availgames);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.activity_available_games, null);
        builder.setView(view);

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Cancel just closes the dialog box
            }
        });

        builder.setPositiveButton(R.string.create_game, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cloud cloud = new Cloud();
                        final int gameID;
                        try {
                            gameID = cloud.createGame(username, password);

                            if(gameID != -1) {
                                /*
                                 * If game creation fails, display a toast
                                 */
                                view.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        Toast.makeText(getView().getContext(),
                                                R.string.newgame_fail,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else{
                                dlg.dismiss();

                                WaitActivity waitActivity = new WaitActivity();
                                waitActivity.setGameID(gameID);
                                waitActivity.setPlayer1(username);
                                waitActivity.show(getActivity().getSupportFragmentManager(), "wait");
                            }
                        } catch (Exception e) {
                            // Error condition! Something went wrong
                            Log.e("CreateUserButton", "Something went wrong when creating user", e);
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(view.getContext(), R.string.newuser_fail, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });



        // Find the list view
        ListView list = (ListView)view.findViewById(R.id.listGames);

        final Cloud.CatalogAdapter adapter = new Cloud.CatalogAdapter(list);
        adapter.setUser(username);
        adapter.setPass(password);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Get the id of the one we want to load
                /*String catId = adapter.getId(position);*/

                // Dismiss the dialog box
                dlg.dismiss();

//                LoadingDlg loadDlg = new LoadingDlg();
//                loadDlg.setCatId(catId);
//                loadDlg.show(getActivity().getSupportFragmentManager(), "loading");

            }

        });


        dlg = builder.create();
        return dlg;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}