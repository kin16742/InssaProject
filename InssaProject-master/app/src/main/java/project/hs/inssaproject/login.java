package project.hs.inssaproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {

    EditText login_id;
    EditText login_pw;
    ImageButton login_btn;
    ImageButton signup_btn;
    String extra_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_id = (EditText) findViewById(R.id.login_id);
        login_pw = (EditText) findViewById(R.id.login_pw);
        login_btn = (ImageButton) findViewById(R.id.login_btn);
        signup_btn = (ImageButton) findViewById(R.id.signup_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new JSONTask().execute("http://54.180.32.249:3000/login");
                login();
            }
        });
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_signup = new Intent(login.this, signup.class);
                startActivity(intent_signup);
                finish();
            }
        });

    }
    public class JSONTask extends AsyncTask<String, String, String> {
        String extra_id;
        @Override
        protected String doInBackground(String... urls) {
            try {
                final String str_id = login_id.getText().toString();
                extra_id = str_id;
                final String str_pw = login_pw.getText().toString();

                Log.d("str_id", str_id);
                Log.d("str_pw", str_pw);

                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("login_id", str_id);
                jsonObject.accumulate("login_pw", str_pw);

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    //URL url = new URL("http://54.180.32.249:3000/signup");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    Log.d("jsonObject", jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("111")) {
                //로그인 성공 alert message
                Intent intent_main = new Intent(login.this, MainActivity.class);
                intent_main.putExtra("user_id", extra_id);
                startActivity(intent_main);
                finish();
            } else {
                //예외처리 result alert message
            }
        }
    }
    private void login() {
        final String str_id = login_id.getText().toString();
        extra_id = str_id;
        Log.d("signup test", str_id);
        final String str_pw = login_pw.getText().toString();

        Req_login req_login = new Req_login(str_id, str_pw);
        Retrofit retrofit =new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.BASEURL)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Res_img> res = apiService.login(req_login);
        //res = Net.getInstance().getApiService().join(user);
        res.enqueue(new Callback<Res_img>() {
            @Override
            public void onResponse(Call<Res_img> call, Response<Res_img> response) {
                if(response.isSuccessful()){
                    if(response.body() != null) {
                        if(response.body().getCode() == 1) {
                            Intent intent_main = new Intent(login.this, MainActivity.class);
                            intent_main.putExtra("user_id", extra_id);
                            startActivity(intent_main);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Res_img> call, Throwable t) {

            }
        });
    }
}
