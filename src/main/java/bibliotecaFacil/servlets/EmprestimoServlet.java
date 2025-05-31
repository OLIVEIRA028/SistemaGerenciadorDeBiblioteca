package servlet;

import dao.EmprestimoDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class EmprestimoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        int idLivro = Integer.parseInt(request.getParameter("idLivro"));

        EmprestimoDAO dao = new EmprestimoDAO();
        dao.registrarEmprestimo(idUsuario, idLivro);

        response.sendRedirect("sucesso.html");
    }
}
