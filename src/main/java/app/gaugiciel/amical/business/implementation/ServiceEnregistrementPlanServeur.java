package app.gaugiciel.amical.business.implementation;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.gaugiciel.amical.business.contrat.ServiceEnregistrement;

@Service
public class ServiceEnregistrementPlanServeur implements ServiceEnregistrement<MultipartFile> {

	@Value(ServiceStockagePlan.URL_SERVEUR_PLAN)
	private URL urlServeurPlan;

	@Override
	public void enregistrer(MultipartFile fichier) {
		// try {
		// URL url = new URL(urlServeurPlan + fichier.getOriginalFilename());
		// } catch (MalformedURLException e) {
		// }
		// fichier.transferTo(url.getFile());

		// HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		// MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		// String fileName = fichier.getOriginalFilename();
		// File convFile = new File(System.getProperty("java.io.tmpdir") +
		// File.separator + fileName);
		// System.out.println("convFile.getAbsolutePath() : " +
		// convFile.getAbsolutePath());
		// try {
		// fichier.transferTo(convFile);
		// } catch (IllegalStateException | IOException e) {
		// }
		// body.add("file", new FileSystemResource(convFile));
		// HttpEntity<MultiValueMap<String, Object>> requestEntity = new
		// HttpEntity<>(body, headers);
		// HttpEntity<File> requestEntity = new HttpEntity<>(convFile);
		// String serverUrl = "http://localhost:80/img/plan/";
		// URL url=new URL("http://localhost:80/img/plan/");
		// try {
		// URI uri = new URI("http://localhost:80/img/plan/");
		// RestTemplate restTemplate = new RestTemplate();
		// restTemplate.postForLocation(uri, requestEntity);
		// } catch (URISyntaxException e) {
		// }
		// ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, //
		// requestEntity, String.class);
	}

}
