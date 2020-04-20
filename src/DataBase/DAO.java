package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Server.SCenter;

public class DAO {
	public static DAO ins;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private SCenter Sct;

	private DAO() {
	}

	public static DAO getInstance() {
		if (ins == null) {
			ins = new DAO();
		}
		return ins;
	}

	private boolean link() {
		boolean result = false;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<String[]> getContents() {
		ArrayList<String[]> Data = new ArrayList();
		if (link()) {// if에서 link()를 한번 실행해줌
			try {
				st = con.createStatement();
				String sql = "select * from song";
				rs = st.executeQuery(sql);
				Server_DTO Sdb = new Server_DTO();
				while (rs.next()) {
					Sdb.setNo(rs.getString("no"));
					Sdb.setStitle(rs.getString("곡명"));
					Sdb.setSinger(rs.getString("가수명"));
					Sdb.setAlbum(rs.getString("앨범"));
					Sdb.setGenre(rs.getString("장르"));
					Data.add(Sdb.getArray());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("DB연결실패");
			System.exit(0);
		}
		return Data;
	}

	public boolean DBIdcheck(String id) {
		if (link()) {
			try {
				String sql = "select count(*) cnt from loogin where id= ?";
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, id);
				rs = pst.executeQuery();

				if (rs.next()) {
					int cnt = rs.getInt("cnt");
					if (cnt > 0) {
						return true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public int signup(String id, String pw) {
		int k = 0;
		if (link()) {
			String sql = "insert into loogin values(?,?)";
			try {
				PreparedStatement pps = con.prepareStatement(sql);
				pps.setString(1, id);
				pps.setString(2, pw);
				k = pps.executeUpdate();
				System.out.println(k);
				return k;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return k;
	}

	public boolean login(String id, String pw) {
		if (link()) {
			try {
				String sql = "select count(*) cnt from loogin where id= ? and pw= ?";
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, id);
				pst.setString(2, pw);
				rs = pst.executeQuery();

				if (rs.next()) {
					int cnt = rs.getInt("cnt");
					if (cnt > 0) {
						return true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public int popular(Client_DTO uSData) {
		int k = 0;
		if (link()) {
			String sql = "insert into popular values(?,?,?,?)";
			try {
				PreparedStatement pps = con.prepareStatement(sql);
				pps.setString(1, uSData.getId());
				pps.setString(2, uSData.getSong());
				pps.setString(3, uSData.getSinger());
				pps.setString(4, uSData.getGenre());
				k = pps.executeUpdate();
				return k;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return k;
	}

	public ArrayList<String[]> currently(String id) {
		ArrayList<String[]> currentlyData = new ArrayList();
		if (link()) {
			String sql = "select * from popular where id = '" + id + "'";
			try {
				Server_DTO SD = new Server_DTO();
				st = con.createStatement();
				rs = st.executeQuery(sql);
				while (rs.next()) {//DB에 있는 값을 set한다음 currentlyData리스트에 넣음
					//그다음 다시 DB에 있는 다음 값을 set 덮어쓰기하여, DB에 있는 값만큼 반복
					SD.setStitle(rs.getString("곡명"));
					SD.setSinger(rs.getString("가수명"));
					SD.setGenre(rs.getString("장르"));
					currentlyData.add(SD.currently());
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("오라클 에러/ 기존에 선택한 song가 없음");
			}

		}
		return currentlyData;
	}
}