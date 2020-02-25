package app.gaugiciel.amical.business.implementation.cotation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.paukov.combinatorics3.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Cotation;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteTertiaire;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@NoArgsConstructor(onConstructor_ = { @Autowired })
@AllArgsConstructor(staticName = "creer")
@Getter
@Setter
@EqualsAndHashCode
public class ServiceCotationFrance implements Cotation {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCotationFrance.class);

	public static final List<ServiceCotationFrance> ALL_COTATIONS = Generator
			.cartesianProduct(CotationFranceUnitePrincipale.LABELS, CotationFranceUniteSecondaire.LABELS,
					CotationFranceUniteTertiaire.LABELS)
			.stream()
			.map(unites -> creer(CotationFranceUnitePrincipale.ofLabel(unites.get(0)),
					CotationFranceUniteSecondaire.ofLabel(unites.get(1)),
					CotationFranceUniteTertiaire.ofLabel(unites.get(2))))
			.collect(Collectors.toList());
	public static final int SIZE = ALL_COTATIONS.size();
	public static final ServiceCotationFrance COTATION_MIN = creer(CotationFranceUnitePrincipale.UNITE_MIN,
			CotationFranceUniteSecondaire.UNITE_MIN, CotationFranceUniteTertiaire.UNITE_MIN);
	public static final ServiceCotationFrance COTATION_MAX = creer(CotationFranceUnitePrincipale.UNITE_MAX,
			CotationFranceUniteSecondaire.UNITE_MAX, CotationFranceUniteTertiaire.UNITE_MAX);

	private CotationFranceUnitePrincipale unitePrincipale;
	private CotationFranceUniteSecondaire uniteSecondaire;
	private CotationFranceUniteTertiaire uniteTertiaire;

	@Override
	public String toString() {
		return unitePrincipale.label + uniteSecondaire.label + uniteTertiaire.label;
	}

	public void setUnites(CotationFranceUnitePrincipale unitePrincipale, CotationFranceUniteSecondaire uniteSecondaire,
			CotationFranceUniteTertiaire uniteTertiaire) {
		creer(unitePrincipale, uniteSecondaire, uniteTertiaire);
	}

	public static final List<ServiceCotationFrance> getBetween(ServiceCotationFrance cotationMin,
			ServiceCotationFrance cotationMax) {
		LOGGER.info("Start {}()", "getBetween");
		if (cotationMin.getUnitePrincipale().estVide() && !cotationMax.getUnitePrincipale().estVide()) {
			return ALL_COTATIONS.subList(0, ALL_COTATIONS.indexOf(cotationMax));
		}
		if (!cotationMin.getUnitePrincipale().estVide() && cotationMax.getUnitePrincipale().estVide()) {
			return ALL_COTATIONS.subList(ALL_COTATIONS.indexOf(cotationMin), SIZE - 1);
		}
		if (cotationMin.equals(cotationMax)) {
			return Stream.of(cotationMin).collect(Collectors.toList());
		}
		return ALL_COTATIONS.subList(ALL_COTATIONS.indexOf(cotationMin), ALL_COTATIONS.indexOf(cotationMax));
	}

}
