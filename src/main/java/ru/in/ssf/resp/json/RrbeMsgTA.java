package ru.in.ssf.resp.json;

import static ru.in.ssf.resp.json.JsonHelper.baseMsgReader;
import static ru.in.ssf.resp.json.JsonHelper.callLegWrite;
import static ru.in.ssf.resp.json.JsonHelper.getCallLeg;
import static ru.in.ssf.resp.json.JsonHelper.getValue;
import static ru.in.ssf.resp.json.JsonHelper.valueWrite;
import static ru.in.ssf.resp.json.JsonHelper.writeCamelMsg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.CapRrbe;
import ru.in.ssf.resp.camel.EventBCSM;
import ru.in.ssf.resp.camel.primitives.DetectionPoint;
import ru.in.ssf.resp.camel.primitives.MonMode;



public class RrbeMsgTA extends TypeAdapter<CapRrbe> {
	private static final Logger logger = LoggerFactory.getLogger(RrbeMsgTA.class);

	@Override
	public void write(JsonWriter out, CapRrbe msg) throws IOException {
		out.beginObject();
		out.name(msg.getMsgType().toString());
		out.beginObject();
		writeCamelMsg(out, msg);
		out.name("events").beginObject();
		out.name("count").value(msg.getEvents().size());
		out.name("events").beginArray();
		for (final EventBCSM event : msg.getEvents()) {
			out.beginObject();
			out.name("event_id");
			valueWrite(out, event.getEvent_id().getCode());
			out.name("legID");
			callLegWrite(out, event.getCallLeg());
			out.name("mon_mode");
			valueWrite(out, event.getMonModel().getCode());
			out.name("criteria");
			out.beginObject();
				out.name("appTimer");
				valueWrite(out, event.getAppTimer());
			out.endObject();
			out.endObject();
		}

		out.endArray().endObject();
		out.endObject();
		out.endObject();
	}

	@Override
	public CapRrbe read(JsonReader in) throws IOException {
		CapRrbe result = new CapRrbe();
		in.beginObject();
		while (in.hasNext()) {
			String nextName = in.nextName();
			switch (nextName) {
			case "events":
				in.beginObject();
				while (in.hasNext()) {
					switch (in.nextName()) {
					case "count":
						result.setCount(in.nextInt());
						break;
					case "events":
						in.beginArray();
						final List<EventBCSM> events = new ArrayList<>();
						while (in.hasNext()) {
							in.beginObject();
							events.add(getEvent(in));
							in.endObject();
						}
						in.endArray();
						result.setEvents(events);
						break;
					default:
						logger.error("Something strange was recieved: " + in.nextString());
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

	private EventBCSM getEvent(JsonReader in) throws IOException {
		final EventBCSM event = new EventBCSM();
		while (in.hasNext()) {
			switch (in.nextName()) {
			case "event_id":
				event.setEvent_id(DetectionPoint.getInstance(getValue(in)));
				break;
			case "legID":
				event.setCallLeg(getCallLeg(in));
				break;
			case "mon_mode":
				event.setMonModel(MonMode.getInstance(getValue(in)));
				break;
			case "criteria":
				in.beginObject();
				if (in.hasNext()) {
					if ("appTimer".equals(in.nextName()))
						event.setAppTimer(getValue(in));
				}	
				in.endObject();
				break;
			default:
				logger.error("Something strange was recieved: " + in.nextString());
				break;
			}
		}
		return event;
	}

	public RrbeMsgTA() {	}

}
