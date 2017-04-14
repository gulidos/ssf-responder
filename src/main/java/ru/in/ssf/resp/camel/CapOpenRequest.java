package ru.in.ssf.resp.camel;


import ru.in.ssf.resp.camel.primitives.CapMsg;

public class CapOpenRequest extends CapMessage {
	private final int destSSN;
	private final String destGTAddressInfo;
    private final int version;
   

    public CapOpenRequest(Builder b) {
    	super(b);
    	this.destSSN = b.destSSN;
		this.destGTAddressInfo = b.destGTAddressInfo;
		this.version = b.version;
    }

	public int getDestSSN() {	return destSSN;	}
	public String getDestGTAddressInfo() {return destGTAddressInfo;	}
	public int getVersion() {return version;}
	
	
	
	@Override
	public String toString() {
		return "CapOpenRequest [ " + super.toString() + " destSSN=" + destSSN + ", destGTAddressInfo=" + destGTAddressInfo + ", version="
				+ version + ", toString()=" + "]";
	}



	// Class Builder
	public static class Builder extends CapMessage.InnerBuilder<Builder> {
		private int destSSN;
		private String destGTAddressInfo;
	    private int version;
	    
		public Builder() {	}
		
		public Builder setDestSSN(int destSSN) {this.destSSN = destSSN; return this;}
		
		public Builder setDestGTAddressInfo(String destGTAddressInfo) {
			this.destGTAddressInfo = destGTAddressInfo; 
			return this;	
		}
		
		public Builder setVersion(int version) {this.version = version; return this;}

		@Override
		public CapOpenRequest build() {
			setMsgType(CapMsg.CAP_OPEN_REQ);
			return new CapOpenRequest(this);
		}
	}

}
