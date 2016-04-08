package entity;

public class Company {

	private int id;
	private String companyId;
	private String companyName;
	
	
	public Company() {
		
	}
	
	public Company(int id, String companyId, String companyName) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.companyName = companyName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
