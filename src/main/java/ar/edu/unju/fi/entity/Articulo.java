package ar.edu.unju.fi.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Entity
@Table(name = "articulos")
public class Articulo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "articulo_id")
    private Integer id;
	
	@Column(name = "codigo", nullable = false, unique = true, length = 12)
	private String codigo;
	
	@Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
	
	@Column(name = "descripcion", nullable = false, length = 250)
    private String descripcion;
	
	@Column(name = "precio_lista", nullable = false)
    private Double precioLista;
	
	@Column(name = "stock", nullable = false)
    private Integer stock;

	@ToString.Exclude
	@OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	List<DetalleTicket> detalles = new ArrayList<>();
	
	@Column(name = "estado", nullable = false)
	private Boolean estado;
	
	public Articulo() {
		this.codigo = UUID.randomUUID().toString().substring(0, 12);
		this.estado = true;
		this.stock  = 0;
	}
	
	public Articulo (String nombre, String descripcion, Double precioLista, Integer stock) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precioLista = precioLista;
		this.stock = stock;	
		this.codigo = UUID.randomUUID().toString().substring(0, 12);
		this.estado = true;
	}

	// ----- Getters y Setters -----
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

	public Double getPrecioLista() { return precioLista; }
	public void setPrecioLista(Double precioLista) { this.precioLista = precioLista; }

	public Integer getStock() { return stock; }
	public void setStock(Integer stock) { this.stock = stock; }

	public List<DetalleTicket> getDetalles() { return detalles; }
	public void setDetalles(List<DetalleTicket> detalles) { this.detalles = detalles; }

	public Boolean getEstado() { return estado; }
	public void setEstado(Boolean estado) { this.estado = estado; }
}
