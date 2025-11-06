package ar.edu.unju.fi.controller;

import ar.edu.unju.fi.entity.Articulo;
import ar.edu.unju.fi.service.ArticuloService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/articulos")
@CrossOrigin
public class ArticuloController {
    private final ArticuloService service;
    public ArticuloController(ArticuloService service){ this.service = service; }

    // GET /api/articulos/top10
    @GetMapping("/top10")
    public List<Articulo> top10() {
        return service.listarTop10();
    }

    // PUT /api/articulos/{id}/stock?delta=3&adminId=1
    @PutMapping("/{id}/stock")
    public Articulo ajustarStock(@PathVariable Integer id,
                                 @RequestParam Integer delta,
                                 @RequestParam Integer adminId) {
        return service.ajustarStock(id, delta, adminId);
    }
}