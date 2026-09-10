package org.afdt.vacaciones.model.facade;

import org.afdt.vacaciones.model.Usuario;
import org.afdt.vacaciones.model.dao.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioFacade {
	
	@Autowired
	private UsuarioDAO usuDAO;

	public Usuario iniciarSesion(String email, String clave) {
		return usuDAO.findByEmailAndClave(email, clave);
	}

	public Usuario altaUsuario(Usuario usuario) {
		return usuDAO.save(usuario);
	}

}
