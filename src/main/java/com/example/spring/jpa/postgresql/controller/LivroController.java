package com.example.spring.jpa.postgresql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.jpa.postgresql.model.Livro;
import com.example.spring.jpa.postgresql.repository.LivroRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class LivroController {

	@Autowired
	LivroRepository livroRepository;

	@GetMapping("/livros")
	public ResponseEntity<List<Livro>> getAllLivros(@RequestParam(required = false) String titulo) {
		try {
			List<Livro> livros = new ArrayList<Livro>();

			if (titulo == null)
				livroRepository.findAll().forEach(livros::add);
			else
				livroRepository.findByTituloContaining(titulo).forEach(livros::add);

			if (livros.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(livros, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/livros/{id}")
	public ResponseEntity<Livro> getLivroById(@PathVariable("id") long id) {
		Optional<Livro> livroData = livroRepository.findById(id);

		if (livroData.isPresent()) {
			return new ResponseEntity<>(livroData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/livros")
	public ResponseEntity<Livro> createLivro(@RequestBody Livro livro) {
		try {
			Livro _livro = livroRepository
					.save(new Livro(livro.getTitulo(), livro.getDescricao(), false));
			return new ResponseEntity<>(_livro, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/livros/{id}")
	public ResponseEntity<Livro> updateLivro(@PathVariable("id") long id, @RequestBody Livro livro) {
		Optional<Livro> livroData = livroRepository.findById(id);

		if (livroData.isPresent()) {
			Livro _livro = livroData.get();
			_livro.setTitulo(livro.getTitulo());
			_livro.setDescricao(livro.getDescricao());
			_livro.setPublicado(livro.isPublicado());
			return new ResponseEntity<>(livroRepository.save(_livro), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/livros/{id}")
	public ResponseEntity<HttpStatus> deleteLivro(@PathVariable("id") long id) {
		try {
			livroRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/livros")
	public ResponseEntity<HttpStatus> deleteAllLivros() {
		try {
			livroRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/livros/publicado")
	public ResponseEntity<List<Livro>> findByPublicado() {
		try {
			List<Livro> livros = livroRepository.findByPublicado(true);

			if (livros.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(livros, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
