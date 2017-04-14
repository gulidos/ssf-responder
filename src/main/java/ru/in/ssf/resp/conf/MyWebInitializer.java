package ru.in.ssf.resp.conf;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import ru.in.ssf.resp.web.WebConfig;


public class MyWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
	    return new Class<?>[] { WebConfig.class };

	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
