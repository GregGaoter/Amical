package fr.lesamisdelescalade.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MaquetteController {

	@RequestMapping(value = "/maquette_login")
	public String maquetteLogin() {
		return "maquette_login";

	}
}
