package app.gaugiciel.amical.business.implementation.enregistrement;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.stockage.ServiceStockagePlan;

@Service
public class ServiceEnregistrementPlanServeur implements Enregistrement<MultipartFile> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnregistrementPlanServeur.class);

	@Value(ServiceStockagePlan.URL_SERVEUR_PLAN)
	private URL urlServeurPlan;

	@Override
	public void enregistrer(MultipartFile fichier) {
		LOGGER.info("Start {}()", "enregistrer");
		// try {
		// URL url = new URL(urlServeurPlan + fichier.getOriginalFilename());
		// } catch (MalformedURLException e) {
		// }
		// fichier.transferTo(url.getFile());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		// MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		String fileName = fichier.getOriginalFilename();
		File convFile = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);
		try {
			fichier.transferTo(convFile);
		} catch (IllegalStateException | IOException e) {
		}
		// body.add("file", new FileSystemResource(convFile));
		// HttpEntity<MultiValueMap<String, Object>> requestEntity = new
		// HttpEntity<>(body, headers);
		HttpEntity<File> requestEntity = new HttpEntity<>(convFile);
		// String serverUrl = "http://localhost:80/img/plan/";
		// URL url=new URL("http://localhost:80/img/plan/");
		try {
			URI uri = new URI("http://localhost:80/img/plan/");
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForLocation(uri, requestEntity);
		} catch (URISyntaxException e) {
		}
		// ResponseEntity<String> response = restTemplate.postForEntity(serverUrl,
		// requestEntity, String.class);
	}

}
