package ru.in.ssf.resp.nio.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToByteEncoder;

public class StringToDtgrmEncoder extends MessageToByteEncoder<DatagramPacket> {
	
	public StringToDtgrmEncoder() {	}

	@Override
	protected void encode(ChannelHandlerContext ctx, DatagramPacket pkt, ByteBuf out) throws Exception {
		ctx.write(pkt);	
	}

}
