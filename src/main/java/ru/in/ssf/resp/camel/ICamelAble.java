package ru.in.ssf.resp.camel;


import java.net.InetSocketAddress;

import ru.in.ssf.resp.camel.primitives.CapMsg;

public interface ICamelAble {

	public CapMsg getMsgType();

	public int getDialogId();

	public int getInvokeId();

	public void setMsgType(CapMsg msgType);

	public void setInvokeId(int invokeId);

	public void setDialogId(int dialogId);
	
	public String getCallId();
	
	public void setCallId(String callId);
	
	public void onReceive(ICall call, ICallState callstate);

	public String toString();
	
	public static class Builder {}

	InetSocketAddress getSender();

	void setSender(InetSocketAddress sender);
	
}
