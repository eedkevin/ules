package hk.hku.cs.msc.ules;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class USBSocketServer extends Thread{
	private static final String TAG = "USBSocketServer";
	
	private String serverIP;
	private String port;
	private ServerSocket serverSocket;
	
	private final Context mContext;
	private Handler handler;

	USBSocketServer(Context context){
		this.mContext = context;
		this.serverIP = context.getResources().getString(R.string.web_server_default_address);
		this.port = context.getResources().getString(R.string.socket_connection_port);
		handler = new USBSocketServerHandler(this);
	}
	
	public Handler getHandler(){
		return this.handler;
	}
	
	public Context getContext(){
		return this.mContext;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try{
			if(serverIP != null){
				serverSocket = new ServerSocket(Integer.parseInt(port));
				while(true){
					Socket client = serverSocket.accept();
					String ipAddr = client.getInetAddress().getHostAddress();
					Log.v(TAG, "=======>" + ipAddr + "<======");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  

	
}
