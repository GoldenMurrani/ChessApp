package edu.msu.murraniy.project1;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PromoteDlg extends DialogFragment {

    private int position;

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

        // list of pieces that the pawn can be promoted to
        String[] promotedPieces = new String[] {"Queen", "Rook", "Knight", "Bishop", "Pawn (No Change)"};

        builder.setItems(R.array.promotion_array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position = which;
                String s = promotedPieces[which];
                Toast.makeText(getActivity(), "Pawn Promoted to " + s, Toast.LENGTH_SHORT).show();
                ((ChessActivity)getActivity()).piecePromotion(position);
            }
        });


        // Create an adapter
        //final ChessActivity.CatalogAdapter adapter = new ChessActivity.CatalogAdapter();

        //list.setAdapter(adapter);

        return builder.create();
    }

}
