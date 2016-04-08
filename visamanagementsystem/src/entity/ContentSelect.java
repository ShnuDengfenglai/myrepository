package entity;

public class ContentSelect {

	private String abbr;
	private String content;
	private int govCharge;
	private int serCharge;
	private int speCharge;
	private String companyId;
	
	public ContentSelect() {
		
	}

	public ContentSelect(String abbr, String content, int govCharge,
			int serCharge, int speCharge, String companyId) {
		super();
		this.abbr = abbr;
		this.content = content;
		this.govCharge = govCharge;
		this.serCharge = serCharge;
		this.speCharge = speCharge;
		this.companyId = companyId;
	}
	
	public ContentSelect(String abbr, String content, int govCharge,
			int serCharge, int speCharge) {
		super();
		this.abbr = abbr;
		this.content = content;
		this.govCharge = govCharge;
		this.serCharge = serCharge;
		this.speCharge = speCharge;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getGovCharge() {
		return govCharge;
	}

	public void setGovCharge(int govCharge) {
		this.govCharge = govCharge;
	}

	public int getSerCharge() {
		return serCharge;
	}

	public void setSerCharge(int serCharge) {
		this.serCharge = serCharge;
	}

	public int getSpeCharge() {
		return speCharge;
	}

	public void setSpeCharge(int speCharge) {
		this.speCharge = speCharge;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
	
}
