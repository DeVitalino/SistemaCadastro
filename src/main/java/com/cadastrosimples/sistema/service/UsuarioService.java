package com.cadastrosimples.sistema.service;

import com.cadastrosimples.sistema.model.Usuario;
import com.cadastrosimples.sistema.model.Empresa;
import com.cadastrosimples.sistema.repository.UsuarioRepository;
import com.cadastrosimples.sistema.repository.EmpresaRepository;
import com.cadastrosimples.sistema.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public List<Usuario> listarPorEmpresa(Long empresaId) {
        return repository.findByEmpresaId(empresaId);
    }

    public Usuario cadastrar(Usuario usuario, Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

        usuario.setEmpresa(empresa);
        return repository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario dados) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (dados.getNome() != null) {
            usuario.setNome(dados.getNome());
        }

        if (dados.getEmail() != null) {
            usuario.setEmail(dados.getEmail());
        }

        if (dados.getTelefone() != null) {
            usuario.setTelefone(dados.getTelefone());
        }

        if (dados.getEmpresa() != null && dados.getEmpresa().getId() != null) {
            Empresa empresa = empresaRepository.findById(dados.getEmpresa().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

            usuario.setEmpresa(empresa);
        }

        return repository.save(usuario);
    }

    public boolean remover(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }

        repository.deleteById(id);
        return true;
    }
}