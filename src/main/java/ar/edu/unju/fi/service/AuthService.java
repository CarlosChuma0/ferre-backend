package ar.edu.unju.fi.service;

import ar.edu.unju.fi.entity.Usuario;
import ar.edu.unju.fi.entity.Cliente;

public interface AuthService {
    Usuario login(String email, String rawPassword);
    Cliente registrarCliente(String nombre, String email, String tel,
                             String rawPassword, String dni, String cuit,
                             String direccion, String categoriaFiscal);
}
