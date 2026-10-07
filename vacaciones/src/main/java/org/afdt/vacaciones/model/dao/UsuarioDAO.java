package org.afdt.vacaciones.model.dao;

import java.util.List;

import org.afdt.vacaciones.model.Centro;
import org.afdt.vacaciones.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

	Usuario findByEmail(String email);

	List<Usuario> findByNombreContainingAndCentro(String nombre, Centro centro);

}