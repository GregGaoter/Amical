package app.gaugiciel.amical.business.implementation.authentification;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.implementation.model.ServiceModel;

@Service
public class ServiceLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.removeAttribute(ServiceModel.UTILISATEUR.label);
		response.sendRedirect("/authentification?deconnexion");
	}

}
