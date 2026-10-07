package org.afdt.vacaciones.model.facade;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.afdt.vacaciones.Filtros.FiltroPeticiones;
import org.afdt.vacaciones.model.Centro;
import org.afdt.vacaciones.model.EstadoPeticion;
import org.afdt.vacaciones.model.PeticionVacaciones;
import org.afdt.vacaciones.model.Usuario;
import org.afdt.vacaciones.model.dao.PeticionDAO;
import org.afdt.vacaciones.model.dao.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UsuarioFacade {

	@Autowired
	private UsuarioDAO usuDAO;

	@Autowired
	private PeticionDAO petDAO;

	public Usuario iniciarSesion(String email) {
		return usuDAO.findByEmail(email);
	}

	public Usuario altaUsuario(Usuario usuario) {
		return usuDAO.save(usuario);
	}

	public void actualizarUsuario(int id, String concifrada, String email, Centro cent) {
		Optional<Usuario> usu = usuDAO.findById(id);
		usu.get().setClave(concifrada);
		usu.get().setEmail(email);
		usu.get().setCentro(cent);
		usuDAO.save(usu.get());
	}

	public Usuario encontrarUsuario(Integer id) {
		return usuDAO.findById(id).get();
	}

	public PeticionVacaciones altaPeticion(PeticionVacaciones peticionVacaciones) {
		return petDAO.save(peticionVacaciones);
	}

	public List<PeticionVacaciones> buscarPeticiones(Usuario usu, EstadoPeticion est) {
		return petDAO.findByUsuarioAndEstado(usu, est);
	}

	public PeticionVacaciones buscarPetId(int id) {
		return petDAO.findById(id).get();
	}

	public List<PeticionVacaciones> buscarpetAnho(int idUsuario, int anho) {
		return petDAO.findByUsuario_IdUsuarioAndAño(idUsuario, anho);
	}

	public List<PeticionVacaciones> buscarAnho(int anho) {
		return petDAO.findByAño(anho);
	}

	public List<PeticionVacaciones> buscarEstado(EstadoPeticion est) {
		return petDAO.findByEstado(est);
	}

	public List<PeticionVacaciones> buscarpetEstado(int idUsuario, EstadoPeticion est) {
		return petDAO.findByUsuario_IdUsuarioAndEstado(idUsuario, est);
	}

	public List<PeticionVacaciones> buscarTodasLasPeticiones(Centro centro) {
		return petDAO.findByUsuarioCentro(centro);
	}

	public Usuario buscarCorreo(String email) {
		return usuDAO.findByEmail(email);
	}

	public void actualizarComentario(int id, String comentario, EstadoPeticion estado) {
		Optional<PeticionVacaciones> pet = petDAO.findById(id);
		pet.get().setComentario(comentario);
		pet.get().setEstado(estado);
		petDAO.save(pet.get());
	}

	public List<PeticionVacaciones> buscarPeticiones(Usuario usu, Integer año, EstadoPeticion estado) {

		Specification<PeticionVacaciones> especificacion = FiltroPeticiones.filtrar(usu, año, estado);

		return petDAO.findAll(especificacion);
	}

	public List<PeticionVacaciones> buscarpetUsu(String usuario) {
		return petDAO.findByUsuarioNombre(usuario);
	}

	public List<PeticionVacaciones> buscarpetCentro(Centro cent) {
		return petDAO.findByUsuarioCentro(cent);
	}

	public List<PeticionVacaciones> buscarTodo(Integer anho, EstadoPeticion estado, Centro cent, String usu) {
		Specification<PeticionVacaciones> especificacion = FiltroPeticiones.buscar(anho, estado, cent, usu);
		return petDAO.findAll(especificacion);
	}

	public List<PeticionVacaciones> buscarFecha(LocalDate inicio, LocalDate fin, Centro centro) {
		return petDAO.buscarFecha(inicio, fin, centro);
	}

	public List<Usuario> buscarUsuarios(String usuariobuscado, Centro centro) {
		return usuDAO.findByNombreContainingAndCentro(usuariobuscado, centro);
	}

	public void enviarCorreo(String email, String email2, String congenerada) {
		// TODO Auto-generated method stub
		
	}

	public void cambiarClaveUsu(int idUsuario, String conReset) {
		Optional<Usuario> usu = usuDAO.findById(idUsuario);
		usu.get().setClave(conReset);
		usu.get().setClaveReseteada(true);
		usuDAO.save(usu.get());
	}

	public void actualizarconUsuario(int idUsuario, String concifrada) {
		Optional<Usuario> usu = usuDAO.findById(idUsuario);
		usu.get().setClave(concifrada);
		usu.get().setClaveReseteada(false);
		usuDAO.save(usu.get());
	}
}