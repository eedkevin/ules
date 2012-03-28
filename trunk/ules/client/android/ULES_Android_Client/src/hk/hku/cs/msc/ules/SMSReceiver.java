package hk.hku.cs.msc.ules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
	public final static String TAG = "SMSReceiver";

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
			
			for(SmsMessage msg: smsMessages){
				if(msg.getOriginatingAddress().equals("ULES")){
					Log.v(TAG, "one SMS from ULES received");
					String content = msg.getMessageBody();
					
					
				}
			}
		}
	}
}
