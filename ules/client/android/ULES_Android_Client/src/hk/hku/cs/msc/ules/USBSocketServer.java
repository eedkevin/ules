package hk.hku.cs.msc.ules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
	private Socket client;
	private PrintWriter writer;
	
	private final Context mContext;
	private Handler handler;
	
	private boolean isAccepted;

	USBSocketServer(Context context){
		Log.v(TAG, "Started");
		
		this.mContext = context;
		this.serverIP = context.getResources().getString(R.string.socket_server_address);
		this.port = context.getResources().getString(R.string.socket_connection_port);

		handler = new USBSocketServerHandler(this);
		isAccepted = false;
//		// start a new thread to listen to the socket connection
//		if(serverIP != null && port != null){
//			startServer();
//		}		
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Log.v(TAG, "destroy");
		super.destroy();
		this.closeSockets();
	}
	
	

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		Log.v(TAG, "interrupt");
		super.interrupt();
		this.closeSockets();
	}

	public Handler getHandler(){
		return this.handler;
	}
	
	public Context getContext(){
		return this.mContext;
	}
	
	protected void write(String text){
		int n = 0;
		
		// try up to 5 times, that's 10 seconds
		while(!isAccepted && n <5){
			try {
				Thread.sleep(2000);
				n++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(serverSocket !=null && client !=null){
			writer.println(text);
			writer.flush();
		}else{
			Log.v(TAG, "there is no client to write data to");
		}
		((ULESActivity)mContext).getHandler().obtainMessage(R.id.quit).sendToTarget();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();	
		
		if(serverIP != null && port != null){
			startServer();
		}else{
			this.serverIP = mContext.getResources().getString(R.string.socket_server_address);
			this.port = mContext.getResources().getString(R.string.socket_connection_port);
			startServer();
		}
	}  
	
	private void startServer(){
		Log.v(TAG,"startServer");
		try{
			serverSocket = new ServerSocket(Integer.parseInt(port));
			while(!isAccepted){
				client = serverSocket.accept();
				isAccepted = true;
				String ipAddr = client.getInetAddress().getHostAddress();
				Log.v(TAG, "Client ip address: " + ipAddr);
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				Log.v(TAG, "Client said: " + reader.readLine());
				
				writer = new PrintWriter(client.getOutputStream(),true);
				writer.println("Hi pc client, you are already connected to android server");
				writer.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isAccepted = false;
		}	
	}
	
//	private void isClientConnecting(){
//		try{
//			serverSocket.
//		}catch(Exception ex){
//		      reconnect();
//		}
//	}
	
	private void closeSockets(){
		Log.v(TAG, "close sockets");
		try {
			if(client!=null) client.close();
			if(serverSocket!=null) serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
