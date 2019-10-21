package app.gaugiciel.amical.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InscriptionController {

	@RequestMapping(value = "/inscription")
	public String inscription() {
		return "inscription";
	}

}
