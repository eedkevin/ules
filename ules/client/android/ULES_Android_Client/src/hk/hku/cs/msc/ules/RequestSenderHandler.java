package hk.hku.cs.msc.ules;

import hk.hku.cs.msc.ules.dto.RequestData;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class RequestSenderHandler extends Handler{
	private final static String TAG = "RequestSenderHandler";
	
	private final RequestSender owner;
	
	RequestSenderHandler(RequestSender thread){
		this.owner = thread;
	}
	
	@Override
	public void handleMessage(Message message){
		switch(message.what){
			case R.id.request_random_key:
				Log.v(TAG, "request_random_key");
				String line = owner.requestRandomKey((RequestData)message.obj);
				if(line == null){
					((ULESActivity)owner.getContext()).getHandler().obtainMessage(R.id.connection_failed).sendToTarget();
				}else{
					((ULESActivity)owner.getContext()).getHandler().obtainMessage(R.id.connection_succeeded).sendToTarget();
				}
				break;
			case R.id.request_mount_key:
				Log.v(TAG, "request_mount_key");
				String mountKey = owner.requestMountKey((RequestData)message.obj);
				((ULESActivity)owner.getContext()).getHandler().obtainMessage(R.id.mount_key_received, mountKey).sendToTarget();
				break;
			case R.id.quit:
				Log.v(TAG, "quit");
				//this.close();
				break;
		}
	}
	
	private void close(){
		this.getLooper().quit();
	}
}
