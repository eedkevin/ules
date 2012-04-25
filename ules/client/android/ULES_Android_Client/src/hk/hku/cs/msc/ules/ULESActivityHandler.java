package hk.hku.cs.msc.ules;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ULESActivityHandler extends Handler{
	private final static String TAG = "ULESActivityHandler";
	
	private final ULESActivity activity;
//	private SMSReceiver smsReceiver;
	private RequestSender requestSender;
	
	ULESActivityHandler(ULESActivity activity){
		this.activity = activity;
//		smsReceiver = new SMSReceiver(activity);
		requestSender = new RequestSender(activity);
		requestSender.start();
	}
	
	@Override
	public void handleMessage(Message message){
		switch(message.what){
			case R.id.request_random_key:
				Log.v(TAG,"request random key");
				requestSender.getHandler().obtainMessage(R.id.request_random_key, message.obj).sendToTarget();
			case R.id.connection_failed:
				Log.v(TAG,"connection failed");
				activity.alertConnectionStatus(R.string.connection_failed);
				break;
			case R.id.connection_succeeded:
				Log.v(TAG,"connection succeeded");
				activity.alertConnectionStatus(R.string.connection_succeeded);
				break;
			case R.id.sms_received:
				Log.v(TAG,"sms received");
				String url = ((ULESApplication)activity.getApplication()).getServerAddress() + "mountkey";
				requestSender.getHandler().obtainMessage(R.id.request_mount_key, message.obj).sendToTarget();
			case R.id.dismiss_progress_dialog:
				Log.v(TAG,"dismiss progress dialog");
				activity.dismissProgressDialog();
		}
	}
}
