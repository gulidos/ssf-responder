package ru.in.ssf.resp.camel;

import ru.in.ssf.resp.camel.primitives.CallLeg;
import ru.in.ssf.resp.camel.primitives.CapMsg;
import ru.in.ssf.resp.camel.primitives.DetectionPoint;

public class CapErb extends CapMessage {
	private DetectionPoint event;
	private CallLeg legId;
	
	public CapErb() {
		super(CapMsg.CAP_ERB, 0, 0, "");
	}

	public CapErb(int invokeId, int dialogId, String callId, DetectionPoint event, CallLeg legId) {
		super(CapMsg.CAP_ERB, invokeId, dialogId, callId);
		this.event =event;
		this.legId = legId;
	}

	public DetectionPoint getEvent() {	return event;	}
	public void setEvent(DetectionPoint event) {this.event = event;	}

	public CallLeg getLegId() {	return legId;}
	public void setLegId(CallLeg legId) {this.legId = legId;}

	@Override
	public String toString() {
		return "[ " + super.toString() + " event=" + event + " legId=" + legId + "]";
	}

	
}
