package entity;

public class ServiceContent {

	private String business_code;
	private String content;
	private int gov_charge;
	private int ser_charge;
	private int spe_charge;
	private int finish;
	
	public ServiceContent() {
		
	}

	public ServiceContent(String business_code, String content, int gov_charge,
			int ser_charge, int spe_charge, int finish) {
		super();
		this.business_code = business_code;
		this.content = content;
		this.gov_charge = gov_charge;
		this.ser_charge = ser_charge;
		this.spe_charge = spe_charge;
		this.finish = finish;
	}

	public String getBusiness_code() {
		return business_code;
	}

	public void setBusiness_code(String business_code) {
		this.business_code = business_code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getGov_charge() {
		return gov_charge;
	}

	public void setGov_charge(int gov_charge) {
		this.gov_charge = gov_charge;
	}

	public int getSer_charge() {
		return ser_charge;
	}

	public void setSer_charge(int ser_charge) {
		this.ser_charge = ser_charge;
	}

	public int getSpe_charge() {
		return spe_charge;
	}

	public void setSpe_charge(int spe_charge) {
		this.spe_charge = spe_charge;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}
	
	
	
}
