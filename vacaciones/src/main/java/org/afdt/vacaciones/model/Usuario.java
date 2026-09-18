package org.afdt.vacaciones.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUsuario;
	private String email;
	private String clave;
	private String nombre;
	private String apellidos;
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;
	@Enumerated(EnumType.STRING)
	private Centro centro;
	private boolean claveReseteada;
	
	public Usuario() {
	}

	public Usuario(String email, String clave, String nombre, String apellidos, TipoUsuario tipoUsuario,
			Centro centro, boolean claveR) {
		this.email = email;
		this.clave = clave;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.tipoUsuario = tipoUsuario;
		this.centro = centro;
		this.claveReseteada = claveR; 
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}	

	public boolean isClaveReseteada() {
		return claveReseteada;
	}

	public void setClaveReseteada(boolean claveReseteada) {
		this.claveReseteada = claveReseteada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return idUsuario == other.idUsuario;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [idUsuario=").append(idUsuario).append(", email=").append(email).append(", clave=")
				.append(clave).append(", nombre=").append(nombre).append(", apellidos=").append(apellidos)
				.append(", tipoUsuario=").append(tipoUsuario).append(", centro=").append(centro).append("claveReseteada=").append(claveReseteada).append("]");
		return builder.toString();
	}
}
