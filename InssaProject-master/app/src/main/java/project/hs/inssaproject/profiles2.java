package project.hs.inssaproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class profiles2 extends AppCompatActivity {
    ImageButton btn_home;
    ImageButton btn_profiles;
    ImageButton btn_chatting;
    ImageButton btn_board;
    ImageButton btn_setting;
    ImageButton btn_all;
    ImageButton btn_loving;

    //ImageView imgView;
    Bitmap bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles2);

        btn_home = (ImageButton)findViewById(R.id.btn_home);
        btn_chatting = (ImageButton)findViewById(R.id.btn_chatting);
        btn_profiles = (ImageButton)findViewById(R.id.btn_profiles);
        btn_setting = (ImageButton)findViewById(R.id.btn_setting);
        btn_board = (ImageButton)findViewById(R.id.btn_board);

        btn_all = (ImageButton)findViewById(R.id.btn_all);
        btn_loving = (ImageButton)findViewById(R.id.btn_loving);
        /*
        imgView = findViewById(R.id.imgView);
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    URL url = new URL("http://54.180.32.249:3000/uploads/1395728192374.jpg");
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
            imgView.setImageBitmap(bm);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        */

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_home = new Intent(profiles2.this, MainActivity.class);
                startActivity(intent_home);
                finish();
            }
        });
        btn_profiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_profiles = new Intent(profiles2.this, profiles.class);
                startActivity(intent_profiles);
                finish();
            }
        });
        btn_chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_chattingList = new Intent(profiles2.this, chattingList.class);
                startActivity(intent_chattingList);
                finish();
            }
        });
        btn_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_board = new Intent(profiles2.this, board.class);
                startActivity(intent_board);
                finish();
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_setting = new Intent(profiles2.this, setting.class);
                startActivity(intent_setting);
                finish();
            }
        });
        btn_all.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent_profiles = new Intent(profiles2.this, profiles.class);
                startActivity(intent_profiles);
                finish();
            }
        });
        btn_loving.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent_profiles2 = new Intent(profiles2.this, profiles2.class);
                startActivity(intent_profiles2);
                finish();
            }
        });
    }
}
