package org.afdt.vacaciones.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Configuracion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_configuracion")
	private int idConfiguracion;
	private String color;
	
	public Configuracion() {
	}

	public Configuracion(int idConfiguracion, String color) {
		this.idConfiguracion = idConfiguracion;
		this.color = color;
	}

	public int getIdConfiguracion() {
		return idConfiguracion;
	}

	public void setIdConfiguracion(int idConfiguracion) {
		this.idConfiguracion = idConfiguracion;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idConfiguracion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuracion other = (Configuracion) obj;
		return idConfiguracion == other.idConfiguracion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Configuracion [idConfiguracion=").append(idConfiguracion).append(", color=").append(color)
				.append("]");
		return builder.toString();
	}
}
