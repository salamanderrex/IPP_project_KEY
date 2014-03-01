package com.rex;

import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rex.constant.CONSTANT;
import com.rex.net.socket_connect;

public class IPP_project_KEYActivity extends Activity {
    /** Called when the activity is first created. */
	ImageButton b_lock;
	//Button b_unlock;
	TextView hint;
	
	static Socket server;
	socket_connect clientsocket;
	
	int i_lock_status;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        b_lock= (ImageButton) findViewById(R.id.lock);
      //  b_unlock=(Button) findViewById(R.id.unlock);
        hint=(TextView) findViewById(R.id.hint);
  

		
        final Handler myHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == CONSTANT.NET.UPDATE_CARINFO) {
					System.out.println("updating the panel");
					//title_charge.setText(msg.getData().getString("charge").toString());
					//title_speed.setText(msg.getData().getString("speed").toString());

				}
				
				else if (msg.what == CONSTANT.NET.UPDATE_LOCKSTATUS) {
					System.out.println("updating the pan2el");
					//title_charge.setText(msg.getData().getString("charge").toString());
					//title_speed.setText(msg.getData().getString("speed").toString());
					String lock_status=msg.getData().getString("lock_status").toString();
					System.out.println(lock_status);
					if(lock_status.equals("0")) //locked
					{
						//b_lock.setText("To unlock");
						hint.setText(lock_status);
						b_lock.setImageDrawable(getResources().getDrawable(R.drawable.pic_unlock));
						i_lock_status=0;
						//clientsocket.disconnet();
					}
						else
					{
							//b_lock.setText("To lock");
							hint.setText(lock_status);
							b_lock.setImageDrawable(getResources().getDrawable(R.drawable.pic_locked));
							i_lock_status=1;
							//clientsocket.disconnet();
					}
				}
			}

		};
		
        //net 
		clientsocket = new socket_connect(server, myHandler);
		clientsocket.start();
		
		
        b_lock.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(i_lock_status==1)
				{
				clientsocket.write("TAB 02 00 88888888");
				}
				else
				{
					clientsocket.write("TAB 02 01 88888888");
				
				}
				//clientsocket.disconnet();
				//clientsocket.stop();
			}
		});

    }
}