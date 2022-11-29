package edu.msu.murraniy.project1.Cloud;

import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import edu.msu.murraniy.project1.Chess;
import edu.msu.murraniy.project1.Cloud.Models.CreateUser;
import edu.msu.murraniy.project1.Cloud.Models.Move;
import edu.msu.murraniy.project1.Cloud.Models.ValidateUser;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@SuppressWarnings("deprecation")
public class Cloud {
    private static final String MAGIC = "NechAtHa6RuzeR8x";
    private static final String BASE_URL = "https://webdev.cse.msu.edu/~kroskema/cse476/project2/";
    public static final String CREATEUSER_PATH = "chess-createuser.php";
    public static final String CREATEGAME_PATH = "chess-creategame.php";
    public static final String DELETEGAME_PATH = "chess-deletegame.php";
    public static final String GETGAMESTATE_PATH = "chess-getgamestate.php";
    public static final String VALIDATEUSER_PATH = "chess-validateuser.php";
    public static final String MOVE_PATH = "chess-move.php";
    private static final String UTF8 = "UTF-8";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    public boolean createUser(String username, String password) {

        username = username.trim();
        password = password.trim();

        if(username.length() == 0){
            return false;
        }
        if(password.length() == 0){
            return false;
        }

        ChessService service = retrofit.create(ChessService.class);

        try{
            Response<CreateUser> response = service.createUser(username, MAGIC, password).execute();

            if(response.isSuccessful()){
                CreateUser result = response.body();

                if (result.getStatus() != null && result.getStatus().equals("yes")) {
                    return true;
                }
                Log.e("CreateUser", "Failed to create, message = '" + result.getMessage() + "'");
                return false;

            }
            Log.e("CreateUser", "Failed to create, message = '" + response.code() + "'");
            return false;

        }catch (IOException e){
            Log.e("CreateUser", "Exception occurred while trying to create user!", e);
            return false;
        }catch (RuntimeException e) {	// to catch xml errors to help debug step 6
            Log.e("CreateUser", "Runtime Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean validateUser(String username, String password) throws IOException, RuntimeException {

        username = username.trim();
        password = password.trim();

        if(username.length() == 0){
            return false;
        }
        if(password.length() == 0){
            return false;
        }

        ChessService service = retrofit.create(ChessService.class);

        try{
            Response<ValidateUser> response = service.validateUser(username, MAGIC, password).execute();

            if(response.isSuccessful()){
                ValidateUser result = response.body();

                if (result.getStatus() != null && result.getStatus().equals("yes")) {
                    return true;
                }
                Log.e("ValidateUser", "Failed to validate, message = '" + result.getMessage() + "'");
                return false;

            }
            Log.e("ValidateUser", "Failed to create, message = '" + response.code() + "'");
            return false;

        }catch (IOException e){
            Log.e("ValidateUser", "Exception occurred while trying to validate user!", e);
            return false;
        }catch (RuntimeException e) {	// to catch xml errors to help debug step 6
            Log.e("ValidateUser", "Runtime Exception: " + e.getMessage());
            return false;
        }
    }

    // Updates the game with the most recent move
    public boolean movePiece(int gameId, int pieceId, int pieceX, int pieceY) {
        ChessService service = retrofit.create(ChessService.class);

        try{
            Response<Move> response = service.movePiece(MAGIC, gameId, pieceId, pieceX, pieceY).execute();

            if(response.isSuccessful()){
                Move result = response.body();

                if (result.getStatus() != null && result.getStatus().equals("yes")) {
                    return true;
                }
                Log.e("Move", "Failed to validate, message = '" + result.getMessage() + "'");
                return false;

            }
            Log.e("Move", "Failed to create, message = '" + response.code() + "'");
            return false;

        }catch (IOException e){
            Log.e("Move", "Exception occurred while trying to move piece!", e);
            return false;
        }catch (RuntimeException e) {	// to catch xml errors to help debug step 6
            Log.e("Move", "Runtime Exception: " + e.getMessage());
            return false;
        }
    }
}

