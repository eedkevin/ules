package hk.hku.cs.msc.ules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{
	
	public static final String TAG = "LoginActivity";
	
	private Context mContext;
	
	private EditText etUsername;
	private EditText etPassword;
	private Button btnLogin;
	private ProgressDialog progressDialog;
	
	private String username;
	private String password;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mContext = getApplication();
		
		 // Set default value for server address
        ((ULESApplication)getApplication()).setServerAddress(getResources().getString(R.string.web_server_default_address));
		
		etUsername = (EditText) findViewById(R.id.editText_username);
		etPassword = (EditText) findViewById(R.id.editText_passowrd);
		
		btnLogin = (Button) findViewById(R.id.button_login);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				username = etUsername.getText().toString().trim();
				password = etPassword.getText().toString().trim();
				doLogin();
				
			}
		});
	}	
	
	private void doLogin(){
		//String url = "http://192.168.1.16:8080/ufle/getmountkey.jsp?username=kevin&sms=374513";
		
		showProgressDialog();
		
		String url = ((ULESApplication)getApplication()).getServerAddress() + "logincheck.jsp";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("username", username));
		param.add(new BasicNameValuePair("password", password));
		
		String line = null;
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(param));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				line = readResponse(httpResponse);
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
		
		if(line!=null){
			receiveResponse(Integer.parseInt(line));
		}else{
			Toast.makeText(this, "No response from server", Toast.LENGTH_LONG);
		}
	}

    private void showProgressDialog(){
    	progressDialog = ProgressDialog.show(this, "Connecting to Server", "Please wait a second");
    }
    
    private void dismissProgressDialog(){
    	progressDialog.dismiss();
    	progressDialog = null;
    }
    
	private void receiveResponse(int flag){

//		Builder dialog = new AlertDialog.Builder(this);
		switch(flag){
		case 0:
//			dialog.setMessage(R.string.login_fail);
//			dialog.show();
			Toast.makeText(this, R.string.login_fail, Toast.LENGTH_LONG);
			dismissProgressDialog();
			break;
		case 1:
//			dialog.setMessage(R.string.login_succeed);
//			dialog.show();
			Toast.makeText(this, R.string.login_succeed, Toast.LENGTH_LONG);
			dismissProgressDialog();
			Intent intent = new Intent(this, ULESActivity.class);
			intent.putExtra("username", username);
			intent.putExtra("password", password);
			startActivity(intent);
			break;
		case 2:
//			dialog.setMessage(R.string.operation_error);
//			dialog.show();
			Toast.makeText(this, R.string.operation_error, Toast.LENGTH_LONG);
			dismissProgressDialog();
			break;
		case 3:
//			dialog.setMessage(R.string.user_account_locked);
//			dialog.show();
			Toast.makeText(this, R.string.user_account_locked, Toast.LENGTH_LONG);
			dismissProgressDialog();
			break;
		default:
			dismissProgressDialog();
			break;	
		}
	}
	
	private String readResponse(HttpResponse httpResponse){
		String line = null;
		
		try { 
			InputStream content = httpResponse.getEntity().getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));
			line = in.readLine();

			in.close();

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}

}
