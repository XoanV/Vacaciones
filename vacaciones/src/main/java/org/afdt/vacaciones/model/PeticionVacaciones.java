package org.afdt.vacaciones.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PeticionVacaciones {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPeticion;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private int año;
	private String comentario;
	@Enumerated(EnumType.STRING)
	private EstadoPeticion estado;
	@ManyToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;
	
	public PeticionVacaciones() {
	}

	public PeticionVacaciones(int idPeticion, LocalDate fechaInicio, LocalDate fechaFin, int año, String comentario,
			EstadoPeticion estado, Usuario usuario) {
		this.idPeticion = idPeticion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.año = año;
		this.comentario = comentario;
		this.estado = estado;
		this.usuario = usuario;
	}

	public int getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(int idPeticion) {
		this.idPeticion = idPeticion;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	public EstadoPeticion getEstado() {
		return estado;
	}

	public void setEstado(EstadoPeticion estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPeticion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PeticionVacaciones other = (PeticionVacaciones) obj;
		return idPeticion == other.idPeticion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PeticionVacaciones [idPeticion=").append(idPeticion).append(", fechaInicio=")
				.append(fechaInicio).append(", fechaFin=").append(fechaFin).append(", año=").append(año)
				.append(", comentario=").append(comentario).append(", estado=").append(estado).append(", usuario=")
				.append(usuario).append("]");
		return builder.toString();
	}
}