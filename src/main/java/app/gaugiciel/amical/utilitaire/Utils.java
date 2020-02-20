package app.gaugiciel.amical.utilitaire;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

public abstract class Utils {

	@Value("/img/plan/")
	private String cheminPlan;
	@Value("#{repertoireNouveauPlanConvertionConfiguration.convert('${repertoire.plan}').getAbsolutePath()}")
	private String cheminPlanExterne;
	public static final int AUTO_COMPLETE_MAX_RESULTS = 10;

	public String getCheminPlan() {
		return cheminPlan;
	}

	public String getCheminPlanExterne() {
		return cheminPlanExterne;
	}

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

	public static final String formaterTimestamp(Timestamp timestamp) {
		LocalDateTime localDateTime = timestamp.toLocalDateTime();
		int date = localDateTime.getDayOfMonth();
		String dateStr = date < 10 ? "0" + date : String.valueOf(date);
		int mois = localDateTime.getMonthValue();
		String moisStr = mois < 10 ? "0" + mois : String.valueOf(mois);
		String anneeStr = String.valueOf(localDateTime.getYear());
		int heure = localDateTime.getHour();
		String heureStr = heure < 10 ? "0" + heure : String.valueOf(heure);
		int minute = localDateTime.getMinute();
		String minuteStr = minute < 10 ? "0" + minute : String.valueOf(minute);
		int seconde = localDateTime.getSecond();
		String secondeStr = seconde < 10 ? "0" + seconde : String.valueOf(seconde);
		return String.format("%1$s.%2$s.%3$s à %4$s:%5$s:%6$s", dateStr, moisStr, anneeStr, heureStr, minuteStr,
				secondeStr);
	}

}
