package app.gaugiciel.amical.business.implementation.cotation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.paukov.combinatorics3.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceCotation;
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
public class ServiceCotationFrance implements ServiceCotation {

	public static final List<ServiceCotationFrance> ALL_COTATIONS = Generator
			.cartesianProduct(ServiceCotationFranceUnitePrincipale.LABELS, ServiceCotationFranceUniteSecondaire.LABELS,
					ServiceCotationFranceUniteTertiaire.LABELS)
			.stream()
			.map(unites -> creer(ServiceCotationFranceUnitePrincipale.ofLabel(unites.get(0)),
					ServiceCotationFranceUniteSecondaire.ofLabel(unites.get(1)),
					ServiceCotationFranceUniteTertiaire.ofLabel(unites.get(2))))
			.collect(Collectors.toList());
	public static final int SIZE = ALL_COTATIONS.size();
	public static final ServiceCotationFrance COTATION_MIN = creer(ServiceCotationFranceUnitePrincipale.UNITE_MIN,
			ServiceCotationFranceUniteSecondaire.UNITE_MIN, ServiceCotationFranceUniteTertiaire.UNITE_MIN);
	public static final ServiceCotationFrance COTATION_MAX = creer(ServiceCotationFranceUnitePrincipale.UNITE_MAX,
			ServiceCotationFranceUniteSecondaire.UNITE_MAX, ServiceCotationFranceUniteTertiaire.UNITE_MAX);

	private ServiceCotationFranceUnitePrincipale unitePrincipale;
	private ServiceCotationFranceUniteSecondaire uniteSecondaire;
	private ServiceCotationFranceUniteTertiaire uniteTertiaire;

	@Override
	public String toString() {
		return unitePrincipale.label + uniteSecondaire.label + uniteTertiaire.label;
	}

	public void setUnites(ServiceCotationFranceUnitePrincipale unitePrincipale,
			ServiceCotationFranceUniteSecondaire uniteSecondaire, ServiceCotationFranceUniteTertiaire uniteTertiaire) {
		creer(unitePrincipale, uniteSecondaire, uniteTertiaire);
	}

	public static final List<ServiceCotationFrance> getBetween(ServiceCotationFrance cotationMin,
			ServiceCotationFrance cotationMax) {
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
