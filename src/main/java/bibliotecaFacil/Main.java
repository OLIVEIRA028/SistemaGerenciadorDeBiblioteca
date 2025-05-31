package bibliotecaFacil;

import dao.UsuarioDAO;
import modelo.Aluno;
import modelo.Professor;
import modelo.Usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@SpringBootApplication
public class Main {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "index"; 
    }

    @GetMapping("/cadastroLivro")
    public String cadastroLivro() {
        return "cadastroLivro";
    }

    @GetMapping("/cadastroUsuario")
    public String cadastroUsuario() {
        return "cadastroUsuario";
    }

    @GetMapping("/listarLivros")
    public String listarLivros() {
        return "listarLivros";
    }

    @GetMapping("/registrarEmprestimoDevolucao")
    public String registrarEmprestimoDevolucao() {
        return "registrarEmprestimoDevolucao";
    }

    // Método para tratar o POST do formulário de cadastro de usuário
    @PostMapping("/usuario")
    public String cadastrarUsuario(
            @RequestParam String nome,
            @RequestParam String matricula,
            @RequestParam String cpf,
            @RequestParam String email,
            @RequestParam String tipo
    ) {
        Usuario usuario;
        if ("Aluno".equalsIgnoreCase(tipo)) {
            usuario = new Aluno(0, nome, matricula, cpf, email);
        } else {
            usuario = new Professor(0, nome, matricula, cpf, email);
        }

        usuarioDAO.inserir(usuario);

        // Redireciona para a página inicial após cadastrar
        return "redirect:/";
    }
}
