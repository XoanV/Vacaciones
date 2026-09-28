package org.afdt.vacaciones.model.dao;

import java.util.List;

import org.afdt.vacaciones.model.EstadoPeticion;
import org.afdt.vacaciones.model.PeticionVacaciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeticionDAO extends JpaRepository<PeticionVacaciones, Integer> {

	List<PeticionVacaciones> findByEstado(EstadoPeticion est);

}
