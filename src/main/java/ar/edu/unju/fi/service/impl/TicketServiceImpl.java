package ar.edu.unju.fi.service.impl;

import ar.edu.unju.fi.entity.*;
import ar.edu.unju.fi.repo.*;
import ar.edu.unju.fi.service.LineaCompra;
import ar.edu.unju.fi.service.TicketService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepo;
    private final ClienteRepository clienteRepo;
    private final ArticuloRepository articuloRepo;
    private final DetalleTicketRepository detalleRepo;
    private final EntityManager em;

    public TicketServiceImpl(TicketRepository t, ClienteRepository c, ArticuloRepository a, DetalleTicketRepository d, EntityManager em) {
        this.ticketRepo = t; this.clienteRepo = c; this.articuloRepo = a; this.detalleRepo = d; this.em = em;
    }

    @Transactional
    public Ticket abrir(Integer clienteId) {
        Cliente c = clienteRepo.findById(clienteId).orElseThrow(() -> new IllegalArgumentException("Cliente inexistente"));
        Ticket t = new Ticket(0.0, c); // total = 0.0 (NOT NULL), trigger lo recalcula
        return ticketRepo.save(t);
    }

    @Transactional
    public DetalleTicket agregarDetalle(Integer ticketId, Integer articuloId, Integer cantidad, Double precioUnitario) {
        Ticket t = ticketRepo.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket inexistente"));
        Articulo a = articuloRepo.findById(articuloId).orElseThrow(() -> new IllegalArgumentException("Artículo inexistente"));
        Double pu = (precioUnitario != null ? precioUnitario : a.getPrecioLista());
        DetalleTicket d = new DetalleTicket(cantidad, pu, t, a);
        d = detalleRepo.saveAndFlush(d);
        em.refresh(d); em.refresh(t);
        return d;
    }

    @Transactional
    public Ticket pagar(Integer ticketId) {
        Ticket t = ticketRepo.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket inexistente"));
        t.setTipoTicket(ar.edu.unju.fi.util.enums.TicketEstado.PAGADO.getEstado());
        return ticketRepo.save(t);
    }

    @Transactional
    public Ticket anular(Integer ticketId) {
        Ticket t = ticketRepo.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket inexistente"));
        t.setTipoTicket(ar.edu.unju.fi.util.enums.TicketEstado.ANULADO.getEstado());
        return ticketRepo.save(t);
    }

    @Transactional(readOnly = true)
    public Ticket porId(Integer id) { return ticketRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe")); }

    @Transactional(readOnly = true)
    public List<Ticket> porCliente(Integer clienteId){ return ticketRepo.findByClienteId(clienteId); }

    @Transactional
    public Ticket realizarCompra(Integer clienteId, List<LineaCompra> lineas) {
        Ticket t = abrir(clienteId); // total=0.0; triggers recalculan después
        for (LineaCompra l : lineas) {
            Articulo a = articuloRepo.findById(l.articuloId()).orElseThrow(() -> new IllegalArgumentException("Artículo inexistente"));
            Double pu = (l.precioUnitario() != null ? l.precioUnitario() : a.getPrecioLista());
            DetalleTicket d = new DetalleTicket(l.cantidad(), pu, t, a);
            detalleRepo.save(d);
        }
        // Sincronizamos y refrescamos para traer total/subtotales
        ticketRepo.flush();
        em.refresh(t);
        return t;
    }
}
