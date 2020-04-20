package Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cmain {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket sc = new Socket("10.0.0.97", 9999);
		System.out.println(sc.getInetAddress() + "/" + sc.getPort() + "/ 서버 접속");// 접속확인용
		new CCenter(sc);
	}
}