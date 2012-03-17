package hk.hku.cs.msc.ules;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ULESActivity extends Activity {
	
	public static final String SETTING_INFO = "setting_info";
	public static final String USERNAME = "USERNAME";
	public static final String PASSWORD = "PASSWORD";
	
	
	private Button btnRequestKey;
		
	private String username;
	private String password;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        btnRequestKey = (Button) findViewById(R.id.main_btn_request_key);
        btnRequestKey.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Request random key from server
				requestKeyFromServer();
			}
		});
        
        // Set default value for server address
        ((ULESApplication)getApplication()).setGlobalServerAddress(getResources().getString(R.string.server_default_address));
        
        
    }
    
    private void requestKeyFromServer(){
    	
    	getSavedPassword();
    	
    	if(username.equals("") || password.equals("")){
    		// Display a input dialog request for username and password
    		
    	}else{
    		
    	}
    }
    
    // Get the saved username and password
    private void getSavedPassword(){
    	
    	// Restore prederences
    	SharedPreferences sp = getSharedPreferences(SETTING_INFO, 0);
    	username = sp.getString(USERNAME, "");
    	password = sp.getString(PASSWORD, "");
    }
    
    
    
    
    
    
}