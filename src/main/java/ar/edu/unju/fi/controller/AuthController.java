package ar.edu.unju.fi.controller;

import ar.edu.unju.fi.entity.Usuario;
import ar.edu.unju.fi.entity.Cliente;
import ar.edu.unju.fi.service.AuthService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService){ this.authService = authService; }

    // POST /api/auth/login
    // { "email":"...", "password":"..." }
    @PostMapping("/login")
    public Usuario login(@RequestBody Map<String, Object> body) {
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        return authService.login(email, password);
    }

    // POST /api/auth/register/cliente
    // {
    //  "nombre":"...","email":"...","telefono":"...",
    //  "password":"...","dni":"...","cuit":"...","direccion":"...","categoriaFiscal":"..."
    // }
    @PostMapping("/register/cliente")
    public Cliente registrarCliente(@RequestBody Map<String, Object> b) {
        return authService.registrarCliente(
                (String) b.get("nombre"),
                (String) b.get("email"),
                (String) b.get("telefono"),
                (String) b.get("password"),
                (String) b.get("dni"),
                (String) b.get("cuit"),
                (String) b.get("direccion"),
                (String) b.get("categoriaFiscal")
        );
    }
}
