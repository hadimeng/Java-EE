import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String args[]){
		final int port = 3333;
		ServerSocket serverSocket = null;
		
		
		try {
			serverSocket = new ServerSocket(port);
			
			System.out.println("端口为： " + serverSocket.getLocalPort());
			while(true){
				Socket clientSocket = serverSocket.accept();
				System.out.println("已建立TCP连接，客户端地址为：" + clientSocket.getInetAddress() + "：" + clientSocket.getPort());
				
				serverThread thread = new serverThread(clientSocket);
				Thread line = new Thread(thread);
				
				line.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class serverThread implements Runnable{
	Socket clientSocket = null;
			
	public serverThread(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	public void run(){
		try {
			InputStream socketIn = clientSocket.getInputStream();
			Thread.sleep(500);
			
			int size = socketIn.available();
			byte[] buffer = new byte[size];
			socketIn.read(buffer);
			String request = new String(buffer);
			System.out.println("原字符串：" + request);
		
			//反转
			StringBuffer re=new StringBuffer(request);
	        String str=re.reverse().toString();
	          
			System.out.println("反转之后：" + str);
			
			OutputStream socketOut = clientSocket.getOutputStream();
			socketOut.write(str.getBytes());
			Thread.sleep(1000);
			
			clientSocket.close();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
