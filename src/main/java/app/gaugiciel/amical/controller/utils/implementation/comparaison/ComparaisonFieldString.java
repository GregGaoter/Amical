package app.gaugiciel.amical.controller.utils.implementation.comparaison;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import app.gaugiciel.amical.controller.utils.contrat.ComparaisonField;

@Component
public class ComparaisonFieldString implements ComparaisonField<String> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComparaisonFieldString.class);

	@Override
	public int comparer(String str1, String str2) {
		String txt1 = StringUtils.stripAccents(str1);
		String txt2 = StringUtils.stripAccents(str2);
		return txt1.compareToIgnoreCase(txt2);
	}

}
