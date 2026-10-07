package org.afdt.vacaciones.controller;

import java.util.List;

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
	public String petVacaciones(Model modelo, HttpSession sesion,
			@ModelAttribute("peticion") PeticionVacaciones peticionVacaciones,
			@RequestParam(name = "anho") String ano) {
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
				if (peticionVacaciones.getComentario().isBlank() || peticionVacaciones.getComentario() == null) {
					peticionVacaciones.setComentario(null);
				}
				peticionVacaciones.setEstado(EstadoPeticion.PENDIENTE);
				peticionVacaciones.setUsuario(usu);
				fachada.altaPeticion(peticionVacaciones);
				List<PeticionVacaciones> peticionesPend = fachada.buscarPeticiones(usu, EstadoPeticion.PENDIENTE);
				modelo.addAttribute("peticionVacacionesPendientes", peticionesPend);
				return "Inicioemp";
			}
		}
	}

	@GetMapping("/buscarAnhoEstado")
	public String buscarAnho(Model modelo, HttpSession sesion) {

		Usuario nuevoUsuario = (Usuario) sesion.getAttribute("usuario");

		modelo.addAttribute("usuario", nuevoUsuario);
		modelo.addAttribute("estados", EstadoPeticion.values());

		return "AnhoEstado";
	}

	@PostMapping("/buscarAnhoEstado")
	public String buscarPeticiones(@RequestParam(name = "anho", required = false) String año, @RequestParam(name = "estado", required = false) EstadoPeticion estado, Model modelo, HttpSession sesion) {
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		Integer anho = null;
		if (año != null && !año.isBlank()) {
			if (!año.matches("\\d+")) {
				modelo.addAttribute("errorR", "El año no puede ser texto.");
				return "AnhoEstado";
			}
		
			anho = Integer.parseInt(año);
		
		}
		List<PeticionVacaciones> peticiones = fachada.buscarPeticiones(usuario, anho, estado);

		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("peticiones", peticiones);
		modelo.addAttribute("estados", EstadoPeticion.values());
		return "AnhoEstado";
	}
}