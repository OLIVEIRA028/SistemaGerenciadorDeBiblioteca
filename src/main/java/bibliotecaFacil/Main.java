package bibliotecaFacil;

import bibliotecaFacil.dao.EmprestimoDAO;
import bibliotecaFacil.dao.UsuarioDAO;
import bibliotecaFacil.modelo.Aluno;
import bibliotecaFacil.modelo.Professor;
import bibliotecaFacil.modelo.Usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SpringBootApplication
public class Main {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

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
    public String registrarEmprestimoDevolucao(Model model) {
        // Carregar a lista de usuários cadastrados para popular o select
        List<Usuario> usuarios = usuarioDAO.listar();
        model.addAttribute("usuarios", usuarios);
        return "registrarEmprestimoDevolucao";
    }

    // POST para cadastro de usuário
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

        return "redirect:/";
    }

    // POST para registrar empréstimo ou devolução
    @PostMapping("/emprestimoDevolucao")
    public String registrarEmprestimoDevolucao(
            @RequestParam String acao,
            @RequestParam(required = false) Integer idUsuario,
            @RequestParam(required = false) Integer idLivro,
            @RequestParam(required = false) Integer idEmprestimo
    ) {
        if ("emprestimo".equalsIgnoreCase(acao)) {
            if (idUsuario != null && idLivro != null) {
                emprestimoDAO.registrarEmprestimo(idUsuario, idLivro);
            }
        } else if ("devolucao".equalsIgnoreCase(acao)) {
            if (idEmprestimo != null && idLivro != null) {
                emprestimoDAO.registrarDevolucao(idEmprestimo, idLivro);
            }
        }
        return "redirect:/registrarEmprestimoDevolucao";
    }
}
