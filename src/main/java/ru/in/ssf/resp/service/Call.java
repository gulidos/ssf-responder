package ru.in.ssf.resp.service;

import lombok.Data;
import ru.in.ssf.resp.camel.CapInitialDP;
@Data
public class Call {
	private final CapInitialDP idp;
	private final Scenario scenario;
	static int maxduration  = 40;
	private int duration = 0;
	
	public Call(CapInitialDP idp, Scenario s) {
		this.idp = idp;
		this.scenario = s;
	}

	public enum Stage {
		Alerting, Active, Fail; 
	}
	
}
