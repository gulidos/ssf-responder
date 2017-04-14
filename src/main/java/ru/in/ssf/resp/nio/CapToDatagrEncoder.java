package ru.in.ssf.resp.nio;

import com.google.gson.Gson;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import lombok.Getter;
import ru.in.ssf.resp.camel.ICamelAble;
import ru.in.ssf.resp.json.JsonHelper;


public class CapToDatagrEncoder extends MessageToByteEncoder<ICamelAble> {
	private final Gson gson;

	@Getter private int sentPackets = 0;

	public CapToDatagrEncoder() {
		super();
		this.gson = JsonHelper.getGson();
	}


	@Override
	protected void encode(ChannelHandlerContext ctx, ICamelAble msg, ByteBuf out) throws Exception {
		out.writeBytes(Unpooled.copiedBuffer(gson.toJson(msg), CharsetUtil.UTF_8));
		sentPackets++;
	}

}
