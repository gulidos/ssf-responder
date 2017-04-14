package ru.in.ssf.resp.json;

import static ru.in.ssf.resp.json.JsonHelper.baseMsgReader;
import static ru.in.ssf.resp.json.JsonHelper.getIntValue;
import static ru.in.ssf.resp.json.JsonHelper.getValue;
import static ru.in.ssf.resp.json.JsonHelper.valueWrite;
import static ru.in.ssf.resp.json.JsonHelper.writeCamelMsg;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.CapErb;
import ru.in.ssf.resp.camel.primitives.CallLeg;
import ru.in.ssf.resp.camel.primitives.DetectionPoint;

public class ErbMsgTA extends TypeAdapter<CapErb> {
	private static final Logger logger = LoggerFactory.getLogger(ErbMsgTA.class);


	@Override
	public void write(JsonWriter out, CapErb msg) throws IOException {
		out.beginObject();
		out.name(msg.getMsgType().toString());
		out.beginObject();
		writeCamelMsg(out, msg);
		out.name("event");
		out.beginObject();
		out.name("event");
		valueWrite(out, msg.getEvent().getCode());
		out.name("sideID");	
		out.beginObject();
			out.name("side").value(msg.getLegId().getLeg());
		out.endObject();
		out.endObject();  
		out.endObject();
		out.endObject();
	}

	@Override
	public CapErb read(JsonReader in) throws IOException {
		CapErb msg = new CapErb();
		in.beginObject();
		while (in.hasNext()) { 
			String nextName = in.nextName();
			switch (nextName) {
			case "event":
				in.beginObject();
				while (in.hasNext()) {
					nextName = in.nextName();
					System.out.println(nextName);

					switch (nextName) {
					case "event":
						msg.setEvent(DetectionPoint.getInstance(getValue(in)));
						break;
					case "sideID":
						msg.setLegId(CallLeg.getInstance(getIntValue(in, "side")));
						break;	
					default:
						logger.error("Something strange was recieved: " + in.nextString());
						break;
					}
				}
				in.endObject();
				break;
			
			default:
				baseMsgReader(nextName, in, msg);
				break;
			}
		}
		in.endObject();
		return msg;
	}

	
	
	public ErbMsgTA() {	}
	
	

}
