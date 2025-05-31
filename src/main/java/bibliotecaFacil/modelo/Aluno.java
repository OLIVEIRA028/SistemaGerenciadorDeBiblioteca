package bibliotecaFacil.modelo;

public class Aluno extends Usuario {
    public Aluno(int matricula, String nome, String cpf, String email) {
        super(matricula, nome, cpf, email);
    }

    @Override
    public String getTipo() {
        return "ALUNO";
    }
}
