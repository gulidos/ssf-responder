package ru.in.ssf.resp.camel;

import ru.in.ssf.resp.camel.primitives.CallLeg;
import ru.in.ssf.resp.camel.primitives.DetectionPoint;
import ru.in.ssf.resp.camel.primitives.MonMode;

public class EventBCSM {
	private DetectionPoint event_id;
	private MonMode mon_mode;
	private CallLeg legID;
	private int appTimer;
	
	public EventBCSM(MonMode monMode, CallLeg callLeg, DetectionPoint dp, int appTimer) {
		super();
		this.mon_mode = monMode;
		this.legID = callLeg;
		this.event_id = dp;
		this.appTimer = appTimer;
	}
		
	public EventBCSM(MonMode monMode) {
		this(monMode, null, null, 0);
	}
	
	public EventBCSM() {}
	
	


	@Override
	public String toString() {
		return "EventBCSM [event_id=" + event_id + ", mon_mode=" + mon_mode + ", legID=" + legID + ", appTimer="
				+ appTimer + "]";
	}

	public MonMode getMonModel() {	return mon_mode;	}
	public void setMonModel(MonMode monModel) {	this.mon_mode = monModel;	}

	public CallLeg getCallLeg() {	return legID;	}
	public void setCallLeg(CallLeg callLeg) {	this.legID = callLeg;	}

	public DetectionPoint getEvent_id() {	return event_id;}
	public void setEvent_id(DetectionPoint event_id) {	this.event_id = event_id;	}

	public int getAppTimer() {	return appTimer;	}
	public void setAppTimer(int appTimer) {	this.appTimer = appTimer;}	
}
