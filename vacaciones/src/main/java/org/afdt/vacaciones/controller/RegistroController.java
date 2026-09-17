package org.afdt.vacaciones.controller;

import org.afdt.vacaciones.model.Centro;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

	@Autowired
	private UsuarioFacade fachada;
	
	@Autowired
	private PasswordEncoder cifrado;
	
	@GetMapping("/registro")
	public String irRegistro(Model modelo) {
		Usuario nuevoUsuario = new Usuario();
		modelo.addAttribute("usuario", nuevoUsuario);
		modelo.addAttribute("centros", Centro.values());
		return "Registro";
	}

	@RequestMapping(path = "registro", method = RequestMethod.POST)
	public String registrarse(@ModelAttribute("usuario") Usuario usuario, Model modelo,
			@RequestParam String passwordrepe) {
		if (!usuario.getClave().equals(passwordrepe)) {
			modelo.addAttribute("errorR", "Las contraseñas no coinciden.");
			return "Registro";
		} else if (usuario.getNombre() == null || usuario.getNombre().equals("") || usuario.getEmail() == null || usuario.getApellidos() == null || usuario.getApellidos().equals("") || usuario.getEmail().equals("") || usuario.getClave() == null || usuario.getClave().equals("") || passwordrepe == null || passwordrepe.equals("")) {
			modelo.addAttribute("errorR", "Hay campos incompletos.");
			modelo.addAttribute("usuario", usuario);
			return "Registro";
		} else {
			try {
				usuario.setTipoUsuario(usuario.getTipoUsuario().EMPLEADO);
				String con = cifrado.encode(usuario.getClave());
				usuario.setClave(con);
				Usuario usuarioGuardado = fachada.altaUsuario(usuario);
				modelo.addAttribute("usuario", new Usuario());
				return "Iniciarsesion";
			} catch (Exception e) {
				modelo.addAttribute("errorR", "Error desconocido");
				modelo.addAttribute("usuario", usuario);
				return "Registro";
			}
		}
	}
}
