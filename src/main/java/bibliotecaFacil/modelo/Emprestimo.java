package bibliotecaFacil.modelo;

import java.util.Date;
import java.util.List;

public class Emprestimo {
    private int id;
    private Usuario usuario;
    private List<Livro> livros;
    private Date data;
    private String status;

    public Emprestimo(int id, Usuario usuario, List<Livro> livros, Date data, String status) {
        this.id = id;
        this.usuario = usuario;
        this.livros = livros;
        this.data = data;
        this.status = status;
    }

    // Getters e Setters
}
