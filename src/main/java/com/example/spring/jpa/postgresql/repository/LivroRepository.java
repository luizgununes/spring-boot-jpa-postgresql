package com.example.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.jpa.postgresql.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
  List<Livro> findByPublicado(boolean publicado);
  List<Livro> findByTituloContaining(String titulo);
}
