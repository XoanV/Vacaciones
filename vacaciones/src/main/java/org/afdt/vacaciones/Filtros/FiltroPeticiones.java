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

	public static Specification<PeticionVacaciones> buscar(Integer anho, EstadoPeticion estado, Centro centro,
			Usuario usuario) {

		return (root, query, cb) -> {

			List<Predicate> filtros = new ArrayList<>();

			if (anho != null) {
				filtros.add(cb.equal(root.get("anho"), anho));
			}

			if (estado != null) {
				filtros.add(cb.equal(root.get("estado"), estado));
			}

			if (centro != null) {
				filtros.add(cb.equal(root.get("usuario").get("centro"), centro));
			}

			if (usuario != null) {
				filtros.add(cb.equal(root.get("usuario"), usuario));
			}

			return cb.and(filtros.toArray(new Predicate[0]));
		};
	}
}