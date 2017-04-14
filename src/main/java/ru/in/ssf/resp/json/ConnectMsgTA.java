package ru.in.ssf.resp.json;

import static ru.in.ssf.resp.json.JsonHelper.baseMsgReader;
import static ru.in.ssf.resp.json.JsonHelper.getPhone;
import static ru.in.ssf.resp.json.JsonHelper.phoneWrite;
import static ru.in.ssf.resp.json.JsonHelper.writeCamelMsg;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.CapConnect;


public class ConnectMsgTA extends TypeAdapter<CapConnect> {

	@Override
	public void write(JsonWriter out, CapConnect msg) throws IOException {
		out.beginObject();
		out.name(msg.getMsgType().toString());
		out.beginObject();
		writeCamelMsg(out, msg);
		out.name("dest");
		phoneWrite(out, msg.getDest());
		out.name("gn_acgpn");
		phoneWrite(out, msg.getGn_acgpn());
		out.endObject();
		out.endObject();
	}

	@Override
	public CapConnect read(JsonReader in) throws IOException {
		CapConnect result = new CapConnect();
		in.beginObject();
		while (in.hasNext()) {
			String nextName = in.nextName();
			switch (nextName) {
			case "dest":
				result.setDest(getPhone(in));
				break;
			case "gn_acgpn":
				result.setGn_acgpn(getPhone(in));
				break;
			default:
				baseMsgReader(nextName, in, result);
				break;
			}
		}
		in.endObject();
		return result;
	}

	public ConnectMsgTA() {	}
	
	

}
