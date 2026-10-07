package org.afdt.vacaciones.Filtros;

import java.util.ArrayList;
import java.util.List;

import org.afdt.vacaciones.model.Centro;
import org.afdt.vacaciones.model.EstadoPeticion;
import org.afdt.vacaciones.model.PeticionVacaciones;
import org.afdt.vacaciones.model.Usuario;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class FiltroPeticiones {

	public static Specification<PeticionVacaciones> filtrar(Usuario usu, Integer año, EstadoPeticion estado) {

		return (root, query, cb) -> {

			var condiciones = cb.conjunction();
			
			condiciones = cb.and(condiciones, cb.equal(root.get("usuario"), usu));

			if (año != null) {
				condiciones = cb.and(condiciones, cb.equal(root.get("año"), año));
			}

			if (estado != null) {
				condiciones = cb.and(condiciones, cb.equal(root.get("estado"), estado));
			}

			return condiciones;
		};
	}

	public static Specification<PeticionVacaciones> buscar(Integer anho, EstadoPeticion estado, Centro centro, String usuario) {

		return (root, query, cb) -> {

			List<Predicate> filtros = new ArrayList<>();

			if (anho != null) {
				filtros.add(cb.equal(root.get("año"), anho));
			}

			if (estado != null) {
				filtros.add(cb.equal(root.get("estado"), estado));
			}

			if (centro != null) {
				filtros.add(cb.equal(root.get("usuario").get("centro"), centro));
			}

			if (usuario != null) {
				filtros.add(cb.equal(root.get("usuario").get("nombre"), usuario));
			}

			return cb.and(filtros.toArray(new Predicate[0]));
		};
	}
}