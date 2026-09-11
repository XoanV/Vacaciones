package org.afdt.vacaciones.controller;

import org.afdt.vacaciones.model.Usuario;
import org.afdt.vacaciones.model.facade.UsuarioFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
public class SesionController {

	@Autowired
	private UsuarioFacade fachada;
	
	@Autowired
	private PasswordEncoder cifrado;
	
	@GetMapping("/inicio")
	public String inicioSesion(Model modelo) {
		Usuario nuevoUsuario = new Usuario();
		modelo.addAttribute("usuario", nuevoUsuario);
		return "Iniciarsesion";
	}

	@RequestMapping(path = "inicio", method = RequestMethod.POST)
	public String iniciarSesion(@ModelAttribute("usuario") Usuario usuario, Model modelo, HttpSession sesion) {
		Usuario resultado = fachada.iniciarSesion(usuario.getEmail());
		if (resultado != null) {
			if (cifrado.matches(usuario.getClave(), resultado.getClave())) {
				if (resultado.getTipoUsuario().name().equals("EMPLEADO")) {
					sesion.setAttribute("usuario", resultado);
					return "Inicioemp";
				} else if (resultado.getTipoUsuario().name().equals("GESTOR")) {
					sesion.setAttribute("usuario", resultado);
					return "Iniciogestor";
				}
			} else {
				if (usuario.getEmail() == null || usuario.getEmail().equals("") || usuario.getClave() == null || usuario.getClave().equals("")) {
					modelo.addAttribute("error", "No puede haber campos vacíos.");
					return "Iniciarsesion";
				}
				modelo.addAttribute("error", "Las contraseñas no coinciden.");
				return "Iniciarsesion";
			}						
		} else {
			modelo.addAttribute("error", "Email o contraseña incorrectos.");
		}
		return "Iniciarsesion";
	}
}

