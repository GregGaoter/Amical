package app.gaugiciel.amical.business.implementation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceComparaison;

@Service
public class ServiceComparaisonString implements ServiceComparaison<String> {

	@Override
	public int comparer(String arg1, String arg2) {
		String txt1 = StringUtils.stripAccents(arg1);
		String txt2 = StringUtils.stripAccents(arg2);
		return txt1.compareToIgnoreCase(txt2);
	}

}
