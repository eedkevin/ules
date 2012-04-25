package hk.hku.cs.msc.ules;

import android.app.Application;

public class ULESApplication extends Application{

	private String serverAddress;
	
	public String getServerAddress(){
		return this.serverAddress;
	}
	
	public void setServerAddress(String serverAddress){
		this.serverAddress = serverAddress;
	}

}
