package entity;

public class NoticeList {

	private String noticeCode;
	private String comChName;
	private String comEnName;
	private String cusComName;
	private String userName;
	private String account;
	private String bankName;
	private String salesman;
	private String produceTime;
	
	public NoticeList() {
		
	}

	public NoticeList(String noticeCode, String comChName, String comEnName,
			String cusComName, String userName, String account,
			String bankName, String salesman, String produceTime) {
		super();
		this.noticeCode = noticeCode;
		this.comChName = comChName;
		this.comEnName = comEnName;
		this.cusComName = cusComName;
		this.userName = userName;
		this.account = account;
		this.bankName = bankName;
		this.salesman = salesman;
		this.produceTime = produceTime;
	}

	public String getNoticeCode() {
		return noticeCode;
	}

	public void setNoticeCode(String noticeCode) {
		this.noticeCode = noticeCode;
	}

	public String getComChName() {
		return comChName;
	}

	public void setComChName(String comChName) {
		this.comChName = comChName;
	}

	public String getComEnName() {
		return comEnName;
	}

	public void setComEnName(String comEnName) {
		this.comEnName = comEnName;
	}

	public String getCusComName() {
		return cusComName;
	}

	public void setCusComName(String cusComName) {
		this.cusComName = cusComName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getProduceTime() {
		return produceTime;
	}

	public void setProduceTime(String produceTime) {
		this.produceTime = produceTime;
	}
	
	
	
	
}
