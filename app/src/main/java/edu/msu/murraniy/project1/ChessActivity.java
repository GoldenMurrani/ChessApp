package edu.msu.murraniy.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
    private ChessView chessView;

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

        chessView = (ChessView)this.findViewById(R.id.chessView);
        chessView.setActivity(this);

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
//        chessView.changeChessTurn();
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

    public void onDone(View view){
        //uncomment to make the done button bring up promotion dialog for debugging
        //PromoteDlg dlg = new PromoteDlg();
        //dlg.show(getSupportFragmentManager(), "promotion");
        chessView.changeChessTurn();
    }

    public void onResign(View view){
        Intent intent = new Intent(this, WinnerActivity.class);

        Bundle bundle_info = new Bundle();
        bundle_info.putString("player_1", playerOnen);
        bundle_info.putString("player_2", playerTwon);
        bundle_info.putInt("current_turn", playerTurn);
        bundle_info.putBoolean("resigned", true);

        intent.putExtras(bundle_info);

        startActivity(intent);
    }

    public void callGame(int playerNum){
        Intent intent = new Intent(this, WinnerActivity.class);

        Bundle bundle_info = new Bundle();
        bundle_info.putString("player_1", playerOnen);
        bundle_info.putString("player_2", playerTwon);
        bundle_info.putInt("current_turn", playerTurn);
        bundle_info.putBoolean("resigned", false);

        intent.putExtras(bundle_info);

        startActivity(intent);
    }

    public void piecePromotion(ChessPiece piece){
        PromoteDlg dlg = new PromoteDlg();
        dlg.show(getSupportFragmentManager(), "promotion");

        int position = dlg.getPos();

        if(piece.getTeam() == Chess.Team.WHITE){

            if(position == 0){
                piece.setId(R.drawable.queen_white);
                piece.setType(Chess.Type.QUEEN);
            }else if(position == 1){
                piece.setId(R.drawable.rook_white);
                piece.setType(Chess.Type.ROOK);
            }else if(position == 2){
                piece.setId(R.drawable.knight_white);
                piece.setType(Chess.Type.KNIGHT);
            }else if(position == 3){
                piece.setId(R.drawable.bishop_white);
                piece.setType(Chess.Type.BISHOP);
            }

        }
        if(piece.getTeam() == Chess.Team.BLACK){

            if(position == 0){
                piece.setId(R.drawable.queen_black);
                piece.setType(Chess.Type.QUEEN);
            }else if(position == 1){
                piece.setId(R.drawable.rook_black);
                piece.setType(Chess.Type.ROOK);
            }else if(position == 2){
                piece.setId(R.drawable.knight_black);
                piece.setType(Chess.Type.KNIGHT);
            }else if(position == 3){
                piece.setId(R.drawable.bishop_black);
                piece.setType(Chess.Type.BISHOP);
            }

        }

    }

    /**
     * An adapter so that list boxes can display a list of filenames from
     * the cloud server.
     */
    public static class CatalogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.promotion_item, viewGroup, false);
            }

            TextView tv = (TextView)view.findViewById(R.id.textItem);

            switch(i){
                case 0:
                    tv.setText(R.string.queen);
                    break;
                case 1:
                    tv.setText(R.string.rook);
                    break;
                case 2:
                    tv.setText(R.string.knight);
                    break;
                case 3:
                    tv.setText(R.string.bishop);
                    break;
            }


            return view;
        }
    }
}