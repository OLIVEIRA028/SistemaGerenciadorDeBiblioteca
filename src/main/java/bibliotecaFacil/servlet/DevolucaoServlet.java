package servlet;

import dao.EmprestimoDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DevolucaoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idEmprestimo = Integer.parseInt(request.getParameter("idEmprestimo"));
        int idLivro = Integer.parseInt(request.getParameter("idLivro")); // recupera o ID do livro também

        EmprestimoDAO dao = new EmprestimoDAO();
        dao.registrarDevolucao(idEmprestimo, idLivro); // agora com os dois argumentos

        response.sendRedirect("sucesso.html");
    }
}
