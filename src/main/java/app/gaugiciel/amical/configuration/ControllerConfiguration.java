package app.gaugiciel.amical.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryVoie;

@Configuration
public class ControllerConfiguration implements WebMvcConfigurer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerConfiguration.class);

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/authentification").setViewName("authentification");
	}

}
