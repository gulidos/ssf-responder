package ru.in.ssf.resp.ipc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.in.ssf.resp.conf.Settings;


public class IpcSocket {
	private static final Logger logger = LoggerFactory.getLogger(IpcSocket.class);

	private DatagramSocket sock;

	public IpcSocket() throws UnknownHostException, SocketException {
		logger.info("opening socket on: " + Settings.CAMEL_SERVER_PORT_1 + " host=" + Settings.UDP_SERVER_HOST);
		try {
			sock = new DatagramSocket(Settings.CAMEL_SERVER_PORT_1, InetAddress.getByName(Settings.UDP_SERVER_HOST));
			logger.info("listening, UDP port=" + Settings.CAMEL_SERVER_PORT_1 + " host=" + Settings.UDP_SERVER_HOST);
		} catch (UnknownHostException uhe) {
			logger.error("unknon host for UPD listening: " + Settings.CAMEL_SERVER_PORT_1, uhe);
			throw new UnknownHostException ("unknon host for UPD listening: " + Settings.CAMEL_SERVER_PORT_1);
		} catch (SocketException e) {
			logger.error("SocketException listening on UDP port " + Settings.CAMEL_SERVER_PORT_1, e);
			throw new SocketException("SocketException listening on UDP port " + Settings.CAMEL_SERVER_PORT_1);
		}
	}

	public void close() {
		sock.close();
		logger.info("closed");
	}

	public DatagramPacket receive() {
		byte[] data = new byte[Settings.UDP_MESSAGE_MAX_SIZE];
		DatagramPacket pkt = new DatagramPacket(data, data.length);

		try {
			sock.receive(pkt);
			return pkt;
		} catch (IOException e) {
			if (e.getMessage().equalsIgnoreCase("Socket closed")) {
				if (logger.isDebugEnabled())
					logger.debug("Socket closed");
			} else {
				logger.error("IOException on UDP receive", e);
			}
		}
		return null;
	}

	public boolean sendResponse(DatagramPacket pktIn, String resp) {

		try {
			InetAddress addr = pktIn.getAddress();
			int port = pktIn.getPort();
			byte[] buf = resp.getBytes();
			DatagramPacket pkt = new DatagramPacket(buf, buf.length, addr, port);

			sock.send(pkt);
//			if (logger.isDebugEnabled())
//				logger.debug("sent msg=" + new String(pkt.getData()));
			return true;

		} catch (IOException ioe) {
			logger.error("IOException on UDP send msg=" + resp, ioe);
		} catch (Exception e) {
			logger.error("Exception on UDP send msg=" + resp, e);
		}
		return false;
	}
}
