package hk.hku.cs.msc.ules;

import android.os.Handler;
import android.os.Message;

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
		}
	}
	
	
}
