package ar.edu.unju.fi.repo;

import ar.edu.unju.fi.entity.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {
}
