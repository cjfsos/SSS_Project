package JTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class C_MsgBox extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MsgBox frame = new MsgBox();
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
	public C_MsgBox() {
//		signupMSG();
//		signupFalseMSG();
//		logNotSel();
//		SelRowMiss();
//		addOver();
//		recommendNull();
	}

	public void recommendNull() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 258, 149);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("현재까지 들은 곡이 없어서");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 13));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(12, 10, 218, 22);
		contentPane.add(lblNewLabel_2);

		JButton btnNewButton_1 = new JButton("확인");
		btnNewButton_1.setBounds(60, 67, 105, 30);
		contentPane.add(btnNewButton_1);
		
		JLabel label = new JLabel("추천을 할 수가 없습니다.");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.PLAIN, 13));
		label.setBounds(12, 35, 218, 22);
		contentPane.add(label);

		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public void addOver() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 258, 149);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("같은 곡을 추가 할 수 없습니다.");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 13));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(12, 28, 218, 22);
		contentPane.add(lblNewLabel_2);

		JButton btnNewButton_1 = new JButton("확인");
		btnNewButton_1.setBounds(60, 67, 105, 30);
		contentPane.add(btnNewButton_1);

		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public void SelRowMiss() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 258, 149);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("원하는 리스트를 왼쪽클릭으로 선택");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 13));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(12, 10, 218, 22);
		contentPane.add(lblNewLabel_2);

		JButton btnNewButton_1 = new JButton("확인");
		btnNewButton_1.setBounds(60, 67, 105, 30);
		contentPane.add(btnNewButton_1);

		JLabel label = new JLabel("색이 파랗게 변한 후에 실행하세요");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.PLAIN, 13));
		label.setBounds(12, 35, 208, 22);
		contentPane.add(label);

		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public void logNotSel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 258, 149);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("목록에서 듣고 싶은 노래를 선택 후");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 13));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(12, 10, 218, 22);
		contentPane.add(lblNewLabel_2);

		JButton btnNewButton_1 = new JButton("확인");
		btnNewButton_1.setBounds(60, 67, 105, 30);
		contentPane.add(btnNewButton_1);

		JLabel label = new JLabel("음악 듣기를 눌러주세요.");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.PLAIN, 13));
		label.setBounds(12, 35, 208, 22);
		contentPane.add(label);

		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public void signupMSG() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 248, 177);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("<html><body>sing-sang-sung에<br>가입 하신걸 환영합니다.</body></html>");
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 14));
		lblNewLabel.setBounds(12, 10, 217, 37);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("확인!");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\회원가입 완료 버튼.jpg"));
		btnNewButton.setBounds(78, 73, 78, 37);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\회원가입 배경.png"));
		lblNewLabel_1.setBounds(0, 0, 230, 140);
		contentPane.add(lblNewLabel_1);
		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public void signupFalseMSG() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 248, 177);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("ID중복확인과 PW 사용가능 여부가");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 13));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(12, 10, 208, 30);
		contentPane.add(lblNewLabel_2);

		JLabel label = new JLabel("전부 확인 된 후 회원가입이");
		label.setFont(new Font("굴림", Font.PLAIN, 13));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 31, 208, 24);
		contentPane.add(label);

		JButton btnNewButton_1 = new JButton("확인");
		btnNewButton_1.setBounds(60, 90, 105, 30);
		contentPane.add(btnNewButton_1);

		JLabel lblNewLabel_3 = new JLabel(" 가능합니다.");
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 13));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(12, 52, 208, 15);
		contentPane.add(lblNewLabel_3);

		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
