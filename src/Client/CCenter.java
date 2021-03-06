package Client;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import JTable.MainFrames;
import JTable.C_MsgBox;

public class CCenter {
	private Socket sc = null;
	private InputStream inMsg = null;
	private OutputStream outMsg = null;
	private MainFrames MF = null;
	private int CObjectPort;
	private CObject CO;
	private Socket CObjectSK;
	public C_MsgBox MB = new C_MsgBox();
	private String CkedID;

	CCenter(Socket sc) {
		this.sc = sc;
		String startmsg = "StartProgram";
		Send(startmsg);
		Receive();
	}

	public void Send(String msg) {// 굳이 여기서 보내고
		try {
			outMsg = sc.getOutputStream();
//			System.out.println(msg + "클라이언트가 전송");// 전송확인용
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
//				System.out.println(msg);// 전송이 제대로 왔는지 확인용
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
				MF = new MainFrames(this, CO);
			} else {
				System.out.println("서버 프로그램 허락메시지 오류");
			}
		} else if (msg.equals("중복된 ID입니다.")) {
			MF.SU.idcheck.setText("중복된 ID입니다.");
			MF.SU.idcheck.setForeground(Color.RED);
		} else if (msg.equals("사용가능한 ID입니다.")) {
			MF.SU.idcheck.setText("사용가능한 ID입니다.");
			MF.SU.idcheck.setForeground(Color.BLUE);
			MF.SU.idckeked = true;
		} else if (msg.equals("SignUpCommit")) {
			MB.signupMSG();
			MF.SU.dispose();
		} else if (msg.contains("LoginSuccess")) {
			StringTokenizer tk = new StringTokenizer(msg, "/");
			if (tk.nextToken().equals("LoginSuccess")) {
				MF.loginMSG.setText("");
				MF.IDtextField.setText("");
				MF.PWtextField.setText("");
				MF.LoginCk = true;
				MF.EpNp1.setVisible(false);
				MF.EpNp2.setVisible(true);
				CkedID = tk.nextToken();
				MF.guest.setText(CkedID);
				logListSetting();
			}
		} else if (msg.equals("LoginFalse")) {
			MF.loginMSG.setText("");
			MF.loginMSG.setText("<html>가입되지 않은 ID이거나 <br> ID/PW가 틀립니다.</html>");
		} else if (msg.equals("OBok")) {
			MF.CollectOB();
		} else if (msg.equals("CollteADsucceed")) {
			int sumRow = MF.SubTableModel.getRowCount();
			for (int i = sumRow; i > 0; i--) {
				MF.SubTableModel.removeRow(0);
			}
		} else if (msg.equals("CollteADsfail")) {
			MB.Collectfail();
		} else if (msg.equals("CollteSDsucceed")) {
			if (MF.LogRow != -1) {
				MF.SubTableModel.removeRow(MF.LogRow);
			} else if (MF.LogRow == -1) {
				MB.SelRowMiss();
			}
		} else if (msg.equals("CollteSDsfail")) {
			MB.Collectfail();
		}
	}

	public void logListSetting() {
		int sumRow = MF.SubTableModel.getRowCount();
		for (int i = sumRow; i > 0; i--) {
			MF.SubTableModel.removeRow(0);
		}
		Send("currently/" + CkedID);
		CO.receiveCurrently();
	}

	public void Recommend() {
		int sumRow = MF.SubTableModel.getRowCount();
		for (int i = sumRow; i > 0; i--) {
			MF.SubTableModel.removeRow(0);
		}
		Send("Recommend/" + CkedID);
		CO.receiveRecommend();
	}

	private void SettingReObject() {
		try {
			CObjectSK = new Socket("10.0.0.97", CObjectPort);
			CO = new CObject(CObjectSK);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}