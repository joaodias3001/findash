package com.example.financial_dashboard.controller;

import com.example.financial_dashboard.dto.UserLoginDTO;
import com.example.financial_dashboard.dto.UserRegisterDTO;
import com.example.financial_dashboard.model.User;
import com.example.financial_dashboard.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO dto) {
        User user = authService.register(dto);
        return ResponseEntity.ok("Utilizador registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO dto) {
        User user = authService.authenticate(dto);
        return ResponseEntity.ok(user); // futuramente, vamos retornar um token JWT aqui
    }

    @GetMapping("/status")
    public ResponseEntity<?> checkAuthStatus(HttpServletRequest request) {
        // Aqui você pode verificar a sessão ou o token (se houver) para validar a autenticação
        if (request.getSession(false) != null) {
            return ResponseEntity.ok().build(); // Usuário autenticado
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Usuário não autenticado
    }
}
