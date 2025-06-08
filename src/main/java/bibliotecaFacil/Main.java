package bibliotecaFacil;

import bibliotecaFacil.dao.EmprestimoDAO;
import bibliotecaFacil.dao.LivroDAO;
import bibliotecaFacil.dao.UsuarioDAO;
import bibliotecaFacil.modelo.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@SpringBootApplication
public class Main {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    private final LivroDAO livroDAO = new LivroDAO();

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
    public String listarLivros(Model model) {
        List<Livro> livros = livroDAO.listar();
        model.addAttribute("livros", livros);
        return "listarLivros";
    }

    @GetMapping("/registrarEmprestimoDevolucao")
    public String registrarEmprestimoDevolucao(Model model) {
        List<Usuario> usuarios = usuarioDAO.listar();
        List<Livro> livros = livroDAO.listar();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("livros", livros);
        return "registrarEmprestimoDevolucao";
    }

    // ✅ Novo método: cadastrar livro
    @PostMapping("/livro")
    public String cadastrarLivro(
            @RequestParam String isbn,
            @RequestParam String titulo,
            @RequestParam String autor
    ) {
        Livro livro = new Livro(
                isbn,
                titulo,
                autor,
                new Date(),              // valor genérico para data de publicação
                "Desconhecida",          // valor padrão para editora
                true                     // o livro começa como disponível
        );
        livroDAO.inserir(livro);
        return "redirect:/listarLivros";
    }

    @PostMapping("/usuario")
    public String cadastrarUsuario(
            @RequestParam String nome,
            @RequestParam String matricula,
            @RequestParam String cpf,
            @RequestParam String email,
            @RequestParam String tipo
    ) {
        try {
            int matriculaInt = Integer.parseInt(matricula);
            Usuario usuario;
            if ("Aluno".equalsIgnoreCase(tipo)) {
                usuario = new Aluno(matriculaInt, nome, cpf, email);
            } else {
                usuario = new Professor(matriculaInt, nome, cpf, email);
            }
            usuarioDAO.inserir(usuario);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @PostMapping("/emprestimoDevolucao")
    public String registrarEmprestimoDevolucao(
            @RequestParam String acao,
            @RequestParam(required = false) Integer idUsuario,
            @RequestParam(required = false) String idLivro,
            @RequestParam(required = false) Integer idEmprestimo
    ) {
        try {
            if ("emprestimo".equalsIgnoreCase(acao)) {
                if (idUsuario != null && idLivro != null && !idLivro.isEmpty()) {
                    int idLivroInt = Integer.parseInt(idLivro);
                    emprestimoDAO.registrarEmprestimo(idUsuario, idLivroInt);
                }
            } else if ("devolucao".equalsIgnoreCase(acao)) {
                if (idEmprestimo != null && idLivro != null && !idLivro.isEmpty()) {
                    int idLivroInt = Integer.parseInt(idLivro);
                    emprestimoDAO.registrarDevolucao(idEmprestimo, idLivroInt);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "redirect:/registrarEmprestimoDevolucao";
    }
}
