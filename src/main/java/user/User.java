package user;

import java.time.LocalDate;

public class User {
	private String uid;
	private String pwd;
	private String uname;
	private String email;
	private LocalDate regDate;
	private int isDel;
	
	public User() {}
	
	public User(String uid, String pwd, String uname, String email, LocalDate regDate, int isDel) {
		super();
		this.uid = uid;
		this.pwd = pwd;
		this.uname = uname;
		this.email = email;
		this.regDate = regDate;
		this.isDel = isDel;
	}
	
	public User(String uid, String pwd, String uname, String email, String regDate, int isDel) {
		super();
		this.uid = uid;
		this.pwd = pwd;
		this.uname = uname;
		this.email = email;
		this.regDate = LocalDate.parse(regDate);
		this.isDel = isDel;
	}
	
	/** 사용자 입력 */
	public User(String uid, String pwd, String uname, String email) {
		super();
		this.uid = uid;
		this.pwd = pwd;
		this.uname = uname;
		this.email = email;
	}
	
	public User(String uid, String pwd, String uname) {
		super();
		this.uid = uid;
		this.pwd = pwd;
		this.uname = uname;
	}

	public String getUid() {
		return uid;
	}

	public String getPwd() {
		return pwd;
	}

	public String getUname() {
		return uname;
	}

	public String getEmail() {
		return email;
	}

	public LocalDate getRegDate() {
		return regDate;
	}

	public int getIsDel() {
		return isDel;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", pwd=" + pwd + ", uname=" + uname + ", email=" + email + ", regDate=" + regDate
				+ ", isDel=" + isDel + "]";
	}
	
}
