package org.afdt.vacaciones.model.dao;

import org.afdt.vacaciones.model.PeticionVacaciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeticionDAO extends JpaRepository<PeticionVacaciones, Integer> {

}
