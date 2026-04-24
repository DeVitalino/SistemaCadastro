package com.cadastrosimples.sistema.repository;

import com.cadastrosimples.sistema.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByEmpresaId(Long empresaId);
}