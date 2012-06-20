package hk.hku.cs.msc.ules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartService extends BroadcastReceiver{
	public final static String TAG = "StartService";	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.v(TAG,"onReceive");
		
		Intent mIntent = new Intent(context, LoginActivity.class);
		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);		
		context.startActivity(mIntent);
		
		Log.v(TAG, "starting LoginActivity ...");
	}

}
