package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<String> criarUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioService.criarUsuario(usuario);
            return new ResponseEntity<>("Usuário criado com sucesso", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> efetuarLogin(@RequestBody Usuario usuario) {
        try {
            usuarioService.efetuarLogin(usuario.getUsername(), usuario.getSenha());
            return new ResponseEntity<>("Login efetuado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/trocasenha")
    public ResponseEntity<String> trocarSenha(@RequestBody Usuario usuario, @RequestParam String novaSenha) {
        try {
            usuarioService.trocarSenha(usuario.getUsername(), usuario.getSenha(), novaSenha);
            return new ResponseEntity<>("Senha trocada com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bloqueados")
    public ResponseEntity<List<Usuario>> listarUsuariosBloqueados() {
        return new ResponseEntity<>(usuarioService.listarUsuariosBloqueados(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/desbloquear/{username}")
    public ResponseEntity<String> desbloquearUsuario(@PathVariable String username) {
        try {
            usuarioService.desbloquearUsuario(username);
            return new ResponseEntity<>("Usuário desbloqueado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}