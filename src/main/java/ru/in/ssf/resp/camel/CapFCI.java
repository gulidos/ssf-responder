package ru.in.ssf.resp.camel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.in.ssf.resp.camel.primitives.CallLeg;
import ru.in.ssf.resp.camel.primitives.CapMsg;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class CapFCI extends CapMessage {
	
	private String stringNumber;
	private String freeFormatData;
	private CallLeg partyToCharge;
	
	public CapFCI() {
		super(CapMsg.CAP_FCI, 0, 0, "");
	}
	
	public CapFCI(int invokeId, int dialogId, String callId, String stringNumber, String  freeFormatData, CallLeg partyToCharge) {
		super(CapMsg.CAP_FCI, invokeId, dialogId, callId);
		this.stringNumber = stringNumber;
		this.freeFormatData = freeFormatData;
		this.partyToCharge = partyToCharge;
	}
		
	@Override
	public void onReceive(ICall call, ICallState callstate) {
		callstate.onCapFCI(call, this); // works out in states: analysedInformation,  
	}
}
