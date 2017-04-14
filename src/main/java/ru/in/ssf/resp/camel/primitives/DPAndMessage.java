package ru.in.ssf.resp.camel.primitives;

import ru.in.ssf.resp.camel.ICamelAble;

public class DPAndMessage {  
	private final DetectionPoint dp;
	private final ICamelAble msg;
	


	public DPAndMessage(DetectionPoint dp, ICamelAble msg) {
		super();
		this.dp = dp;
		this.msg = msg;
	}

	public DetectionPoint getDp() {return dp;}
	public ICamelAble getMsg() {return msg;}
}
