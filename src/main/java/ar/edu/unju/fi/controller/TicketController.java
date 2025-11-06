// ar/edu/unju/fi/controller/TicketController.java
package ar.edu.unju.fi.controller;

import ar.edu.unju.fi.entity.Ticket;
import ar.edu.unju.fi.service.LineaCompra;
import ar.edu.unju.fi.service.TicketService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
public class TicketController {
    private final TicketService service;
    public TicketController(TicketService service){ this.service = service; }

    // POST /api/tickets/compra
    // body: { "clienteId": 5, "lineas": [ { "articuloId": 10, "cantidad": 2 }, ... ] }
    @PostMapping("/compra")
    public Ticket realizarCompra(@RequestBody Map<String, Object> body) {
        Integer clienteId = ((Number) body.get("clienteId")).intValue();

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> raw = (List<Map<String, Object>>) body.get("lineas");

        List<LineaCompra> lineas = raw.stream().map(m -> new LineaCompra(
                ((Number)m.get("articuloId")).intValue(),
                ((Number)m.get("cantidad")).intValue(),
                m.get("precioUnitario") == null ? null : ((Number)m.get("precioUnitario")).doubleValue()
        )).toList();

        return service.realizarCompra(clienteId, lineas);
    }

    // GET /api/tickets/{id}
    @GetMapping("/{id}")
    public Ticket porId(@PathVariable Integer id){
        return service.porId(id);
    }
}