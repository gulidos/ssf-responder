package ru.in.ssf.resp.camel;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;
import ru.in.ssf.resp.camel.primitives.CapMsg;
@Data
public class CapMessage implements ICamelAble {
	private static final Logger logger = LoggerFactory.getLogger(CapMessage.class);

	private CapMsg msgType; 
	private int invokeId;
	private int dialogId;
	private String callId;
	private InetSocketAddress sender;
	
	
    
	public CapMessage() {};
	
    public CapMessage(CapMsg msgType, int invokeId, int dialogId, String callId) {
		super();
		this.msgType = msgType;
		this.invokeId = invokeId;
		this.dialogId = dialogId;
		this.callId = callId;
	}
    
    public CapMessage(InnerBuilder<?> builder) {
		this.msgType = builder.msgType;
		this.invokeId = builder.invokeId;
		this.dialogId = builder.dialogId;
		this.callId = builder.callId;
	}
	
	@Override
	public void onReceive(ICall call, ICallState callstate) {
		logger.debug("Unexpected CapMsg {} was received: for call {} in state {}", toString(), call.toString(), callstate.getState());
	}
	@Override
	public InetSocketAddress getSender() {return sender;}
	@Override
	public void setSender(InetSocketAddress sender) {this.sender = sender;}

	@SuppressWarnings("unchecked")
	public static class InnerBuilder<B extends InnerBuilder<B>> {
		private CapMsg msgType;
		private int invokeId;
		private int dialogId;
		private String callId;

		protected InnerBuilder() {}
		protected B setMsgType(CapMsg msgType) { this.msgType = msgType; return (B) this;}
		public B setInvokeId(int invokeId) { this.invokeId = invokeId; return (B) this;}
		public B setDialogId(int dialogId) { this.dialogId = dialogId; return (B) this;}
		public B setCallId(String callId) { this.callId = callId;	return (B) this;}
		public CapMessage build() {	return new CapMessage(this);}
	}


	
}
