package ru.in.ssf.resp.camel;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.in.ssf.resp.camel.primitives.CapMsg;

@EqualsAndHashCode(callSuper=true, exclude="events")
@ToString(callSuper=true)
public class CapRrbe extends CapMessage  {
	private int count;
	private List<EventBCSM> events;
	
	
	public CapRrbe(int invokeId, int dialogId, String callId, List<EventBCSM> events) {
		super(CapMsg.CAP_RRBE, invokeId, dialogId, callId);
		this.events = events;
	}

	public CapRrbe() {
		super(CapMsg.CAP_RRBE, 0, 0, "");
	}

	public void addEvent(EventBCSM event) {
		events.add(event);
	}
	public List<EventBCSM> getEvents() {return events;}
	public void setEvents(List<EventBCSM> events) {	this.events = events;}

	
	public int getCount() {	return count;	}
	public void setCount(int count) {	this.count = count;	}


	@Override
	public void onReceive(ICall call, ICallState callstate) {
		callstate.onCapRrbe(call, this); // works in states: analysedInformation, 
	}
	
}
