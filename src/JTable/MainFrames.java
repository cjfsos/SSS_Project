package JTable;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Client.CCenter;
import Client.CObject;
import DataBase.Client_DTO;
import music.MusicLod;

public class MainFrames extends JFrame {
	private JPanel contentPane;
	ArrayList<String[]> DTList = new ArrayList<>();
	JPanel Ep;
	String header[] = { "번호", "곡명", "가수명", "앨범", "장르" };
	String contents[][];
	public DefaultTableModel MainTableModel = new DefaultTableModel(contents, header) {
		public boolean isCellEditable(int a, int b) {
			return false;
		}
	};
	JTable MainTable = new JTable(MainTableModel);
	CObject CO;
	public CCenter CT;
	MainFrames MF = this;
	public C_MsgBox MB = new C_MsgBox();
	public Sign_Up SU = null;
	public JTextField IDtextField;
	public JTextField PWtextField;
	JButton signIn;
	public String UIid;
	String UIpw;
	public JLabel loginMSG;
	public JPanel EpNp1;
	public JPanel EpNp2;
	public JLabel guest;
	public String subContents[][];
	public DefaultTableModel SubTableModel;
	public boolean LoginCk = false;
	public JTable SubTable;
	JPopupMenu popup;
	public boolean CurrentlyList = false;
	int USrow = -1;
	public int LogRow = -1;
	MusicLod ML = null;

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
	public MainFrames(CCenter CT, CObject CO) {
		this.CT = CT;
		this.CO = CO;
		CO.setMFins(MF);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 672, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		WestSetting();
		EastSetting();
		this.setVisible(true);
	}

	private void EastSetting() {
		Ep = new JPanel();
		Ep.setBounds(451, 0, 213, 399);
		contentPane.add(Ep);
		Ep.setLayout(null);
		EpNorthSetting1();
		EpNorthSetting2();
		EpSouthSetting();
		EpCenterSettig();
		MainTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (LoginCk) {// 로그인시
						if (CurrentlyList) {// 로그인 + 최근재생목록
							afterSet();
						}
					} else {// 비로그인시
						beforeSet();
					}
				}
			}
		});
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) MainTable.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		MainTable.getTableHeader().setDefaultRenderer(renderer);
		TableColumnModel tc = MainTable.getColumnModel();
		tc.getColumn(0).setCellRenderer(renderer);
	}

	protected void beforeSet() {
		USrow = MainTable.getSelectedRow();
		String Maintitle = (String) MainTable.getValueAt(USrow, 1);// 곡
		String Mainsinger = (String) MainTable.getValueAt(USrow, 2);// 가수
		String Maingenre = (String) MainTable.getValueAt(USrow, 4);// 장르

		int Max = SubTable.getRowCount();
		if (Max == 0) {
			String Subsubcontents[] = new String[3];
			Subsubcontents[0] = Maintitle;// 곡
			Subsubcontents[1] = Mainsinger;// 가수
			Subsubcontents[2] = Maingenre;// 장르
			SubTableModel.addRow(Subsubcontents);
		} else {
			boolean check = false;
			for (int i = 0; i < Max; i++) {
				if (Maintitle.equals(SubTable.getValueAt(i, 0))
						/* 곡 일치여부 */ && Mainsinger.equals(SubTable.getValueAt(i, 1))/* 가수 일치여부 */
						&& Maingenre.equals(SubTable.getValueAt(i, 2))/* 장르 일치여부 */) {
					System.out.println("일치");
					MB.addOver();
					check = true;
					break;
				}
			}
			if (!check) {
//				System.out.println("불일치");
				String Subsubcontents[] = new String[3];
				Subsubcontents[0] = Maintitle;// 곡
				Subsubcontents[1] = Mainsinger;// 가수
				Subsubcontents[2] = Maingenre;// 장르
				SubTableModel.addRow(Subsubcontents);
			}
		}
	}

	protected void afterSet() {
		USrow = MainTable.getSelectedRow();
		String Maintitle = (String) MainTable.getValueAt(USrow, 1);// 곡
		String Mainsinger = (String) MainTable.getValueAt(USrow, 2);// 가수
		String Maingenre = (String) MainTable.getValueAt(USrow, 4);// 장르

		int Max = SubTable.getRowCount();
//		System.out.println(Max);
		if (Max == 0) {
//			System.out.println("불일치");
			CT.Send("OBReady?");
//			System.out.println("선택행 없음");
			String Subsubcontents[] = new String[3];
			Subsubcontents[0] = Maintitle;// 곡
			Subsubcontents[1] = Mainsinger;// 가수
			Subsubcontents[2] = Maingenre;// 장르
			SubTableModel.addRow(Subsubcontents);
		} else {
			boolean check = false;
			for (int i = 0; i < Max; i++) {
				if (Maintitle.equals(SubTable.getValueAt(i, 0))
						/* 곡 일치여부 */ && Mainsinger.equals(SubTable.getValueAt(i, 1))/* 가수 일치여부 */
						&& Maingenre.equals(SubTable.getValueAt(i, 2))/* 장르 일치여부 */) {
//					System.out.println("일치");
					MB.addOver();
					check = true;
					break;
				}
			}
			if (!check) {
//				System.out.println("불일치");
				CT.Send("OBReady?");
				USrow = MainTable.getSelectedRow();
				String Subsubcontents[] = new String[3];
				Subsubcontents[0] = Maintitle;// 곡
				Subsubcontents[1] = Mainsinger;// 가수
				Subsubcontents[2] = Maingenre;// 장르
				SubTableModel.addRow(Subsubcontents);
			}
		}
	}

	public void CollectOB() {
		Client_DTO CD = new Client_DTO();
		CD.setId(guest.getText());
		CD.setSong((String) MainTable.getValueAt(USrow, 1));
		CD.setSinger((String) MainTable.getValueAt(USrow, 2));
		CD.setGenre((String) MainTable.getValueAt(USrow, 4));
		CO.SendCollete(CD);
	}

	private void EpNorthSetting1() {
		EpNp1 = new JPanel();
		EpNp1.setBounds(0, 0, 213, 126);
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
		IDtextField.setBounds(55, 10, 120, 21);
		EpNp1.add(IDtextField);
		IDtextField.setColumns(10);

		PWtextField = new JTextField();
		PWtextField.setColumns(10);
		PWtextField.setBounds(55, 38, 120, 21);
		EpNp1.add(PWtextField);

		loginMSG = new JLabel("");
		loginMSG.setBounds(55, 62, 140, 30);
		EpNp1.add(loginMSG);

		signIn = new JButton("로그인");
		signInAction();
		signIn.setBounds(15, 95, 80, 23);
		EpNp1.add(signIn);

		JButton Sign_Up = new JButton("회원가입");
		Sign_Up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SU = new Sign_Up(MF);
			}
		});
		Sign_Up.setBounds(105, 95, 96, 23);
		EpNp1.add(Sign_Up);
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
		EpNp2.setBounds(0, 0, 213, 126);
		Ep.add(EpNp2);
		EpNp2.setLayout(null);

		guest = new JLabel("");
		guest.setHorizontalAlignment(SwingConstants.RIGHT);
		guest.setBounds(5, 10, 80, 22);
		EpNp2.add(guest);

		JLabel lblNewLabel = new JLabel("님 접속중");
		lblNewLabel.setBounds(97, 10, 59, 22);
		EpNp2.add(lblNewLabel);

		JButton logout = new JButton("");
		logout.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\로그아웃.png"));
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guest.setText("");
				EpNp2.setVisible(false);
				EpNp1.setVisible(true);
				LoginCk = false;
				CurrentlyList = false;
				int sumRow = MF.SubTableModel.getRowCount();
				for (int i = sumRow; i > 0; i--) {
					MF.SubTableModel.removeRow(0);
				}
			}
		});
		logout.setBounds(160, 10, 38, 27);
		EpNp2.add(logout);

		JButton button_1 = new JButton("추천받기");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CT.Recommend();
				CurrentlyList = false;
			}
		});
		button_1.setBounds(108, 80, 90, 23);
		EpNp2.add(button_1);

		JButton button_2 = new JButton("재생 목록");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CT.logListSetting();
				CurrentlyList = true;
			}
		});
		button_2.setBounds(12, 80, 90, 23);
		EpNp2.add(button_2);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(57, 42, 144, 22);
		EpNp2.add(label);
		EpNp2.setVisible(false);

		SimpleDateFormat form = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");
		Date type = new Date();
		String time = form.format(type);
		label.setText(time);

		JLabel label_1 = new JLabel("접속시간");
		label_1.setFont(new Font("굴림", Font.PLAIN, 11));
		label_1.setBounds(8, 42, 44, 22);
		EpNp2.add(label_1);
	}

	private void EpCenterSettig() {
		String Subheader[] = { "", "재생목록", "" };
		SubTableModel = new DefaultTableModel(subContents, Subheader) {
			public boolean isCellEditable(int a, int b) {
				return false;
			}
		};
		SubTable = new JTable(SubTableModel);
		JScrollPane SubScrollPane = new JScrollPane(SubTable) {
			public void setBorder(Border border) {
				SubTable.setShowHorizontalLines(false);
				SubTable.setShowVerticalLines(false);
			}
		};
		SubTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getButton() == 3) {
					if (LoginCk && !CurrentlyList) {
					} else {
						int column = SubTable.columnAtPoint(e.getPoint());
						int row = SubTable.rowAtPoint(e.getPoint());
						popup.show(SubTable, e.getX(), e.getY());
					}
				}
			}
		});
		popupSetting();
		SubScrollPane.setBounds(0, 125, 213, 273);
		Ep.add(SubScrollPane);
	}

	private void popupSetting() {
		popup = new JPopupMenu();
		JMenuItem popupDel = new JMenuItem("리스트에서 삭제");
		popupDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!LoginCk) {// 비로그인시
					int Row = SubTable.getSelectedRow();
					if (Row != -1) {
						SubTableModel.removeRow(Row);
					} else if (Row == -1) {
						MB.SelRowMiss();
					}
				} else if (LoginCk && CurrentlyList) {// 로그인 후 최근재생
					String noWid = guest.getText();
					LogRow = SubTable.getSelectedRow();
					String title = (String) SubTable.getValueAt(LogRow, 0);
					String Singer = (String) SubTable.getValueAt(LogRow, 1);
					String genre = (String) SubTable.getValueAt(LogRow, 2);
					CT.Send("DEllSel/" + noWid + "/" + title + "/" + Singer + "/" + genre);
				}
			}
		});
		JMenuItem popupAllDell = new JMenuItem("리스트 전체 삭제");
		popupAllDell.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!LoginCk) {// 비로그인시
					int sumRow = SubTableModel.getRowCount();
					for (int i = sumRow; i > 0; i--) {
						SubTableModel.removeRow(0);
					}
				} else if (LoginCk && CurrentlyList) {// 로그인 후 최근재생리스트
					String noWid = guest.getText();
					CT.Send("DellAll/" + noWid);
				}
			}
		});
		popup.add(popupDel);
		popup.add(popupAllDell);
	}

	private void EpSouthSetting() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 334, 213, 63);
		Ep.add(panel);
		panel.setLayout(null);

		JButton playBten = new JButton("");
		playBten.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\재생버튼.png"));
		playBten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = SubTable.getSelectedRow();
				int endRow = SubTable.getRowCount() - 1;
				if (row > -1) {
					String Seltitle = (String) SubTable.getValueAt(row, 0);
					if (LoginCk) {
						if (ML == null) {
							ML = new MusicLod(Seltitle);
							ML.start();
						} else {
							ML.Close();
							ML = new MusicLod(Seltitle);
							ML.start();
						}
					} else {
						if (ML == null) {
							ML = new MusicLod(Seltitle);
							ML.start();
							ML.limited();// 시간제한 부분 1분만 미리듣기
						} else {
							ML.Close();
							ML = new MusicLod(Seltitle);
							ML.start();
							ML.limited();
						}
					}
				} else {
					MB.SelRowMiss();
				}
			}
		});
		playBten.setBounds(50, 10, 30, 35);
		panel.add(playBten);

		JButton playStopbutton = new JButton("");
		playStopbutton.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\정지.png"));
		playStopbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ML == null) {
					System.out.println(ML);
					MB.NoPlaying();
				} else {
					System.out.println(ML);
					ML.Close();
				}
			}
		});
		playStopbutton.setBounds(175, 10, 30, 35);
		panel.add(playStopbutton);

		JButton playpreviousBtn = new JButton("");
		playpreviousBtn.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\이전곡.png"));
		playpreviousBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = SubTable.getSelectedRow();
				if (LoginCk) {
					if (row == 0) {
						MB.beforeBtn();
					} else if (row > 0) {
						String BeforeSeltitle = (String) SubTable.getValueAt(row - 1, 0);
						if (ML == null) {
							ML = new MusicLod(BeforeSeltitle);
							ML.start();
							SubTable.setRowSelectionInterval(row - 1, row - 1);
						} else {
							ML.Close();
							ML = new MusicLod(BeforeSeltitle);
							SubTable.setRowSelectionInterval(row - 1, row - 1);
							ML.start();
						}
					}
				} else {
					MB.logNotNoeBtn();
				}
			}
		});
		playpreviousBtn.setBounds(10, 10, 30, 35);
		panel.add(playpreviousBtn);

		JButton playnextBtn = new JButton("");
		playnextBtn.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\다음 곡.png"));
		playnextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = SubTable.getSelectedRow();
				int limit = SubTable.getRowCount() - 1;
				if (LoginCk) {
					if (row == limit) {
						MB.NextBtn();
					} else if (row < limit) {
						String NextSeltitle = (String) SubTable.getValueAt(row + 1, 0);
						if (ML == null) {
							ML = new MusicLod(NextSeltitle);
							ML.start();
							SubTable.setRowSelectionInterval(row + 1, row + 1);
						} else {
							ML.Close();
							ML = new MusicLod(NextSeltitle);
							SubTable.setRowSelectionInterval(row + 1, row + 1);
							ML.start();
						}
					}
				} else {
					MB.logNotNoeBtn();
				}
			}
		});
		playnextBtn.setBounds(90, 10, 30, 35);
		panel.add(playnextBtn);

		JButton oneSelInf = new JButton("");
		oneSelInf.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\1곡 반복재생.png"));
		oneSelInf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = SubTable.getSelectedRow();
				if (row > -1) {
					String Seltitle = (String) SubTable.getValueAt(row, 0);
					if (LoginCk) {
						if (ML == null) {
							ML = new MusicLod(Seltitle);
							ML.OneInfinite();
						}
					} else {
						MB.logNotNoeBtn();
					}
				}
			}
		});
		oneSelInf.setBounds(135, 10, 30, 35);
		panel.add(oneSelInf);
	}

	private void WestSetting() {

		JScrollPane MainScrollPane = new JScrollPane(MainTable);
		MainScrollPane.setBounds(0, 0, 452, 399);
		contentPane.add(MainScrollPane);

		String loadList = "SongList";
		CO.ReceiveNewSongList();// 리스트를 오브젝트로 받을 준비
		CT.Send(loadList);// 리스트를 서버에게 달라고 요청
		MainTable.getTableHeader().setReorderingAllowed(false);
		MainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
