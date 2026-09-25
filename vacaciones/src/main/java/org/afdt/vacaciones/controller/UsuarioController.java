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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioFacade fachada;
	
	@Autowired
	private PasswordEncoder cifrado;	

	@GetMapping("/paginaInicio")
	public String Inicio(Model mod) {
		Usuario nuevoUsuario = new Usuario();
		mod.addAttribute("usuario", nuevoUsuario);
		return "Inicioemp";
	}
	
	@PostMapping("/paginaInicio")
	public String pagInicio(Model modelo, HttpSession sesion) {
		String pagina = "";
		Usuario nuevoUsuario = (Usuario) sesion.getAttribute("usuario");
		if (nuevoUsuario.getTipoUsuario().name().equals("EMPLEADO")) {
			sesion.setAttribute("usuario", nuevoUsuario);
			pagina = "Inicioemp";
		} else if (nuevoUsuario.getTipoUsuario().name().equals("GESTOR")) {
			sesion.setAttribute("usuario", nuevoUsuario);
			pagina = "Iniciogestor";
		}
		return pagina;
	}
	
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
			modelo.addAttribute("error", "El correo no existe.");
		}
		return "Iniciarsesion";
	}
	
	@GetMapping("/sesioncerrada")
	public String cerrarSesion(HttpSession sesion) {
		sesion.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/perfilUsuario")
	public String irMiPerfil(Model modelo,  HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("centros", Centro.values());
		return "Perfil";
	}
	
	@GetMapping("/actualizarPerfil")
	public String actPerfil(Model modelo, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		modelo.addAttribute("usuario", usuario);
		return "Perfil";
	}
	
	@PostMapping("/actualizarPerfil")
	public String actPerfil(@RequestParam(name = "id") Integer id, @RequestParam(name = "conAct") String conActual, @RequestParam(name = "conNueva") String nueva, @RequestParam(name = "passwordrepe") String repetida, @RequestParam(name = "correo") String email, @RequestParam(name = "centro") Centro cent, Model modelo, HttpSession sesion) {
		Usuario usu = fachada.encontrarUsuario(id);
		if (email == null || email.isBlank() || conActual == null || conActual.isBlank() || nueva == null || nueva.isBlank() || repetida == null || repetida.isBlank()) {
			modelo.addAttribute("error", "No puede haber campos vacíos.");
			return "Perfil";
		} else if (cifrado.matches(conActual, usu.getClave())) {
			if (nueva.equals(repetida)) {
				String concifrada = cifrado.encode(nueva);
				fachada.actualizarUsuario(usu.getIdUsuario(), concifrada, email, cent);
				Usuario actual = fachada.encontrarUsuario(id);
				modelo.addAttribute("usuario", actual);
			} else {
				modelo.addAttribute("error", "Las contraseñas no coinciden.");
			}		 	
		} else {
			modelo.addAttribute("error", "La contraseña actual no coincide con la almacenada en la base de datos.");
		}
		return "Perfil";
	}
}

