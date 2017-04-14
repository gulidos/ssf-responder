package ru.in.ssf.resp.json;

import static ru.in.ssf.resp.json.JsonHelper.baseMsgReader;
import static ru.in.ssf.resp.json.JsonHelper.getIntValue;
import static ru.in.ssf.resp.json.JsonHelper.getStrValue;
import static ru.in.ssf.resp.json.JsonHelper.writeCamelMsg;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.CapFCI;
import ru.in.ssf.resp.camel.primitives.CallLeg;

public class CapFCITA extends TypeAdapter<CapFCI> {

	@Override
	public void write(JsonWriter out, CapFCI msg) throws IOException {
		out.beginObject();
			out.name(msg.getMsgType().toString());
			out.beginObject();
				writeCamelMsg(out, msg);
				out.name("arg");
				out.beginObject();
					out.name("stringNumber").value(msg.getStringNumber());
				out.endObject();
				out.name("billing");
				out.beginObject();
					out.name("freeFormatData");
					out.beginObject();
						out.name("stringNumber").value(msg.getFreeFormatData());
					out.endObject();
					out.name("partyToCharge");
					out.beginObject();
						out.name("side").value(msg.getPartyToCharge().getChoice());	
					out.endObject();
				out.endObject();
			out.endObject();
		out.endObject();
	}

	
	@Override
	public CapFCI read(JsonReader in) throws IOException {
		CapFCI result = new CapFCI();
		in.beginObject();
		while (in.hasNext()) {
			String nextName = in.nextName();
			switch (nextName) {
			case "arg":
				result.setStringNumber(getStrValue(in, "stringNumber"));
				break;
			case "billing":
				in.beginObject();
				while (in.hasNext()) {
					switch (in.nextName()) {
					case "freeFormatData":
						result.setFreeFormatData(getStrValue(in, "stringNumber"));
						break;
					case "partyToCharge":	
						result.setPartyToCharge(CallLeg.getInstance(getIntValue(in, "side")));
					default:
						break;
					}
				}
				in.endObject();
				break;
			default:
				baseMsgReader(nextName, in, result);
				break;
			}
		}
		in.endObject();
		return result;
	}


	public CapFCITA() {	}
	
	

}
