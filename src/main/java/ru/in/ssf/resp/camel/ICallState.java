package ru.in.ssf.resp.camel;

import ru.in.ssf.resp.camel.primitives.StateBCSM;

public interface ICallState {
	public void onInvite(ICall call);
	
	public void onCancel(ICall call);
	
//	public void onBusy(Call call, SipServletResponse resp);
	
	public void onNoAnswer(ICall call);
	
	public void onRouteSelectFailure(ICall call);
	
	public void onAnswer(ICall call);
	
//	public void onDisconnect(Call call, SipServletRequest req);

	public void onCapRrbe(ICall call, CapRrbe rrbe);
	
	public void onCapContinue(ICall call);
	
	public void onCapConnect(ICall call, CapConnect con);
	
	public void onTimerIDP(ICall call);
	
	public void onTimerERB(ICall call);
	
	public void onTimerAppCharging(ICall call);
		
	public void onCapApplyCharging (ICall call, CapApplyCharging apch);
	
	public void onCapFCI (ICall call, CapFCI fci);
	
	public void onCapRelease (ICall call, int cause);
	
	public StateBCSM getState();
}
