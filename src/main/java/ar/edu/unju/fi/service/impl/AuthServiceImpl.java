package ar.edu.unju.fi.service.impl;

import ar.edu.unju.fi.entity.Cliente;
import ar.edu.unju.fi.entity.Usuario;
import ar.edu.unju.fi.repo.ClienteRepository;
import ar.edu.unju.fi.repo.UsuarioRepo;
import ar.edu.unju.fi.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepo usuarioRepo;
    private final ClienteRepository clienteRepo;

    // “pepper” simple para demo académica (no almacenar en DB, solo server-side)
    private static final String PEPPER = "pepper-demo-ferreteria";

    public AuthServiceImpl(UsuarioRepo usuarioRepo, ClienteRepository clienteRepo){
        this.usuarioRepo = usuarioRepo;
        this.clienteRepo = clienteRepo;
    }

    // ---------- Helpers de hash ----------
    private String sha256Base64(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error generando hash", e);
        }
    }

    /** Hash con "pepper" fijo (simple para el TP). */
    private String hashPassword(String raw) {
        return sha256Base64(raw + ":" + PEPPER);
    }

    /** Comparación constante para evitar timing attacks (nivel demo). */
    private boolean constantEquals(String a, String b) {
        if (a == null || b == null) return false;
        byte[] ba = a.getBytes(StandardCharsets.UTF_8);
        byte[] bb = b.getBytes(StandardCharsets.UTF_8);
        if (ba.length != bb.length) return false;
        int r = 0;
        for (int i = 0; i < ba.length; i++) r |= (ba[i] ^ bb[i]);
        return r == 0;
    }

    // ---------- Casos de uso ----------
    @Transactional(readOnly = true)
    public Usuario login(String email, String rawPassword) {
        Usuario u = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
        if (u.getEstado() == null || !u.getEstado()) {
            throw new IllegalArgumentException("Usuario inactivo");
        }
        String candidate = hashPassword(rawPassword);
        if (!constantEquals(candidate, u.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
        return u; // password_hash está @JsonIgnore
    }

    @Transactional
    public Cliente registrarCliente(String nombre, String email, String tel,
                                    String rawPassword, String dni, String cuit,
                                    String direccion, String categoriaFiscal) {
        if (usuarioRepo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email ya registrado");
        }
        String hash = hashPassword(rawPassword);
        Cliente c = new Cliente(nombre, email, tel, hash, dni, cuit, direccion, categoriaFiscal);
        return clienteRepo.save(c);
    }
}
