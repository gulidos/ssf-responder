package ru.in.ssf.resp.json;

import static ru.in.ssf.resp.json.JsonHelper.callLegWrite;
import static ru.in.ssf.resp.json.JsonHelper.valueWrite;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.EventBCSM;


public class EventBCSMTA extends TypeAdapter<EventBCSM>  {

	@Override
	public void write(JsonWriter out, EventBCSM event) throws IOException {
		out.beginObject();
		out.name("some text");
		out.beginObject();
		out.name("event_id");
		valueWrite(out, event.getEvent_id().getCode());
		out.name("legID");
		callLegWrite(out,  event.getCallLeg());
		out.name("mon_mode");
		valueWrite(out, event.getMonModel().getCode());
		out.endObject();
		out.endObject();
	}

	@Override
	public EventBCSM read(JsonReader in) throws IOException {
		return null;
	}

	
	
	
}
