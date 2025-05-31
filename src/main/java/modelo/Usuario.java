package modelo;

public abstract class Usuario {
    protected int id;
    protected String nome;
    protected String matricula;
    protected String cpf;
    protected String email;

    public Usuario() {}

    public Usuario(int id, String nome, String matricula, String cpf, String email) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.cpf = cpf;
        this.email = email;
    }

    public abstract String getTipo();

    // Getters e Setters
}
