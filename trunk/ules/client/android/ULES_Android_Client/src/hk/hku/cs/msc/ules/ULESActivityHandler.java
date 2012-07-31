package hk.hku.cs.msc.ules;

import hk.hku.cs.msc.ules.dto.RequestData;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ULESActivityHandler extends Handler{
	private final static String TAG = "ULESActivityHandler";
	
	private final ULESActivity owner;
//	private SMSReceiver smsReceiver;
	private RequestSender requestSender;
	private USBSocketServer usbSocketServer;
	
	ULESActivityHandler(ULESActivity activity){
		this.owner = activity;
//		smsReceiver = new SMSReceiver(activity);
		requestSender = new RequestSender(activity);
		requestSender.start();
		
		usbSocketServer = new USBSocketServer(activity);
		usbSocketServer.start();
	}
	
	@Override
	public void handleMessage(Message message){
		switch(message.what){
			case R.id.request_random_key:
				Log.v(TAG,"internal msg: request random key");
				requestSender.getHandler().obtainMessage(R.id.request_random_key, message.obj).sendToTarget();
				break;
			case R.id.connection_failed:
				Log.v(TAG,"internal msg: connection failed");
				owner.dismissProgressDialog();
				owner.alert(R.string.connection_failed);
				break;
			case R.id.connection_succeeded:
				Log.v(TAG,"internal msg: connection succeeded");
				owner.alertShort(R.string.connection_succeeded);
				break;
			case R.id.failed_reading_json:
				Log.v(TAG,"internal msg: failed reading json");
				owner.alert(R.string.failed_reading_json);
				break;
			case R.id.sms_received:
				Log.v(TAG,"internal msg: sms received");
				owner.alertShort("Random Key received");
				String url = ((ULESApplication)owner.getApplication()).getServerAddress() + "getmountkey.jsp";
				RequestData data = new RequestData(owner.username, url, (String)message.obj);
				requestSender.getHandler().obtainMessage(R.id.request_mount_key, data).sendToTarget();
				break;
			case R.id.mount_key_received:
				owner.alertShort("Mount Key received");
				owner.dismissProgressDialog();
				owner.alert(R.string.transferring_pmk);
				usbSocketServer.getHandler().obtainMessage(R.id.mount_key_received, message.obj).sendToTarget();
				break;
			case R.id.close_sub_threads:
				Log.v(TAG, "internal msg: close sub threads");
				closeSubThreads();
				break;
			case R.id.quit:
				Log.v(TAG, "internal msg: quit ULES activity");
				//closeSubThreads();
				((ULESApplication)owner.getContext()).setExit(true);
				owner.finish();
				break;
		}
	}
	
	private void closeSubThreads(){
		Log.v(TAG, "close sub threads");
		requestSender.interrupt();
		usbSocketServer.interrupt();
	}
}
