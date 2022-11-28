package edu.msu.murraniy.project1.Cloud;

import edu.msu.murraniy.project1.Cloud.Models.CreateUser;

import edu.msu.murraniy.project1.Cloud.Models.ValidateUser;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static edu.msu.murraniy.project1.Cloud.Cloud.CREATEUSER_PATH;
import static edu.msu.murraniy.project1.Cloud.Cloud.VALIDATEUSER_PATH;


public interface ChessService {

    @FormUrlEncoded
    @POST(CREATEUSER_PATH)
    Call<CreateUser> createUser(
            @Field("user") String userId,
            @Field("magic") String magic,
            @Field("pw") String password
    );

    @GET(VALIDATEUSER_PATH)
    Call<ValidateUser> validateUser(
            @Field("user") String userId,
            @Field("magic") String magic,
            @Field("pw") String password
    );
}
