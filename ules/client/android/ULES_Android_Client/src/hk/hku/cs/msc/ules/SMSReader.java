package hk.hku.cs.msc.ules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReader extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];
		for(int i=0; i<messages.length; i++){
			smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
		}
		
	}

}
