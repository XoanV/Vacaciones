package org.afdt.vacaciones.model.dao;

import java.util.List;

import org.afdt.vacaciones.model.Centro;
import org.afdt.vacaciones.model.EstadoPeticion;
import org.afdt.vacaciones.model.PeticionVacaciones;
import org.afdt.vacaciones.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PeticionDAO extends JpaRepository<PeticionVacaciones, Integer>, JpaSpecificationExecutor<PeticionVacaciones> {

	List<PeticionVacaciones> findByEstado(EstadoPeticion est);

	List<PeticionVacaciones> findByUsuario_IdUsuarioAndAño(int idUsuario, int anho);
	
	List<PeticionVacaciones> findByUsuario_IdUsuarioAndEstado(int idUsuario, EstadoPeticion est);
	
	List<PeticionVacaciones> findByUsuarioCentro(Centro cent);
	
	List<PeticionVacaciones> findByUsuarioNombre(String nom);

	List<PeticionVacaciones> findByUsuarioAndEstado(Usuario usu, EstadoPeticion est);
	
	List<PeticionVacaciones> findByAño(int anho);

}
