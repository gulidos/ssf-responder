package ru.in.ssf.resp.json.primitives;

public class Phone {
	private Integer SI;
	private String phone;
	
	public Phone () {};
	
	public Phone (String phone) {
		 this.phone = phone;
	}

	public Phone (int si, String phone) {
		this.SI = si;
		this.phone = phone;
	}

	public String getPhone() {return phone;	}
	public Integer getSI() {return SI;	}

	public void setSI(Integer sI) {	SI = sI;}
	public void setPhone(String phone) {this.phone = phone;	}

	@Override
	public String toString() {
		return "[SI=" + SI + ", phone=" + phone + "]";
	}
	
}