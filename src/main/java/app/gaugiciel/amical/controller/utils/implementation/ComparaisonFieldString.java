package app.gaugiciel.amical.controller.utils.implementation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import app.gaugiciel.amical.controller.utils.contrat.ComparaisonField;

@Component
public class ComparaisonFieldString implements ComparaisonField<String> {

	@Override
	public int comparer(String str1, String str2) {
		String txt1 = StringUtils.stripAccents(str1);
		String txt2 = StringUtils.stripAccents(str2);
		return txt1.compareToIgnoreCase(txt2);
	}

}
