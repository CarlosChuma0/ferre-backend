package ar.edu.unju.fi.service;
import ar.edu.unju.fi.entity.Cliente;
import java.util.Optional;

public interface ClienteService {
    Cliente crear(Cliente c);
    Optional<Cliente> porId(Integer id);
}