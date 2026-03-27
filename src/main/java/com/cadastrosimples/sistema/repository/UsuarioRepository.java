package com.cadastrosimples.sistema.repository;

import com.cadastrosimples.sistema.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {
    private List<Usuario> usuarios = new ArrayList<>();

    public List<Usuario> findAll() {
        return new ArrayList<>(usuarios);
    }

    public Optional<Usuario> findById(int id) {
        return usuarios.stream().filter(u -> u.getId() == id).findFirst();
    }

    public Usuario save(Usuario usuario) {
        usuarios.add(usuario);
        return usuario;
    }

    public boolean deleteById(int id) {
        return usuarios.removeIf(u -> u.getId() == id);
    }
}
