package entity;

public class ServiceMaintain {

	private String id;
	private String content;
	private int govCharge;
	private int serCharge;
	private int speCharge;
	
	public ServiceMaintain() {
		
	}

	public ServiceMaintain(String id, String content, int govCharge,
			int serCharge, int speCharge) {
		super();
		this.id = id;
		this.content = content;
		this.govCharge = govCharge;
		this.serCharge = serCharge;
		this.speCharge = speCharge;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	
	
}
