package edu.msu.murraniy.project1.Cloud;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import edu.msu.murraniy.project1.Chess;
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
    private static final String USER = "nav";
    private static final String PASSWORD = "navpass";
    private static final String UTF8 = "UTF-8";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    public boolean createUser(String username, String password) { return true; }

    public boolean validateUser() throws IOException, RuntimeException {
        ChessService service = retrofit.create(ChessService.class);

        Response<ValidateUser> response = service.validateUser(USER, PASSWORD, MAGIC).execute();

        if(!response.isSuccessful()){
            Log.e("validateUser", "Failed to validate user, response code is = " + response.code());
            return false;
        }
        return true;

    }

    /*// Create a GET query
    public Catalog getCatalog() throws IOException, RuntimeException {
        HatterService service = retrofit.create(HatterService.class);

        Response<Catalog> response = service.getCatalog(USER, MAGIC, PASSWORD).execute();
        // check if request failed
        if (!response.isSuccessful()) {
            Log.e("getCatalog", "Failed to get catalog, response code is = " + response.code());
            return new Catalog("no", new ArrayList<Item>(), "Server error " + response.code());
        }
        Catalog catalog = response.body();
        if (catalog.getStatus().equals("no")) {
            String string = "Failed to get catalog, msg is = " + catalog.getMessage();
            Log.e("getCatalog", string);
            return new Catalog("no", new ArrayList<Item>(), string);
        };
        if (catalog.getItems() == null) {
            catalog.setItems(new ArrayList<Item>());
        }
        return catalog;
    }

    *//**
     * Open a connection to a hatting in the cloud.
     * @param id id for the hatting
     * @return reference to an input stream or null if this fails
     *//*

    public Hat openFromCloud(final String id) {
        HatterService service = retrofit.create(HatterService.class);
        try {
            Response<LoadResult> response = service.loadHat(USER, MAGIC, PASSWORD, id).execute();

            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("OpenFromCloud", "Failed to load hat, response code is = " + response.code());
                return null;
            }

            LoadResult result = response.body();
            if (result.getStatus().equals("yes")) {
                return result.getHat();
            }

            Log.e("OpenFromCloud", "Failed to load hat, message is = '" + result.getMessage() + "'");
            return null;
        } catch (IOException | RuntimeException e) {
            Log.e("OpenFromCloud", "Exception occurred while loading hat!", e);
            return null;
        }
    }*/
}

