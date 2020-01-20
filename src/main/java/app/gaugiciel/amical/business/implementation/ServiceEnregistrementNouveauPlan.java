package app.gaugiciel.amical.business.implementation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.gaugiciel.amical.business.contrat.ServiceEnregistrement;

@Service
public class ServiceEnregistrementNouveauPlan implements ServiceEnregistrement<MultipartFile> {

	@Value("#{repertoireNouveauPlanConvertionConfiguration.convert('${repertoire.plan}')}")
	private File repertoirePlan;

	@Override
	public void enregistrer(MultipartFile fichier) {
		if (repertoirePlan.mkdir() || repertoirePlan.isDirectory()) {
			try {
				fichier.transferTo(
						new File(Paths.get(repertoirePlan.getPath(), fichier.getOriginalFilename()).toString()));
			} catch (IllegalStateException | IOException e) {
			}
		}
	}

}
