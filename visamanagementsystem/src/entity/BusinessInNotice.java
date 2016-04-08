package entity;

public class BusinessInNotice {

	private int id;
	private String noticeCode;
	private String businessCode;
	
	public BusinessInNotice() {

	}
	
	public BusinessInNotice(int id, String noticeCode, String businessCode) {
		super();
		this.id = id;
		this.noticeCode = noticeCode;
		this.businessCode = businessCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNoticeCode() {
		return noticeCode;
	}

	public void setNoticeCode(String noticeCode) {
		this.noticeCode = noticeCode;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	
	
	
}
