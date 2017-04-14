package ru.in.ssf.resp.json;

import static ru.in.ssf.resp.json.JsonHelper.baseMsgReader;
import static ru.in.ssf.resp.json.JsonHelper.getIntValue;
import static ru.in.ssf.resp.json.JsonHelper.writeCamelMsg;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.CapApplyCharging;
import ru.in.ssf.resp.camel.primitives.CallLeg;


public class ApplyChargingTA extends TypeAdapter<CapApplyCharging> {

	@Override
	public void write(JsonWriter out, CapApplyCharging msg) throws IOException {
		out.beginObject();
		out.name(msg.getMsgType().toString());
		out.beginObject();
		writeCamelMsg(out, msg);
		out.name("billing");
		out.beginObject();
		out.name("maxCallPeriodDuration").value(msg.getMaxCallPeriodDuration());	
		out.endObject();
		out.name("partyToCharge");
		out.beginObject();
		out.name("side").value(msg.getPartyToCharge().getChoice());	
		out.endObject();
		out.endObject();
		out.endObject();
	}

	@Override
	public CapApplyCharging read(JsonReader in) throws IOException {
		CapApplyCharging result = new CapApplyCharging();
		in.beginObject();
		while (in.hasNext()) {
			String nextName = in.nextName();
			switch (nextName) {
			case "billing":
				result.setMaxCallPeriodDuration(getIntValue(in, "maxCallPeriodDuration"));
				break;
			case "partyToCharge":
				result.setPartyToCharge(CallLeg.getInstance(getIntValue(in, "side")));
				break;
			default:
				baseMsgReader(nextName, in, result);
				break;
			}
		}
		in.endObject();
		return result;
	}

	public ApplyChargingTA() {	}
	
	

}
