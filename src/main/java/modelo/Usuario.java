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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
