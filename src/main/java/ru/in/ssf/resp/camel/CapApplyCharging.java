package ru.in.ssf.resp.camel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.in.ssf.resp.camel.primitives.CallLeg;
import ru.in.ssf.resp.camel.primitives.CapMsg;
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)

public class CapApplyCharging extends CapMessage {
	
	private CallLeg partyToCharge;
	private int maxCallPeriodDuration;
   
	
	public CapApplyCharging() {
		super(CapMsg.CAP_APPLY_CHARGING, 0, 0, "");
	}
		
	public CapApplyCharging(int invokeId, int dialogId, String callId,  CallLeg partyToCharge , int max) {
		super(CapMsg.CAP_APPLY_CHARGING, invokeId, dialogId, callId);
		this.partyToCharge = partyToCharge;
		this.maxCallPeriodDuration = max;
	}
	
	@Override
	public void onReceive(ICall call, ICallState callstate) {
		callstate.onCapApplyCharging(call, this); // works  in states: analysedInformation, 
	}

	
}
