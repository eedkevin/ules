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
				Log.v(TAG,"request random key");
				requestSender.getHandler().obtainMessage(R.id.request_random_key, message.obj).sendToTarget();
				break;
			case R.id.connection_failed:
				Log.v(TAG,"connection failed");
				owner.dismissProgressDialog();
				owner.alertConnectionStatus(R.string.connection_failed);
				break;
			case R.id.connection_succeeded:
				Log.v(TAG,"connection succeeded");
				owner.alertConnectionStatus(R.string.connection_succeeded);
				break;
			case R.id.sms_received:
				Log.v(TAG,"sms received");
				String url = ((ULESApplication)owner.getApplication()).getServerAddress() + "getmountkey.jsp";
				RequestData data = new RequestData(owner.username, url, (String)message.obj);
				requestSender.getHandler().obtainMessage(R.id.request_mount_key, data).sendToTarget();
				break;
			case R.id.mount_key_received:
				owner.dismissProgressDialog();
				owner.alertConnectionStatus(R.string.transferring_pmk);
				usbSocketServer.getHandler().obtainMessage(R.id.mount_key_received, message.obj).sendToTarget();
				break;
			case R.id.close_sub_threads:
				Log.v(TAG, "close sub threads");
				closeSubThreads();
				break;
			case R.id.quit:
				Log.v(TAG, "quit ULES activity");
				closeSubThreads();
				owner.finish();
				break;
		}
	}
	
	
	private void closeSubThreads(){
		requestSender.interrupt();
		usbSocketServer.interrupt();
	}
}
