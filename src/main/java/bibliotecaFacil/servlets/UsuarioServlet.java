package servlet;

import dao.UsuarioDAO;
import modelo.Aluno;
import modelo.Professor;
import modelo.Usuario;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UsuarioServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String matricula = request.getParameter("matricula");
        String cpf = request.getParameter("cpf");
        String email = request.getParameter("email");
        String tipo = request.getParameter("tipo");

        Usuario usuario;
        if ("Aluno".equalsIgnoreCase(tipo)) {
            usuario = new Aluno(0, nome, matricula, cpf, email);
        } else {
            usuario = new Professor(0, nome, matricula, cpf, email);
        }

        UsuarioDAO dao = new UsuarioDAO();
        dao.inserir(usuario);

        response.sendRedirect("sucesso.html");
    }
}
