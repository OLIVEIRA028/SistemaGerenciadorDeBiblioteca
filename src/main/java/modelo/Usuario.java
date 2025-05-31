package modelo;

public abstract class Usuario {
    protected String nome;
    protected int matricula;
    protected String cpf;
    protected String email;

    public Usuario() {}

    public Usuario(int matricula, String nome, String cpf, String email) {
        this.nome = nome;
        this.matricula = matricula;
        this.cpf = cpf;
        this.email = email;
    }

    public abstract String getTipo();

    public String getNome() { return nome; }
    public int getMatricula() { return matricula; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }

    public void setNome(String nome) { this.nome = nome; }
    public void setMatricula(int matricula) { this.matricula = matricula; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setEmail(String email) { this.email = email; }
}
