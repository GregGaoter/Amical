package app.gaugiciel.amical.business.contrat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public interface ServiceLogin {
	
	public void login(HttpServletRequest request);

}
