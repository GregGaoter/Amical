package app.gaugiciel.amical.business.implementation.authentification;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.implementation.enumeration.NomModel;

@Service
public class ServiceLogoutSuccessHandler implements LogoutSuccessHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogoutSuccessHandler.class);

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		LOGGER.info("Start {}()", "onLogoutSuccess");
		HttpSession session = request.getSession();
		session.removeAttribute(NomModel.UTILISATEUR.label);
		response.sendRedirect("/authentification?deconnexion");
	}

}
