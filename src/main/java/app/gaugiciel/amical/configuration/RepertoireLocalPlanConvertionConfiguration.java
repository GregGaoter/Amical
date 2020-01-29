package app.gaugiciel.amical.configuration;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RepertoireLocalPlanConvertionConfiguration implements Converter<String, File> {

	@Override
	public File convert(String propriete) {
		return new File(propriete);
	}

}
