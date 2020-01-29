package app.gaugiciel.amical.business.implementation;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.gaugiciel.amical.business.contrat.ServiceEnregistrement;

@Service
public class ServiceEnregistrementPlanLocal implements ServiceEnregistrement<MultipartFile> {

	@Value(ServiceStockagePlan.REPERTOIRE_LOCAL_PLAN)
	private File repertoireLocalPlan;

	@Override
	public void enregistrer(MultipartFile multipartFile) {
		File fichierToSave = new File(repertoireLocalPlan + File.separator + multipartFile.getOriginalFilename());
		try {
			multipartFile.transferTo(fichierToSave);
		} catch (IllegalStateException | IOException e) {
		}
	}

}
