package bibliotecaFacil.servlet;

import bibliotecaFacil.dao.UsuarioDAO;
import bibliotecaFacil.modelo.Aluno;
import bibliotecaFacil.modelo.Professor;
import bibliotecaFacil.modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String nome = request.getParameter("nome");
            int matricula = Integer.parseInt(request.getParameter("matricula"));
            String cpf = request.getParameter("cpf");
            String email = request.getParameter("email");
            String tipo = request.getParameter("tipo");

            Usuario usuario;
            if ("Aluno".equalsIgnoreCase(tipo)) {
                usuario = new Aluno(matricula, nome, cpf, email);
            } else if ("Professor".equalsIgnoreCase(tipo)) {
                usuario = new Professor(matricula, nome, cpf, email);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de usuário inválido.");
                return;
            }

            UsuarioDAO dao = new UsuarioDAO();
            dao.inserir(usuario);

            response.setStatus(HttpServletResponse.SC_CREATED); // 201
            response.getWriter().println("Usuário cadastrado com sucesso!");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Matrícula inválida.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
