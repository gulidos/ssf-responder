package ru.in.ssf.resp.service;

import java.net.DatagramPacket;

import ru.in.ssf.resp.camel.CapApplyChargingReport;
import ru.in.ssf.resp.camel.CapErb;
import ru.in.ssf.resp.camel.CapInitialDP;

public interface Scenario {

	void processIdp(CapInitialDP idp, DatagramPacket pkt);
	void processErb(CapErb erb, DatagramPacket pkt);
	void processAcr(CapApplyChargingReport acr, DatagramPacket pkt);
}