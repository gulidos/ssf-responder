package ru.in.ssf.resp.json;

import static ru.in.ssf.resp.json.JsonHelper.writeCamelMsg;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.CapOpenRequest;


public class CapOpenTA extends TypeAdapter<CapOpenRequest> {
	
	private static final Logger logger = LoggerFactory.getLogger(CapOpenTA.class);


	@Override
	public void write(JsonWriter out, CapOpenRequest msg) throws IOException {
		out.beginObject();
			out.name(msg.getMsgType().toString());
			out.beginObject();
				writeCamelMsg(out, msg);
				out.name("camelVersion");
				out.beginObject();
					out.name("version").value(msg.getVersion());
				out.endObject();
				out.name("destinationAddress");
				out.beginObject();
					out.name("SSN").value(msg.getDestSSN());
					out.name("GlobalTitle");
					out.beginObject();
						out.name("AddressInformation").value(msg.getDestGTAddressInfo());		
					out.endObject();
				out.endObject();
			out.endObject();
		out.endObject();
	}

	@Override
	public CapOpenRequest read(JsonReader in) throws IOException {
		CapOpenRequest.Builder builder = new CapOpenRequest.Builder();

		in.beginObject();
		while (in.hasNext()) {
			String nextName = in.nextName();
			switch (nextName) {
			case "camelVersion":
				builder.setVersion(JsonHelper.getIntValue(in, "version"));
				break;
			case "destinationAddress":
				in.beginObject();
				while (in.hasNext()) {
					nextName = in.nextName();
					switch (nextName) {
					case "SSN":
						builder.setDestSSN(in.nextInt());
						break;
					case "GlobalTitle":
						builder.setDestGTAddressInfo(JsonHelper.getStrValue(in, "AddressInformation"));
						break;	
					default:
						logger.error("Something strange was recieved: " + in.nextString());
						break;
					}
				}
				in.endObject();
				break;
			
			default:
				JsonHelper.baseMsgBuilder(nextName, in, builder);

				break;
			}
		}
		in.endObject();
		return builder.build();
	}

	public CapOpenTA() {	}
	
	

}
