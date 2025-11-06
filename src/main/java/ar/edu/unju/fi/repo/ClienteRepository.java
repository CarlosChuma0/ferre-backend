package ar.edu.unju.fi.repo;

import ar.edu.unju.fi.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {    boolean existsByEmail(String email);}
