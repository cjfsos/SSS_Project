package JTable;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import DataBase.DAO;

public class Sign_Up extends JFrame {

	JPanel contentPane;
	JTextField idtextField;
	JTextField PWtextField_1;
	JTextField PWtextField_2;
	public JLabel idcheck = new JLabel("");
	public JButton btnId = null;
	JLabel pwcheck = new JLabel("");
	public boolean idckeked = false;
	boolean pwckeked = false;
	String id;
	String pw;
	DAO daoIns = DAO.getInstance();
	JLabel pwicon1 = new JLabel("");
	JLabel pwicon2 = new JLabel("");
	MainFrames MF = null;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SignUp frame = new SignUp();
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
	public Sign_Up(MainFrames MF) {
		super("회원가입");
		this.MF = MF;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 326, 192);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 10, 57, 21);
		contentPane.add(lblNewLabel);

		JLabel lblPw = new JLabel("PW");
		lblPw.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPw.setBounds(10, 50, 57, 30);
		contentPane.add(lblPw);

		JLabel lblPw_1 = new JLabel("PW 확인");
		lblPw_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPw_1.setBounds(0, 81, 67, 30);
		contentPane.add(lblPw_1);

		idtextField = new JTextField();
		idtextField.setBounds(79, 10, 116, 21);
		contentPane.add(idtextField);
		idtextField.setColumns(10);

		pwicon1.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\pw체크전.png"));
		pwicon1.setBounds(207, 54, 25, 25);
		contentPane.add(pwicon1);

		pwicon2.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\pw체크전.png"));
		pwicon2.setBounds(207, 85, 25, 25);
		contentPane.add(pwicon2);
		idcheck.setVisible(true);

		PWtextField_1 = new JTextField();
		PWtextField_1.setColumns(10);
		PWtextField_1.setBounds(79, 55, 116, 21);
		contentPane.add(PWtextField_1);
		PWtextField_1.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (PWtextField_1.getText().length() < 3) {
					pwcheck.setText("PW는 4글자 이상이어야합니다.");
					pwicon1.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\pw체크전.png"));
				} else {
					pwicon1.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\pw체크후.png"));
					pwcheck.setText("");

				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		PWtextField_2 = new JTextField();
		PWtextField_2.setColumns(10);
		PWtextField_2.setBounds(79, 86, 116, 21);
		contentPane.add(PWtextField_2);// DB에 접근하지 않으므로 키리스너로 바로바로 확인!
		PWtextField_2.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {// 키를 눌렀다가 떼었을때
			}

			@Override
			public void keyReleased(KeyEvent e) {// 키를 떼었을때
				if (PWtextField_2.getText().equals(PWtextField_1.getText())) {
					pwcheck.setForeground(Color.BLUE);
					pwcheck.setText("사용 가능한 PW입니다.");
					pwckeked = true;
					pw = PWtextField_2.getText();
					pwicon2.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\pw체크후.png"));
				} else if (!PWtextField_2.getText().equals(PWtextField_1.getText())) {
					pwcheck.setText("PW와 PW확인이 일치해야 합니다.");
					pwicon2.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\pw체크전.png"));
				}
				if (PWtextField_1.getText().equals("") && PWtextField_2.getText().equals("")) {
					pwicon2.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\pw체크전.png"));
					pwcheck.setText("");
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {// 키를 눌렀을때
			}
		});

		JButton btnNewButton = new JButton("회원 가입");
		btnNewButton.setBounds(79, 129, 116, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (idckeked && pwckeked) {
					String msg = "SignUpDB/" + id + "/" + pw;
					MF.CT.Send(msg);
				} else {
					MF.CT.MB.signupFalseMSG();
				}
			}
		});

		btnId = new JButton("ID중복확인");
		btnId.setBounds(207, 9, 100, 23);
		contentPane.add(btnId);
		btnId.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {// DB에 너무 자주 왔다 갔다 하지 않게 하기위해 버튼을 클릭시 DB에서 확인하도록
				if (idtextField.getText().equals("")) {
					idcheck.setText("ID는 필수 입력사항입니다.");
					btnId.setText("ID중복확인");
				} else if (!idtextField.getText().equals("")) {
					id = idtextField.getText();
					String msg = "IDOverCheck" + "/" + id;
					MF.CT.Send(msg);
				}
			}
		});

		pwcheck.setFont(new Font("바탕체", Font.BOLD, 12));
		pwcheck.setBounds(79, 110, 214, 15);
		pwcheck.setOpaque(true);
		pwcheck.setForeground(Color.RED);
		contentPane.add(pwcheck);
		pwcheck.setVisible(true);

		idcheck.setFont(new Font("바탕", Font.BOLD, 12));
		idcheck.setForeground(Color.RED);
		idcheck.setHorizontalAlignment(SwingConstants.LEFT);
		idcheck.setBounds(79, 35, 183, 15);
		contentPane.add(idcheck);

		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
	}
}
