package hk.hku.cs.msc.ules;

import hk.hku.cs.msc.ules.dto.RequestData;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LoginActivityHandler extends Handler{
	private final static String TAG = "LoginActivityHandler";
	
	private final LoginActivity owner;
	
	LoginActivityHandler(LoginActivity activity){
		this.owner = activity;
	}
	
	@Override
	public void handleMessage(Message message){
		switch(message.what){

		}
	}
}
