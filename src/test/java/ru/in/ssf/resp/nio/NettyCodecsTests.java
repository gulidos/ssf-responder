package ru.in.ssf.resp.nio;

import java.net.InetSocketAddress;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import ru.in.ssf.resp.camel.CapOpenRequest;
import ru.in.ssf.resp.json.JsonHelper;
import ru.in.ssf.resp.nio.CapToDatagrEncoder;
import ru.in.ssf.resp.nio.DatagramToCapDecoder;
import ru.in.ssf.resp.nio.echoserver.EchoServerHandler;
import ru.in.ssf.resp.nio.echoserver.StringToDtgrmEncoder;

public class NettyCodecsTests {
	static String testMessage;
	private static Gson gson;
	
	public NettyCodecsTests() {	}
	
	@BeforeClass
	public static void init(){
		gson = JsonHelper.getGson();
		testMessage = "This is a message for testing purposes"; 
	}
	
	@Test
	public void outgoingTest() {
		EmbeddedChannel channel = new EmbeddedChannel(new StringToDtgrmEncoder());

	    Assert.assertTrue(channel.writeOutbound(testMessage));
	    Assert.assertTrue(channel.finish());
	    
	    String read =  (String) channel.readOutbound();
	    Assert.assertEquals(testMessage, read);
	    System.out.println(read);
	}
	
	@Test
	public void incommingTest() {
		EmbeddedChannel channel = new EmbeddedChannel(new EchoServerHandler(null));
		DatagramPacket pkt = new DatagramPacket(
				Unpooled.copiedBuffer(testMessage, CharsetUtil.UTF_8), new InetSocketAddress("127.0.0.1", 80));
		Assert.assertTrue(channel.writeInbound(pkt));
		Assert.assertTrue(channel.finish());
		
		String read = (String) channel.readInbound();
		Assert.assertEquals(testMessage, read);
	    System.out.println(read);
	}
	
	@Test
	public void incommingCap() {
//		ChannelHandler[] handlers = new ChannelHandler[2];
//		handlers[0] = new DatagramToCapDecoder();
//		handlers[1] = new StringToCapDecoder();
		
		EmbeddedChannel channel = new EmbeddedChannel(new DatagramToCapDecoder());
		
		CapOpenRequest capMsg = new CapOpenRequest.Builder()
				.setCallId("asdf3r4fq34f")
				.setDialogId(123)
				.setInvokeId(2)
				.setDestGTAddressInfo("sdfsfdasdfasdf")
				.setDestSSN(1234)
				.build();
		String json = gson.toJson(capMsg);
		
		DatagramPacket pkt = new DatagramPacket(
				Unpooled.copiedBuffer(json, CharsetUtil.UTF_8), new InetSocketAddress("127.0.0.1", 80));

		Assert.assertTrue(channel.writeInbound(pkt));
		Assert.assertTrue(channel.finish());
		
		CapOpenRequest read = (CapOpenRequest) channel.readInbound();
		Assert.assertEquals(capMsg, read);
	}

	@Test
	public void outgoungCap() {
		EmbeddedChannel channel = new EmbeddedChannel(new CapToDatagrEncoder());
		CapOpenRequest capMsg = new CapOpenRequest.Builder()
				.setCallId("asdf3r4fq34f")
				.setDialogId(123)
				.setInvokeId(2)
				.setDestGTAddressInfo("sdfsfdasdfasdf")
				.setDestSSN(1234)
				.build();
		String json = gson.toJson(capMsg);
		ByteBuf writedBuf = Unpooled.copiedBuffer(json, CharsetUtil.UTF_8);
		
	    Assert.assertTrue(channel.writeOutbound(capMsg));
	    Assert.assertTrue(channel.finish());
	    
	    ByteBuf readedBuf = (ByteBuf) channel.readOutbound();
	    Assert.assertEquals(writedBuf, readedBuf);
	}
}
