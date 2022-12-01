package edu.msu.murraniy.project1;

import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import edu.msu.murraniy.project1.Cloud.Cloud;
import edu.msu.murraniy.project1.Cloud.Models.Game;

public class AvailableGamesActivity extends androidx.fragment.app.DialogFragment {

    private AlertDialog dlg;

    private volatile String username;
    private volatile String password;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity activity = (MainActivity) getActivity();

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

                            if(gameID == -1) {
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
                            Log.e("CreateUserButton", "Something went wrong when creating newgame", e);
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
                Intent intent = new Intent(getActivity(), ChessActivity.class);
                startActivity(intent);

                dlg.dismiss();
//                LoadingDlg loadDlg = new LoadingDlg();
//                loadDlg.setCatId(catId);
//                loadDlg.show(getActivity().getSupportFragmentManager(), "loading");


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final boolean result;
                        Cloud cloud = new Cloud();
                        try {
                            result = cloud.joinGame(Integer.parseInt(selectedGame.getId()),username);

                            if(!result) {
                                /*
                                 * If game join fails, display a toast
                                 */
                                view.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        Toast.makeText(getView().getContext(),
                                                R.string.joinfail,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else{
                                dlg.dismiss();

                                startChess(selectedGame.getName(), username, Integer.parseInt(selectedGame.getId()), 2);
                            }
                        } catch (Exception e) {
                            // Error condition! Something went wrong
                            Log.e("JoinGame", "Something went wrong when joining a game", e);
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(view.getContext(), R.string.joinfail, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
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

    public void startChess(String player1, String player2, int gameID, int player) {
        Intent intent = new Intent(getActivity(), ChessActivity.class);

        Bundle bundle_chess = new Bundle();
        bundle_chess.putString("player_name_1", player1);
        bundle_chess.putString("player_name_2", player2);
        bundle_chess.putInt("gameID", gameID);
        bundle_chess.putInt("player", player);

        intent.putExtras(bundle_chess);

        startActivity(intent);
    }
}