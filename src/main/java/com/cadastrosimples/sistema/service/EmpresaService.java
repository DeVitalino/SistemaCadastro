package com.cadastrosimples.sistema.service;

import com.cadastrosimples.sistema.model.Empresa;
import com.cadastrosimples.sistema.repository.EmpresaRepository;
import com.cadastrosimples.sistema.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repository;

    public List<Empresa> listarTodos() {
        return repository.findAll();
    }

    public Empresa cadastrar(Empresa empresa) {
        return repository.save(empresa);
    }

    public Empresa atualizar(Long id, Empresa dados) {
        Empresa empresa = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

        if (dados.getNome() != null) {
            empresa.setNome(dados.getNome());
        }

        if (dados.getCnpj() != null) {
            empresa.setCnpj(dados.getCnpj());
        }

        return repository.save(empresa);
    }

    public boolean remover(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Empresa não encontrada");
        }

        repository.deleteById(id);
        return true;
    }
}