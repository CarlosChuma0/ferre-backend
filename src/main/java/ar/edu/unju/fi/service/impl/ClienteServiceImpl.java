package ar.edu.unju.fi.service.impl;
import ar.edu.unju.fi.repo.ClienteRepository;
import ar.edu.unju.fi.service.ClienteService;
import ar.edu.unju.fi.entity.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService{
    private final ClienteRepository repo;
    public ClienteServiceImpl(ClienteRepository repo){ this.repo = repo; }

    @Transactional
    public Cliente crear(Cliente c) {
        if (repo.existsByEmail(c.getEmail())) throw new IllegalArgumentException("Email ya registrado");
        return repo.save(c);
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> porId(Integer id){ return repo.findById(id); }
}
