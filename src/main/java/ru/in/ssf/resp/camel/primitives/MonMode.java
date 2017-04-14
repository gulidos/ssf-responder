package ru.in.ssf.resp.camel.primitives;

public enum MonMode {
	interrupted(0),
	notifyAndContinue(1),
	transparentMode(2);
	private final int code;

	private MonMode(int code) {
		this.code = code;
	}

	public int getCode() {return code;}
	
	public static MonMode getInstance(int code) {
		switch (code) {
		case 0:
			return MonMode.interrupted;
		case 1:
			return MonMode.notifyAndContinue;
		case 2:
			return MonMode.transparentMode;
		default:
			return null;
		}
	}	
}
