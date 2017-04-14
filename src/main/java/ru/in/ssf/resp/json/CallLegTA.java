package ru.in.ssf.resp.json;

import static ru.in.ssf.resp.json.JsonHelper.callLegWrite;
import static ru.in.ssf.resp.json.JsonHelper.getCallLeg;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.primitives.CallLeg;


public class CallLegTA extends TypeAdapter<CallLeg>  {

	@Override
	public void write(JsonWriter out, CallLeg callLeg) throws IOException {
		callLegWrite(out, callLeg);
	}

	@Override
	public CallLeg read(JsonReader in) throws IOException {
		return getCallLeg(in);
	}
	
	
}
