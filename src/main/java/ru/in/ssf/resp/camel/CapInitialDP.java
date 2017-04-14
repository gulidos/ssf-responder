package ru.in.ssf.resp.camel;


import ru.in.ssf.resp.camel.primitives.CapMsg;

public class CapInitialDP extends CapMessage {
    private final int serviceKey;
    private final String calledNumber;
    private final String callingNumber;
    private final String calledPartyBCDNumber;
    private final String iMSI;
	private final int eventType;
    private final String mscAddress;
	
	public CapInitialDP(Builder b) {
		super(b);
		this.serviceKey = b.serviceKey;
		this.calledNumber = b.calledNumber;
		this.callingNumber = b.callingNumber;
		this.calledPartyBCDNumber = b.cPBCDNumber;
		this.eventType = b.eventType;
		this.mscAddress = b.mscAddress;
		this.iMSI = b.iMSI;
	}


	public int getServiceKey() { return serviceKey;	}
	public String getCalledNumber() { return calledNumber; }
	public String getCallingNumber() { return callingNumber; }
	public String getCalledPartyBCDNumber() { return calledPartyBCDNumber; }
	public int getEventType() { return eventType;	}
	public String getMscAddress() { return mscAddress; }
	public String getiMSI() {	return iMSI;}

	
	// Class Builder
	public static class Builder extends CapMessage.InnerBuilder<Builder> {
		private int serviceKey;
		private String calledNumber;
		private String callingNumber;
		private String cPBCDNumber;
		private String iMSI;
		private int eventType;
		private String mscAddress;

		public Builder() {}
		public Builder setServiceKey(int serviceKey) {this.serviceKey = serviceKey; return this;}
		public Builder setCalledNumber(String calledNumber) {this.calledNumber = calledNumber; return this;	}
		public Builder setCallingNumber(String callingNumber) {this.callingNumber = callingNumber; return this;	}
		public Builder setCPBCDNumber(String cPBCDNumber) {	this.cPBCDNumber = cPBCDNumber;	return this; }
		public Builder setEventType(int eventType) {this.eventType = eventType;	return this; }
		public Builder setMscAddress(String mscAddress) {	this.mscAddress = mscAddress; return this;}
		public Builder setiMSI(String iMSI) {this.iMSI = iMSI; return this;	}

		@Override
		public CapInitialDP build() {
			setMsgType(CapMsg.CAP_INITIAL_DP);
			return new CapInitialDP(this);
		}
	}
}

