package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;

import DataBase.DAO;

public class SCenter extends Thread {
	Socket sc = null;
	private InputStream inMsg = null;
	private OutputStream outMsg = null;
	private DAO dao = DAO.getInstance();
	private ServerSocket SsObjectSK = null;
	private Socket SObjectSK = null;
	private Random r = new Random();
	private int SObjectPort = r.nextInt(998) + 9000;
//	private SCenter sct = null;
	private SObject Sob;

	SCenter(Socket sc) {
//		sct = this;
		this.sc = sc;
	}

	@Override
	public void run() {
		Receive();
	}

	private void Receive() {
		while (true) {
			try {
				inMsg = sc.getInputStream();
				byte reBuffer[] = new byte[1024];
				inMsg.read(reBuffer);
				String msg = new String(reBuffer);
				msg = msg.trim();
				System.out.println(msg);
				ForkedRoad(msg);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("클라이언트 접속종료");
				break;
			}
		}
	}

	private void ForkedRoad(String msg) {
		if (msg.equals("StartProgram")) {
			Send("allowProgram" + "/" + SObjectPort);
			SettingSeObject();// MF에서 노래리스트를 요청할테니 미리 생성
		} else if (msg.equals("SongList")) {
			Sob.SendNewSongList();
		} else if (msg.contains("IDOverCheck")) {
			STidcheck(msg);
		} else if (msg.contains("SignUpDB")) {
			STSignUp(msg);
		} else if (msg.contains("loginChecking")) {
			STsignin(msg);
		} else if (msg.contains("currently")) {
			currently(msg);
		}else if(msg.contains("Recommend")) {
			Recommend(msg);
		}else if(msg.equals("OBReady?")) {
			Sob.receveCollte();
			Send("OBok");
		}else {
			System.out.println("클라이언트 승인 오류");
		}
	}

	private void Recommend(String msg) {
		StringTokenizer tk = new StringTokenizer(msg,"/");
		if(tk.nextToken().equals("Recommend")) {
			Sob.SendRecommend(dao.SeachMode(tk.nextToken()));
		}
	}

	private void currently(String msg) {
		StringTokenizer tk = new StringTokenizer(msg,"/");
		if(tk.nextToken().equals("currently")) {
			Sob.SendCurrently(dao.currently(tk.nextToken()));
		}
	}

	private void STsignin(String msg) {
		StringTokenizer tk = new StringTokenizer(msg, "/");
		if ("loginChecking".equals(tk.nextToken())) {
			String logID = tk.nextToken();
			String logPW = tk.nextToken();
			boolean result = dao.login(logID, logPW);
			if (result) {
				Send("LoginSuccess/" + logID);
			} else {
				Send("LoginFalse");
			}
		}
	}

	private void STSignUp(String msg) {
		StringTokenizer tk = new StringTokenizer(msg, "/");
		if (tk.nextToken().equals("SignUpDB")) {
			String id = tk.nextToken();
			String pw = tk.nextToken();
			int k = dao.signup(id, pw);
			if (k == 1) {
				Send("SignUpCommit");
			} else if (k == 0) {
				System.out.println("회원가입DB erorr");
			}
		}
	}

	private void STidcheck(String msg) {
		StringTokenizer tk = new StringTokenizer(msg, "/");
		if (tk.nextToken().equals("IDOverCheck")) {
			String id = tk.nextToken();
			if (dao.DBIdcheck(id)) {
				Send("중복된 ID입니다.");
			} else {
				Send("사용가능한 ID입니다.");
			}
		} else {
			System.out.println("ID중복 메시지 오류");
		}
	}

	private void Send(String msg) {
		try {
			outMsg = sc.getOutputStream();
			String sket = msg;
			System.out.println(sket + "서버가전송");// 메시지 확인용
			outMsg.write(sket.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void SettingSeObject() {
		try {
			SsObjectSK = new ServerSocket();
			SsObjectSK.bind(new InetSocketAddress("10.0.0.97", SObjectPort));
			SObjectSK = SsObjectSK.accept();
			Sob = new SObject(SObjectSK);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}