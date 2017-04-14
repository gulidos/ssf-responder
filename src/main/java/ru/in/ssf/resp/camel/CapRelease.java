package ru.in.ssf.resp.camel;


import ru.in.ssf.resp.camel.primitives.CapMsg;


public class CapRelease extends CapMessage {
	private int cause;
	
	public CapRelease(int invokeId, int dialogId, String callId, int cause) {
		super(CapMsg.CAP_RELEASE, invokeId, dialogId, callId);
		this.cause = cause;
	}

	public CapRelease() {
		super(CapMsg.CAP_RELEASE, 0, 0, "");
	}

	@Override
	public void onReceive(ICall call, ICallState callstate) {
		callstate.onCapRelease(call, cause); 
	}
	
	public int getCause() {	return cause;	}
	public void setCause(int cause) {	this.cause = cause;	}

	@Override
	public String toString() {
		return "[" + super.toString() + " cause: " + cause + "]";
	}

}
