package app.gaugiciel.amical.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ResourceConfiguration implements WebMvcConfigurer {

	@Value("${url.serveur.plan}")
	private String urlServeurPlan;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/plan/**").addResourceLocations(urlServeurPlan);
	}

	@Bean
	public ClassLoaderTemplateResolver visiteurTemplateResolver() {
		return initialiseTemplateResolver("templates/visiteur/", 1);
	}

	@Bean
	public ClassLoaderTemplateResolver authentificationTemplateResolver() {
		return initialiseTemplateResolver("templates/authentification/", 2);
	}

	@Bean
	public ClassLoaderTemplateResolver amiTemplateResolver() {
		return initialiseTemplateResolver("templates/ami/", 3);
	}

	private ClassLoaderTemplateResolver initialiseTemplateResolver(String prefix, int order) {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix(prefix);
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(order);
		templateResolver.setCheckExistence(true);
		return templateResolver;
	}

}
