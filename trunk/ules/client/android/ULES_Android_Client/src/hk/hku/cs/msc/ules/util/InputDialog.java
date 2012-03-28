package hk.hku.cs.msc.ules.util;

import hk.hku.cs.msc.ules.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputDialog extends Dialog{
	
	private EditText username;
	private EditText password;
	private Button confirm;
	private Button cancel;

	public InputDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.input_dialog);
		username = (EditText) findViewById(R.id.editText_username);
		password = (EditText) findViewById(R.id.editText_password);
		
//		confirm = (Button) findViewById(R.id.button_confirm);
//		confirm.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		cancel = (Button) findViewById(R.id.button_cancel);
//		cancel.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}
	
	public String getUsername(){
		return this.username.getText().toString().trim();
	}
	
	public String getPassword(){
		return this.password.getText().toString().trim();
	}	
	
	

}
