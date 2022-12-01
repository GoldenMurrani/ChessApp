package edu.msu.murraniy.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.msu.murraniy.project1.Cloud.Cloud;

public class ChessActivity extends AppCompatActivity {

    /**
     * bundle keys
     */
    private final static String PLAYERTURN = "ChessActivity.PlayerTurn";

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

    private int gameID;
    private int player;

    /**
     * Get the puzzle view
     * @return PuzzleView reference
     */
    private ChessView getChessView() {
        return (ChessView)this.findViewById(R.id.chessView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Cloud cloud = new Cloud();
        cloud.deleteGame(gameID);

    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_chess);

        Bundle temp = getIntent().getExtras();
        playerOnen = temp.getString("player_name_1");
        playerTwon = temp.getString("player_name_2");

        gameID = temp.getInt("gameID");
        player = temp.getInt("player");


        chessView = (ChessView)this.findViewById(R.id.chessView);
        chessView.setActivity(this);
        chessView.setChessGameId(gameID);
        chessView.setChessSide(player);

        tv1 = (TextView)findViewById(R.id.playerText);


        setPlayerTurnText(playerTurn);

        if(bundle != null) {
            // We have saved state
            loadInstanceState(bundle);
            setPlayerTurnText(playerTurn);
            getChessView().loadInstanceState(bundle);
        }
    }

    /**
     * Load the Player Turn from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        playerTurn = bundle.getInt(PLAYERTURN);
    }

    /**
     * Save the instance state into a bundle
     * @param bundle the bundle to save into
     */
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putInt(PLAYERTURN, playerTurn);
        getChessView().saveInstanceState(bundle);
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

    /**
     * Opens the dialog box for pawn promotion
     */
    public void promotionDialog() {
        PromoteDlg dlg = new PromoteDlg();
        dlg.show(getSupportFragmentManager(), "promotion");
    }

    /**
     * Sends the promotion number to chess view
     * @param position
     */
    public void piecePromotion(int position){
        chessView.updatePawn(position);
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