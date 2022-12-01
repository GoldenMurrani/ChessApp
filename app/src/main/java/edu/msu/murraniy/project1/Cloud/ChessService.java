package edu.msu.murraniy.project1.Cloud;

import edu.msu.murraniy.project1.Cloud.Models.Catalog;
import edu.msu.murraniy.project1.Cloud.Models.CheckJoin;
import edu.msu.murraniy.project1.Cloud.Models.CheckTurn;
import edu.msu.murraniy.project1.Cloud.Models.CreateGame;
import edu.msu.murraniy.project1.Cloud.Models.CreateUser;

import edu.msu.murraniy.project1.Cloud.Models.DeleteGame;
import edu.msu.murraniy.project1.Cloud.Models.JoinGame;
import edu.msu.murraniy.project1.Cloud.Models.Move;
import edu.msu.murraniy.project1.Cloud.Models.ValidateUser;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static edu.msu.murraniy.project1.Cloud.Cloud.CHECKJOIN_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.CHECKTURN_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.CREATEGAME_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.CREATEUSER_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.DELETEGAME_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.GETGAMESTATE_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.JOINGAME_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.MOVE_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.VALIDATEUSER_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.GETGAMES_PATH;


public interface ChessService {

    @FormUrlEncoded
    @POST(CREATEGAME_PATH)
    Call<CreateGame> createGame(
            @Field("user") String userId,
            @Field("magic") String magic,
            @Field("pw") String password
    );

    @GET(GETGAMES_PATH)
    Call<Catalog> getCatalog(
            @Query("user") String userId,
            @Query("magic") String magic,
            @Query("pw") String password
    );

    @FormUrlEncoded
    @POST(CREATEUSER_PATH)
    Call<CreateUser> createUser(
            @Field("user") String userId,
            @Field("magic") String magic,
            @Field("pw") String password
    );

    @GET(VALIDATEUSER_PATH)
    Call<ValidateUser> validateUser(
            @Query("user") String userId,
            @Query("magic") String magic,
            @Query("pw") String password
    );

    @GET(CHECKTURN_PATH)
    Call<CheckTurn> checkTurn(
            @Query("magic") String magic,
            @Query("game") int gameId
    );

    @GET(CHECKJOIN_PATH)
    Call<CheckJoin> checkJoin(
            @Query("magic") String magic,
            @Query("game") int gameId
    );

    @POST(JOINGAME_PATH)
    Call<JoinGame> joinGame(
            @Query("magic") String magic,
            @Query("game") int gameId,
            @Query("user") String userId
    );

    @POST(DELETEGAME_PATH)
    Call<DeleteGame> deleteGame(
            @Query("magic") String magic,
            @Query("game") int gameId
    );

    @FormUrlEncoded
    @POST(MOVE_PATH)
    Call<Move> movePiece(
            @Field("magic") String magic,
            @Field("game") int gameId,
            @Field("piece") int pieceId,
            @Field("x") int pieceX,
            @Field("y") int pieceY,
            @Field("turn") int turn
    );
}
