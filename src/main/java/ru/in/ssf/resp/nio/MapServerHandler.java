package ru.in.ssf.resp.nio;

import java.io.IOException;
import java.util.Random;

import org.springframework.util.Assert;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


@Sharable
public class MapServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
	private static final Random random = new Random();
	private final static String atsi = "(ATSI";
	private final static String imsi = "(SENDIMSI";
	private final static String ati = "(ATI_LAC"; 
	


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		String iStr = msg.content().toString(CharsetUtil.UTF_8);
		
		 ctx.write(new DatagramPacket(
                 Unpooled.copiedBuffer(getResponse(iStr), CharsetUtil.UTF_8), msg.sender()));
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		  ctx.flush();
	}
	
	public String getResponse(String req) throws IOException {
		Assert.hasLength(req);	
		if (req.startsWith(atsi)) {
			int number = Math.abs(random.nextInt()) % 10000000;
			return "(OK,sk,21,gt,7916" + number + ",dch,1)";
		} else if (req.startsWith(imsi)) {
			int number = Math.abs(random.nextInt()) % 10000000;
			return "(OK,imsi,25001111" + number + ")";
		} else if (req.startsWith(ati)) {			//(OK,vlr,79167494014,mcc,250,mnc,01,la,6330,cellid,1596)
			String vlr 	  = "7916" + String.valueOf(Math.abs(random.nextInt()) % 10000000);
			String cellid = String.valueOf(Math.abs(random.nextInt()) % 10000);
			String la	  = String.valueOf(Math.abs(random.nextInt()) % 10000);
			return "(OK,vlr," + vlr + ",mcc,250,mnc,01,la," + la +",cellid," + cellid + ")";
		} 
		else 
			return null;	
	}
}
