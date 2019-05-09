package project.hs.inssaproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.util.ArrayList;

public class chatting extends AppCompatActivity {
    //자신의 아이디 : MainActivity.user_id
    public String my_id=MainActivity.user_id;
    public String opp_id;
    Button backButton;
    Button send;
    EditText editText;
    ListView listView;
    TextView opp_textView;
    ChatAdapter adapter=new ChatAdapter();
    Handler handler = new Handler();

    com.github.nkzawa.socketio.client.Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://54.180.32.249:3000");
            mSocket.emit("socket_id",my_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        Intent intent = getIntent();
        opp_id = intent.getStringExtra("profile_id"); //상대방의 아이디


        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on("message", onNewMessage);
        mSocket.connect();
        mSocket.emit("loadChat",my_id+":"+opp_id);
        mSocket.emit("loadChat",opp_id+":"+my_id);

        backButton=(Button)findViewById(R.id.back_button);
        send=(Button)findViewById(R.id.send);
        editText=(EditText)findViewById(R.id.editText);
        opp_textView=(TextView)findViewById(R.id.opp_textView);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        opp_textView.setText(opp_id);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString().trim();
                editText.setText("");
                JSONObject object=new JSONObject();
                try{
                    object.accumulate("sender_id",my_id);
                    object.accumulate("listener_id", opp_id);
                    object.accumulate("message",message);
                    mSocket.emit("message", object);
                }catch(Exception e){}
                editText.callOnClick();
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    send.callOnClick();
                    return true;
                }
                return false;
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_chatList = new Intent(chatting.this, chattingList.class);
                startActivity(intent_chatList);
                finish();
            }
        });
    }

    class ChatAdapter extends BaseAdapter {
        ArrayList<ChatItem> items=new ArrayList<ChatItem>();
        public void addItem(ChatItem item){
            items.add(item);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ChatItemView view=new ChatItemView(getApplicationContext());
            ChatItem item=items.get(position);
            view.setSender(item.getSender());
            view.setMessage(item.getMessage());
            return view;
        }
    }

    private Emitter.Listener onConnect=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mSocket.connect();
            mSocket.emit("socket_id",my_id);
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String name="";
                    String message="";
                    try {
                        message = data.getString("message");
                        name=data.getString("name");
                        if(name.equals("상대")){
                            name=opp_id;
                        }
                    }catch(Exception e){}
                    final String n=name;
                    final String m=message;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addItem(new ChatItem(n, m));
                            listView.setAdapter(adapter);
                            listView.post(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setSelection(listView.getAdapter().getCount() - 1);
                                }
                            });
                        }
                    });
                }
            });
        }
    };
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent_chatList = new Intent(chatting.this, chattingList.class);
            startActivity(intent_chatList);
            finish();
        }
        return true;
    }
}
