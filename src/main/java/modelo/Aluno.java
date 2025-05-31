package modelo;

public class Aluno extends Usuario {
    public Aluno(int id, String nome, String matricula, String cpf, String email) {
        super(id, nome, matricula, cpf, email);
    }

    @Override
    public String getTipo() {
        return "Aluno";
    }
}
