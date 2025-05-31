package modelo;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private int ano;
    private String editora;
    private boolean disponivel;

    public Livro() {}

    public Livro(int id, String titulo, String autor, String isbn, int ano, String editora, boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.ano = ano;
        this.editora = editora;
        this.disponivel = disponivel;
    }

    // Getters e Setters
    // (omitir aqui para brevidade, mas incluir todos)
}
