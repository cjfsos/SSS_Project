package JTable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Client.CCenter;
import Client.CObject;
import DataBase.DAO;

public class MainFrame extends JFrame {
	private JPanel contentPane;
	DAO daoIns = DAO.getInstance();
	ArrayList<String[]> DTList = new ArrayList<>();
	JPanel Ep;
	String header[] = { "번호", "곡명", "가수명", "앨범", "장르" };
	String contents[][];
	public DefaultTableModel MainTableModel = new DefaultTableModel(contents, header);
	JTable MainTable = new JTable(MainTableModel);
	CObject CO;
	public CCenter CT;
	MainFrame MF = this;
	int selectedRow = -1;
	MsgBox MB = new MsgBox();
	public SignUp SU = null;
	public JTextField IDtextField;
	public JTextField PWtextField;
	JButton signIn;
	public String UIid;
	String UIpw;
	public JLabel loginMSG;
	public JPanel EpNp1;
	public JPanel EpNp2;
	public JLabel guest;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainFrame frame = new MainFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public MainFrame(CCenter CT, CObject CO) {
		this.CT = CT;
		this.CO = CO;
		CO.setMFins(MF);
		WestSetting();
		EastSetting();
		this.setVisible(true);
	}

	private void EastSetting() {
		Ep = new JPanel();
		Ep.setBounds(621, 0, 204, 578);
		contentPane.add(Ep);
		Ep.setLayout(null);
		EpNorthSetting1();
		EpNorthSetting2();
		EpSouthSetting();
		EpCenterSettig();
	}
	
	private void EpNorthSetting1() {
		EpNp1 = new JPanel();
		EpNp1.setBounds(0, 0, 204, 126);
		Ep.add(EpNp1);
		EpNp1.setLayout(null);
		
		JLabel IDLabel = new JLabel("ID");
		IDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		IDLabel.setBounds(22, 16, 27, 15);
		EpNp1.add(IDLabel);
		
		JLabel PWLabel = new JLabel("PW");
		PWLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		PWLabel.setBounds(22, 41, 27, 15);
		EpNp1.add(PWLabel);
		
		IDtextField = new JTextField();
		IDtextField.setBounds(55, 10, 107, 21);
		EpNp1.add(IDtextField);
		IDtextField.setColumns(10);
		
		PWtextField = new JTextField();
		PWtextField.setColumns(10);
		PWtextField.setBounds(55, 38, 107, 21);
		EpNp1.add(PWtextField);
		
		loginMSG = new JLabel("");
		loginMSG.setBounds(35, 64, 140, 30);
		EpNp1.add(loginMSG);
		
		signIn = new JButton("로그인");
		signInAction();
		signIn.setBounds(15, 95, 80, 23);
		EpNp1.add(signIn);
		
		JButton SignUp = new JButton("회원가입");
		SignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SU = new SignUp(MF);
			}
		});
		SignUp.setBounds(105, 95, 85, 23);
		EpNp1.add(SignUp);
		EpNp1.setVisible(true);
	}

	private void signInAction() {
		signIn.addActionListener(new ActionListener() {// 로그인 버튼을 눌렀을때
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		IDtextField.addActionListener(new ActionListener() {// ID텍스트필드에서 엔터를 눌렀을때
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		PWtextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {// PW텍스트필드에서 엔터를 눌렀을때
				login();
			}
		});
	}

	protected void login() {
		// ID/Pw일치할때만 로그인 성공이 나오게 하고 그외에는 전부
				// 가입되지 않은 ID이거나 ID/PW가 틀립니다.
				// 각 ID나 PW일치시 알려주는건 보안문제가 발생하니까
				if (!IDtextField.getText().equals("") && !PWtextField.getText().equals("")) {
					UIid = IDtextField.getText();
					UIpw = PWtextField.getText();
					String loginCKmsg = "loginChecking/" + UIid + "/" + UIpw;
					CT.Send(loginCKmsg);// 바로 메시지 보내지 말고 입력값이 없으면 클라이언트에서 바로 오류메시지 뜨게할것!
				} else {
					loginMSG.setText("<html>ID와 PW를<br> 전부 입력해주세요.</html>");
				}
	}

	private void EpNorthSetting2() {
		EpNp2 = new JPanel();
		EpNp2.setBounds(0, 0, 204, 126);
		Ep.add(EpNp2);
		EpNp2.setLayout(null);
		
		guest = new JLabel("");
		guest.setHorizontalAlignment(SwingConstants.RIGHT);
		guest.setBounds(12, 20, 80, 22);
		EpNp2.add(guest);
		
		JLabel lblNewLabel = new JLabel("님 접속중");
		lblNewLabel.setBounds(104, 20, 77, 22);
		EpNp2.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(12, 52, 85, 23);
		EpNp2.add(btnNewButton);
		
		JButton logout = new JButton("로그아웃");
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guest.setText("");
				EpNp2.setVisible(false);
				EpNp1.setVisible(true);
			}
		});
		logout.setBounds(104, 52, 85, 23);
		EpNp2.add(logout);
		
		JButton button_1 = new JButton("New button");
		button_1.setBounds(104, 85, 85, 23);
		EpNp2.add(button_1);
		
		JButton button_2 = new JButton("New button");
		button_2.setBounds(12, 85, 85, 23);
		EpNp2.add(button_2);
		EpNp2.setVisible(false);
	}

	private void EpCenterSettig() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 125, 204, 391);
		Ep.add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 515, 204, 63);
		Ep.add(panel);
		panel.setLayout(null);
		
		JLabel playBtn = new JLabel("");
		playBtn.setIcon(new ImageIcon("C:\\Users\\w\\Desktop\\재생버튼.png"));
		playBtn.setBounds(35, 10, 45, 35);
		panel.add(playBtn);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\w\\Desktop\\정지.png"));
		label.setBounds(124, 10, 45, 35);
		panel.add(label);
	}

	private void EpSouthSetting() {
	}


	private void WestSetting() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 841, 617);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane MainScrollPane = new JScrollPane(MainTable);
		MainScrollPane.setBounds(0, 0, 623, 578);
		contentPane.add(MainScrollPane);
		
		String loadList = "SongList";
		CO.ReceiveNewSongList();// 리스트를 오브젝트로 받을 준비
		CT.Send(loadList);// 리스트를 서버에게 달라고 요청
		MainTable.getTableHeader().setReorderingAllowed(false);
		MainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
