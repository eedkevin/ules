package hk.hku.cs.msc.ules;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ULESActivityHandler extends Handler{
	private final static String TAG = "ULESActivityHandler";
	
	private final ULESActivity activity;
	
	ULESActivityHandler(ULESActivity activity){
		this.activity = activity;
		
	}
	
	@Override
	public void handleMessage(Message message){
		switch(message.what){
			case R.id.connection_failed:
				Log.v(TAG,"connection failed");
				activity.alertConnectionStatus(R.string.connection_failed);
				break;
			case R.id.connection_succeeded:
				Log.v(TAG,"connection succeeded");
				activity.alertConnectionStatus(R.string.connection_succeeded);
				break;
		}
	}
}
