package echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static String SERVER_IP="192.168.1.2";
	private static int SERVER_PORT=8000;
	public static void main(String[] args) {
		Socket socket =null;
		try {
			socket=new Socket();
			InetSocketAddress inetSocketAddress=new InetSocketAddress(SERVER_IP,SERVER_PORT);
			socket.connect(inetSocketAddress);
			System.out.println("[EchoClient] connected");
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			Scanner sc = new Scanner(System.in);
			while(true) {
				System.out.print(">>");
				String input= sc.nextLine();
				if("exit".equals(input))
					break;
				os.write(input.getBytes("UTF-8"));
				
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer);
					
					if(readByteCount == -1) {
						System.out.println("[EchoClient] closed by client");
						break;
					}
					String data = new String(buffer,0,readByteCount,"UTF-8");
					System.out.println("<< : "+data);		
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(socket!=null && socket.isClosed()==false)
					socket.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
