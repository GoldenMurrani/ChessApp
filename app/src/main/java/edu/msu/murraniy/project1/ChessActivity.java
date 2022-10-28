package edu.msu.murraniy.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ChessActivity extends AppCompatActivity {

    /**
     * Player 1 Name
     */
    private String playerOnen;

    /**
     * Player 2 Name
     */
    private String playerTwon;

    /**
     * ChessView
     */
    private ChessView view;

    private int playerTurn = 1;

    private TextView tv1;

    private String messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

        Bundle temp = getIntent().getExtras();
        playerOnen = temp.getString("player_name_1");
        playerTwon = temp.getString("player_name_2");

        view = (ChessView)this.findViewById(R.id.chessView);
        view.setActivity(this);

        tv1 = (TextView)findViewById(R.id.playerText);


        setPlayerTurnText(playerTurn);
    }

    public void changeTurn(){
        if(playerTurn == 1){
            playerTurn = 2;
        }else if(playerTurn == 2){
            playerTurn = 1;
        }

        setPlayerTurnText(playerTurn);
    }

    private void setPlayerTurnText(int player){

        switch(player){
            case 1:
                messageText = playerOnen + getString(R.string.turn_suffix);
                break;
            case 2:
                messageText = playerTwon + getString(R.string.turn_suffix);
                break;
        }

        tv1.setText(messageText);
    }
}