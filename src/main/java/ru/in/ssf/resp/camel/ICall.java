package ru.in.ssf.resp.camel;

import org.springframework.context.annotation.Profile;

import ru.in.ssf.resp.camel.primitives.CallLeg;
import ru.in.ssf.resp.camel.primitives.DetectionPoint;

public interface ICall {

	void setTimer(String timerName, int delay);

	void cancelTimer(String timerName);

	/** Gets a list of incoming Camel messages and process them continuously */
	int processCapResps();

	void addMsgForProcessing(ICamelAble msg);

	EventBCSM getEvent(DetectionPoint dp);

	void renewDp(CapRrbe rrbe);

	/** Sets up attribute to SAS. SasAttributeListener will initiate processing of the queue with messages*/
	void addReadQueueAttribute();

	String getGsmScfGt();

	int getGsmScfSk();

//	Dch getDch();

	String getSrcAddress();

	void setSrcAddress(String srcAddress);

	String getDstAddress();

	void setDstAddress(String dstAddress);

	int getInvokeId();

	int incInvokeId();

	int getDialogId();

	void setCallActive(boolean b);

	boolean isCallActive();

//	SipServletRequest getIncomeRequest();

//	SipApplicationSession getSas();

	String getOrigSipSessionId();

	String getServedUser();

//	Direction getDirection();

	String getFCI();

	void setFCI(String chargInfo);

	CallLeg getPartyToCharge();

	void setPartyToCharge(CallLeg partyToCharge);

	int getMaxDuration();

	void setMaxDuration(int maxDuration);

	ICallState getCallState();

	void setCallState(ICallState callState);

	Profile getProfile();

	void setProfile(Profile profile);

//	Cdr getCdr();

	int getNoAnswerTimer();

	String toString();

}