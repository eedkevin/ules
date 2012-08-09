package hk.hku.cs.msc.ules;

import hk.hku.cs.msc.ules.dto.RequestData;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
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
	
	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
	
	private Context mContext;
	protected SMSReceiver smsReceiver;
	
	private Handler handler;
	
	private ProgressDialog progressDialog;		
	
	// UI components
	private Button btnRequestKey;
	private EditText et_username;
	private EditText et_password;
	
	// temp data
	protected String username;
	private String password;
	private String from = "mobile";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = getApplication();
        
        // Set the exit flag. This flag is used to quit activities which belongs to the application
        ((ULESApplication)getApplication()).setExit(false);
        
        // Set default value for server address
        ((ULESApplication)getApplication()).setServerAddress(getResources().getString(R.string.web_server_default_address));        
        
        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");
        
        handler = new ULESActivityHandler(this);
        registerSMSReceiver();
        
        btnRequestKey = (Button) findViewById(R.id.main_btn_request_key);
        btnRequestKey.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Request random key from server
				requestRandomKey();
			}
		});
        
    }          

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		 // Set the exit flag. This flag is used to quit activities which belongs to the application
        ((ULESApplication)mContext).setExit(false);
        
        // Set default value for server address
        ((ULESApplication)mContext).setServerAddress(getResources().getString(R.string.web_server_default_address));
        
        handler = new ULESActivityHandler(this);
        registerSMSReceiver();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onStop");
		super.onStop();
		
		// it will close the sub threads, i.e. RequestSender thread and USBSocketServer thread
		handler.sendEmptyMessage(R.id.close_sub_threads);
		
		// unregister the SMSReceiver
		unregisterSMSReceiver();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		handler.removeCallbacks(null);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu){
    	boolean result = super.onCreateOptionsMenu(menu);
    	menu.addSubMenu(0, Menu.FIRST, Menu.NONE, "Settings"); 
    	menu.addSubMenu(0, Menu.FIRST + 1, Menu.NONE, "Help"); 
    	//settingsMenu.add(0, Menu.FIRST + 10, Menu.NONE, "Set Server Address");
    	return result;
    }
    
	@Override
    public boolean onOptionsItemSelected(MenuItem item){
    	boolean result = super.onOptionsItemSelected(item);
    	switch(item.getItemId()){
    	case Menu.FIRST:
    		startActivity(new Intent(this, SettingsActivity.class));
    		break;
    	case Menu.FIRST + 1:
    		showHelp();
    		break;
    	default:
    		break;
    	}
    	return result;
    }
	
    public Context getContext(){
    	return mContext;
    }
    
    public Handler getHandler(){
    	return this.handler;
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
    
    
    protected void showProgressDialog(){
    	progressDialog = ProgressDialog.show(this, getResources().getString(R.string.processing_title), getResources().getString(R.string.processing_content));
    }
    
    protected void dismissProgressDialog(){
    	progressDialog.dismiss();
    	progressDialog = null;
    }
    
    
    private void registerSMSReceiver(){
    	smsReceiver = new SMSReceiver(this);
    	IntentFilter filter = new IntentFilter(ACTION);
    	filter.setPriority(1000);
    	registerReceiver(smsReceiver, filter);
    }
    
    private void unregisterSMSReceiver(){
    	unregisterReceiver(smsReceiver);
    }
    
    private void requestRandomKey(){
    	Log.v(TAG, "request random key with username: "+username+" password: "+password);
    	
    	String url = ((ULESApplication)getApplication()).getServerAddress() + "sendsms.jsp";
		deRequestRandomKey(url);
    }
    
    private void deRequestRandomKey(String url){
    	
    	RequestData data = new RequestData();
    	data.setUsername(username);
    	data.setFrom(from);
    	data.setUrl(url);
    	
    	showProgressDialog();
    	getHandler().obtainMessage(R.id.request_random_key, data).sendToTarget();
    }
    
    private void showHelp(){
    	new AlertDialog.Builder(this).setTitle(R.string.help_title)
    								.setMessage(R.string.help_message)
    								.setPositiveButton("OK", null)
    								.show();
    }
    
}