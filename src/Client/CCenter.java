package Client;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import JTable.MainFrame;
import JTable.MsgBox;

public class CCenter {
	private Socket sc = null;
	private InputStream inMsg = null;
	private OutputStream outMsg = null;
	private MainFrame MF = null;
	private int CObjectPort;
	private CObject CO;
	private Socket CObjectSK;
	public MsgBox MB = new MsgBox();

	CCenter(Socket sc) {
		this.sc = sc;
		String startmsg = "StartProgram";
		Send(startmsg);
		Receive();
//		beginData();
	}

	public void Send(String msg) {// 굳이 여기서 보내고
		try {
			outMsg = sc.getOutputStream();
//		System.out.println(msg);//전송확인용
			outMsg.write(msg.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void Receive() {// S에게 메시지를 받은 다음에 프레임이 열리게 한 이유는 서버가 닫혀 있는상태에서 서비스 이용할수 없게 하기 위해
		while (true) {
			try {
				inMsg = sc.getInputStream();
				byte reBuffer[] = new byte[1024];
				inMsg.read(reBuffer);
				String msg = new String(reBuffer);
				msg = msg.trim();
				System.out.println(msg);// 전송이 제대로 왔는지 확인용
				ForkedRoad(msg);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("서버 접속 끈킴");
				break;
			}
		}
	}

	private void ForkedRoad(String msg) {
		if (msg.contains("allowProgram")) {
			StringTokenizer tk = new StringTokenizer(msg, "/");
			if (tk.nextToken().equals("allowProgram")) {
				CObjectPort = Integer.parseInt(tk.nextToken());
				SettingReObject();
				MF = new MainFrame(this, CO);
			} else {
				System.out.println("서버 프로그램 허락메시지 오류");
			}
		} else if (msg.equals("중복된 ID입니다.")) {
//			MF.SU.idcheck.setText("중복된 ID입니다.");
//			MF.SU.idcheck.setForeground(Color.RED);
//		} else if (msg.equals("사용가능한 ID입니다.")) {
//			MF.SU.idcheck.setText("사용가능한 ID입니다.");
//			MF.SU.idcheck.setForeground(Color.BLUE);
//			MF.SU.idckeked = true;
//		} else if(msg.equals("SignUpCommit")) {
//			MB.signupMSG();
//			MF.SU.dispose();
		} else if (msg.contains("LoginSuccess")) {
			StringTokenizer tk = new StringTokenizer(msg, "/");
			if(tk.nextToken().equals("LoginSuccess")){
				MF.loginMSG.setText("");
				MF.IDtextField.setText("");
				MF.PWtextField.setText("");
				MF.EpNp1.setVisible(false);
				MF.EpNp2.setVisible(true);
				MF.guest.setText(tk.nextToken());
			}
		} else if (msg.equals("LoginFalse")) {
			MF.loginMSG.setText("");
			MF.loginMSG.setText("<html>가입되지 않은 ID이거나 <br> ID/PW가 틀립니다.</html>");
		}
	}

	private void SettingReObject() {
		try {
			CObjectSK = new Socket("172.30.1.5", CObjectPort);
			CO = new CObject(CObjectSK);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}