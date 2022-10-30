package edu.msu.murraniy.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WinnerActivity extends AppCompatActivity {

    /**
     * Player 1 Name
     */
    private String player_1;

    /**
     * Player 2 Name
     */
    private String player_2;

    /**
     * Current Player Turn
     */
    private int currentTurn;

    private TextView winnerText;
    private TextView loserText;

    private String winMessage;
    private String loseMessage;

    private boolean resigned = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle info = getIntent().getExtras();
        player_1 = info.getString("player_1");
        player_2 = info.getString("player_2");
        currentTurn = info.getInt("current_turn");
        resigned = info.getBoolean("resigned");

        setContentView(R.layout.activity_winner);

        winnerText = (TextView)findViewById(R.id.winnerText);
        loserText = (TextView)findViewById(R.id.loserText);


        checkWinner(resigned);

    }


    public void onPlayAgain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void checkWinner(boolean resigned){
        if(resigned){
            if(currentTurn == 1){
                winMessage = player_2 + " " + getString(R.string.winText);
                loseMessage = player_1 + " " + getString(R.string.loseText);
            }else{
                winMessage = player_1 + " " + getString(R.string.winText);
                loseMessage = player_2 + " " + getString(R.string.loseText);
            }

        }else{

        }

        winnerText.setText(winMessage);
        loserText.setText(loseMessage);

    }
}