package hk.hku.cs.msc.ules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.ClientProtocolException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class RequestSender {
	public static final String TAG = "RequestSender";
	
	public static String requestRandomKey(String url, String username, String password){
		Log.v(TAG, "requestRandomKey");
		
		String status;
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));

		String line = null;
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			line = readResponse(httpResponse);
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} catch(ClientProtocolException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,"Could not establish a HTTP connection to the server or could not get a response properly from the server.",e);
			e.printStackTrace();
		}
		
		if(line == null){
			return "Connection Failed";
		}
		
		status = "Reading Json Failed";
		try {
			JSONObject json = new JSONObject(line);
			status = json.getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	
	private static String readResponse(HttpResponse httpResponse) {
		String line = null;
		String status = null;

		try {
			InputStream content = httpResponse.getEntity().getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));
			line = in.readLine();
			
			JSONObject json = new JSONObject(line);
			status = json.getString("status");
			
			in.close();

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return status;

	}	
}
