package com.cadastrosimples.sistema.controller;

import com.cadastrosimples.sistema.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void deveGerarTokenComSucesso() {
        String token = jwtService.gerarToken("admin");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void deveValidarTokenERetornarUsuario() {
        String token = jwtService.gerarToken("admin");

        String usuario = jwtService.validarToken(token);

        assertEquals("admin", usuario);
    }

    @Test
    void deveFalharAoValidarTokenInvalido() {
        assertThrows(Exception.class, () -> {
            jwtService.validarToken("token_invalido");
        });
    }
}