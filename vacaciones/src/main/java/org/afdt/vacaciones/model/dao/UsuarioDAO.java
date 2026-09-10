package org.afdt.vacaciones.model.dao;

import org.afdt.vacaciones.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

	Usuario findByEmailAndClave(String email, String clave);

}
