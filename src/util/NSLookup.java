package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String name=null;
		while(true) {
			System.out.print(">>");
			name= sc.nextLine();
			if("quit".equals(name))
				break;
			try {
				InetAddress inetMyAddress=InetAddress.getLocalHost();
				String hostAddress = inetMyAddress.getHostAddress();
				System.out.println(hostAddress);
				InetAddress[] inetAddresses = InetAddress.getAllByName(name);
				for(InetAddress inetAddress : inetAddresses) {
					System.out.println(inetAddress.getHostAddress());
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
