package project.hs.inssaproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    public static String user_id;
    TextView your_mentor;
    TextView your_mentee;
    ImageButton btn_home;
    ImageButton btn_profiles;
    ImageButton btn_chatting;
    ImageButton btn_board;
    ImageButton btn_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        your_mentor = (TextView)findViewById(R.id.your_mentor);
        your_mentee = (TextView)findViewById(R.id.your_mentee);
        btn_home = (ImageButton)findViewById(R.id.btn_home);
        btn_chatting = (ImageButton)findViewById(R.id.btn_chatting);
        btn_profiles = (ImageButton)findViewById(R.id.btn_profiles);
        btn_setting = (ImageButton)findViewById(R.id.btn_setting);
        btn_board = (ImageButton)findViewById(R.id.btn_board);

        //new JSONTask().execute("http://54.180.32.249:3000/main");
        numberSetting();

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_home = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent_home);
                finish();
            }
        });
        btn_profiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_profiles = new Intent(MainActivity.this, profiles.class);
                startActivity(intent_profiles);
                finish();
            }
        });
        btn_chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_chattingList = new Intent(MainActivity.this, chattingList.class);
                startActivity(intent_chattingList);
                finish();
            }
        });
        btn_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_board = new Intent(MainActivity.this, board.class);
                startActivity(intent_board);
                finish();
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_setting = new Intent(MainActivity.this, setting.class);
                startActivity(intent_setting);
                finish();
            }
        });
    }
    private void numberSetting() {

        final String str_id = MainActivity.user_id;

        Req_number req_number = new Req_number(str_id);
        Retrofit retrofit =new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.BASEURL)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Res_number> res = apiService.numberSetting(req_number);
        res.enqueue(new Callback<Res_number>() {
            @Override
            public void onResponse(Call<Res_number> call, Response<Res_number> response) {
                if(response.isSuccessful()){
                    if(response.body() != null) {
                        Log.d("getMentor", Integer.toString(response.body().getMentor()));
                        Log.d("getMentee", Integer.toString(response.body().getMentee()));
                        your_mentor.setText(Integer.toString(response.body().getMentor()));
                        your_mentee.setText(Integer.toString(response.body().getMentee()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Res_number> call, Throwable t) {

            }
        });
        /*
        call.enqueue(new Callback<Res_join>() {
            @Override
            public void onResponse(Call<Res_join> call, Response<Res_join> response) {
                if (response.isSuccessful()) {
                    Intent intent_login = new Intent(signup.this, login.class);
                    startActivity(intent_login);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<Res_join> call, Throwable t) {
                Log.e("업로드 실패", t.getLocalizedMessage());
            }
        });
        */
    }
    /*
    public class JSONTask extends AsyncTask<String, String, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            try {
                final String str_id = user_id;

                Log.d("str_id", str_id);

                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("user_id", str_id);

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

                    return new JSONObject(buffer.toString());//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

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
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            try{
                your_mentor.setText(Integer.toString(result.getInt("user_mentor")));
                your_mentee.setText(Integer.toString(result.getInt("user_mentee")));
                your_inssa.setText(Integer.toString(result.getInt("user_mentor") + result.getInt("user_mentee")));
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
    */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder alertDig = new AlertDialog.Builder(this);

            alertDig.setMessage("종료 하시겠습니까??");
            alertDig.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    System.exit(0);
                }
            });

            alertDig.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            });

            AlertDialog alert = alertDig.create();
            alert.setTitle("뒤로가기 버튼 이벤트");
            //alert.section(R.draw.ic_launcher);
            alert.show();
        }
        return true;
    }
}
