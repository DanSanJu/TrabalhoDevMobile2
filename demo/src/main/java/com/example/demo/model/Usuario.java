package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String senha;

    private int totalLogins;

    private int totalFalhas;

    private boolean bloqueado;

   // Getters e Setters
   public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}

public String getSenha() {
    return senha;
}

public void setSenha(String senha) {
    this.senha = senha;
}

public int getTotalLogins() {
    return totalLogins;
}

public void setTotalLogins(int totalLogins) {
    this.totalLogins = totalLogins;
}

public int getTotalFalhas() {
    return totalFalhas;
}

public void setTotalFalhas(int totalFalhas) {
    this.totalFalhas = totalFalhas;
}

public boolean isBloqueado() {
    return bloqueado;
}

public void setBloqueado(boolean bloqueado) {
    this.bloqueado = bloqueado;
}
}
