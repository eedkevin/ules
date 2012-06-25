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
	
	private Handler handler;
	
	private ProgressDialog progressDialog;		
	
	// UI components
	private Button btnRequestKey;
	private EditText et_username;
	private EditText et_password;
	
	// temp data
	protected String username = "kevin";
	private String password = "123456";
	private String from = "mobile";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = getApplication();
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
        
        // Set default value for server address
        ((ULESApplication)getApplication()).setServerAddress(getResources().getString(R.string.web_server_default_address));
        
        
    }       
    
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu){
    	boolean result = super.onCreateOptionsMenu(menu);
    	SubMenu settingsMenu = menu.addSubMenu(0, Menu.FIRST, Menu.NONE, "Settings"); 
    	SubMenu helpMenu = menu.addSubMenu(0, Menu.FIRST + 1, Menu.NONE, "Help"); 
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
    		break;
    	case Menu.FIRST + 10:
    		break;
    	default:
    		break;
    	}
    	return result;
    }
    
    public Handler getHandler(){
    	return this.handler;
    }
    
    public void alertConnectionStatus(int status){
    	Toast.makeText(this, status, 3).show();
    }
    
    public void requestMountKey(String randomKey){
    	Log.v(TAG+" requestMountKey", "random key: "+ randomKey);
    	
    }
    
    private void registerSMSReceiver(){
    	SMSReceiver receiver = new SMSReceiver(this);
    	IntentFilter filter = new IntentFilter(ACTION);
    	filter.setPriority(1000);
    	registerReceiver(receiver, filter);
    }
    
    private void unregisterSMSReceiver(){
    	
    }
    
    private void requestRandomKey(){
//    	getSavedPassword();
    	Log.v(TAG+" getSavedPassword", "username: "+username+" password: "+password);
    	
    	if(username.equals("") || password.equals("")){
    		// Pop up a input dialog requesting for username and password    		
    		buildDialog();
    	}else{
//    		new AlertDialog.Builder(this).setTitle(username).show();
    		String url = ((ULESApplication)getApplication()).getServerAddress() + "sendsms.jsp";
    		deRequestRandomKey(url);
    	}
    	
    }
    
    private void deRequestRandomKey(String url){
    	Log.v(TAG,"doRequestRandomKey");
    	
    	RequestData data = new RequestData();
    	data.setUsername(username);
    	data.setFrom(from);
    	data.setUrl(url);
    	
    	showProgressDialog();
    	getHandler().obtainMessage(R.id.request_random_key, data).sendToTarget();
    }
    
    protected void showProgressDialog(){
    	progressDialog = ProgressDialog.show(this, "Connecting to Server", "Please wait a second");
    }
    
    protected void dismissProgressDialog(){
    	progressDialog.dismiss();
    	progressDialog = null;
    }

    private void buildDialog(){
    	Builder dialog = new AlertDialog.Builder(this);
		LinearLayout layout = (LinearLayout) ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.input_dialog, null);
		dialog.setView(layout);
		et_username = (EditText) layout.findViewById(R.id.editText_username);
		et_password = (EditText) layout.findViewById(R.id.editText_passowrd);
		
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
    }    
    
    
//  // Get the saved username and password
//  private void getSavedPassword(){
//  	
//  	// Restore prederences
//  	SharedPreferences sp = getSharedPreferences(SETTING_INFO, 0);
//  	username = sp.getString(USERNAME, "");
//  	password = sp.getString(PASSWORD, "");
//  	Log.v(TAG+" getSavedPassword", "username: "+username+" password: "+password);
//  }
    
    
//  private void doRequestMountKey(String randomKey){
//	Log.v(TAG,"doRequestMountKey");
//	progressDialog = ProgressDialog.show(this, "Connecting to Server", "Please wait a second");
//	
//	final String url = ((ULESApplication)getApplication()).getServerAddress() + "mountkey";    	
//}
}