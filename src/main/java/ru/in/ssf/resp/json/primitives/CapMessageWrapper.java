package ru.in.ssf.resp.json.primitives;

import java.lang.reflect.Type;

import ru.in.ssf.resp.camel.primitives.CapMsg;


public class CapMessageWrapper {
	private final CapMsg msgName;
	private final Type msgType;
	private final String json;

	public CapMessageWrapper(Type msgType, String json, CapMsg msgName) {
		super();
		this.msgName = msgName;
		this.msgType = msgType;
		this.json = json;
	}


	public Type getMsgType() {	return msgType;	}

	public String getJson() {return json;}

	public CapMsg getMsgName() {	return msgName;	}

}
