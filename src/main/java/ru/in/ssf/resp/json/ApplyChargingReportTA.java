package ru.in.ssf.resp.json;

import static ru.in.ssf.resp.json.JsonHelper.baseMsgReader;
import static ru.in.ssf.resp.json.JsonHelper.getIntValue;
import static ru.in.ssf.resp.json.JsonHelper.getStrValue;
import static ru.in.ssf.resp.json.JsonHelper.writeCamelMsg;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ru.in.ssf.resp.camel.CapApplyChargingReport;
import ru.in.ssf.resp.camel.CapApplyChargingReport.TimeInformation;
import ru.in.ssf.resp.camel.primitives.CallLeg;



public class ApplyChargingReportTA extends TypeAdapter<CapApplyChargingReport> {

	@Override
	public void write(JsonWriter out, CapApplyChargingReport msg) throws IOException {
		out.beginObject();
			out.name(msg.getMsgType().toString());
			out.beginObject();
				writeCamelMsg(out, msg);
				out.name("arg");
				out.beginObject();
					out.name("stringNumber").value(msg.getStringNumber());
				out.endObject();

				out.name("result");
				out.beginObject();
					out.name("partyToCharge");
					out.beginObject();
						out.name("side").value(msg.getPartyToCharge().getChoice());
					out.endObject();
					out.name("timeInformation");
					out.beginObject();
						out.name(msg.getTimeInformationType().toString()).value(msg.getTimeInformation());
					out.endObject();
					out.name("active").value(msg.isCallAactive());
				out.endObject();
			out.endObject();
		out.endObject();
	}

	
	@Override
	public CapApplyChargingReport read(JsonReader in) throws IOException {
		CapApplyChargingReport result = new CapApplyChargingReport();
//		CapApplyChargingReport.Builder result = new CapApplyChargingReport.Builder();
		in.beginObject();
		while (in.hasNext()) {
			String nextName = in.nextName();
			switch (nextName) {
			case "arg":
				result.setStringNumber(getStrValue(in, "stringNumber"));
				break;
			case "result":
				getResult(in, result);
				break;
			default:
				baseMsgReader(nextName, in, result);
				break;
			}
		}
		in.endObject();
		return result;
	}

	private void getResult(JsonReader in, CapApplyChargingReport result) throws IOException {
		in.beginObject();
		while (in.hasNext()) {
			switch (in.nextName()) {
			case "partyToCharge":
				result.setPartyToCharge(CallLeg.getInstance(getIntValue(in, "side")));
				break;
			case "timeInformation":
				in.beginObject();
				switch (in.nextName()) {
				case "timeIfNoTariffSwitch":
					result.setTimeInformationType(TimeInformation.timeIfNoTariffSwitch);
					result.setTimeInformation(in.nextInt());
					break;
				case "timeSinceTariffSwitch":
					result.setTimeInformationType(TimeInformation.timeSinceTariffSwitch);
					result.setTimeInformation(in.nextInt());							
					break;
				case "tariffSwitchInterval":
					result.setTimeInformationType(TimeInformation.tariffSwitchInterval);
					result.setTimeInformation(in.nextInt());							
					break;
				default:
					break;
				}
				in.endObject();
				break;
			case "active":
				result.setCallAactive(in.nextBoolean());
				break;
			default:
				in.skipValue();
				break;
			}
		}
		in.endObject();
	}

	public ApplyChargingReportTA() {	}
	
	

}
