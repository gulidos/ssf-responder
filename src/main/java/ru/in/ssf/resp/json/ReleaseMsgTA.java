package ru.in.ssf.resp.json;

import static ru.in.ssf.resp.json.JsonHelper.baseMsgReader;
import static ru.in.ssf.resp.json.JsonHelper.getIntValue;
import static ru.in.ssf.resp.json.JsonHelper.writeCamelMsg;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.CapRelease;



public class ReleaseMsgTA extends TypeAdapter<CapRelease> {

	@Override
	public void write(JsonWriter out, CapRelease msg) throws IOException {
		out.beginObject();
		out.name(msg.getMsgType().toString());
		out.beginObject();
		writeCamelMsg(out, msg);
		out.name("cause");
		JsonHelper.writeIntValue(out, msg.getCause(), "cause");
		out.endObject();
		out.endObject();
	}

	@Override
	public CapRelease read(JsonReader in) throws IOException {
		CapRelease result = new CapRelease();
		in.beginObject();
		while (in.hasNext()) {
			String nextName = in.nextName();
			switch (nextName) {
			case "cause":
				result.setCause(getIntValue(in, "cause"));
				break;

			default:
				baseMsgReader(nextName, in, result);
				break;
			}
		}
		in.endObject();
		return result;
	}

	public ReleaseMsgTA() {	}
	
	

}
