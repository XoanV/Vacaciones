package org.afdt.vacaciones.model.facade;

import java.util.List;
import java.util.Optional;

import org.afdt.vacaciones.model.Centro;
import org.afdt.vacaciones.model.EstadoPeticion;
import org.afdt.vacaciones.model.PeticionVacaciones;
import org.afdt.vacaciones.model.Usuario;
import org.afdt.vacaciones.model.dao.PeticionDAO;
import org.afdt.vacaciones.model.dao.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
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

	public List<PeticionVacaciones> buscarTodasLasPeticiones() {
		return petDAO.findAll();
	}
}
