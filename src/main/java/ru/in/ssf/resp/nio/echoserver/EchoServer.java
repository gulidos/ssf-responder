package ru.in.ssf.resp.nio.echoserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;


public class EchoServer {
	private final int port;

	public EchoServer(int port) {
		this.port = port;
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
					.addLast(new StringToDtgrmEncoder())
					.addLast(new EchoServerHandler(ch));
					
					System.out.println("inited");
				}
			});
			
			System.out.println("Echo server started  on port " + port);

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
