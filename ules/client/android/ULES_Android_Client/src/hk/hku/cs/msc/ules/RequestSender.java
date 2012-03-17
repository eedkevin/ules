package hk.hku.cs.msc.ules;

import org.apache.http.client.methods.HttpPost;

import android.util.Log;

public class RequestSender {
	public static final String TAG = "RequestSender";
	
	public static int request(String url, String requestCmd){
		Log.v(TAG, "request");
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		return 0;
	}
}
