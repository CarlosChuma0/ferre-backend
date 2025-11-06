package ar.edu.unju.fi.service;
import ar.edu.unju.fi.entity.Ticket;
import ar.edu.unju.fi.entity.DetalleTicket;
import java.util.List;

public interface TicketService {
    Ticket abrir(Integer clienteId);
    DetalleTicket agregarDetalle(Integer ticketId, Integer articuloId, Integer cantidad, Double precioUnitario);
    Ticket pagar(Integer ticketId);
    Ticket anular(Integer ticketId);
    Ticket porId(Integer id);
    List<Ticket> porCliente(Integer clienteId);
    Ticket realizarCompra(Integer clienteId, List<LineaCompra> lineas);
}
