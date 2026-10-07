package org.afdt.vacaciones.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.afdt.vacaciones.Service.ExportacionService;
import org.afdt.vacaciones.Service.PasswordService;
import org.afdt.vacaciones.model.Centro;
import org.afdt.vacaciones.model.EstadoPeticion;
import org.afdt.vacaciones.model.PeticionVacaciones;
import org.afdt.vacaciones.model.Usuario;
import org.afdt.vacaciones.model.facade.UsuarioFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
@Controller
public class GestorController {

	@Autowired
	private UsuarioFacade fachada;

	@Autowired
	private ExportacionService exportacionService;

	@Autowired
	private PasswordService passService;

	@Autowired
	private PasswordEncoder cifrado;

	@ModelAttribute
	public void cargarDatos(Model modelo) {
		modelo.addAttribute("centros", Centro.values());
	}

	@PostMapping("/actualizarCom")
	public String peticiones(@RequestParam(name = "id") int id, @RequestParam(name = "com") String comentario,
			@RequestParam(name = "estado") EstadoPeticion estado, Model modelo, HttpSession sesion) {
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		PeticionVacaciones pet = fachada.buscarPetId(id);
		if (comentario == null || comentario.isBlank()) {
			comentario = null;
		}
		fachada.actualizarComentario(pet.getIdPeticion(), comentario, estado);
		modelo.addAttribute("peticion", pet);
		modelo.addAttribute("usuario", usuario);
		sesion.setAttribute("usuario", usuario);
		return "Peticiones";
	}

	@GetMapping("/buscarpetUsuario")
	public String buscarUsuario(Model modelo, HttpSession sesion) {

		Usuario nuevoUsuario = (Usuario) sesion.getAttribute("usuario");

		modelo.addAttribute("usuario", nuevoUsuario);

		return "Usuarios";
	}

	@PostMapping("/buscarpetUsuario")
	public String peticionesUsuario(Model modelo, HttpSession sesion, @RequestParam(name = "usu") String usuario) {

		String pag = "Usuarios";

		List<PeticionVacaciones> peticiones = new ArrayList<PeticionVacaciones>();
		if (usuario == null || usuario.isBlank()) {
			modelo.addAttribute("errorR", "El usuario no puede estar vacío.");
			return pag;
		} else {
			Usuario usu = (Usuario) sesion.getAttribute("usuario");
			peticiones = fachada.buscarpetUsu(usuario);
			modelo.addAttribute("usuario", usu);
			modelo.addAttribute("peticiones", peticiones);

			return pag;
		}
	}

	@GetMapping("/buscarCentro")
	public String peticionesCentro(Model modelo, HttpSession sesion, @RequestParam(name = "cent") Centro cent) {

		String pag = "Centros";

		List<PeticionVacaciones> peticiones = new ArrayList<PeticionVacaciones>();

		Usuario usu = (Usuario) sesion.getAttribute("usuario");

		peticiones = fachada.buscarpetCentro(cent);

		modelo.addAttribute("usuario", usu);
		modelo.addAttribute("peticiones", peticiones);

		return pag;
	}

	@GetMapping("/busqueda")
	public String buscarTodo(Model modelo, HttpSession sesion) {

		Usuario nuevoUsuario = (Usuario) sesion.getAttribute("usuario");

		modelo.addAttribute("usuario", nuevoUsuario);
		modelo.addAttribute("estados", EstadoPeticion.values());
		modelo.addAttribute("centros", Centro.values());

		return "BusquedaGlobal";
	}

	@PostMapping("/busqueda")
	public String buscarPeticiones(@RequestParam(name = "anho", required = false) String año,
			@RequestParam(name = "estado", required = false) EstadoPeticion estado,
			@RequestParam(name = "usu", required = false) String usu,
			@RequestParam(name = "cent", required = false) Centro cent, Model modelo, HttpSession sesion) {
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		Integer anho = null;
		if (año != null && !año.isBlank()) {
			if (!año.matches("\\d+")) {
				modelo.addAttribute("errorR", "El año no puede ser texto.");
				return "BusquedaGlobal";
			}
			anho = Integer.parseInt(año);
		}
		List<PeticionVacaciones> peticiones = fachada.buscarTodo(anho, estado, cent, usu);

		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("peticiones", peticiones);
		modelo.addAttribute("estados", EstadoPeticion.values());
		modelo.addAttribute("centros", Centro.values());
		return "BusquedaGlobal";
	}

	@GetMapping("/peticionesporFecha")
	public String buscarFecha(Model modelo, HttpSession sesion) {

		Usuario nuevoUsuario = (Usuario) sesion.getAttribute("usuario");

		modelo.addAttribute("usuario", nuevoUsuario);

		return "Fecha";
	}

	@PostMapping("/peticionesporFecha")
	public String peticionesRangoFecha(Model modelo, HttpSession sesion,
			@RequestParam("fechaInicio") String fechaInicio, @RequestParam("fechaFin") String fechaFin) {

		String pag = "Fecha";

		Usuario nuevoUsuario = (Usuario) sesion.getAttribute("usuario");

		if (fechaInicio == null || fechaInicio.isBlank() || fechaFin == null || fechaFin.isBlank()) {

			modelo.addAttribute("error", "Debes seleccionar las dos fechas.");

			return pag;

		}

		LocalDate inicio = LocalDate.parse(fechaInicio);
		LocalDate fin = LocalDate.parse(fechaFin);

		if (inicio.isAfter(fin)) {

			modelo.addAttribute("error", "La fecha de inicio no puede ser posterior a la fecha final.");

			return pag;

		}

		List<PeticionVacaciones> peticiones = fachada.buscarFecha(inicio, fin, nuevoUsuario.getCentro());
		modelo.addAttribute("peticiones", peticiones);

		modelo.addAttribute("fechaInicio", fechaInicio);

		modelo.addAttribute("fechaFin", fechaFin);

		return pag;
	}

	@PostMapping("/exportar")
	public ResponseEntity<byte[]> exportar(@RequestParam("contenido") String contenido,
			@RequestParam("titulo") String titulo, @RequestParam("formato") String formato) {
		try {
			return exportacionService.exportar(contenido, titulo, formato);
		} catch (Exception e) {
			throw new RuntimeException("Error al exportar el listado", e);
		}
	}

	@GetMapping("/reseteoContrasenha")
	public String reset(Model mod) {
		return "Contrasenha";
	}

	@PostMapping("/reseteoContrasenha")
	public String reseteo(Model mod, HttpSession ses, @RequestParam(name = "usu") String usuariobuscado) {
		Usuario usuario = (Usuario) ses.getAttribute("usuario");

		List<Usuario> lista = fachada.buscarUsuarios(usuariobuscado, usuario.getCentro());
		if (lista.isEmpty()) {
			mod.addAttribute("error", "El usuario no existe en el centro de " + usuario.getCentro());
		} else {
			mod.addAttribute("usu", lista);
		}

		return "Contrasenha";
	}

	@PostMapping("/correo")
	public String enviarCorreo(Model mod, HttpSession ses, @RequestParam(name = "id") int id, @RequestParam(name = "con") String contra) {
		Usuario usuarioActual = (Usuario) ses.getAttribute("usuario"); 
		
		Usuario usuario = fachada.encontrarUsuario(id);
		if (!cifrado.matches(contra, usuarioActual.getClave())) { 
			mod.addAttribute("errorCon", "La contraseña no coincide con la del usuario actual."); 
			mod.addAttribute("id", id); 
		} else { 
			String congenerada = "Ac@demiaPostal6"; 
			mod.addAttribute("sms", "La contraseña temporal de " + usuario.getNombre() + " " + usuario.getApellidos() + " es " + congenerada); 
			// fachada.enviarCorreo( // usuarioActual.getEmail(), // usuario.getEmail(), // congenerada // ); } 
			String conReset = cifrado.encode(congenerada);
			fachada.cambiarClaveUsu(usuario.getIdUsuario(), conReset);
		}
		return "Contrasenha";
	}
}