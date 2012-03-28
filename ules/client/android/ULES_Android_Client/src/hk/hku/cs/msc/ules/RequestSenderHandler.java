package hk.hku.cs.msc.ules;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class RequestSenderHandler extends Handler{
	private final static String TAG = "RequestSenderHandler";
	
	private final RequestSender sender;
	
	RequestSenderHandler(RequestSender sender){
		this.sender = sender;
	}
	
	@Override
	public void handleMessage(Message message){
		switch(message.what){
		case R.id.request_random_key:
			Log.v(TAG, "request_random_key");
			//sender.request(url, requestCmd);
			
		}
	}
}
