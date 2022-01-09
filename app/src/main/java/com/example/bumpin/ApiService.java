package com.example.bumpin;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    public static final String API_URL = "http://bfaf-143-248-229-108.ngrok.io/";

    /*
    @Path =  (@Path("pk") int pk)에서 pk는 윗줄에 rest api어노테이션 뒤에 사용하는 변수
    @Query =  (@Query("example") String test)에서 test는 변수, 입력을 aaaa로 받았을때 url은  xxxxx?example=aaaa
    @Field =  (@Field("email") String test)에서 test는 데이터베이스의 필드 하나에 해당함 즉 email 레코드의 필드 하나 값
    @FormUrlEncoded = 입력된 스트링이나 해시맵을을데이터베이스에 반영될수 있도록 인코딩해줌
    */
    @GET("tests")
    Call<ResponseBody> get_Test(@Query("format") String json);

    // @PATH을 이용해 db data 수정 삭제
    // django 에서 test 라는 charfield 하나만 만들었지만
    // 자동으로 id 라는 primary key이자 autoincrement 속성을 가지는 intfield가 하나 있기 때문
    // 고유값으로 id 3을 삭제 후, add 해도 id가 3이 되지 않음 -> 고유값이기 떄문
    @POST("tests/")
    Call<Json_Test_Java> post_json_test_java(@Query("format") String json, @Body Json_Test_Java json_test_java);

    @FormUrlEncoded
    @PATCH("tests/{pk}/")
    Call<ResponseBody> patch_Test(@Path("pk") int pk, @Query("format") String json, @Field("test") String test);

    @DELETE("tests/{pk}/")
    Call<ResponseBody> delete_Test(@Path("pk") int pk, @Query("format") String json);

}