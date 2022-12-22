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

public class BoardDao {
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static Connection conn;
	private String sql;
	private PreparedStatement pstmt;
	private static ResultSet rs;
	private int bid;
	private String uid;
	private String btitle;
	private String bcontent;
	private LocalDateTime modtime;
	private int viewCnt;
	private int replyCnt;
	private int isdel;
	private String files;
	private String uname;
	private String modtimeStr;

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
	public List<Board> boardList(String field, String query, int page) {
		myGetConn();
		int offset = (page - 1) * 10;

		sql = "SELECT b.bid, b.uid, b.title," + "	b.modTIme, b.viewCnt, b.replyCnt, u.uname" + "	FROM board AS b"
				+ "	JOIN user AS u" + "	ON b.uid = u.uid" + "	WHERE b.isDel = 0 AND " + field + " LIKE ?"
				+ "	ORDER BY b.bid DESC" + "	LIMIT 10 OFFSET ?;";
		List<Board> list = new ArrayList<>();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + query + "%");
			pstmt.setInt(2, offset);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bid = rs.getInt(1);
				uid = rs.getString(2);
				btitle = rs.getString(3);
				modtimeStr = rs.getString(4);
				viewCnt = rs.getInt(5);
				replyCnt = rs.getInt(6);
				uname = rs.getString(7);
				Board b = new Board(bid, uid, btitle, modtimeStr, viewCnt, replyCnt, uname);
				list.add(b);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/** 페이지네이션 */
	public int getBoardCnt() {
		myGetConn();
		String sql = "SELECT COUNT(title) FROM board WHERE isDel = 0;";
		int count = 0;

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt(1);
			}

			stmt.close();
			conn.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("[게시물수 불러오기 오류] : " + e.getMessage());
		}
		return count;
	}

	/** 페이지네이션2 */
	public int getBoardPageCnt(String field, String query) {
		myGetConn();
		String sql = "SELECT COUNT(bid) FROM board AS b\r\n" + "JOIN USER AS u\r\n" + "ON b.uid = u.uid\r\n"
				+ "WHERE b.isDel = 0 AND " + field + " LIKE ?;";
		int count = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + query + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}

			pstmt.close();
			conn.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("[게시물수 불러오기 오류] : " + e.getMessage());
		}
		return (int) Math.ceil(count / 10.);
	}

	/** 게시글 작성 */
	public void insertBoard(Board b) {
		myGetConn();
		sql = "INSERT INTO board(uid, title, content, files)\r\n" + "VALUES (?,?,?,?);";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getUid());
			pstmt.setString(2, b.getBtitle());
			pstmt.setString(3, b.getBcontent());
			pstmt.setString(4, b.getFiles());
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("[게시물 등록 오류] : " + e.getMessage());
		}

	}

	/** 게시물 상세 보기 */
	public Board getBoardDetail(int bid) {
		myGetConn();
		sql = "SELECT b.bid, b.uid, b.title, b.content," + "	b.modTIme, b.viewCnt, b.replyCnt, b.files, u.uname"
				+ "	FROM board AS b" + "	JOIN user AS u" + "	ON b.uid = u.uid" + "	WHERE b.bid = ?;";
		Board b = new Board();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				b.setBid(rs.getInt(1));
				b.setUid(rs.getString(2));
				b.setBtitle(rs.getString(3));
				b.setBcontent(rs.getString(4));
				b.setModtime(rs.getString(5));
				b.setViewCnt(rs.getInt(6));
				b.setReplyCnt(rs.getInt(7));
				b.setFiles(rs.getString(8));
				b.setUname(rs.getString(9));
			}
			pstmt.close();
			rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return b;
	}

	public void viewCountUpdate(int bid) {
		myGetConn();
		sql = "UPDATE board SET viewCnt = viewCnt+1 WHERE bid = ?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bid);
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("[조회수 카운트 오류] : " + e.getMessage());
		}
	}

	/** 댓글 카운트 */
	public void replyCountUpdate(int bid) {
		myGetConn();
		sql = "UPDATE board SET replyCnt = replyCnt+1 WHERE bid = ?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bid);
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("[댓글 카운트 오류] : " + e.getMessage());
		}
	}

	public void delBoard(int bid) {
		myGetConn();
		sql = "UPDATE board SET isDel = 1 WHERE bid = ?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bid);
			pstmt.executeUpdate();

			pstmt.close();
			conn.close();
			System.out.println("게시글 삭제 완료");
		} catch (SQLException e) {
			System.out.println("[게시글 삭제 오류] : " + e.getMessage());
		}
	}

	/** 게시물 수정 */
	public void updateBoard(Board b) {
		myGetConn();
		sql = "UPDATE board SET title= ?, content= ?, files=?, modTime = NOW() WHERE bid = ?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getBtitle());
			pstmt.setString(2, b.getBcontent());
			pstmt.setString(3, b.getFiles());
			pstmt.setInt(4, b.getBid());
			pstmt.executeUpdate();

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("[게시글 수정 오류] : " + e.getMessage());
		}

	}

}
