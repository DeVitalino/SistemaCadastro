package com.cadastrosimples.sistema.repository;

import com.cadastrosimples.sistema.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}