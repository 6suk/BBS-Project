package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.mindrot.jbcrypt.BCrypt;


public class UserDAO {
	private static Connection conn;

	public static void myGetConn() {
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/project");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 1. 회원 가입 */
	public void regUser(User u) {
		myGetConn();
		String sql = "INSERT INTO user VALUES(?,?,?,?,DEFAULT,DEFAULT);";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getUid());
			String Bpwd = BCrypt.hashpw(u.getPwd(), BCrypt.gensalt());
			pstmt.setString(2, Bpwd); // 암호화된 패스워드로 DB에 등록
			pstmt.setString(3, u.getUname());
			pstmt.setString(4, u.getEmail());
			pstmt.executeUpdate();

			conn.close();
			pstmt.close();
			System.out.println("[회원가입 완료] id : " + u.getUid() + " | 이름 : " + u.getUname());
		} catch (SQLException e) {
			System.out.println("[회원가입 오류] : " + e.getMessage());
		}

	}

	/** 2. 회원 목록 */
	public List<User> userList() {
		myGetConn();
		String sql = "SELECT * FROM user WHERE isdel = '0' ORDER BY regDate DESC, uid LIMIT 10;";
		List<User> list = new ArrayList<>();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String uid = rs.getString(1);
				String pwd = rs.getString(2);
				String uname = rs.getString(3);
				String email = rs.getString(4) == null ? "미등록" : rs.getString(4);
				String regDate = rs.getString(5);
				int isDel = rs.getInt(6);
				User user = new User(uid, pwd, uname, email, regDate, isDel);
				list.add(user);
			}
			
			stmt.close();
			conn.close();
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("[목록 불러오기 오류] : " + e.getMessage());
		}
		
		
		return list;
	}
	
	/** 3. 특정 회원 목록 */
	public User getUserInfo(String uid) {
		myGetConn();
		String sql = "SELECT * FROM user WHERE uid = ?;";
		User u = new User();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String uid2 = rs.getString(1);
				String pwd = rs.getString(2);
				String uname = rs.getString(3);
				String email = rs.getString(4);
				String regDate = rs.getString(5);
				int isDel = rs.getInt(6);
				u = new User(uid2, pwd, uname, email, regDate, isDel);
			}
			
			pstmt.close();
			conn.close();
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("[회원 불러오기 오류] : " + e.getMessage());
		}
		return u;
	}
	
	/** 4. 회원 수정 */
	public void updateUser(User u) {
		myGetConn();
		String sql = "UPDATE user SET pwd = ? , uname = ?, email = ? WHERE uid = ?;";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			String Bpwd = BCrypt.hashpw(u.getPwd(), BCrypt.gensalt());
			pstmt.setString(1, Bpwd); // 암호화된 패스워드로 DB에 등록
			pstmt.setString(2, u.getUname());
			pstmt.setString(3, u.getEmail());
			pstmt.setString(4, u.getUid());
			pstmt.executeUpdate();
			
			pstmt.close(); conn.close();
			System.out.println("회원 수정 완료");
		} catch (SQLException e) {
			System.out.println("[회원 수정 오류] : " + e.getMessage());
		}
		
		
	}
	
	/** 5. 회원 탈퇴 */
	public void delUser(String uid) {
		myGetConn();
		String sql = "UPDATE user SET isDel = 1 WHERE uid = ?;";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			pstmt.executeUpdate();
			
			pstmt.close(); conn.close();
			System.out.println("회원 탈퇴 완료");
		} catch (SQLException e) {
			System.out.println("[회원 탈퇴 오류] : " + e.getMessage());
		}
		
	}
}
