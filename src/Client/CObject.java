package Client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import JTable.MainFrame;

public class CObject {
	InputStream IS = null;
	Socket sc;
	MainFrame MF = null;

	CObject(Socket sc) {
		this.sc = sc;
	}

	public void setMFins(MainFrame mf) {
		this.MF = mf;
	}

	public void ReceiveNewSongList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				byte[] reBuffer = new byte[1024];
				// byte타입으로 받을 그릇 생성
				try {
					IS = sc.getInputStream();
					// 소켓을 통해 받을 InputStream생성
					IS.read(reBuffer);// 여기서 대기로 인해 코드가 멈추는걸 방지하기 위해 멀티 쓰레드
					// byte 그릇으로 전송된 Data를 읽어옴
					ByteArrayInputStream bais = new ByteArrayInputStream(reBuffer);
					// 해당 byte를 정해진 순서대로 조립하기 위함
					ObjectInputStream ois = new ObjectInputStream(bais);
					// bais의 순서대로 오브젝트 타입으로 byte를 재조립
					try {
						Object o = ois.readObject();
						// 정해진 순서대로 재조립된 byte를 Object객체로 형 변환
						ArrayList<String[]> Data = (ArrayList<String[]>) o;
						// Object인 o를 직렬화전의 객체 형태인 ArrayList<String[]>타입으로 재조직
						for (int i = 0; i < Data.size(); i++) {
							MF.MainTableModel.addRow(Data.get(i));
							// 노래리스트 추가
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}