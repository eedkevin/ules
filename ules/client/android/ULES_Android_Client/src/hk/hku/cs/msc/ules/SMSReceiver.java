package hk.hku.cs.msc.ules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
	public final static String TAG = "SMSReceiver";
	
	private Context context;
	
	private Handler handler;
	
	SMSReceiver(Context context){
		Log.v(TAG,"SMSReceiver create");
		this.context = context;
	}
	
	public Handler getHandler(){
		return this.handler;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		Object[] messages = (Object[]) bundle.get("pdus");
		
		if(messages != null && messages.length > 0){
			SmsMessage[] smsMessages = new SmsMessage[messages.length];
			
			for(int i=0; i<messages.length; i++){
				smsMessages[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
			}
			
			for(SmsMessage message: smsMessages){
				if(message.getOriginatingAddress().equals("ULES")){
					Log.v(TAG, "one SMS from ULES received");
					String content = message.getMessageBody();
					
					// content is the random key
					Message msg = Message.obtain(((ULESActivity) context).getHandler(), R.id.sms_received, content);
					msg.sendToTarget();
				}
			}
		}
	}
}
