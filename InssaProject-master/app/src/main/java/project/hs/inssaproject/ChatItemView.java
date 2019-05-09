package project.hs.inssaproject;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatItemView extends LinearLayout {
    TextView sender;
    TextView message;
    public ChatItemView(Context context) {
        super(context);
        init(context);
    }

    public ChatItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.chat_item,this,true);
        sender=(TextView)findViewById(R.id.sender);
        message=(TextView)findViewById(R.id.message);
    }
    public void setSender(String name){
        sender.setText(name);
    }
    public void setMessage(String msg){
        message.setText(msg);
    }
}
