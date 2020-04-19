package Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import DataBase.DAO;

public class SObject {
	Socket sc;
	InputStream IS = null;
	OutputStream OS = null;
	DAO dao = DAO.getInstance();

	SObject(Socket S) {
		sc = S;
	}

	public void SendNewSongList() {
		ArrayList<String[]> sendData = dao.getContents();//db에 접근해서 리스트 받기
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 객체 분해시 순서대로 byte를 재조립 해야 하므로 순서대로 분해하는 틀(객체를 클래스화하는걸 생각해보자)을 만들어줌
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// ObjectOutputStream의 메소드를 사용할때 정해진 byte순서대로 하기위해 매개변수에 baos를 넣어줌
			oos.writeObject(sendData);
			// 전송할 데이터를 Object형식으로 정해준 baos byte로 쪼갬
			byte reBuffer[] = baos.toByteArray();
			// 쪼개진 Object형식데이터를 baos의 순서대로 /byte[]그릇에 순차적으로 담음
			OS = sc.getOutputStream();
			// 해당하는 소켓으로 보낼 OutputStream생성
			OS.write(reBuffer);
			// 위에서 쪼개진 Object형식데이터 byte로 전송
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}