package ru.in.ssf.resp.camel;


import ru.in.ssf.resp.camel.primitives.CapMsg;

public class CapContinue extends CapMessage {

	public CapContinue(int invokeId, int dialogId, String callId) {
		super(CapMsg.CAP_CONTINUE, invokeId, dialogId, callId);
	}

	public CapContinue() {
		super(CapMsg.CAP_CONTINUE, 0, 0, "");
	}

	public CapContinue(Builder builder) {
		super(builder);
	}
	
	@Override
	public void onReceive(ICall call, ICallState callstate) {
		callstate.onCapContinue(call); // works out in states: analysedInformation,  oActive
	}
//	
//	@Override
//	public String toString() {
//		return "[" + super.toString() + "]";
//	}

	
	// Class Builder
	public static class Builder extends CapMessage.InnerBuilder<Builder> {
		public Builder() {	}
		
		@Override
		public CapContinue build() {
			setMsgType(CapMsg.CAP_CONTINUE);
			return new CapContinue(this);
		}
	}
}
