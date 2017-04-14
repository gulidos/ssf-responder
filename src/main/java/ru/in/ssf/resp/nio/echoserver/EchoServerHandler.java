package ru.in.ssf.resp.nio.echoserver;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;


@Sharable
public class EchoServerHandler extends MessageToMessageDecoder<DatagramPacket> {
	private final Random rnd;
	private final Timer timer;
	private final DatagramChannel ch;
	
	public EchoServerHandler(DatagramChannel ch) {
		super();
		this.rnd = new Random();
		this.timer = new HashedWheelTimer();
		this.ch = ch;
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
		String str = msg.content().toString(CharsetUtil.UTF_8);
		int delay = 50; 
		out.add(str);
		TimerTask tt = new AnswerHandler(str, msg.sender());
		timer.newTimeout(tt, delay, TimeUnit.MILLISECONDS);
	}


	
	public class AnswerHandler implements TimerTask {
		private final String msg;
		private final InetSocketAddress addr;
		
		public AnswerHandler(String msg, InetSocketAddress a) {
			super();
			this.msg = msg;
			this.addr = a;
		}

		@Override
		public void run(Timeout timeout) throws Exception {
			ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8), addr));
		}		
	}


}
