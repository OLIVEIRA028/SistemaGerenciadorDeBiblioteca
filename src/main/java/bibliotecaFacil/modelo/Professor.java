package bibliotecaFacil.modelo;

public class Professor extends Usuario {
    
    public Professor(int matricula, String nome, String cpf, String email) {
        super(matricula, nome, cpf, email);
    }

    @Override
    public String getTipo() {
        return "PROFESSOR";
    }
}
