package ar.edu.unju.fi.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.util.enums.TicketEstado;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
    private Integer id;
	
	@Column(name = "fecha", nullable = false)
    private LocalDate fecha;
	
	@Column(name = "total", nullable = false)
    private Double total;
	
	@Column(name = "tipo_ticket", nullable = false, length = 7)
	private String tipoTicket;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
	@com.fasterxml.jackson.annotation.JsonIgnore
	List<DetalleTicket> detalles = new ArrayList<>();
	
	@Column(name = "estado", nullable = false)
	private Boolean estado;
	
	public Ticket() {		
		this.fecha = LocalDate.now();
		this.tipoTicket = TicketEstado.valueOf("EMITIDO").getEstado();
		this.estado = true;
	}
	
	public Ticket(Double total, Cliente cliente) {
		this.tipoTicket = TicketEstado.valueOf("EMITIDO").getEstado();
		this.total = total;
		this.fecha = LocalDate.now();
		this.estado = true;
		this.cliente = cliente;
		this.total = 0.0;
	}

	// ----- Getters y Setters -----
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	public LocalDate getFecha() { return fecha; }
	public void setFecha(LocalDate fecha) { this.fecha = fecha; }

	public Double getTotal() { return total; }
	public void setTotal(Double total) { this.total = total; }

	public String getTipoTicket() { return tipoTicket; }
	public void setTipoTicket(String tipoTicket) { this.tipoTicket = tipoTicket; }

	public Cliente getCliente() { return cliente; }
	public void setCliente(Cliente cliente) { this.cliente = cliente; }


	public List<DetalleTicket> getDetalles() { return detalles; }
	public void setDetalles(List<DetalleTicket> detalles) { this.detalles = detalles; }

	public Boolean getEstado() { return estado; }
	public void setEstado(Boolean estado) { this.estado = estado; }
}
