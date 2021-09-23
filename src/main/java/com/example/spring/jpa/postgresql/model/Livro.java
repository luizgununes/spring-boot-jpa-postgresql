package com.example.spring.jpa.postgresql.model;

import javax.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "titulo")
	private String titulo;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "publicado")
	private boolean publicado;

	public Livro() {

	}

	public Livro(String titulo, String descricao, boolean publicado) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.publicado = publicado;
	}

	public long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isPublicado() {
		return publicado;
	}

	public void setPublicado(boolean isPublicado) {
		this.publicado = isPublicado;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", publicado=" + publicado + "]";
	}

}
