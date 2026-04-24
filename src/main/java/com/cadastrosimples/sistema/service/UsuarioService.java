package com.cadastrosimples.sistema.service;

import com.cadastrosimples.sistema.model.Usuario;
import com.cadastrosimples.sistema.model.Empresa;
import com.cadastrosimples.sistema.repository.UsuarioRepository;
import com.cadastrosimples.sistema.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Empresa> empresaOpt = empresaRepository.findById(empresaId);

        if (empresaOpt.isPresent()) {
            usuario.setEmpresa(empresaOpt.get());
            return repository.save(usuario);
        }

        return null;
    }

    public Usuario atualizar(Long id, Usuario dados) {
        Optional<Usuario> usuarioOpt = repository.findById(id);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

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
                Optional<Empresa> empresaOpt = empresaRepository.findById(dados.getEmpresa().getId());
                empresaOpt.ifPresent(usuario::setEmpresa);
            }

            return repository.save(usuario);
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