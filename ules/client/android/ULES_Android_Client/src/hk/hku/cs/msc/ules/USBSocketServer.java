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
		this.serverIP = context.getResources().getString(R.string.web_server_default_address);
		this.port = context.getResources().getString(R.string.socket_connection_port);

		handler = new USBSocketServerHandler(this);
		
//		// start a new thread to listen to the socket connection
//		if(serverIP != null && port != null){
//			startServer();
//		}		
		
	}
	
	public Handler getHandler(){
		return this.handler;
	}
	
	public Context getContext(){
		return this.mContext;
	}
	
	protected void write(String text){
		
		while(!isAccepted){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writer.println(text);
		writer.flush();
		
	}
	
	private void startServer(){
		try{
			isAccepted = false;
			serverSocket = new ServerSocket(Integer.parseInt(port));
			while(true){
				client = serverSocket.accept();
				isAccepted = true;
				String ipAddr = client.getInetAddress().getHostAddress();
				Log.v(TAG, "Client ip address: " + ipAddr);
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				Log.v(TAG, "Client said: " + reader.readLine());
				
				writer = new PrintWriter(client.getOutputStream(),true);
				writer.println("Hi pc client, you are already connected to android server");		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isAccepted = false;
		}		
	}

	
//	private void startServer(){
//		new Thread(){
//			public void run(){
////				serverIP = mContext.getResources().getString(R.string.web_server_default_address);
////				port = mContext.getResources().getString(R.string.socket_connection_port);
//				try{
//					serverSocket = new ServerSocket(Integer.parseInt(port));
//					while(true){
//						client = serverSocket.accept();
//						String ipAddr = client.getInetAddress().getHostAddress();
//						Log.v(TAG, "Client ip address: " + ipAddr);
//						BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//						Log.v(TAG, "Client said: " + reader.readLine());
//						
//						writer = new PrintWriter(client.getOutputStream(),true);
//						writer.println("Hi pc client, you are already connected to android server");		
//					}
//				} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				}
//			}
//		}.start();		
//	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();	
		
		if(serverIP != null && port != null){
			startServer();
		}else{
			this.serverIP = mContext.getResources().getString(R.string.web_server_default_address);
			this.port = mContext.getResources().getString(R.string.socket_connection_port);
			startServer();
		}
		
//		try{
//			if(serverIP != null){
//				serverSocket = new ServerSocket(Integer.parseInt(port));
//				while(true){
//					Socket client = serverSocket.accept();
//					String ipAddr = client.getInetAddress().getHostAddress();
//					Log.v(TAG, "=======>" + ipAddr + "<======");
//					BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//					Log.v(TAG, "message received: " + reader.readLine());
//					
//					PrintWriter writer = new PrintWriter(client.getOutputStream(),true);
//					writer.println("Hi pc client, you are already connected to android server");
//					
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}  

	
}
