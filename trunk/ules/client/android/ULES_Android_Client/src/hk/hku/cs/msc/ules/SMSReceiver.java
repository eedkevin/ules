package hk.hku.cs.msc.ules;

import hk.hku.cs.msc.ules.dto.SMSData;
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
	
	private ULESActivity mContext;
	
	private Handler handler;
	
	public SMSReceiver(){
		super();
		Log.v(TAG,"SMSReceiver create");
	}	
	
	public SMSReceiver(ULESActivity context){
		super();
		Log.v(TAG,"SMSReceiver create");
		this.mContext = context;
	}
	
	public Handler getHandler(){
		return this.handler;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//Log.v(TAG,"onReceive");
		
		Bundle bundle = intent.getExtras();
		Object[] messages = (Object[]) bundle.get("pdus");
		
		if(messages != null && messages.length > 0){
			SmsMessage[] smsMessages = new SmsMessage[messages.length];
			
			for(int i=0; i<messages.length; i++){
				smsMessages[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
			}
			
			for(SmsMessage message: smsMessages){
				if(message.getOriginatingAddress().equals("UFLEServer")){
					Log.v(TAG, "one SMS from UFLEServer received");
					
					SMSData data = this.buildSMSData(message);
					if(data.getFrom().equals("laptop")){
						Log.v(TAG, "ignore this SMS as it's requested from laptop");
						// do nothing, as the laptop application has internet connection and could access the web server itselft
					}else{
						// the content is the random key
						Message.obtain(this.mContext.getHandler(), R.id.sms_received, data.getContent()).sendToTarget();						
					}
					
				}
			}
		}
	}
	
	private SMSData buildSMSData(SmsMessage message){
		String sender;
		String from;
		String content;		// the random key
		
		sender = message.getOriginatingAddress();
		
		String[] str = message.getMessageBody().split(":");
		from = str[0].trim();
		content = str[1].trim();
		
		return new SMSData(sender, from, content);
	}
}
