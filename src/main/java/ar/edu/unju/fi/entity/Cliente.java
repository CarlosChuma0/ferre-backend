package ar.edu.unju.fi.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@ToString
@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Cliente extends Usuario {
	@Column(name = "dni", nullable = false, length = 15)
    private String dni;
	
	@Column(name = "cuit_cuil", nullable = false, length = 15)
    private String cuitCuil;
	
	@Column(name = "direccion", nullable = false, length = 100)
    private String direccion;
	
	@Column(name = "categoria_fiscal", nullable = false, length = 100)
    private String categoriaFiscal;

	@ToString.Exclude
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	List<Ticket> tickets = new ArrayList<>();
	
	public Cliente() {
	}
	
	public Cliente (String nombre, String email, String telefono, String passwordHash, String dni, String cuitCuil, String direccion, String categoriaFiscal) {
		super(nombre, email, telefono, passwordHash);
		this.dni = dni;
		this.cuitCuil = cuitCuil;
		this.direccion = direccion;
		this.categoriaFiscal = categoriaFiscal;
	}

	// ----- Getters y Setters -----
	public String getDni() { return dni; }
	public void setDni(String dni) { this.dni = dni; }

	public String getCuitCuil() { return cuitCuil; }
	public void setCuitCuil(String cuitCuil) { this.cuitCuil = cuitCuil; }

	public String getDireccion() { return direccion; }
	public void setDireccion(String direccion) { this.direccion = direccion; }

	public String getCategoriaFiscal() { return categoriaFiscal; }
	public void setCategoriaFiscal(String categoriaFiscal) { this.categoriaFiscal = categoriaFiscal; }

	public List<Ticket> getTickets() { return tickets; }
	public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
}
