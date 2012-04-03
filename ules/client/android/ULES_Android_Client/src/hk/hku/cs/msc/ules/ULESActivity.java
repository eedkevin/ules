package hk.hku.cs.msc.ules;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ULESActivity extends Activity {
	
	public static final String TAG = "ULESActivity";
	
	public static final String SETTING_INFO = "setting_info";
	public static final String USERNAME = "USERNAME";
	public static final String PASSWORD = "PASSWORD";
	
	private Context context;
	
	private Handler handler;
	
	private ProgressDialog progressDialog;		
	
	// UI components
	private Button btnRequestKey;
	private EditText et_username;
	private EditText et_password;
	
	// temp data
	private String username = "bmw1916";
	private String password = "123456";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = getApplication();
        handler = new ULESActivityHandler(this);
        
        btnRequestKey = (Button) findViewById(R.id.main_btn_request_key);
        btnRequestKey.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Request random key from server
				requestRandomKey();
			}
		});
        
        // Set default value for server address
        ((ULESApplication)getApplication()).setGlobalServerAddress(getResources().getString(R.string.server_default_address));
        
        
    }
    
    public Handler getHandler(){
    	return this.handler;
    }
    
    public void alertConnectionStatus(int status){
    	Toast.makeText(this, status, 3).show();
    }
    
    
    private void requestRandomKey(){
    	
//    	getSavedPassword();
    	Log.v(TAG+" getSavedPassword", "username: "+username+" password: "+password);
    	
    	if(username.equals("") || password.equals("")){
    		// Pop up a input dialog requesting for username and password
//    		new AlertDialog.Builder(this).setTitle("Please input your username and password to login the Server")
//    									 .setView(new EditText(this))
////    									 .setView(new EditText(this))
//    									 .setPositiveButton("Confirm", null)
//    									 .setNegativeButton("Cancel", null)
//    									 .show();
    		
//    		new hk.hku.cs.msc.ules.util.InputDialog(this).show();
    		
    		Builder dialog = new AlertDialog.Builder(this);
    		LinearLayout layout = (LinearLayout) ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.input_dialog, null);
    		dialog.setView(layout);
    		et_username = (EditText) layout.findViewById(R.id.editText_username);
    		et_password = (EditText) layout.findViewById(R.id.editText_password);
    		
    		dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
    		
    		dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					username = et_username.getText().toString().trim();
					password = et_password.getText().toString().trim();
					Log.v(TAG, "username: "+username+" password: "+password);
				}
			});
    		dialog.show();
    	}else{
//    		new AlertDialog.Builder(this).setTitle(username).show();
    		doRequestRandomKey();
    	}
    	
    }
    
    // Get the saved username and password
    private void getSavedPassword(){
    	
    	// Restore prederences
    	SharedPreferences sp = getSharedPreferences(SETTING_INFO, 0);
    	username = sp.getString(USERNAME, "");
    	password = sp.getString(PASSWORD, "");
    	Log.v(TAG+" getSavedPassword", "username: "+username+" password: "+password);
    }
    
    private void doRequestRandomKey(){
    	Log.v(TAG,"doRequestRandomKey");
    	progressDialog = ProgressDialog.show(this, "Connecting to Server", "Please wait a second");
    	
    	final String url = "http://147.8.82.145:8080/" + "randomkey";
    	
    	// start a new thread to connect the web server
    	new Thread(){
    		public void run(){
    			String status = RequestSender.requestRandomKey(url, username, password);
    			
    			progressDialog.dismiss();
    			
    			if(!status.equals(context.getString(R.string.connection_succeeded))){
    				Log.e(TAG, status);
    				Message msg = Message.obtain(handler, R.id.connection_failed);
    				msg.sendToTarget();
    			}else{
    				Log.e(TAG, status);
    				Message msg = Message.obtain(handler, R.id.connection_succeeded);
    				msg.sendToTarget();
    			}
    			
//    			if(status.equals("Connection Failed") || status.equals("Reading Json Failed")){
//    				Log.e(TAG, status);
//    				// send a msg to ULESActivityHandler to displays a Toast
//    			}else{
//    				Log.v(TAG, status);
//    				// send a msg to ULESActivityHandler to displays a Toast
//    			}
    		}
    	}.start();
    }
    
    
    
    
}