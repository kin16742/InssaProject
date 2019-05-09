package project.hs.inssaproject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    static final String BASEURL  = "http://54.180.32.249:3000";
    //POST 메소드를 통한 http rest api통신
    //Call<받아올 데이터의 형태> 메소드명 (@Header("정의하고 싶은 속성 이름") 자료형 변수명,
    //
    //@Query("변수 이름") 자료형 변수명);
    //
    //
    //
    //출처: http://www.feelteller.com/6 [김정헌의 안드로이드 코딩 여행기]
    /*

    @POST("signup_test")
    Call<User> signupAttachment(@Query("user_id") String user_id,
                                        @Query("user_pw") String user_pw,
                                        @Query("user_name") String user_name,
                                        @Query("user_age") int user_age,
                                        @Query("user_saying") String user_saying,
                                        @Query("user_major") String user_major,
                                        @Query("user_sex") String user_sex,
                                        @Query("user_grade") int user_grade
                                        );
    */
    @POST("signup_test")
    Call<Res_join> join(@Body User user); //보낼때는 Req로 보내고 받을 때는 Res로 받음.

    @POST("login")
    Call<Res_img> login(@Body Req_login req_login);

    @POST("numberSetting")
    Call<Res_number> numberSetting(@Body Req_number req_number);

    @POST("showProfile")
    Call<User> showProfile(@Body Req_number req_number);

    @POST("allProfile")
    Call<List<User>> allProfile();

    @Multipart
    @POST("imgUpload/{userID}")
    Call<Res_img> uploadAttachment(@Part MultipartBody.Part file, @Path("userID") String userID);//@Part("description") RequestBody description);@Path("filename") String filename);//, @Part("name") RequestBody description);
    //Call<JsonObject> getSuccess (@Query("getSuc);
}
