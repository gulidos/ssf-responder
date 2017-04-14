package ru.in.ssf.resp.conf;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import ru.in.ssf.resp.ipc.IpcServer;
import ru.in.ssf.resp.ipc.IpcSocket;
import ru.in.ssf.resp.ipc.ProcessorTask;
import ru.in.ssf.resp.service.Calls;
import ru.in.ssf.resp.service.Connect;
import ru.in.ssf.resp.service.ContinueInterrupted;
import ru.in.ssf.resp.service.ContinueNotify;


@Configuration
//@EnableCaching
//@EnableAsync
//@EnableScheduling
// @ComponentScan(basePackages = { "ru.rik.cardsnew" }, excludeFilters = {
// @Filter(type = FilterType.CUSTOM, value = WebPackage.class) })

public class RootConfig implements SchedulingConfigurer {
	

	public static class WebPackage extends RegexPatternTypeFilter {
		public WebPackage() {
			super(Pattern.compile("ru.rik.cardsnew\\.web"));
		}
	}

	
	@Bean
    public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(3);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("MyExecutor-");
        executor.initialize(); 
        return executor;
    }
	
	@Bean(destroyMethod="shutdown")
    public TaskExecutor taskSheduleExecutor() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("MyScheduler-");
		scheduler.setPoolSize(5);
        return scheduler;
    }

//	@Bean
//	public CompletionService<Profile> completionService() {
//		CompletionService<Profile> service = new ExecutorCompletionService<Profile>(taskExecutor());
//		return service;
//	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskSheduleExecutor());
	}
	
	@Bean 
	public IpcSocket ipcSocket() throws UnknownHostException, SocketException {
		return new IpcSocket();
	}
	
	@Bean(initMethod="start", destroyMethod="stop")
	public IpcServer ipcServer() throws UnknownHostException, SocketException {
	 return new IpcServer(taskExecutor(), ipcSocket());		
	}
	
	@Bean public Calls calls() {return new Calls();}
	
	@Bean public Connect connect() throws UnknownHostException, SocketException {
		return new Connect(calls(), ipcServer());
	}
	
	@Bean public ContinueInterrupted cueInt() throws UnknownHostException, SocketException {
		return new ContinueInterrupted(calls(), ipcServer());
	}
	
	@Bean public ContinueNotify cueNotify() throws UnknownHostException, SocketException {
		return new ContinueNotify(calls(), ipcServer());
	}
	@Bean(initMethod="start")
	public ProcessorTask processorTask() throws UnknownHostException, SocketException {
		return new ProcessorTask(taskExecutor(), ipcServer(), calls());
	}
	
	
	// ============= netty servers ====
	
//	@Bean public MapServer mapServer() {return new MapServer();}
	
}
