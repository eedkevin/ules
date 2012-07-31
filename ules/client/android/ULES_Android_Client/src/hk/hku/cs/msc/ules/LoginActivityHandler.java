package hk.hku.cs.msc.ules;

import hk.hku.cs.msc.ules.dto.RequestData;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class LoginActivityHandler extends Handler{
	private final static String TAG = "LoginActivityHandler";
	
	private final LoginActivity owner;
	
	LoginActivityHandler(LoginActivity activity){
		this.owner = activity;
	}
	
	@Override
	public void handleMessage(Message message){
		switch(message.what){
			case R.id.server_response:
				if(message.obj!=null){
					owner.receiveResponse(Integer.parseInt((String)message.obj));
				}else{
					owner.alert("No response from server");
				}
				break;
			case R.id.login_succeed:
				break;
			case R.id.login_fail:
				break;
			case R.id.operation_error:
				break;
			case R.id.user_account_locked:
				break;
			case R.id.quit:
				Log.v(TAG, "internal msg: activity quit");
				owner.finish();
				break;
		}
	}
}
