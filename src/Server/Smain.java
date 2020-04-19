package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Smain {

	public static void main(String[] args) throws IOException {
		ServerSocket ssc = new ServerSocket();
		ssc.bind(new InetSocketAddress("172.30.1.5", 9999));
		while (true) {
			System.out.println("클라이언트 접속대기중");//확인용
			Socket sc = ssc.accept();
			System.out.println(sc.getInetAddress() + "/" + sc.getPort() + "/ 클라이언트 접속");// 접속확인용
			new SCenter(sc);
		}
	}
}