package edu.msu.murraniy.project1;

import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.msu.murraniy.project1.Cloud.Cloud;

public class AvailableGamesActivity extends DialogFragment {

    private AlertDialog dlg;

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


        // Find the list view
        ListView list = (ListView)view.findViewById(R.id.listGames);

        /*final Cloud.CatalogAdapter adapter = new Cloud.CatalogAdapter(list);
        list.setAdapter(adapter);*/

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
}