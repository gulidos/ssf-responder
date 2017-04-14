package ru.in.ssf.resp.nio;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import ru.in.ssf.resp.camel.CapInitialDP;
import ru.in.ssf.resp.camel.ICamelAble;
import ru.in.ssf.resp.conf.Settings;
import ru.in.ssf.resp.service.Calls;
import ru.in.ssf.resp.service.Connect;
import ru.in.ssf.resp.service.ContinueInterrupted;
import ru.in.ssf.resp.service.ContinueNotify;
import ru.in.ssf.resp.service.Scenario;

public class ProcessorTask implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ProcessorTask.class);
	private final Calls calls;
	private final TaskExecutor taskExecutor;
	private final BlockingQueue<ICamelAble> msgPool; 
	@Autowired Connect connect;
	@Autowired ContinueInterrupted cueInt;
	@Autowired ContinueNotify cueNotify;

	@Autowired
	public ProcessorTask(TaskExecutor taskExecutor, Calls calls) {
		msgPool = new LinkedBlockingQueue<ICamelAble>(Settings.UDP_MAX_QUEUE_SIZE); 
		this.calls = calls;
		this.taskExecutor = taskExecutor;
	}

	public void start() {
    	logger.info("Tasks initializing ...");
    	taskExecutor.execute(this);
    }
    
    
	@Override
	public void run() {
//		Thread.currentThread().setName("ProcessorTask-" + Thread.currentThread().getId());
//		try {
//			logger.info("started");
//			while (!Thread.currentThread().isInterrupted()) {
//				try {
//					ICamelAble capMsg = msgPool.take();
//					processPacket(capMsg);
//				} catch (InterruptedException e) {
//					logger.info("interrupted");
//					Thread.currentThread().interrupt();
//					break;
//				} catch (Exception e) {
//					String pktStr;
//					try {
//						pktStr = new String(pkt.getData(), 0, pkt.getLength());
//					} catch (Exception ex) {
//						pktStr = "[corrupted]";
//					}
//					logger.error("failed to process packet " + pktStr + e.getMessage(), e);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("failed to start processor " + e.toString(), e);
//		} finally {
//			logger.info("stopped");
//		}
	}

	private void processPacket(ICamelAble capMsg) throws Exception {
//
//		
//		switch (capMsg.getMsgType()) {
//		case CAP_INITIAL_DP:   
//			CapInitialDP idp = (CapInitialDP) capMsg;
//			Scenario sc = getScenario(idp);
//			calls.addCallId(new Call(idp, sc));
//			sc.processIdp(idp, pkt); 
//			break;
//		case CAP_ERB:  
//			Call c = calls.checkCallId(capMsg);
//			c.getScenario().processErb((CapErb) capMsg, pkt); 
//			break;
//		case CAP_APPLY_CHARGING_REPORT:  
//			c = calls.checkCallId(capMsg);
//			c.getScenario().processAcr((CapApplyChargingReport) capMsg, pkt); 
//			break;
//		default:
//			logger.debug("JSON: {}", json);
//			break;
//		}
	}

	private Scenario getScenario(CapInitialDP idp) {
		String dst = idp.getCalledNumber();
		String lastNumber = dst.substring(dst.length() -1);
		switch (lastNumber) {
		case "5": return connect;
		case "7": return cueInt;	
		default: return cueNotify;
		}
	}
}
