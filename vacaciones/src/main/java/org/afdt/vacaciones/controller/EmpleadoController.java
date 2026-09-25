package org.afdt.vacaciones.controller;

import org.afdt.vacaciones.model.EstadoPeticion;
import org.afdt.vacaciones.model.PeticionVacaciones;
import org.afdt.vacaciones.model.Usuario;
import org.afdt.vacaciones.model.facade.UsuarioFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmpleadoController {
	
	@Autowired
	private UsuarioFacade fachada;

	@GetMapping("/realizarPeticion")
	public String peticion(Model mod) {
		PeticionVacaciones petV = new PeticionVacaciones();
		mod.addAttribute("peticion", petV);
		return "Realizarpet";
	}
	
	@PostMapping("/realizarPeticion")
	public String petVacaciones(Model modelo, HttpSession sesion, @ModelAttribute("peticion") PeticionVacaciones peticionVacaciones, @RequestParam(name = "anho") String ano) {
		if (ano == null || ano.isBlank()) {
			modelo.addAttribute("errorR", "El año no puede estar vacío.");
			return "Realizarpet";
		} else {
			if (!ano.matches("\\d+")) {
				modelo.addAttribute("errorR", "El año no puede ser texto.");
				return "Realizarpet";
			} else {
				Usuario usu = (Usuario) sesion.getAttribute("usuario");
				int anho = Integer.parseInt(ano);
				peticionVacaciones.setAño(anho);
				peticionVacaciones.setEstado(EstadoPeticion.PENDIENTE);
				peticionVacaciones.setUsuario(usu);
				PeticionVacaciones pendiente = fachada.altaPeticion(peticionVacaciones);
				modelo.addAttribute("peticionVacacionesPendientes", pendiente);
				return "Inicioemp";
			}
		}
	}
}
