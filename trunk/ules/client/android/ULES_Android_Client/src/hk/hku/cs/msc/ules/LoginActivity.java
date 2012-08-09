package hk.hku.cs.msc.ules;

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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{
	
	public static final String TAG = "LoginActivity";
	
	private Context mContext;
	private Handler handler;
	
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
		
		handler = new LoginActivityHandler(this);
		
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
				
				if(username.equals("") || password.equals("")){
					alert("Please input a username and password");
				}else{
					doLogin();
				}
				
			}
		});
	}	
	
    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
    	Log.v(TAG, "onStart");
		super.onStart();
	}
    
    @Override
	protected void onRestart() {
		// TODO Auto-generated method stub
    	Log.v(TAG, "onRestart");
		super.onRestart();
		if(((ULESApplication)mContext).isExit()){
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onDestroy");
		super.onDestroy();
		
		// remove all messages sent by this activity before exit
		handler.removeCallbacks(null);
		
		// reset the application variables before exit 
		((ULESApplication)mContext).reset();
	}
    
    
	public Handler getHandler(){
		return this.handler;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
    public void alert(int status){
    	Toast.makeText(this, status, Toast.LENGTH_LONG).show();
    }
    
    public void alert(String text){
    	Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    } 
    
    public void alertShort(int status){
    	Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
    }
    
    public void alertShort(String text){
    	Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }     
    
	
	
	private void doLogin(){
		//String url = "http://192.168.1.16:8080/ufle/getmountkey.jsp?username=kevin&sms=374513";
		Log.v(TAG, "Login username: "+username+" password: "+password);
		showProgressDialog();
		
		// start a new thread to connect to the web server
		new Thread(){
			public void run(){
				
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
				
				// send the received server response to LoginActivityHandler for further processing 
				handler.obtainMessage(R.id.server_response, line).sendToTarget();
			}
		}.start();

	}
	
	private void startULESActivity(){
		Intent intent = new Intent(this, ULESActivity.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		startActivity(intent);
	}

    private void showProgressDialog(){
    	progressDialog = ProgressDialog.show(this, "Connecting to Server", "Please wait a second");
    }
    
    private void dismissProgressDialog(){
    	progressDialog.dismiss();
    	//progressDialog = null;
    }
    
	protected void receiveResponse(int flag){

//		Builder dialog = new AlertDialog.Builder(this);
		switch(flag){
		case 0:
			Log.v(TAG, "login fail");
			alertShort(R.string.login_fail);
//			dialog.setMessage(R.string.login_fail);
//			dialog.show();
			dismissProgressDialog();
			break;
		case 1:
			Log.v(TAG, "login succeed");
			alertShort(R.string.login_succeed);
//			dialog.setMessage(R.string.login_succeed);
//			dialog.show();
			dismissProgressDialog();
			startULESActivity();
			break;
		case 2:
			Log.v(TAG, "operation error");
			alertShort(R.string.operation_error);
//			dialog.setMessage(R.string.operation_error);
//			dialog.show();
			dismissProgressDialog();
			break;
		case 3:
			Log.v(TAG, "user account locked");
			alertShort(R.string.user_account_locked);
//			dialog.setMessage(R.string.user_account_locked);
//			dialog.show();
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
