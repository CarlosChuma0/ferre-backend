package ar.edu.unju.fi.repo;

import ar.edu.unju.fi.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByClienteId(Integer clienteId);
}
