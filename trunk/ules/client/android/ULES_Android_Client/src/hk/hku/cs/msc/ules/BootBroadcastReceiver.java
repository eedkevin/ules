package hk.hku.cs.msc.ules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver{
	public final static String TAG = "BootBroadcastReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onReceive");
		Intent service = new Intent(context, StartService.class);
		context.startService(service);
		Log.v(TAG, "starting StartService...");
	}

}
