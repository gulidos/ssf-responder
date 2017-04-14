package ru.in.ssf.resp.nio;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import ru.in.ssf.resp.conf.Settings;


public class MapServer {
	private static final Logger logger = LoggerFactory.getLogger(MapServer.class);
	private final Bootstrap mapBootstrap;
	private Channel serverChannel;
	
	public MapServer() {
		EventLoopGroup group = new NioEventLoopGroup();
		mapBootstrap =  new Bootstrap().group(group).channel(NioDatagramChannel.class)
				.handler(new ChannelInitializer<DatagramChannel>() {
					@Override
					protected void initChannel(DatagramChannel ch) throws Exception {
						ch.pipeline().addLast(new MapServerHandler());
					}
				});
	}

	@PostConstruct
	public void start() throws Exception {
		logger.info("initializing ...");
		serverChannel = mapBootstrap.bind(Settings.MAP_SERVER_PORT)
			.channel().closeFuture().await().sync().channel();
	}
	
	@PreDestroy
	public void stop() {
	    serverChannel.close();
	}
	
	public Channel getChannel() { return serverChannel;}
	
	public static void main(String[] args) throws Exception {
		new MapServer().start();
	}
}
