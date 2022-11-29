package edu.msu.murraniy.project1.Cloud;

import edu.msu.murraniy.project1.Cloud.Models.Catalog;
import edu.msu.murraniy.project1.Cloud.Models.CreateUser;

import edu.msu.murraniy.project1.Cloud.Models.Move;
import edu.msu.murraniy.project1.Cloud.Models.ValidateUser;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static edu.msu.murraniy.project1.Cloud.Cloud.CREATEUSER_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.GETGAMESTATE_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.MOVE_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.VALIDATEUSER_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.GETGAMES_PATH;


public interface ChessService {

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

    @FormUrlEncoded
    @POST(MOVE_PATH)
    Call<Move> movePiece(
            @Field("magic") String magic,
            @Field("game") int gameId,
            @Field("piece") int pieceId,
            @Field("x") int pieceX,
            @Field("y") int pieceY
    );
}
