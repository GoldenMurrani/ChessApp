package edu.msu.murraniy.project1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.msu.murraniy.project1.Cloud.Cloud;

public class MainActivity extends AppCompatActivity {

    /**
     * bundle keys
     */
    private final static String NAMES = "MainActivity.Names";
    private final static String POPUPACTIVE = "MainActivity.PopupActive";

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText startpopup_playerone, startpopup_playertwo;
    private Button startpopup_login, startpopup_cancel, startpopup_newuser;

    private String playerOneN = "Player 1";
    private String playerTwoN = "Player 2";
    /**
     * popupActive used to display popup on changing device orientation.
     */
    boolean popupActive = false;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        if(bundle != null) {
            // We have saved state
            loadInstanceState(bundle);
        }

        if (popupActive){
            createNewContactDialog();
            startpopup_playerone.setText(playerOneN);
            startpopup_playertwo.setText(playerTwoN);
        }
    }

    public void onStart(View view) {
        popupActive = true;
        createNewContactDialog();
    }

    public void createNewContactDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View startPopUp = getLayoutInflater().inflate(R.layout.startpopup, null);

        startpopup_playerone = (EditText) startPopUp.findViewById(R.id.startpopup_p_one);
        startpopup_playertwo = (EditText) startPopUp.findViewById(R.id.startpopup_p_two);

        startpopup_login = (Button) startPopUp.findViewById(R.id.loginButton);
        startpopup_cancel = (Button) startPopUp.findViewById(R.id.cancelButton);
        startpopup_newuser = (Button) startPopUp.findViewById(R.id.newUser);

        dialogBuilder.setView(startPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        startpopup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerOneN = startpopup_playerone.getText().toString();
                playerTwoN = startpopup_playertwo.getText().toString();

                if(playerOneN.matches("")){
                    playerOneN = "Player 1";
                }
                if(playerTwoN.matches("")){
                    playerTwoN = "Player 2";
                }

                playerOneN = toTitleCase(playerOneN);
                playerTwoN = toTitleCase(playerTwoN);

                onLogin(v);
            }
        });

        startpopup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupActive = false;
                playerOneN = "";
                playerTwoN = "";
                dialog.dismiss();
            }
        });

        startpopup_newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = startpopup_playerone.getText().toString();
                String password = startpopup_playertwo.getText().toString();

                username = toTitleCase(playerOneN);
                password = toTitleCase(playerTwoN);

                // Create cloud and call create user here
                Cloud cloud = new Cloud();
                boolean result = cloud.createUser(username, password);
                // Handle result here? Create a toast if fail?
            }
        });

        startpopup_playertwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
                playerOneN = startpopup_playerone.getText().toString();
                playerTwoN = startpopup_playertwo.getText().toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void onLogin(View view) {
        popupActive = false;
        Intent intent = new Intent(this, AvailableGamesActivity.class);

        Bundle bundle_names = new Bundle();
        bundle_names.putString("player_name_1", playerOneN);
        bundle_names.putString("player_name_2", playerTwoN);

        intent.putExtras(bundle_names);



        startActivity(intent);
    }

    public void onHowToPlay(View view){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(view.getContext());
        // Parameterize the builder
        builder.setTitle(R.string.howToPlay);
        builder.setMessage(R.string.instructions);

        // Create the dialog box and show it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        saveInstanceState(bundle);
    }

    /**
     * Save the puzzle to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        String [] names = new String[2];
        names[0] = playerOneN;
        names[1] = playerTwoN;
        bundle.putStringArray(NAMES, names);

        bundle.putBoolean(POPUPACTIVE, popupActive);
    }

    /**
     * Read the player names from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        String [] names = bundle.getStringArray(NAMES);
        playerOneN = names[0];
        playerTwoN = names[1];

        popupActive = bundle.getBoolean(POPUPACTIVE);
    }

    private String toTitleCase(String input) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (char c : input.toCharArray()) {
            if (i==0) {
                sb.append(Character.toUpperCase(c));
                i += 1;
            }
            else if (c==' '){
                sb.append(c);
                i = 0;
            }
            else {
                sb.append(Character.toLowerCase(c));
                i += 1;
            }
        }
        return sb.toString();
    }


}