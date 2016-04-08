package entity;

public class User {

	private int id;
	private String userName;
	private String password;
	private int type;
	private String realName;
	private String stopflag;
	
	public User() {
		
	}
	
	public User(int id, String userName, String password, int type, String realname,String stopflag) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.type = type;
		this.realName=realname;
		this.stopflag=stopflag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getStopflag() {
		return stopflag;
	}

	public void setStopflag(String stopflag) {
		this.stopflag = stopflag;
	}
	
}
