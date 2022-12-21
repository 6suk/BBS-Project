package board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Board {
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

	public Board() {
	}

	/** 게시글 작성 시 */
	public Board(String uid, String btitle, String bcontent, String files) {
		super();
		this.uid = uid;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.files = files;
	}
	/** 게시글 수정 시 */
	public Board(int bid, String btitle, String bcontent, String files) {
		super();
		this.bid = bid;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.files = files;
	}

	public Board(int bid, String uid, String btitle, String bcontent, LocalDateTime modtime, int viewCnt, int replyCnt,
			int isdel, String files, String uname) {
		super();
		this.bid = bid;
		this.uid = uid;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.modtime = modtime;
		this.viewCnt = viewCnt;
		this.replyCnt = replyCnt;
		this.isdel = isdel;
		this.files = files;
		this.uname = uname;
	}

	/** list 출력 */
	public Board(int bid, String uid, String btitle, String modtime, int viewCnt, int replyCnt, String uname) {
		super();
		this.bid = bid;
		this.uid = uid;
		this.btitle = btitle;
		this.modtime = LocalDateTime.parse(modtime, dtf);
		this.viewCnt = viewCnt;
		this.replyCnt = replyCnt;
		this.uname = uname;
	}

	public Board(int bid, String uid, String btitle, String bcontent, String modtime, int viewCnt, int replyCnt,
			int isdel, String files, String uname) {
		super();
		this.bid = bid;
		this.uid = uid;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.modtime = LocalDateTime.parse(modtime, dtf);
		this.viewCnt = viewCnt;
		this.replyCnt = replyCnt;
		this.isdel = isdel;
		this.files = files;
		this.uname = uname;
	}

	public int getBid() {
		return bid;
	}

	public String getUid() {
		return uid;
	}

	public String getBtitle() {
		return btitle;
	}

	public String getBcontent() {
		return bcontent;
	}

	public LocalDateTime getModtime() {
		return modtime;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public int getReplyCnt() {
		return replyCnt;
	}

	public int getIsdel() {
		return isdel;
	}

	public String getFiles() {
		return files;
	}

	public String getUname() {
		return uname;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}

	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}

	public void setModtime(LocalDateTime modtime) {
		this.modtime = modtime;
	}
	public void setModtime(String modtime) {
		this.modtime = LocalDateTime.parse(modtime, dtf);
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}
	

}
