package app.gaugiciel.amical.configuration;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class UrlPlanConvertionConfiguration implements Converter<String, URL> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlPlanConvertionConfiguration.class);

	@Override
	public URL convert(String propriete) {
		try {
			return new URL(propriete);
		} catch (MalformedURLException e) {
			return null;
		}
	}

}
