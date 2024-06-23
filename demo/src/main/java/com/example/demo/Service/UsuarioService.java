package com.example.demo.Service;

import com.example.demo.model.Usuario;
import com.example.demo.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario) throws Exception {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new Exception("Username já existe");
        }
        usuario.setTotalLogins(0);
        usuario.setTotalFalhas(0);
        usuario.setBloqueado(false);
        return usuarioRepository.save(usuario);
    }

    public Usuario efetuarLogin(String username, String senha) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);
        if (!optionalUsuario.isPresent()) {
            throw new Exception("Usuário não encontrado");
        }
        Usuario usuario = optionalUsuario.get();
        if (usuario.isBloqueado()) {
            throw new Exception("Usuário bloqueado");
        }
        if (!usuario.getSenha().equals(senha)) {
            usuario.setTotalFalhas(usuario.getTotalFalhas() + 1);
            if (usuario.getTotalFalhas() > 5) {
                usuario.setBloqueado(true);
            }
            usuarioRepository.save(usuario);
            throw new Exception("Senha inválida");
        }
        usuario.setTotalLogins(usuario.getTotalLogins() + 1);
        if (usuario.getTotalLogins() > 10) {
            throw new Exception("Usuário deve trocar a senha");
        }
        usuario.setTotalFalhas(0);
        return usuarioRepository.save(usuario);
    }

    public Usuario trocarSenha(String username, String senhaAtual, String novaSenha) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);
        if (!optionalUsuario.isPresent()) {
            throw new Exception("Usuário não encontrado");
        }
        Usuario usuario = optionalUsuario.get();
        if (!usuario.getSenha().equals(senhaAtual)) {
            throw new Exception("Senha atual inválida");
        }
        usuario.setSenha(novaSenha);
        usuario.setTotalLogins(0);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuariosBloqueados() {
        return usuarioRepository.findByBloqueado(true);
    }

    public void desbloquearUsuario(String username) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);
        if (!optionalUsuario.isPresent()) {
            throw new Exception("Usuário não encontrado");
        }
        Usuario usuario = optionalUsuario.get();
        usuario.setBloqueado(false);
        usuario.setTotalFalhas(0);
        usuarioRepository.save(usuario);
    }
}