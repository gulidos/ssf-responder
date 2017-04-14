package ru.in.ssf.resp.ipc;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import ru.in.ssf.resp.conf.Settings;


public class IpcServer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(IpcServer.class);
    private volatile boolean enabled = true;
    private final BlockingQueue<DatagramPacket> msgPool; 
    private final ConcurrentMap<Integer, Long> msgTime; 
    private final TaskExecutor taskExecutor;
    private final IpcSocket udpSocket;
    
    
    @Autowired
	public IpcServer(TaskExecutor taskExecutor, IpcSocket socket) {
		msgPool = new LinkedBlockingQueue<DatagramPacket>(Settings.UDP_MAX_QUEUE_SIZE); 
		msgTime = new ConcurrentHashMap<>();
		this.udpSocket = socket;
		this.taskExecutor = taskExecutor;
	}
	
    public void start() {
    	logger.info("Tasks initializing ...");
    	taskExecutor.execute(this);
    }
    
    public void stop () {
    	logger.info("Tasks finalizing...");
    	enabled = false;
    }
    
	@Override
	public void run() {
		Thread.currentThread().setName("UdpListener-" + Thread.currentThread().getId());
		logger.info("started");
		while (enabled) {
			DatagramPacket pkt = udpSocket.receive();
			if (pkt != null) {
				long t1 = System.nanoTime();
				msgTime.put(pkt.hashCode(), t1);
				if (!msgPool.offer(pkt)) {
					ArrayList<DatagramPacket> dropped = new ArrayList<DatagramPacket>(Settings.UDP_MAX_QUEUE_SIZE);
					int dropCount = msgPool.drainTo(dropped) + 1;

					logger.error("udp queue overflow. drop packets: " + dropCount);

					for (DatagramPacket p : dropped) {
						logger.warn("dropped packet: " + Arrays.toString(p.getData()));
					}
				}
			}
		}
	}
	

	public DatagramPacket getPacket() throws InterruptedException {
		return msgPool.take();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public synchronized void sendResponseToPacket(DatagramPacket pkt, String resp) {
		if (udpSocket.sendResponse(pkt, resp)) {
			
        }  

    }


    public void stopServer() {
        enabled = false;
        udpSocket.close();
        logger.info("stopped");
    }

	public int getMsgPoolSize() {return msgPool.size();	}

	public int getMsgTimeSize() {return msgTime.size();	}

}
