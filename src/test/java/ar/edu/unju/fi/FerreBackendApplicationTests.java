package ar.edu.unju.fi;

import ar.edu.unju.fi.entity.*;
import ar.edu.unju.fi.repo.*;
import ar.edu.unju.fi.service.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")  // usa application-test.properties
class FerreBackendApplicationTests {
	@Autowired AdminRepo adminRepo;
	@Autowired ClienteRepository clienteRepo;
	@Autowired ArticuloRepository articuloRepo;
	@Autowired ArticuloService articuloService;
	@Autowired TicketService ticketService;
	@Autowired EntityManager em;

	private Admin nuevoAdmin() {
		Admin a = new Admin("Admin Test","admin@test.com","123","hash");
		return adminRepo.save(a);
	}
	private Cliente nuevoCliente() {
		Cliente c = new Cliente("Cliente JPA","cliente@test.com","123","hash","12345678","20-12345678-9","Calle 1","Monotributo");
		return clienteRepo.save(c);
	}
	private Articulo art(String nombre, double precio, int stock){
		Articulo a = new Articulo(nombre,"desc",precio,stock);
		a.setEstado(true);
		return articuloRepo.save(a);
	}

	@Test
	@Transactional
	@Rollback // por defecto true (no persiste)
	@DisplayName("Admin ajusta stock de un artículo (delta) y no queda persistido")
	void adminAjustaStock_delta_ok() {
		Admin admin = nuevoAdmin();
		Articulo a = art("Martillo", 100.0, 5);

		Articulo mod = articuloService.ajustarStock(a.getId(), +3, admin.getId());
		assertThat(mod.getStock()).isEqualTo(8);

		Articulo mod2 = articuloService.ajustarStock(a.getId(), -2, admin.getId());
		assertThat(mod2.getStock()).isEqualTo(6);
	}

	@Test
	@Transactional
	@Rollback
	@DisplayName("Listar top 10 artículos")
	void listarTop10_ok() {
		List<Articulo> top = articuloService.listarTop10();
		assertThat(top).hasSize(10);
	}

	@Test
	@Transactional
	@Rollback
	@DisplayName("Realizar compra: crea ticket + detalles y devuelve totales calculados por triggers")
	void realizarCompra_ok() {
		Cliente c = nuevoCliente();
		Articulo a1 = art("Disco", 150.0, 10);
		Articulo a2 = art("Mecha", 100.0, 20);

		List<LineaCompra> lineas = List.of(
				new LineaCompra(a1.getId(), 2, null),     // usa precioLista = 150
				new LineaCompra(a2.getId(), 3, 90.0)      // precioUnitario override = 90
		);

		Ticket t = ticketService.realizarCompra(c.getId(), lineas);
		// Traer valores post-trigger
		em.refresh(t);

		// total esperado: (2*150) + (3*90) = 300 + 270 = 570
		assertThat(Math.abs(t.getTotal() - 570.0)).isLessThan(0.01);
		assertThat(t.getTipoTicket()).isEqualTo(ar.edu.unju.fi.util.enums.TicketEstado.EMITIDO.getEstado());
	}
}
