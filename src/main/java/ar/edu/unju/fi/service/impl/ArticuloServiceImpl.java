package ar.edu.unju.fi.service.impl;
import ar.edu.unju.fi.entity.Articulo;
import ar.edu.unju.fi.repo.AdminRepo;
import ar.edu.unju.fi.repo.ArticuloRepository;
import ar.edu.unju.fi.service.ArticuloService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ArticuloServiceImpl implements ArticuloService {
    private final ArticuloRepository repo;
    private final AdminRepo adminRepo;

    public ArticuloServiceImpl(ArticuloRepository repo, AdminRepo adminRepo){
        this.repo = repo; this.adminRepo = adminRepo;
    }

    @Transactional public Articulo crear(Articulo a){ return repo.save(a); }
    @Transactional(readOnly = true) public java.util.Optional<Articulo> porId(Integer id){ return repo.findById(id); }
    @Transactional(readOnly = true) public List<Articulo> listar(){ return repo.findAll(); }

    @Transactional
    public Articulo actualizar(Integer id, Articulo src){
        Articulo a = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe artículo"));
        a.setNombre(src.getNombre());
        a.setDescripcion(src.getDescripcion());
        a.setPrecioLista(src.getPrecioLista());
        a.setStock(src.getStock());
        a.setEstado(src.getEstado());
        return repo.save(a);
    }
    @Transactional public void eliminar(Integer id){ repo.deleteById(id); }

    @Transactional
    public Articulo ajustarStock(Integer articuloId, Integer delta, Integer adminId){
        // Verificación mínima de Admin (existe en tabla admins -> PK=FK)
        adminRepo.findById(adminId).orElseThrow(() -> new IllegalArgumentException("Admin inexistente"));
        Articulo a = repo.findById(articuloId).orElseThrow(() -> new IllegalArgumentException("Artículo inexistente"));
        int nuevo = (a.getStock() == null ? 0 : a.getStock()) + delta;
        if (nuevo < 0) throw new IllegalArgumentException("Stock no puede ser negativo");
        a.setStock(nuevo);
        return repo.save(a);
    }

    @Transactional(readOnly = true)
    public List<Articulo> listarTop10(){
        return repo.findAll(PageRequest.of(0,10)).getContent();
    }
}
