package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ReplyDao {
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static Connection conn;
	private String sql;
	private PreparedStatement pstmt;
	private static ResultSet rs;
	private int bid;
	private String uid, btitle, bcontent;
	private LocalDateTime modtime;
	private int viewCnt;
	private int replyCnt;
	private int isdel;
	private String files;
	private String uname;
	private String modtimeStr;
	private int rid;
	private String rcontent;
	private LocalDateTime regtime;
	private int ismine;

	public static void myGetConn() {
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/project");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 리스트 출력 */
	public List<Reply> replyList(int bid) {
		myGetConn();
		sql = "SELECT r.rid, r.content, r.regDate,"
				+ "r.isMine, r.uid, r.bid, u.uname"
				+ " FROM reply AS r"
				+ " JOIN USER AS u"
				+ " ON r.uid = u.uid"
				+ " WHERE bid = ?;";
		List<Reply> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Reply r = new Reply();
				r.setRid(rs.getInt(1));
				r.setRcontent(rs.getString(2));
				r.setRegtime(rs.getString(3));
				r.setIsmine(rs.getInt(4));
				r.setUid(rs.getString(5));
				r.setBid(rs.getInt(6));
				r.setUname(rs.getString(7));
				list.add(r);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/** 게시글 작성 */
	public void insert(Reply r) {
		myGetConn();
		sql = "INSERT INTO reply (content, isMine, uid, bid) VALUES (?, ?, ?, ?);";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, r.getRcontent());
			pstmt.setInt(2, r.getIsmine());
			pstmt.setString(3, r.getUid());
			pstmt.setInt(4, r.getBid());
			
			pstmt.executeUpdate();
			conn.close(); pstmt.close();
		} catch (SQLException e) {
			System.out.println("[댓글 등록 오류] : " + e.getMessage());
		}
	}
	

	public void viewCountUpdate(int bid) {
		myGetConn();
		sql = "UPDATE board SET viewCnt = viewCnt+1 WHERE bid = ?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bid);
			pstmt.executeUpdate();
			conn.close(); pstmt.close();
		} catch (SQLException e) {
			System.out.println("[조회수 카운트 오류] : " + e.getMessage());
		}
	}

}
