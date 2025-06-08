package bibliotecaFacil.modelo;

public abstract class Usuario {
    protected String nome;
    protected int matricula;
    protected String cpf;
    protected String email;

    public Usuario() {}

    public Usuario(final int matricula, final String nome, final String cpf, final String email) {
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

    public void setNome(final String nome) { this.nome = nome; }
    public void setMatricula(final int matricula) { this.matricula = matricula; }
    public void setCpf(final String cpf) { this.cpf = cpf; }
    public void setEmail(final String email) { this.email = email; }

    @Override
    public String toString() {
        return String.format("Usuario [matricula=%d, nome=%s, cpf=%s, email=%s, tipo=%s]", 
            matricula, nome, cpf, email, getTipo());
    }
}
