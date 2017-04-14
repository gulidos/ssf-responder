
package ru.in.ssf.resp.camel.primitives;

public enum DetectionPoint{
	collectedInfo(2), 
	analyzedInformation(3),
	routeSelectFailure(4), 
	oCalledPartyBusy(5), 
	oNoAnswer(6), 
	oAnswer(7),
	oMidCall(8),
	oDisconnect(9), 
	oAbandon(10), 
	termAttemptAuthorized(12), 
	tBusy(13), 
	tNoAnswer(14), 
	tAnswer(15), 
	tMidCall(16),
	tDisconnect(17), 
	tAbandon(18), 
	oTermSeized(19), 
	callAccepted(27), 
	oChangeOfPosition(50), 
	tChangeOfPosition(51), 
	oServiceChange(52), 
	tServiceChange(53);

	private int code;

	private DetectionPoint(int code) {
		this.code = code;
	}

	public static DetectionPoint getInstance(int code) {
		switch (code) {
		case 2:
			return DetectionPoint.collectedInfo;
		case 3:
			return DetectionPoint.analyzedInformation;
		case 4:
			return DetectionPoint.routeSelectFailure;
		case 5:
			return DetectionPoint.oCalledPartyBusy;
		case 6:
			return DetectionPoint.oNoAnswer;
		case 7:
			return DetectionPoint.oAnswer;
		case 8:
			return DetectionPoint.oMidCall;
		case 9:
			return DetectionPoint.oDisconnect;
		case 10:
			return DetectionPoint.oAbandon;
		case 12:
			return DetectionPoint.termAttemptAuthorized;
		case 13:
			return DetectionPoint.tBusy;
		case 14:
			return DetectionPoint.tNoAnswer;
		case 15:
			return DetectionPoint.tAnswer;
		case 16:
			return DetectionPoint.tMidCall;
		case 17:
			return DetectionPoint.tDisconnect;
		case 18:
			return DetectionPoint.tAbandon;
		case 19:
			return DetectionPoint.oTermSeized;
		case 27:
			return DetectionPoint.callAccepted;
		case 50:
			return DetectionPoint.oChangeOfPosition;
		case 51:
			return DetectionPoint.tChangeOfPosition;
		case 52:
			return DetectionPoint.oServiceChange;
		case 53:
			return DetectionPoint.tServiceChange;
		default:
			return null;
		}
	}

	public int getCode() {	return this.code;	}

}
