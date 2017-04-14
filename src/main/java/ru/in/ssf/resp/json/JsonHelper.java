package ru.in.ssf.resp.json;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.CapApplyCharging;
import ru.in.ssf.resp.camel.CapApplyChargingReport;
import ru.in.ssf.resp.camel.CapConnect;
import ru.in.ssf.resp.camel.CapContinue;
import ru.in.ssf.resp.camel.CapErb;
import ru.in.ssf.resp.camel.CapFCI;
import ru.in.ssf.resp.camel.CapInitialDP;
import ru.in.ssf.resp.camel.CapMessage;
import ru.in.ssf.resp.camel.CapOpenRequest;
import ru.in.ssf.resp.camel.CapRelease;
import ru.in.ssf.resp.camel.CapRrbe;
import ru.in.ssf.resp.camel.ICamelAble;
import ru.in.ssf.resp.camel.primitives.CallLeg;
import ru.in.ssf.resp.camel.primitives.CapMsg;
import ru.in.ssf.resp.json.primitives.CapMessageWrapper;

public class JsonHelper {
//    private static final Logger logger = LoggerFactory.getLogger(JsonHelper.class);
	private static final Pattern p_cap_message_type = 
			Pattern.compile("^\\{\\s*\"(CAP[^\"]*)\"\\s*:\\s*(\\{.+\\})\\s*\\}$", Pattern.DOTALL); 
		// [^\"]*  ^ -инверсия диапазона. Матчит все символы a-z кроме кавычки
		// Pattern.DOTALL -  matches any character, including a line terminator

	
	private static final Gson gson = new GsonBuilder()
//			.setPrettyPrinting()
			.registerTypeAdapter(CapInitialDP.class, new InitialDPMsgTA())
			.registerTypeAdapter(CapConnect.class, new ConnectMsgTA())
			.registerTypeAdapter(CapContinue.class, new ContinueMsgTA())
			.registerTypeAdapter(CapRrbe.class, new RrbeMsgTA())
			.registerTypeAdapter(CapErb.class, new ErbMsgTA())
			.registerTypeAdapter(CapRelease.class, new ReleaseMsgTA())
			.registerTypeAdapter(CapOpenRequest.class, new CapOpenTA())
			.registerTypeAdapter(CapApplyCharging.class, new ApplyChargingTA())
			.registerTypeAdapter(CapApplyChargingReport.class, new ApplyChargingReportTA())
			.registerTypeAdapter(CapFCI.class, new CapFCITA())
			.create();
	

	public static Gson getGson() {
		return gson;
	}

	
	public static ICamelAble getCapMsg(String json) {
		ICamelAble capMsg = null;
		CapMessageWrapper msg = JsonHelper.getMessageType(json);
		capMsg = gson.fromJson(msg.getJson(), msg.getMsgType());
		return capMsg;
	}
	
	
	public static CapMessageWrapper getMessageType(String json) throws JsonParseException {
		Matcher m1 = p_cap_message_type.matcher(json);
		if (!m1.find())
			throw new JsonParseException("Can not parse message type: " + json.toString().substring(0,20));
		
		CapMsg msg = CapMsg.valueOf(m1.group(1));	
		switch (msg) {
			case CAP_CONTINUE:
				return new CapMessageWrapper(CapContinue.class, m1.group(2), CapMsg.CAP_CONTINUE);
			case CAP_CONNECT:
				return new CapMessageWrapper(CapConnect.class, m1.group(2), CapMsg.CAP_CONNECT);
			case CAP_RRBE:
				return new CapMessageWrapper(CapRrbe.class, m1.group(2), CapMsg.CAP_RRBE);
			case CAP_RELEASE:
				return new CapMessageWrapper(CapRelease.class, m1.group(2), CapMsg.CAP_RELEASE);
			case CAP_INITIAL_DP:
				return new CapMessageWrapper(CapInitialDP.class, m1.group(2), CapMsg.CAP_INITIAL_DP);
			case CAP_APPLY_CHARGING:
				return new CapMessageWrapper(CapApplyCharging.class, m1.group(2), CapMsg.CAP_APPLY_CHARGING);
			case CAP_FCI:
				return new CapMessageWrapper(CapFCI.class, m1.group(2), CapMsg.CAP_FCI);	
			case CAP_APPLY_CHARGING_REPORT:
				return new CapMessageWrapper(CapApplyChargingReport.class, m1.group(2), CapMsg.CAP_APPLY_CHARGING_REPORT);
			case CAP_OPEN_REQ:
				return new CapMessageWrapper(CapOpenRequest.class, m1.group(2), CapMsg.CAP_OPEN_REQ);
//				return new CapMessageWrapper(CapOpenRequest.class, "{}", CapMsg.CAP_OPEN_REQ);
			case CAP_ERB:
				return new CapMessageWrapper(CapErb.class, m1.group(2), CapMsg.CAP_ERB);
			default:
				throw new JsonParseException("Unknown message type: " + json.toString().substring(0,20));
		}		
	}
	
		
// 							******* Reader's helpers *******
	
	/**  Parses the Value type of a json response */
	public static int getValue(JsonReader in) throws IOException, JsonParseException {
		String nextName;
		in.beginObject();
		int v;
		if (in.hasNext()) {
			nextName = in.nextName();
			if ("value".equals(nextName)) 
				v = in.nextInt();
			else 
				throw new JsonParseException("Can not parse message. The value type is expected: " + nextName);
		} else 
			throw new JsonParseException("Can not parse message. Got an empty value: ");
		
		in.endObject();
		return v;
	}
	
	/**  Parses the type Phone in a json response */
	protected static String getPhone(JsonReader in) throws IOException, JsonParseException {
		String nextName;
		in.beginObject();
		String p =  null;
		while (in.hasNext()) {
			nextName = in.nextName();
			switch (nextName) {
            case "phone":
              p = in.nextString();
              break;
             default:
            	 in.skipValue();
            	 break;
            }
		} 
		in.endObject();
		return p;
	}
	
	/**  Parses the basic fields of the json response */ 
	protected static void baseMsgReader(String nextName, JsonReader in, ICamelAble msg ) throws JsonParseException, IOException {
		System.out.println(nextName);
		switch (nextName) {
		case "invokeId":
			msg.setInvokeId(getValue(in));
			break;
		case "dialogId":
			msg.setDialogId(getValue(in));
			break;
		case "callId":
			if (in.hasNext()) 
				msg.setCallId(in.nextString());
			else 
				throw new JsonParseException("Can not parse a CallId." );
			break;
		case "bitMask":
			getValue(in); // we don't need it
			break;	
		default:
			in.skipValue();
			break;
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected static void baseMsgBuilder(String nextName, JsonReader in, CapMessage.InnerBuilder builder ) 
			throws JsonParseException, IOException {
		switch (nextName) {
		case "invokeId": builder.setInvokeId(getValue(in));	break;
		case "dialogId": builder.setDialogId(getValue(in)); break;
		case "bitMask":	getValue(in); break;	
		case "callId":
			if (in.hasNext()) 
				builder.setCallId(in.nextString());
			else 
				throw new JsonParseException("Can not parse a CallId." );
			break;
		default: in.skipValue(); break;
		}
	}
	
	public static CallLeg getCallLeg(JsonReader in) throws IOException {
		CallLeg callLeg = null;
		in.beginObject();
		while (in.hasNext()) {
			String n = in.nextName();
			switch (n) {
			case "choice":
				callLeg = CallLeg.getInstance(getValue(in));
				break;
			case "leg":
				in.nextInt();
				break;
			default:
				in.skipValue();
				break;
			}
		}
		in.endObject();
		return callLeg;
	}
	
	public static int getIntValue(JsonReader in, String name) throws IOException, JsonParseException {
		String nextName;
		in.beginObject();
		int v;
		if (in.hasNext()) {
			nextName = in.nextName();
			if (name != null && name.equals(nextName)) 
				v = in.nextInt();
			else 
				throw new JsonParseException("Can not parse message. The value type is expected: " + nextName);
		} else 
			throw new JsonParseException("Can not parse message. Got an empty value: ");
		
		in.endObject();
		return v;
	}
	
	public static String getStrValue(JsonReader in, String name) throws IOException, JsonParseException {
	String nextName;
	in.beginObject();
	String p =  null;
	if (in.hasNext()) {
		nextName = in.nextName();
		if (name != null && name.equals(nextName)) 
          p = in.nextString();
		else 
			throw new JsonParseException("Can not parse message. The value type is expected: " + nextName);
        }
	in.endObject();
	return p;
}

	
// 							******* Writer's helpers *******
	
	public static void callLegWrite(JsonWriter out, CallLeg callLeg) throws IOException {
		out.beginObject();
		out.name("choice");
		valueWrite(out, callLeg.getChoice());
		out.name("leg").value(callLeg.getLeg());
		out.endObject();
	}
	
	public static void valueWrite(JsonWriter out, int v) throws IOException {
		out.beginObject();
		out.name("value").value(v);	
		out.endObject();
	}
	
	public static void writeIntValue (JsonWriter out, int v, String name) throws IOException {
		out.beginObject();
		out.name(name).value(v);	
		out.endObject();
	}
	
	public static void phoneWrite(JsonWriter out, String p) throws IOException {
		out.beginObject();
		out.name("phone").value(p);	
		out.endObject();
	}
	
	/** Writes fields of a base Camel Message (ex. Continue) */
	public static void writeCamelMsg(JsonWriter out, ICamelAble msg) throws IOException {
		out.name("invokeId");
		valueWrite(out, msg.getInvokeId());
		out.name("dialogId");
		valueWrite(out, msg.getDialogId());
		out.name("callId").value(msg.getCallId());
	}
	
	public JsonHelper() {	}
}
