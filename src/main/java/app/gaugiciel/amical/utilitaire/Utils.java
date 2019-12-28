package app.gaugiciel.amical.utilitaire;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public abstract class Utils {

	public static final String CHEMIN_PLAN = "/img/plan/";
	public static final int AUTO_COMPLETE_MAX_RESULTS = 10;

	public static final String normaliser(String str) {
		return StringUtils.stripAccents(str.toUpperCase());
	}

	public static final boolean isValid(Object obj) {
		return !Objects.isNull(obj);
	}

	public static final boolean isValid(String str) {
		return str != null && str.strip().length() > 0;
	}

	public static final boolean isValid(List<Object> objs) {
		return !Objects.isNull(objs) && !objs.isEmpty();
	}

	public static final boolean isValid(Integer i1, Integer i2) {
		return !Objects.isNull(i1) && !Objects.isNull(i2);
	}

	public static final boolean isValid(Integer i) {
		return !Objects.isNull(i);
	}

	public static final boolean isValid(Long l) {
		return !Objects.isNull(l);
	}

}
