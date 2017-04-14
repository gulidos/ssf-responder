package ru.in.ssf.resp.json;

import static ru.in.ssf.resp.json.JsonHelper.baseMsgReader;
import static ru.in.ssf.resp.json.JsonHelper.writeCamelMsg;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.CapContinue;


public class ContinueMsgTA extends TypeAdapter<CapContinue> {

	@Override
	public void write(JsonWriter out, CapContinue msg) throws IOException {
		out.beginObject();
		out.name(msg.getMsgType().toString());
		out.beginObject();
		writeCamelMsg(out, msg);
		out.endObject();
		out.endObject();
	}

	@Override
	public CapContinue read(JsonReader in) throws IOException {
		CapContinue result = new CapContinue();
		in.beginObject();
		while (in.hasNext()) {
			String nextName = in.nextName();
			baseMsgReader(nextName, in, result);
		}
		in.endObject();
		return result;
	}
	
}
