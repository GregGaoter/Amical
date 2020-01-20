package app.gaugiciel.amical.configuration;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RepertoireNouveauPlanConvertionConfiguration implements Converter<String, File> {

	@Override
	public File convert(String property) {
		String[] repertoire = property.split(",");
		File racine = new File(System.getProperty(repertoire[0]));
		return new File(racine, repertoire[1]);
	}

}
