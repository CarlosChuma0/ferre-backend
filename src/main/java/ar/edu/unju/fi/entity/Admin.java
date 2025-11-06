package ar.edu.unju.fi.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Admin extends Usuario{
	@Column(name = "legajo", nullable = false, length = 50)
    private String legajo;
	
	public Admin() {
		super();
		this.legajo = UUID.randomUUID().toString().substring(0, 12);
	}
	
	public Admin(String nombre, String email, String telefono, String passwordHash) {
		super(nombre, email, telefono, passwordHash);
		this.legajo = UUID.randomUUID().toString().substring(0, 12);
	}
	
}
