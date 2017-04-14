package ru.in.ssf.resp.service;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.in.ssf.resp.camel.CapApplyCharging;
import ru.in.ssf.resp.camel.CapApplyChargingReport;
import ru.in.ssf.resp.camel.CapContinue;
import ru.in.ssf.resp.camel.CapErb;
import ru.in.ssf.resp.camel.CapFCI;
import ru.in.ssf.resp.camel.CapInitialDP;
import ru.in.ssf.resp.camel.CapMessage;
import ru.in.ssf.resp.camel.CapRelease;
import ru.in.ssf.resp.camel.CapRrbe;
import ru.in.ssf.resp.camel.EventBCSM;
import ru.in.ssf.resp.camel.primitives.CallLeg;
import ru.in.ssf.resp.camel.primitives.DetectionPoint;
import ru.in.ssf.resp.camel.primitives.MonMode;
import ru.in.ssf.resp.ipc.IpcServer;
import ru.in.ssf.resp.json.JsonHelper;

public class ContinueInterrupted implements Scenario {
	private static final Logger logger = LoggerFactory.getLogger(ContinueInterrupted.class);

	private final Calls calls;
	private final IpcServer listener;

	@Autowired
	public ContinueInterrupted(Calls c, IpcServer l) {
		this.calls = c;		
		this.listener = l;
	}

	@Override
	public void processIdp(CapInitialDP idp, DatagramPacket pkt) {
		logger.debug("received: {}", idp.toString());
		
		List <EventBCSM> events = new ArrayList<>();
		events.add(new EventBCSM(MonMode.notifyAndContinue, CallLeg.sendingSide, DetectionPoint.oNoAnswer, 10));
		events.add(new EventBCSM(MonMode.notifyAndContinue, CallLeg.sendingSide, DetectionPoint.oDisconnect, 0));
		events.add(new EventBCSM(MonMode.notifyAndContinue, CallLeg.receivingSide, DetectionPoint.oDisconnect, 0));
		events.add(new EventBCSM(MonMode.notifyAndContinue, CallLeg.sendingSide, DetectionPoint.oAnswer, 0));
		events.add(new EventBCSM(MonMode.notifyAndContinue, CallLeg.sendingSide, DetectionPoint.oAbandon, 0));
		events.add(new EventBCSM(MonMode.notifyAndContinue, CallLeg.sendingSide, DetectionPoint.oCalledPartyBusy, 0));
		events.add(new EventBCSM(MonMode.notifyAndContinue, CallLeg.sendingSide, DetectionPoint.routeSelectFailure, 0));
		CapRrbe rrbe = new CapRrbe(idp.getInvokeId(), idp.getDialogId(), idp.getCallId(), events) ;
		sendCapMsg(pkt, rrbe);
		
		CapFCI fci = new CapFCI(idp.getInvokeId(), idp.getDialogId(), idp.getCallId(), "234234" , "free_format_data", CallLeg.sendingSide);
		sendCapMsg(pkt, fci);
		
		CapApplyCharging ac = new CapApplyCharging(idp.getInvokeId(), idp.getDialogId(), idp.getCallId(), CallLeg.sendingSide, 12000);
		sendCapMsg(pkt, ac);

		CapContinue cue = new CapContinue(idp.getInvokeId(), idp.getDialogId(), idp.getCallId());
		sendCapMsg(pkt, cue);
	}

	private void sendCapMsg(DatagramPacket pkt, CapMessage msg) {
		String resp = JsonHelper.getGson().toJson(msg);
		listener.sendResponseToPacket(pkt, resp);
		logger.debug("sent: {}", resp);
	}

	
	@Override
	public void processErb(CapErb erb, DatagramPacket pkt) {
		logger.debug("received: {}", erb.toString());
		switch (erb.getEvent()) {
		case oAnswer:
			CapContinue cue = new CapContinue(erb.getInvokeId(), erb.getDialogId(), erb.getCallId());
			sendCapMsg(pkt, cue);
			break;
		case oDisconnect:
			sendRel(erb, pkt, 16); break;
		case oNoAnswer:
			sendRel(erb, pkt, 19); break;	
		case oCalledPartyBusy:
			sendRel(erb, pkt, 17); break;
		case routeSelectFailure:
			sendRel(erb, pkt, 34); break;
		case oAbandon:
			sendRel(erb, pkt, 31); break;	//NORMAL_UNSPECIFIED
		default:
			break;
		}
		
		
	}

	private void sendRel(CapErb erb, DatagramPacket pkt, int cause) {
		Call c = calls.checkCallId(erb);
		calls.removeCallId(c);
		CapRelease rel = new CapRelease(erb.getInvokeId(), erb.getDialogId(), erb.getCallId(), cause);
		String resp = JsonHelper.getGson().toJson(rel);
		listener.sendResponseToPacket(pkt, resp);
		logger.debug("sent: {}", resp);
	}

	
	@Override
	public void processAcr(CapApplyChargingReport acr, DatagramPacket pkt) {
		
	}
}
