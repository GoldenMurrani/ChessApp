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



    }

}