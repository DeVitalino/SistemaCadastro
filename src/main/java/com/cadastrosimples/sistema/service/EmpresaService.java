package com.cadastrosimples.sistema.service;

import com.cadastrosimples.sistema.model.Empresa;
import com.cadastrosimples.sistema.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Empresa> empresaOpt = repository.findById(id);

        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();

            if (dados.getNome() != null) {
                empresa.setNome(dados.getNome());
            }

            if (dados.getCnpj() != null) {
                empresa.setCnpj(dados.getCnpj());
            }

            return repository.save(empresa);
        }

        return null;
    }

    public boolean remover(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}