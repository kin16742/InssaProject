package project.hs.inssaproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class one_profile extends AppCompatActivity {
    TextView profile_id;
    TextView profile_name;
    TextView profile_age;
    TextView profile_grade;
    TextView profile_sex;
    TextView profile_saying;
    TextView profile_major;
    ImageView image_select;
    Button btn_back;
    Button btn_loving;
    Intent intent;
    String show_id;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_profile);

        intent = getIntent();
        show_id = intent.getStringExtra("profile_id");

        profile_id = (TextView)findViewById(R.id.profile_id);
        profile_name = (TextView)findViewById(R.id.profile_name);
        profile_age = (TextView)findViewById(R.id.profile_age);
        profile_grade = (TextView)findViewById(R.id.profile_grade);
        profile_sex = (TextView)findViewById(R.id.profile_sex);
        profile_saying = (TextView)findViewById(R.id.profile_saying);
        profile_major = (TextView)findViewById(R.id.profile_major);
        image_select = (ImageView)findViewById(R.id.image_select);
        btn_back = (Button)findViewById(R.id.btn_back);
        btn_loving = (Button)findViewById(R.id.btn_loving);

        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent_profiles = new Intent(one_profile.this, profiles.class);
                startActivity(intent_profiles);
                finish();
            }
        });

    }
    private void showProfile() {

        Req_number req_number = new Req_number(show_id);
        Log.d("show_id", show_id);
        Retrofit retrofit =new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.BASEURL)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<User> res = apiService.showProfile(req_number);
        //res = Net.getInstance().getApiService().join(user);
        res.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if(response.body() != null) {
                        User user = response.body();
                        Log.d("response body", response.body().toString());
                        profile_id.setText(user.getUser_id());
                        profile_name.setText(user.getUser_name());
                        profile_age.setText(Integer.toString(user.getUser_age()));
                        profile_grade.setText(Integer.toString(user.getUser_grade()));
                        profile_sex.setText(user.getUser_sex());
                        profile_saying.setText(user.getUser_saying());
                        profile_major.setText(user.getUser_major());
                        final String tmpUser_img = user.getUser_img();
                        Thread thread = new Thread(){
                            @Override
                            public void run(){
                                try{
                                    URL url = new URL("http://54.180.32.249:3000/uploads/" + tmpUser_img);
                                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                    conn.setDoInput(true);
                                    conn.connect();

                                    InputStream is = conn.getInputStream();
                                    bm = BitmapFactory.decodeStream(is);
                                }catch (MalformedURLException e){
                                    e.printStackTrace();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        thread.start();
                        try{
                            thread.join();
                            image_select.setImageBitmap(bm);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
