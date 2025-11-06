package ar.edu.unju.fi.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "usuarios")
public abstract class Usuario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id")
    private Integer id;

	@Column(name = "nombre", nullable = false, length = 150)
    private String nombre;
	
	@Column(name = "email", nullable = false, unique = true, length = 254)
    private String email;
	
	@Column(name = "password_hash", nullable = false, length = 100)
	@JsonIgnore
    private String passwordHash;

	@Column(name = "telefono", nullable = false, length = 50)
    private String telefono;

	@Column(name = "tipo_usuario", nullable = false)
	private String tipoUsuario;

    @Column(name = "creado_en", nullable = false)
    private LocalDate creadoEn;

    @Column(name = "estado", nullable = false)
	private Boolean estado;
    
	protected Usuario() {	    
	    this.creadoEn = LocalDate.now();
	    this.tipoUsuario = this.getClass().getSimpleName();
	    this.estado = true;
	}
	protected Usuario(String nombre, String email, String telefono, String passwordHash) {
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.passwordHash = passwordHash;
	    this.creadoEn = LocalDate.now();	   
	    this.tipoUsuario = this.getClass().getSimpleName();
	    this.estado = true;
    }

	// ----- Getters y Setters -----
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPasswordHash() { return passwordHash; }
	public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

	public String getTelefono() { return telefono; }
	public void setTelefono(String telefono) { this.telefono = telefono; }

	public String getTipoUsuario() { return tipoUsuario; }
	public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

	public LocalDate getCreadoEn() { return creadoEn; }
	public void setCreadoEn(LocalDate creadoEn) { this.creadoEn = creadoEn; }

	public Boolean getEstado() { return estado; }
	public void setEstado(Boolean estado) { this.estado = estado; }
}