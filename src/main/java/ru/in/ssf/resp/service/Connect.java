package ru.in.ssf.resp.service;

import java.net.DatagramPacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.in.ssf.resp.camel.CapApplyChargingReport;
import ru.in.ssf.resp.camel.CapConnect;
import ru.in.ssf.resp.camel.CapErb;
import ru.in.ssf.resp.camel.CapInitialDP;
import ru.in.ssf.resp.camel.CapMessage;
import ru.in.ssf.resp.ipc.IpcServer;
import ru.in.ssf.resp.json.JsonHelper;

public class Connect implements Scenario {
	private static final Logger logger = LoggerFactory.getLogger(Connect.class);
	private final Calls calls;

	private final IpcServer listener;

	@Autowired
	public Connect(Calls c, IpcServer l) {
		this.listener = l;
		this.calls = c;

	}

	@Override
	public void processIdp(CapInitialDP idp, DatagramPacket pkt) {
		logger.debug("received: {}", idp.toString());
		
		CapConnect con = new CapConnect(idp.getInvokeId(), idp.getDialogId(), idp.getCallId(), "+886555110335", null);
		sendCapMsg(pkt, con);
		Call c = calls.checkCallId(idp);
		calls.removeCallId(c);
	}

	private void sendCapMsg(DatagramPacket pkt, CapMessage msg) {
		String resp = JsonHelper.getGson().toJson(msg);
		listener.sendResponseToPacket(pkt, resp);
		logger.debug("sent: {}", resp);
	}

	
	@Override
	public void processErb(CapErb erb, DatagramPacket pkt) {
		logger.debug("received: {}", erb.toString());
		
	}
	
	@Override
	public void processAcr(CapApplyChargingReport acr, DatagramPacket pkt) {
		logger.debug("received: {}", acr.toString());

	}
}
