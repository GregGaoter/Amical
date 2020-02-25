package app.gaugiciel.amical.configuration;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RepertoireLocalPlanConvertionConfiguration implements Converter<String, File> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RepertoireLocalPlanConvertionConfiguration.class);

	@Override
	public File convert(String propriete) {
		LOGGER.info("Start {}()", "convert");
		return new File(propriete);
	}

}
