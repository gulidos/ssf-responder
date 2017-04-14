package ru.in.ssf.resp.camel;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import ru.in.ssf.resp.camel.CapErb;
import ru.in.ssf.resp.camel.CapOpenRequest;
import ru.in.ssf.resp.camel.CapRelease;
import ru.in.ssf.resp.camel.primitives.CallLeg;
import ru.in.ssf.resp.camel.primitives.DetectionPoint;
import ru.in.ssf.resp.json.JsonHelper;
import ru.in.ssf.resp.json.primitives.CapMessageWrapper;

public class JsonCodecTests {
	private static Gson gson;
	public JsonCodecTests() {	}
	
	@BeforeClass
	public static void initGson() {
		gson = JsonHelper.getGson();
	}

	@Test
	public void erbTest() {
		CapErb erb = new CapErb(1, 22, "dsasdfa", DetectionPoint.oNoAnswer, CallLeg.receivingSide);
		String json = gson.toJson(erb);
		CapMessageWrapper msg = JsonHelper.getMessageType(json);
		CapErb response = gson.fromJson(msg.getJson(), CapErb.class);
		Assert.assertEquals(erb, response);
	}
	
	@Test
	public void relTest() {
		CapRelease rel = new CapRelease(1, 22, "dsasdfa", 16);
		String json = gson.toJson(rel);
		CapMessageWrapper msg = JsonHelper.getMessageType(json);
		CapRelease response = gson.fromJson(msg.getJson(), CapRelease.class);
		Assert.assertEquals(rel, response);
	}
	
	@Test
	public void openTest() {
		CapOpenRequest open = new CapOpenRequest.Builder()
				.setCallId("asdf3r4fq34f")
				.setDialogId(123)
				.setInvokeId(2)
				.setDestGTAddressInfo("sdfsfdasdfasdf")
				.setDestSSN(1234)
				.build();
		String json = gson.toJson(open);
		CapMessageWrapper msg = JsonHelper.getMessageType(json);
		CapOpenRequest response = gson.fromJson(msg.getJson(), CapOpenRequest.class);
		Assert.assertEquals(open, response);
	}
}
