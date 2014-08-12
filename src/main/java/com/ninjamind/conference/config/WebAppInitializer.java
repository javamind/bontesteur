package com.ninjamind.conference.config;

import com.ninjamind.conference.controller.SimpleCORSFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;


public class WebAppInitializer implements WebApplicationInitializer {

	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { ApplicationConfig.class };
	}

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic registration = null;

        //Chargement du contexte Spring
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(getRootConfigClasses());
        servletContext.addListener(new ContextLoaderListener(rootContext));

        //Definition d'un dispatcher de servlet
        AnnotationConfigWebApplicationContext javamindContext = new AnnotationConfigWebApplicationContext();
        javamindContext.register(WebMvcConfiguration.class);
        registration = servletContext.addServlet("javamind", new DispatcherServlet(javamindContext));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

        //Nous avons besoin de déclarer un Filter pour les requetes CORS
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("corsFilter", new SimpleCORSFilter());
        filterRegistration.addMappingForServletNames(
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE),
                true,
                "javamind"
        );
    }
}
