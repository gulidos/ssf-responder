package ru.in.ssf.resp.camel.primitives;

public enum StateBCSM {
	o_Null("O_Null & Authorise_Origination_Attempt_Collect_Info"),
	analyseInformation("Analyse_Information"),
	routingAndAlerting("Routing & Alerting"),
	o_Active("O_Active"),
	o_Exception("O_Exception"),
	o_Disconnect("Disconnect");
	
	private final String state;

	private StateBCSM(String state) {
		this.state = state;
	}
	
	public String getState() {return state;	}
	
	 public static StateBCSM getEnum(String state) {
	        for(StateBCSM s : values())
	            if(s.getState().equalsIgnoreCase(state)) return s;
	        throw new IllegalArgumentException();
	}
	 
}
