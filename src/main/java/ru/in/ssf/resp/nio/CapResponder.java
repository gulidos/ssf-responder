package ru.in.ssf.resp.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.Getter;
import ru.in.ssf.resp.nio.echoserver.EchoServer;

public class CapResponder {
	private static final Logger logger = LoggerFactory.getLogger(DatagramToCapDecoder.class);
	@Getter private final int port;
	@Getter private final int id;
	
	public CapResponder(int port, int id) {
		super();
		this.port = port;
		this.id = id;
	}

	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {			
			Bootstrap b = new Bootstrap()
			.group(group)
			.channel(NioDatagramChannel.class)
			
			.handler(new ChannelInitializer<DatagramChannel>() {
				@Override
				protected void initChannel(DatagramChannel ch) throws Exception {
					ch.pipeline()
					.addLast(new CapToDatagrEncoder())
					.addLast(new DatagramToCapDecoder());
					
					logger.info("inited on port: " + port);
				}
			});
			
			b.bind(port + 1).channel().closeFuture();
			b.bind(port).channel().closeFuture().await();
		} finally {
			group.shutdownGracefully().sync();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new EchoServer(9999).start();
	}
}
