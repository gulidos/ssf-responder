package ru.in.ssf.resp.camel.primitives;

public enum CallLeg {
	undefinedSide(0),
	sendingSide(1),
	receivingSide(2);
	
	private final int leg;
	private final int choice;
	
	private CallLeg(int code) {
		this.leg = code;
		this.choice = code;
	}
	
	public static CallLeg getInstance(int code) {
		switch (code) {
		case 1:
			return CallLeg.sendingSide;
		case 2:
			return CallLeg.receivingSide;
		default:
			return CallLeg.undefinedSide;
		}
	}
	
	public int getLeg() {return leg;}
	
	public int getChoice() {	return choice;	}
}
