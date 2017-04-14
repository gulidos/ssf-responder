package ru.in.ssf.resp.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.in.ssf.resp.camel.ICamelAble;




public class Calls {
	private static final Logger logger = LoggerFactory.getLogger(Calls.class);
	private final Map<String, Call> dialogs;
//	private final BlockingQueue<Call> callsForProcessing;
	

	public Calls() {	
		dialogs  = new ConcurrentHashMap<>();
//		callsForProcessing = new LinkedBlockingQueue<>(1000);
	}
	

	/** Adds a call to the global Map of the calls where the key is a callId*/
	public void addCallId(Call call) {
		dialogs.put(call.getIdp().getCallId(), call);
	}
	
	public void removeCallId(Call call) {
		String callId = call.getIdp().getCallId();
		if (callId == null || dialogs.remove(callId) == null)
			logger.error("unknown dialogId: " + callId );
	}
	
	
	public Call checkCallId(ICamelAble msg) throws IllegalArgumentException {
		String callId = msg.getCallId();
		Call call = dialogs.get(callId);
		if (call == null)
			throw new IllegalArgumentException("Unknown dialogId in the message: " + msg.toString());
		return call;
	}
}
