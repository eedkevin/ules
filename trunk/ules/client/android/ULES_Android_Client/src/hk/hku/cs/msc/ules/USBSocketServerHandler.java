package hk.hku.cs.msc.ules;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class USBSocketServerHandler extends Handler{
	private final static String TAG = "USBSocketServerHandler";
	
	private final USBSocketServer owner;
	
	USBSocketServerHandler(USBSocketServer thread){
		this.owner = thread;
	}

	@Override
	public void handleMessage(Message message) {
		// TODO Auto-generated method stub
		switch(message.what){
			case R.id.mount_key_received:
				owner.write((String)message.obj);
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
