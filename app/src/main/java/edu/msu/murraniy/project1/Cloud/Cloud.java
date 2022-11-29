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
import edu.msu.murraniy.project1.Cloud.Models.Catalog;
import edu.msu.murraniy.project1.Cloud.Models.CreateUser;
import edu.msu.murraniy.project1.Cloud.Models.Game;
import edu.msu.murraniy.project1.Cloud.Models.ValidateUser;
import edu.msu.murraniy.project1.R;
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
    public static final String GETGAMES_PATH = "chess-getgames.php";
    private static final String USER = "nav";
    private static final String PASSWORD = "navpass";
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

    /**
     * An adapter so that list boxes can display a list of filenames from
     * the cloud server.
     */
    public static class CatalogAdapter extends BaseAdapter {

        /**
         * The items we display in the list box. Initially this is
         * null until we get items from the server.
         */
        private Catalog catalog = new Catalog("", new ArrayList(), "");

        /**
         * Constructor
         */
        public CatalogAdapter(final View view) {
            // Create a thread to load the catalog
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        catalog = getCatalog();

                        if (catalog.getStatus().equals("no")) {
                            String msg = "Loading catalog returned status 'no'! Message is = '" + catalog.getMessage() + "'";
                            throw new Exception(msg);
                        }
                        if (catalog.getItems().isEmpty()) {
                            String msg = "Catalog does not contain any games.";
                            throw new Exception(msg);
                        }
                        view.post(new Runnable() {

                            @Override
                            public void run() {
                                // Tell the adapter the data set has been changed
                                notifyDataSetChanged();
                            }

                        });
                    } catch (final Exception e) {
                        // Error condition! Something went wrong
                        Log.e("CatalogAdapter", "Something went wrong when loading the catalog", e);
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                String string;
                                // make sure that there is a message in the catalog
                                // if there isn't use the message from the exception
                                if (catalog.getMessage() == null) {
                                    string = e.getMessage();
                                } else {
                                    string = catalog.getMessage();
                                }
                                Toast.makeText(view.getContext(), string, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }

        // Create a GET query
        public Catalog getCatalog() throws IOException, RuntimeException {
            ChessService service = retrofit.create(ChessService.class);
            //return service.getCatalog(USER, MAGIC, PASSWORD).execute().body();
            Response<Catalog> response = service.getCatalog(USER, MAGIC, PASSWORD).execute();
            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("getCatalog", "Failed to get catalog, response code is = " + response.code());
                return new Catalog("no", new ArrayList<Game>(), "Server error " + response.code());
            }
            Catalog catalog = response.body();
            if (catalog.getStatus().equals("no")) {
                String string = "Failed to get catalog, msg is = " + catalog.getMessage();
                Log.e("getCatalog", string);
                return new Catalog("no", new ArrayList<Game>(), string);
            };
            if (catalog.getItems() == null) {
                catalog.setItems(new ArrayList<Game>());
            }
            return catalog;
        }

        @Override
        public int getCount() {
            return catalog.getItems().size();
        }

        @Override
        public Game getItem(int position) {
            return catalog.getItems().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_item, parent, false);
            }

            TextView tv = (TextView)view.findViewById(R.id.textItem);
            tv.setText(catalog.getItems().get(position).getName());

            return view;
        }

        public String getId(int position) {
            return catalog.getItems().get(position).getId();
        }
        public String getName(int position) {
            return catalog.getItems().get(position).getName();
        }
    }

}

