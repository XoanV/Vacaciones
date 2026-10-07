package org.afdt.vacaciones.controller;

import java.util.ArrayList;
import java.util.List;

import org.afdt.vacaciones.model.Centro;
import org.afdt.vacaciones.model.EstadoPeticion;
import org.afdt.vacaciones.model.PeticionVacaciones;
import org.afdt.vacaciones.model.TipoUsuario;
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
	public String Inicio(Model mod, HttpSession sesion) {
		String pagina = "";

		Usuario nuevoUsuario = (Usuario) sesion.getAttribute("usuario");

		mod.addAttribute("usuario", nuevoUsuario);		

		if (nuevoUsuario.getTipoUsuario().name().equals("EMPLEADO")) {

			List<PeticionVacaciones> peticionesPend = fachada.buscarPeticiones(nuevoUsuario, EstadoPeticion.PENDIENTE);

			List<PeticionVacaciones> aprobadas = fachada.buscarPeticiones(nuevoUsuario, EstadoPeticion.APROBADA);

			List<PeticionVacaciones> rechazadas = fachada.buscarPeticiones(nuevoUsuario, EstadoPeticion.RECHAZADA);

			mod.addAttribute("peticionVacacionesPendientes", peticionesPend);
			mod.addAttribute("peticionVacacionesAprobadas", aprobadas);
			mod.addAttribute("peticionVacacionesRechazadas", rechazadas);

			pagina = "Inicioemp";

		} else if (nuevoUsuario.getTipoUsuario().name().equals("GESTOR")) {

			List<PeticionVacaciones> peticiones = fachada.buscarTodasLasPeticiones(nuevoUsuario.getCentro());

			mod.addAttribute("peticiones", peticiones);

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

		if (usuario.getEmail() == null || usuario.getEmail().isBlank() || usuario.getClave() == null || usuario.getClave().isBlank()) {

			modelo.addAttribute("error", "No puede haber campos vacíos.");

			return "Iniciarsesion";
		}

		Usuario resultado = fachada.iniciarSesion(usuario.getEmail());

		if (resultado == null) {

			modelo.addAttribute("error", "El correo no existe.");

			return "Iniciarsesion";

		} else if (!cifrado.matches(usuario.getClave(), resultado.getClave())) {

			modelo.addAttribute("error", "Las contraseñas no coinciden.");

			return "Iniciarsesion";

		} else {

			sesion.setAttribute("usuario", resultado);
		}

		return "redirect:/paginaInicio";
	}

	@GetMapping("/sesioncerrada")
	public String cerrarSesion(HttpSession sesion) {

		sesion.invalidate();

		return "redirect:/inicio";
	}

	@GetMapping("/perfilUsuario")
	public String irMiPerfil(Model modelo, HttpSession session) {

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
	public String actPerfil(@RequestParam(name = "id") Integer id, @RequestParam(name = "conAct") String conActual,
			@RequestParam(name = "conNueva") String nueva, @RequestParam(name = "passwordrepe") String repetida,
			@RequestParam(name = "correo") String email, @RequestParam(name = "centro") Centro cent, Model modelo,
			HttpSession sesion) {

		Usuario usu = fachada.encontrarUsuario(id);

		modelo.addAttribute("usuario", usu);
		modelo.addAttribute("centros", Centro.values());

		if (email == null || email.isBlank() || conActual == null || conActual.isBlank() || nueva == null
				|| nueva.isBlank() || repetida == null || repetida.isBlank()) {

			modelo.addAttribute("errorR", "No puede haber campos vacíos.");

			return "Perfil";

		} else {

			if (!cifrado.matches(conActual, usu.getClave())) {

				modelo.addAttribute("errorR", "La contraseña actual no coincide con la almacenada en la base de datos.");

			} else if (!nueva.equals(repetida)) {

				modelo.addAttribute("errorR", "Las contraseñas no coinciden.");

			} else {

				String concifrada = cifrado.encode(nueva);

				fachada.actualizarUsuario(usu.getIdUsuario(), concifrada, email, cent);

				Usuario actual = fachada.encontrarUsuario(id);

				modelo.addAttribute("usuario", actual);

				sesion.setAttribute("usuario", actual);

				return "Perfil";
			}
		}

		return "Perfil";
	}

	@GetMapping("/peticiones")
	public String Peticiones(Model modelo, @RequestParam("id") int id, HttpSession sesion) {

		PeticionVacaciones peticion = fachada.buscarPetId(id);

		Usuario usuario = (Usuario) sesion.getAttribute("usuario");

		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("peticion", peticion);

		return "Peticiones";
	}

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

		try {

			if (usuario.getNombre() == null || usuario.getNombre().equals("") || usuario.getEmail() == null
					|| usuario.getApellidos() == null || usuario.getApellidos().equals("")
					|| usuario.getEmail().equals("") || usuario.getClave() == null || usuario.getClave().equals("")
					|| passwordrepe == null || passwordrepe.equals("")) {

				modelo.addAttribute("errorR", "Hay campos incompletos.");

				modelo.addAttribute("usuario", usuario);

				return "Registro";

			} else {

				if (!usuario.getClave().equals(passwordrepe)) {

					modelo.addAttribute("errorR", "Las contraseñas no coinciden.");

					return "Registro";

				} else {

					Usuario res = fachada.buscarCorreo(usuario.getEmail());

					if (res != null) {

						modelo.addAttribute("errorR", "El correo ya existe.");

						return "Registro";

					} else {

						usuario.setTipoUsuario(TipoUsuario.EMPLEADO);
						System.out.println(usuario.getClave());

						String con = cifrado.encode(usuario.getClave());

						usuario.setClave(con);

						Usuario usuarioGuardado = fachada.altaUsuario(usuario);

						modelo.addAttribute("usuario", usuarioGuardado);

						return "Iniciarsesion";
					}
				}
			}

		} catch (Exception e) {

			modelo.addAttribute("errorR", "Error desconocido");

			modelo.addAttribute("usuario", usuario);
		}

		return "Registro";
	}

	@GetMapping("/buscarpetAnho")
	public String buscarAnho(Model modelo, HttpSession sesion) {

		Usuario nuevoUsuario = (Usuario) sesion.getAttribute("usuario");

		modelo.addAttribute("usuario", nuevoUsuario);

		return "Anho";
	}

	@PostMapping("/buscarpetAnho")
	public String peticionesAnho(Model modelo, HttpSession sesion, @RequestParam(name = "anho") String ano) {

		String pag = "Anho";

		List<PeticionVacaciones> peticiones = new ArrayList<PeticionVacaciones>();
		if (ano == null || ano.isBlank()) {
			modelo.addAttribute("errorR", "El año no puede estar vacío.");
			return pag;
		} else if (!ano.matches("\\d+")) {
			modelo.addAttribute("errorR", "El año no puede ser texto.");
			return pag;
		} else {
			int anho = Integer.parseInt(ano);
			Usuario usu = (Usuario) sesion.getAttribute("usuario");
			if (usu.getTipoUsuario().name().equals("EMPLEADO")) {
				peticiones = fachada.buscarpetAnho(usu.getIdUsuario(), anho);
			} else if (usu.getTipoUsuario().name().equals("GESTOR")) {
				peticiones = fachada.buscarAnho(anho);
			}

			modelo.addAttribute("usuario", usu);
			modelo.addAttribute("peticiones", peticiones);

			return pag;
		}
	}

	@GetMapping("/buscarEstado")
	public String peticionesEstado(Model modelo, HttpSession sesion, @RequestParam(name = "estado") EstadoPeticion est) {

		String pag = "Estado";

		List<PeticionVacaciones> peticiones = new ArrayList<PeticionVacaciones>();

		Usuario usu = (Usuario) sesion.getAttribute("usuario");

		if (usu.getTipoUsuario().name().equals("EMPLEADO")) {

			peticiones = fachada.buscarpetEstado(usu.getIdUsuario(), est);

		} else if (usu.getTipoUsuario().name().equals("GESTOR")) {

			peticiones = fachada.buscarEstado(est);
		}

		modelo.addAttribute("usuario", usu);
		modelo.addAttribute("peticiones", peticiones);

		return pag;
	}
	
	@PostMapping("/cambioContrasenha")
	public String cambioCon(@RequestParam(name = "conAct") String conActual, @RequestParam(name = "conNueva") String nueva, @RequestParam(name = "passwordrepe") String repetida, Model modelo,
			HttpSession sesion) {

		Usuario usu = (Usuario) sesion.getAttribute("usuario");
		if (conActual == null || conActual.isBlank() || nueva == null || nueva.isBlank() || repetida == null || repetida.isBlank()) {
			modelo.addAttribute("errorR", "No puede haber campos vacíos.");
			return Inicio(modelo, sesion);
		} else {
			if (!cifrado.matches(conActual, usu.getClave())) {
				modelo.addAttribute("errorR", "La contraseña actual no coincide con la almacenada en la base de datos.");
			} else if (!nueva.equals(repetida)) {
				modelo.addAttribute("errorR", "Las contraseñas no coinciden.");
			} else {
				String concifrada = cifrado.encode(nueva);
				fachada.actualizarconUsuario(usu.getIdUsuario(), concifrada);
			}
		}
		sesion.invalidate();
		return "redirect:/inicio";
	}
}