package hk.hku.cs.msc.ules;

import android.app.Application;

public class ULESApplication extends Application{

	private String serverAddress;
	private boolean isExit;
	
	public String getServerAddress(){
		return this.serverAddress;
	}
	
	public void setServerAddress(String serverAddress){
		this.serverAddress = serverAddress;
	}
	
	public boolean isExit(){
		return this.isExit;
	}
	
	public void setExit(boolean isExit){
		this.isExit = isExit;
	}
	
	public void reset(){
		setExit(false);
	}

}
