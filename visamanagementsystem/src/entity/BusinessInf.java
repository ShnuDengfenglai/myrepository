package entity;

public class BusinessInf {
	
	private String company_id ;
	private String business_code;
	private String customer_name;
	private String date;
	private String salesman;
	private String remaks;
	private String invoice_code;
	private String charge_finish;
	private String invoice_title;
	private int invoice_money;
	private String dateFinish;
	private String fileName;
	
	public BusinessInf() {
		
	}

	public BusinessInf(String company_id, String business_code,
			String customer_name, String date, String salesman, String remaks,
			String invoice_code, String charge_finish, String invoice_title,
			int invoice_money, String dateFinish, String fileName) {
		super();
		this.company_id = company_id;
		this.business_code = business_code;
		this.customer_name = customer_name;
		this.date = date;
		this.salesman = salesman;
		this.remaks = remaks;
		this.invoice_code = invoice_code;
		this.charge_finish = charge_finish;
		this.invoice_title = invoice_title;
		this.invoice_money = invoice_money;
		this.dateFinish = dateFinish;
		this.fileName = fileName;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getBusiness_code() {
		return business_code;
	}

	public void setBusiness_code(String business_code) {
		this.business_code = business_code;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getRemaks() {
		return remaks;
	}

	public void setRemaks(String remaks) {
		this.remaks = remaks;
	}

	public String getInvoice_code() {
		return invoice_code;
	}

	public void setInvoice_code(String invoice_code) {
		this.invoice_code = invoice_code;
	}

	public String getCharge_finish() {
		return charge_finish;
	}

	public void setCharge_finish(String charge_finish) {
		this.charge_finish = charge_finish;
	}

	public String getInvoice_title() {
		return invoice_title;
	}

	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}

	public int getInvoice_money() {
		return invoice_money;
	}

	public void setInvoice_money(int invoice_money) {
		this.invoice_money = invoice_money;
	}

	public String getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(String dateFinish) {
		this.dateFinish = dateFinish;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	
}
