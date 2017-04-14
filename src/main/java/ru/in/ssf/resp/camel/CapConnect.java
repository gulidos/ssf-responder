package ru.in.ssf.resp.camel;


import ru.in.ssf.resp.camel.primitives.CapMsg;

public class CapConnect extends CapMessage {
    private String dest;
    private String gn_acgpn;

	public CapConnect(int invokeId, int dialogId, String callId, String dest, String  gn_acgpn) {
		super(CapMsg.CAP_CONNECT, invokeId, dialogId, callId);
		this.dest = dest;
		this.gn_acgpn = gn_acgpn;
	}

	public CapConnect() {
		super(CapMsg.CAP_CONNECT, 0, 0, "");
	}
	
	public CapConnect(Builder builder) {
		super(builder);
		this.dest = builder.dest;
		this.gn_acgpn = builder.gn_acgpn;
	}
	
	@Override
	public void onReceive(ICall call, ICallState callstate) {
		callstate.onCapConnect(call, this); // works out in states: analysedInformation, 
	}
	
	public String getDest() { return dest;}
	public String getGn_acgpn() {return gn_acgpn;}
	
	public void setDest(String dest) {this.dest = dest;}
	public void setGn_acgpn(String gn_acgpn) {this.gn_acgpn = gn_acgpn;}

	@Override
	public String toString() {
		return "[" + super.toString() + " dest=" + dest + ", gn_acgpn=" + gn_acgpn + "]";
	}
	
	
	// Class Builder
	public static class Builder extends CapMessage.InnerBuilder<Builder> {
		private String dest;
		private String gn_acgpn;

		public Builder() {}
		
		public Builder setDest(String dest) {this.dest = dest; return this;}
		public Builder setGn_acgpn(String gn_acgpn) {this.gn_acgpn = gn_acgpn; return this;}
		
		@Override
		public CapConnect build() {
			setMsgType(CapMsg.CAP_CONNECT);
			return new CapConnect(this);
		}
	}
	
}
