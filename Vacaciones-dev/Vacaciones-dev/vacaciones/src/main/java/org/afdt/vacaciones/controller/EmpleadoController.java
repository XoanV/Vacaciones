package org.afdt.vacaciones.controller;

import org.afdt.vacaciones.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmpleadoController {

	@GetMapping("/paginaInicio")
	public String Inicio(Model mod) {
		Usuario nuevoUsuario = new Usuario();
		mod.addAttribute("usuario", nuevoUsuario);
		return "Inicioemp";
	}
	
	@PostMapping("/paginaInicio")
	public String pagInicio(Model modelo, HttpSession sesion) {
		String pagina = "";
		Usuario nuevoUsuario = new Usuario();
		if (nuevoUsuario.getTipoUsuario().name().equals("EMPLEADO")) {
			sesion.setAttribute("usuario", nuevoUsuario);
			pagina = "Inicioemp";
		} else if (nuevoUsuario.getTipoUsuario().name().equals("GESTOR")) {
			sesion.setAttribute("usuario", nuevoUsuario);
			pagina = "Iniciogestor";
		}
		return pagina;
	}
	
	@GetMapping("/perfilUsuario")
	public String irMiPerfil(Model modelo) {
		Usuario nuevoUsuario = new Usuario();
		modelo.addAttribute("usuario", nuevoUsuario);
		return "perfil";
	}
}
