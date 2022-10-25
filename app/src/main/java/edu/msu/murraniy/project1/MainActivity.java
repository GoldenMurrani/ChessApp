package edu.msu.murraniy.project1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText startpopup_playerone, startpopup_playertwo;
    private Button startpopup_start, startpopup_cancel;

    private String playerOneN = "Player 1";
    private String playerTwoN = "Player 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStart(View view) {
        createNewContactDialog();
    }

    public void createNewContactDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View startPopUp = getLayoutInflater().inflate(R.layout.startpopup, null);

        startpopup_playerone = (EditText) startPopUp.findViewById(R.id.startpopup_p_one);
        startpopup_playertwo = (EditText) startPopUp.findViewById(R.id.startpopup_p_two);

        startpopup_start = (Button) startPopUp.findViewById(R.id.startButton);
        startpopup_cancel = (Button) startPopUp.findViewById(R.id.cancelButton);

        dialogBuilder.setView(startPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        startpopup_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerOneN = startpopup_playerone.getText().toString();
                playerTwoN = startpopup_playertwo.getText().toString();
                onStartMatch(v);
            }
        });

        startpopup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void onStartMatch(View view) {
        Intent intent = new Intent(this, ChessActivity.class);

        Bundle bundle_names = new Bundle();
        bundle_names.putString("player_name_1", playerOneN);
        bundle_names.putString("player_name_2", playerTwoN);

        intent.putExtras(bundle_names);

        startActivity(intent);
    }
}