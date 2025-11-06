package ar.edu.unju.fi.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.unju.fi.entity.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}