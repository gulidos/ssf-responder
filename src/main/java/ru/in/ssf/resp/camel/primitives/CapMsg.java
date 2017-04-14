package ru.in.ssf.resp.camel.primitives;

public enum CapMsg {
	CAP_OPEN_REQ,
	CAP_INITIAL_DP,
	CAP_CONTINUE,
	CAP_CONNECT,
	CAP_RELEASE,
	CAP_RRBE,
	CAP_U_ABORT,
	CAP_ERB,
	CAP_APPLY_CHARGING,
	CAP_APPLY_CHARGING_REPORT,
	CAP_FCI;  //furnish charging information
	
//	public static CapMsg getInstance(String code) {
//		switch (code) {
//		case "CAP_OPEN_REQ":
//			return CAP_OPEN_REQ;
//		case "CAP_INITIAL_DP":
//			return CAP_INITIAL_DP;
//		case "CAP_CONTINUE":
//			return CAP_CONTINUE;
//		case "CAP_CONNECT":
//			return CAP_CONNECT;
//		case "CAP_RELEASE":
//			return CAP_RELEASE;
//		case "CAP_RRBE":
//			return CAP_RRBE;
//		case "CAP_U_ABORT":
//			return CAP_U_ABORT;
//		case "CAP_ERB":
//			return CAP_ERB;
//		case "CAP_APPLY_CHARGING":
//			return CAP_APPLY_CHARGING;
//		case "CAP_APPLY_CHARGING_REPORT":
//			return CAP_APPLY_CHARGING_REPORT;
//		case "CAP_FCI":
//			return CAP_FCI;
//		default:
//			return null;
//		}	
//	}
}
