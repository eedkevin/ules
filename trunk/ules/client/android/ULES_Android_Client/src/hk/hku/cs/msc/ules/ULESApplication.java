package hk.hku.cs.msc.ules;

import android.app.Application;

public class ULESApplication extends Application{

	private String globalServerAddress;
	
	public String getGlobalServerAddress(){
		return this.globalServerAddress;
	}
	
	public void setGlobalServerAddress(String serverAddress){
		this.globalServerAddress = serverAddress;
	}
}
