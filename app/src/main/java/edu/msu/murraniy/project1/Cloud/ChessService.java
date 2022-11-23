package edu.msu.murraniy.project1.Cloud;

import edu.msu.murraniy.project1.Cloud.Models.CreateUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static edu.msu.murraniy.project1.Cloud.Cloud.CREATEUSER_PATH;


public interface ChessService {

    @POST(CREATEUSER_PATH)
    Call<CreateUser> createUser(
            @Field("user") String userId,
            @Field("magic") String magic,
            @Field("pw") String password,
            @Field("xml") String xmlData
    );
    
}
