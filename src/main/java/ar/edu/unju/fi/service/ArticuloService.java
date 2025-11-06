package ar.edu.unju.fi.service;
import ar.edu.unju.fi.entity.Articulo;
import java.util.List;
import java.util.Optional;

public interface ArticuloService {
    Articulo crear(Articulo a);
    Optional<Articulo> porId(Integer id);
    List<Articulo> listar();
    Articulo actualizar(Integer id, Articulo a);
    void eliminar(Integer id);
    Articulo ajustarStock(Integer articuloId, Integer delta, Integer adminId);
    List<Articulo> listarTop10();
}
