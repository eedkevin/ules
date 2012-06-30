package hk.hku.cs.msc.ules;

import hk.hku.cs.msc.ules.dto.RequestData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.ClientProtocolException;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class RequestSender extends Thread{
	public static final String TAG = "RequestSender";
	
	private Context mContext;
	private Handler handler;
	
	RequestSender(Context context){
		this.mContext = context;
		handler = new RequestSenderHandler(this);
	}
	
	public Handler getHandler(){
		return this.handler;
	}
	
	public Context getContext(){
		return this.mContext;
	}
	
	protected String requestRandomKey(RequestData data){
		return requestRandomKey(data.getUrl(), data.getUsername(), data.getFrom());
		//return requestRandomKey();
	}
	
	protected String requestMountKey(RequestData data){
		return requestMountKey(data.getUrl(), data.getUsername(), data.getRandomKey());
	}
	
	protected void showToast(String str){
		Toast.makeText(mContext, str, Toast.LENGTH_LONG).show();
	}
	
	private String requestRandomKey(){
		
		String url = "http://192.168.1.16:8080/ufle/getmountkey.jsp?username=kevin&sms=374513";
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		String line = null;
		try{
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				line = readRandomKeyStatus(httpResponse);
			}
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} catch(ClientProtocolException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,"Could not establish a HTTP connection to the server or could not get a response properly from the server.",e);
			e.printStackTrace();
		}
		
		return line;	
	}
	
	
	private String requestRandomKey(String url, String username, String from){
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("from", from));

		String line = null;
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				line = readRandomKeyStatus(httpResponse);
			}
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} catch(ClientProtocolException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,"Could not establish a HTTP connection to the server or could not get a response properly from the server.",e);
			e.printStackTrace();
		} finally{
			
		}
		
		return line;
	}
	
	private String requestMountKey(String url, String username, String randomKey){
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("sms", randomKey));

		String mountKey = null;
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				mountKey = readMountKey(httpResponse);
			}
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} catch(ClientProtocolException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,"Could not establish a HTTP connection to the server or could not get a response properly from the server.",e);
			e.printStackTrace();
		}		
		Log.v(TAG,"mountkey = " + mountKey);
		showToast(mountKey);
		return mountKey;
	}
	
	private String readRandomKeyStatus(HttpResponse httpResponse){
		String status = null;
		
		try { 
			InputStream content = httpResponse.getEntity().getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));
			status = in.readLine();

			in.close();

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	private String readMountKey(HttpResponse httpResponse) {
		String mountKey = null;

		try {
//			line = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			InputStream content = httpResponse.getEntity().getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));
			
//			String[] str = in.readLine().split("=");
//			if(str.length > 1){
//				line = str[1];
//			}
			char[] arrChar = new char[365];
			in.read(arrChar);
			mountKey = new String(arrChar);
//			line = arrChar.toString();
			
			in.close();

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mountKey;

	}	
	
}
