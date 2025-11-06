package ar.edu.unju.fi.util.enums;

import java.util.function.Predicate;
import java.util.stream.Stream;

public enum TicketEstado {
	EMITIDO("EMITIDO") , ANULADO("ANULADO"), PAGADO("PAGADO");
	
	private String estado;
	
	private TicketEstado(String estado) {
		this.estado=estado;
	}
	
	public String getEstado() {
		return this.estado;
	}
	
	public static boolean encontrarEstadoPorValor(String valor) {
		Predicate<TicketEstado> exists = s -> s.getEstado().equals(valor);
		return Stream.of(values()).anyMatch(exists);
	}
}
