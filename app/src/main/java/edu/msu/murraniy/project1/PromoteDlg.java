package edu.msu.murraniy.project1;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PromoteDlg extends DialogFragment {
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

        return dlg;
    }


}
