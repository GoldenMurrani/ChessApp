package edu.msu.murraniy.project1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import edu.msu.murraniy.project1.Cloud.Cloud;

public class MainActivity extends AppCompatActivity {

    /**
     * bundle keys
     */
    private final static String NAMES = "MainActivity.Names";
    private final static String POPUPACTIVE = "MainActivity.PopupActive";
    private final static String USERPOPUP = "MainActivity.UserPopup";
    private static final String CHECKBOX = "MainActivity.CheckBox";

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog.Builder dialogBuilder2;
    private AlertDialog dialog;
    private AlertDialog dialog2;
    private EditText startpopup_user, startpopup_pass, new_user, new_pass, new_pass2;
    private Button startpopup_login, startpopup_cancel, startpopup_newuser, newuser_create, newuser_cancel;
    private CheckBox check_box;

    private String username = "";
    private String password = "";
    private String new_username, new_password, new_passwordA;
    /**
     * popupActive used to display popup on changing device orientation.
     */
    boolean popupActive = false;
    boolean userPopUp = false;
    boolean checkbox = false;

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
            startpopup_user.setText(username);
            startpopup_pass.setText(password);
        }
        if(userPopUp){
            createNewUserContactDialog();
        }
    }

    public void onStart(View view) {
        popupActive = true;
        createNewContactDialog();
    }

    public void createNewContactDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View startPopUp = getLayoutInflater().inflate(R.layout.startpopup, null);

        check_box = (CheckBox) startPopUp.findViewById(R.id.checkBox);
        startpopup_user = (EditText) startPopUp.findViewById(R.id.startpopup_p_one);
        startpopup_pass = (EditText) startPopUp.findViewById(R.id.startpopup_p_two);

        startpopup_login = (Button) startPopUp.findViewById(R.id.loginButton);
        startpopup_cancel = (Button) startPopUp.findViewById(R.id.cancelButton);
        startpopup_newuser = (Button) startPopUp.findViewById(R.id.newUser);

        dialogBuilder.setView(startPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        check_box.setChecked(checkbox);

        if(checkbox){
            startpopup_user.setText(username);
            startpopup_pass.setText(password);
        }

        check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkbox = !checkbox;
            }
        });

        startpopup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = startpopup_user.getText().toString();
                password = startpopup_pass.getText().toString();

                onLogin(v);
            }
        });

        startpopup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupActive = false;

                if(!checkbox){
                    username = "";
                    password = "";
                }
                dialog.dismiss();
            }
        });

        startpopup_newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String username = startpopup_playerone.getText().toString();
//                String password = startpopup_playertwo.getText().toString();
//
//                username = toTitleCase(playerOneN);
//                password = toTitleCase(playerTwoN);

                // Create cloud and call create user here
//                Cloud cloud = new Cloud();
//                boolean result = cloud.createUser(username, password);
                // Handle result here? Create a toast if fail?
                userPopUp = true;
                createNewUserContactDialog();
            }
        });

        startpopup_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
                username = startpopup_user.getText().toString();
                password = startpopup_pass.getText().toString();
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final boolean ok;
                try {
                    ok = cloud.validateUser(username, password);

                    if(!ok) {
                        /*
                         * If validation fails, display a toast
                         */
                        view.post(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(view.getContext(),
                                        R.string.validation_fail,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else{
                        AvailableGamesActivity availableGames = new AvailableGamesActivity();
                        availableGames.show(getSupportFragmentManager(), "load");
                    }
                } catch (Exception e) {
                    // Error condition! Something went wrong
                    Log.e("LoginButton", "Something went wrong when validating user", e);
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(view.getContext(), R.string.validation_fail, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public void createNewUserContactDialog(){
        dialogBuilder2 = new AlertDialog.Builder(this);
        final View createUserPopUp = getLayoutInflater().inflate(R.layout.newuserpopup, null);

        new_user = (EditText) createUserPopUp.findViewById(R.id.newusername);
        new_pass = (EditText) createUserPopUp.findViewById(R.id.newpass);
        new_pass2 = (EditText) createUserPopUp.findViewById(R.id.newpassword2);

        newuser_create = (Button) createUserPopUp.findViewById(R.id.createNewButton);
        newuser_cancel = (Button) createUserPopUp.findViewById(R.id.cancelUserButton);

        dialogBuilder2.setView(createUserPopUp);
        dialog2 = dialogBuilder2.create();
        dialog2.show();

        newuser_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPopUp = false;
                dialog2.dismiss();
            }
        });

        newuser_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_username = new_user.getText().toString();
                new_password = new_pass.getText().toString();
                new_passwordA = new_pass2.getText().toString();

                if(new_password.matches(new_passwordA)){

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Cloud cloud = new Cloud();
                            final boolean result;
                            try {
                                result = cloud.createUser(new_username, new_password);

                                if(!result) {
                                    /*
                                     * If validation fails, display a toast
                                     */
                                    v.post(new Runnable() {

                                        @Override
                                        public void run() {
                                            Toast.makeText(v.getContext(),
                                                    R.string.newuser_fail,
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else{
                                    userPopUp = false;
                                    dialog2.dismiss();
                                }
                            } catch (Exception e) {
                                // Error condition! Something went wrong
                                Log.e("CreateUserButton", "Something went wrong when creating user", e);
                                v.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(v.getContext(), R.string.newuser_fail, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();

                }else{
                    Toast.makeText(v.getContext(), R.string.password_val, Toast.LENGTH_SHORT).show();
                }

            }
        });

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
        names[0] = username;
        names[1] = password;
        bundle.putStringArray(NAMES, names);

        bundle.putBoolean(POPUPACTIVE, popupActive);
        bundle.putBoolean(USERPOPUP, userPopUp);
        bundle.putBoolean(CHECKBOX, checkbox);
    }

    /**
     * Read the player names from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        String [] names = bundle.getStringArray(NAMES);
        username = names[0];
        password = names[1];

        popupActive = bundle.getBoolean(POPUPACTIVE);
        userPopUp = bundle.getBoolean(USERPOPUP);
        checkbox = bundle.getBoolean(CHECKBOX);
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