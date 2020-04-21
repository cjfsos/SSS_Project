package Client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import DataBase.Client_DTO;
import DataBase.Server_DTO;
import JTable.MainFrames;

public class CObject {
	InputStream IS = null;
	OutputStream OS = null;
	Socket sc;
	MainFrames MF = null;

	CObject(Socket sc) {
		this.sc = sc;
	}

	public void setMFins(MainFrames mf) {
		this.MF = mf;
	}

	public void ReceiveNewSongList() {// 처음 Frame 켜질시 한번 사용
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

	public void SendCollete(Client_DTO CD) {// user가 더블클릭시마다 사용
		System.out.println("클라이언트 OB송신");
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 객체 분해시 순서대로 byte를 재조립 해야 하므로 순서대로 분해하는 틀(객체를 클래스화하는걸 생각해보자)을 만들어줌
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// ObjectOutputStream의 메소드를 사용할때 정해진 byte순서대로 하기위해 매개변수에 baos를 넣어줌
			oos.writeObject(CD);
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

	public void receiveCurrently() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				byte[] reBuffer = new byte[1024];
				System.out.println("서버로부터 OB수신");
				try {
					IS = sc.getInputStream();
					IS.read(reBuffer);// 여기서 대기로 인해 코드가 멈추는걸 방지하기 위해 멀티 쓰레드
					ByteArrayInputStream bais = new ByteArrayInputStream(reBuffer);
					ObjectInputStream ois = new ObjectInputStream(bais);
					try {
						Object o = ois.readObject();
						ArrayList<String[]> USData = (ArrayList<String[]>) o;
						int sumRow = MF.SubTableModel.getRowCount();
						for (int i = sumRow; i > 0; i--) {
							MF.SubTableModel.removeRow(0);
						}
						for (int i = 0; i < USData.size(); i++) {
							MF.SubTableModel.addRow(USData.get(i));
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

	public void receiveRecommend() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				byte[] reBuffer = new byte[1024];
				System.out.println("서버로부터 OB수신");
				try {
					IS = sc.getInputStream();
					IS.read(reBuffer);// 여기서 대기로 인해 코드가 멈추는걸 방지하기 위해 멀티 쓰레드
					ByteArrayInputStream bais = new ByteArrayInputStream(reBuffer);
					ObjectInputStream ois = new ObjectInputStream(bais);
					try {
						Object o = ois.readObject();
						ArrayList<String[]> Data = (ArrayList<String[]>) o;
						System.out.println(Data.size());
						int sumRow = MF.SubTableModel.getRowCount();
						for (int i = sumRow; i > 0; i--) {
							MF.SubTableModel.removeRow(0);
						}
						for (int i = 0; i < Data.size(); i++) {
							MF.SubTableModel.addRow(Data.get(i));
						}
						MF.CurrentlyList = false;
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