package ru.in.ssf.resp.json;


import static ru.in.ssf.resp.json.JsonHelper.phoneWrite;
import static ru.in.ssf.resp.json.JsonHelper.valueWrite;
import static ru.in.ssf.resp.json.JsonHelper.writeCamelMsg;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.CapInitialDP;


public class InitialDPMsgTA extends TypeAdapter<CapInitialDP> {
	


	@Override
	public void write(JsonWriter out, CapInitialDP msg) throws IOException {
		out.beginObject();
		out.name(msg.getMsgType().toString());
		out.beginObject();
		writeCamelMsg(out, msg);
		out.name("serviceKey");
		valueWrite(out, msg.getServiceKey());
		out.name("calledNumber");
		phoneWrite(out, msg.getCalledNumber());
		out.name("callingNumber");
		phoneWrite(out, msg.getCallingNumber());
		out.name("calledPartyBCDNumber");
		phoneWrite(out, msg.getCalledPartyBCDNumber());	
		out.name("iMSI").beginObject();
			out.name("stringNumber").value(msg.getiMSI());
		out.endObject();	
		out.endObject();
		out.endObject();
	}

	@Override
	public CapInitialDP read(JsonReader in) throws IOException {
		CapInitialDP.Builder builder = new CapInitialDP.Builder();

		in.beginObject();
		while (in.hasNext()) {
			String nextName = in.nextName();
			switch (nextName) {
			case "serviceKey":
				builder.setServiceKey(JsonHelper.getValue(in));
				break;
			case "eventType":
				builder.setEventType(JsonHelper.getValue(in));
				break;
			case "calledNumber":
				builder.setCalledNumber(JsonHelper.getPhone(in));
				break;
			case "callingNumber":
				builder.setCallingNumber(JsonHelper.getPhone(in));
				break;
			case "calledBCDNumber":
				builder.setCPBCDNumber(JsonHelper.getPhone(in));
				break;
			default:
				JsonHelper.baseMsgBuilder(nextName, in, builder);
				break;
			}
		}
		in.endObject();
		return builder.build();
	}

	public InitialDPMsgTA() {	}
	
	

}
