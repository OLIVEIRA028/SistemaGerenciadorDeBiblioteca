package bibliotecaFacil.modelo;

import java.util.Date;

public class Livro {
    private String isbn;
    private String titulo;
    private String autor;
    private Date publicacao;
    private String editora;
    private boolean disponivel;

    public Livro(String isbn, String titulo, String autor, Date publicacao, String editora, boolean disponivel) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.publicacao = publicacao;
        this.editora = editora;
        this.disponivel = disponivel;
    }

    // Getters e Setters

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Date publicacao) {
        this.publicacao = publicacao;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
