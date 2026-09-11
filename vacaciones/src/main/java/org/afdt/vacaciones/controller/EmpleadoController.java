package org.afdt.vacaciones.controller;

import org.afdt.vacaciones.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmpleadoController {

	@GetMapping("/inicioempleado")
	public String inicioSesion(Model modelo) {
		Usuario nuevoUsuario = new Usuario();
		modelo.addAttribute("usuario", nuevoUsuario);
		return "Inicioemp";
	}
}
