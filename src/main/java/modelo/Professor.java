package modelo;

public class Professor extends Usuario {
    public Professor(int id, String nome, String matricula, String cpf, String email) {
        super(id, nome, matricula, cpf, email);
    }

    @Override
    public String getTipo() {
        return "Professor";
    }
}
