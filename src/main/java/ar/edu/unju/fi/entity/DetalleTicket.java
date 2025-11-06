package ar.edu.unju.fi.entity;

import jakarta.persistence.*;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonBackReference;

@ToString
@Entity
@Table(name = "detalle_tickets")
public class DetalleTicket {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detalle_ticket_id")
    private Integer id;
	
	@Column(name = "cantidad", nullable = false)
    private Integer cantidad;
	
	@Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @Column(name = "subtotal", nullable = false, insertable = false, updatable = false)
    private Double subtotal;	
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id", nullable = false)
    @ToString.Exclude
    @JsonBackReference
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "articulo_id", nullable = false)
    @ToString.Exclude
    private Articulo articulo;
    
    @Column(name = "estado", nullable = false)
	private Boolean estado;
    
    public DetalleTicket() {
    	this.estado = true;
    }
    
    public DetalleTicket(Integer cantidad, Double precioUnitario, Double subtotal, Ticket ticket, Articulo articulo) {
    	this.cantidad = cantidad;
    	this.precioUnitario = precioUnitario;
    	this.subtotal = subtotal;
    	this.ticket = ticket;
    	this.articulo = articulo;
    	this.estado = true;
    }
    public DetalleTicket(Integer cantidad, Double precioUnitario, Ticket ticket, Articulo articulo) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.ticket = ticket;
        this.articulo = articulo;
        this.estado = true;
    }

    // ====== MÉTODOS MANUALES para evitar depender de Lombok ======
    public Integer getId() { return id; }
    public Double getSubtotal() { return subtotal; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}
