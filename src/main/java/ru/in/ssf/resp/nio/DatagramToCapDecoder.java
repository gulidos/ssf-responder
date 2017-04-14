package ru.in.ssf.resp.nio;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import ru.in.ssf.resp.camel.ICamelAble;
import ru.in.ssf.resp.json.JsonHelper;

@Sharable
public class DatagramToCapDecoder extends MessageToMessageDecoder<DatagramPacket> {
	private static final Logger logger = LoggerFactory.getLogger(DatagramToCapDecoder.class);

	public DatagramToCapDecoder() {
		super();
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket pkt, List<Object> out) throws Exception {
		String msg = pkt.content().toString(CharsetUtil.UTF_8);
		ICamelAble capMsg = JsonHelper.getCapMsg(msg);
		capMsg.setSender(pkt.sender());
		out.add(capMsg);
	}
	
	   @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        logger.error(cause.getMessage(), cause);
	    }
}
