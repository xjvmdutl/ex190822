package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	private static String SERVER_IP="192.168.1.2";
	private static int SERVER_PORT=5000;
	
	public static void main(String[] args) {
		Socket socket =null;
		try {
			//1. 소켓 생성
			socket = new Socket();
			//2. 서버 연결
			InetSocketAddress inetSocketAddress=new InetSocketAddress(SERVER_IP,SERVER_PORT);
			socket.connect(inetSocketAddress);//연결 실패시 timeover
			System.out.println("[TCPClient] connected");
			
			//3. io스트림 받아오기
			InputStream is =socket.getInputStream();
			OutputStream os=socket.getOutputStream();

			//4.쓰기 
			String data = "안녕하세요\n";
			os.write(data.getBytes("UTF-8"));
			
			//5.읽기
			while(true) {
				byte[] buffer = new byte[256];
				int readByteCount = is.read(buffer);//5.데이터 읽기, 읽은 만큼 데이터 길이 만큼 읽어 온다.
				if(readByteCount == -1) {
					//정상종료 : remoteSocket이 close()메소드를 통해서 정상적으로 소켓을 닫는 경우
					System.out.println("[TCPClient] closed by client");
					return;
				}
				data = new String(buffer,0,readByteCount,"UTF-8");//직접 인코딩한 경우
				System.out.println("[TCPClient] received : "+data);//telnet개행을 하나 더 보내줘서 하나 더 받아준다.(신경 쓰지 않아도됨)
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(socket!=null && socket.isClosed()==false)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
