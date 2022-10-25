package edu.msu.murraniy.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ChessActivity extends AppCompatActivity {

    /**
     * Player 1 Name
     */
    private String playerOnen;
    /**
     * Player 2 Name
     */
    private String playerTwon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

        Bundle temp = getIntent().getExtras();
        playerOnen = temp.getString("player_name_1");
        playerTwon = temp.getString("player_name_2");

        ChessView view = (ChessView)this.findViewById(R.id.chessView);
        view.setPlayerOneName(playerOnen);
        view.setPlayerTwoName(playerTwon);

        TextView tv1 = (TextView)findViewById(R.id.playerText);
        tv1.setText(playerOnen + "'s Turn");

    }

}