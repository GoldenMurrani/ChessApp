package edu.msu.murraniy.project1;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PromoteDlg extends DialogFragment {

    private int pos = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the title
        builder.setTitle(R.string.promote);

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Pass null as the parent view because its going in the dialog layout
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.promotion_dlg, null);
        builder.setView(view);

        AlertDialog dlg = builder.create();

        // Find the list view
        ListView list = (ListView)view.findViewById(R.id.listPieces);


        // Create an adapter
        final ChessActivity.CatalogAdapter adapter = new ChessActivity.CatalogAdapter();

        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    pos = 0;
                }else if(position == 1){
                    pos = 1;
                }else if(position == 2){
                    pos = 2;
                }else if(position == 3){
                    pos = 3;
                }
            }
        });

        return dlg;
    }

    /**
     * Get the position
     * @return
     */
    public int getPos() {
        return pos;
    }


}
