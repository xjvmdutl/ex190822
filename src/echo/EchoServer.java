package echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	private static final int PORT = 8000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket=new ServerSocket();
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localHostAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(localHostAddress,PORT);
			serverSocket.bind(inetSocketAddress);
			System.out.println("[EchoServer] binding "+inetAddress.getHostAddress()+":"+PORT);
			
			Socket socket=serverSocket.accept();
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetRemoteSocketAddress.getPort();
			System.out.println("[EchoServer] Connected from client ["+remoteHostAddress+":"+remoteHostPort+"]");
			try {
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				while(true) {
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer);
					if(readByteCount == -1)
						return;
					String data = new String(buffer,0,readByteCount,"UTF-8");
					System.out.println("[EchoServer] recieve : "+data);
					os.write(data.getBytes("UTF-8"));
				}
			}catch(SocketException e){//소켓이 비정상적으로 종료가 되었을때
				System.out.println("[EchoServer] abnormal closed by client");
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				if(socket.isClosed()==false && socket != null)
					socket.close();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(serverSocket != null && serverSocket.isClosed()==false)
					serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
