package hk.hku.cs.msc.ules;

import hk.hku.cs.msc.ules.dto.RequestData;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class RequestSenderHandler extends Handler{
	private final static String TAG = "RequestSenderHandler";
	
	private final RequestSender sender;
	
	RequestSenderHandler(RequestSender thread){
		this.sender = thread;
	}
	
	@Override
	public void handleMessage(Message message){
		switch(message.what){
			case R.id.request_random_key:
				Log.v(TAG, "request_random_key");
				String line = sender.requestRandomKey((RequestData)message.obj);
				if(line == null){
					((ULESActivity)sender.getContext()).getHandler().obtainMessage(R.id.connection_failed).sendToTarget();
				}else{
					((ULESActivity)sender.getContext()).getHandler().obtainMessage(R.id.connection_succeeded).sendToTarget();
				}
				break;
			case R.id.request_mount_key:
				Log.v(TAG, "request_mount_key");
				String mountKey = sender.requestMountKey((RequestData)message.obj);
				((ULESActivity)sender.getContext()).getHandler().obtainMessage(R.id.mount_key_received, mountKey).sendToTarget();
				break;
		}
	}
}
