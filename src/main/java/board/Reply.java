package board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reply {
	
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private int rid;
	private String rcontent;
	private LocalDateTime regtime;
	private int ismine;
	private String uid;
	private int bid;
	private String uname;
	
	public Reply() {}
	
	public Reply(String rcontent, int ismine, String uid, int bid) {
		super();
		this.rcontent = rcontent;
		this.ismine = ismine;
		this.uid = uid;
		this.bid = bid;
	}

	public Reply(int rid, String rcontent, LocalDateTime regtime, int ismine, String uid, int bid, String uname) {
		super();
		this.rid = rid;
		this.rcontent = rcontent;
		this.regtime = regtime;
		this.ismine = ismine;
		this.uid = uid;
		this.bid = bid;
		this.uname = uname;
	}
	
	public Reply(int rid, String rcontent, String regtime, int ismine, String uid, int bid, String uname) {
		super();
		this.rid = rid;
		this.rcontent = rcontent;
		this.regtime = LocalDateTime.parse(regtime, dtf);
		this.ismine = ismine;
		this.uid = uid;
		this.bid = bid;
		this.uname = uname;
	}

	public int getRid() {
		return rid;
	}

	public String getRcontent() {
		return rcontent;
	}

	public LocalDateTime getRegtime() {
		return regtime;
	}

	public int getIsmine() {
		return ismine;
	}

	public String getUid() {
		return uid;
	}

	public int getBid() {
		return bid;
	}

	public String getUname() {
		return uname;
	}
	
	

}
