package hk.hku.cs.msc.ules;

import hk.hku.cs.msc.ules.dto.RequestData;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ULESActivityHandler extends Handler{
	private final static String TAG = "ULESActivityHandler";
	
	private final ULESActivity activity;
//	private SMSReceiver smsReceiver;
	private RequestSender requestSender;
	private USBSocketServer usbSocketServer;
	
	ULESActivityHandler(ULESActivity activity){
		this.activity = activity;
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
				Log.v(TAG,"request random key");
				requestSender.getHandler().obtainMessage(R.id.request_random_key, message.obj).sendToTarget();
				break;
			case R.id.connection_failed:
				Log.v(TAG,"connection failed");
				activity.dismissProgressDialog();
				activity.alertConnectionStatus(R.string.connection_failed);
				break;
			case R.id.connection_succeeded:
				Log.v(TAG,"connection succeeded");
				activity.dismissProgressDialog();
				activity.alertConnectionStatus(R.string.connection_succeeded);
				break;
			case R.id.sms_received:
				Log.v(TAG,"sms received");
				String url = ((ULESApplication)activity.getApplication()).getServerAddress() + "getmountkey.jsp";
				RequestData data = new RequestData(activity.username, url, (String)message.obj);
				requestSender.getHandler().obtainMessage(R.id.request_mount_key, data).sendToTarget();
				break;
			case R.id.mount_key_received:
				usbSocketServer.getHandler().obtainMessage(R.id.mount_key_received, message.obj).sendToTarget();
				break;
			case R.id.quit:
				Log.v(TAG, "quit");
				this.close();
				break;
		}
	}
	
	
	private void close(){
		requestSender.interrupt();
		requestSender.stop();
		//requestSender.destroy();
		usbSocketServer.interrupt();
		usbSocketServer.stop();
		//usbSocketServer.destroy();
		//this.getLooper().quit();
	}
}
