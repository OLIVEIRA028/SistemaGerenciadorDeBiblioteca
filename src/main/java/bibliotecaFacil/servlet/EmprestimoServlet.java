package bibliotecaFacil.servlet;

import bibliotecaFacil.dao.EmprestimoDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class EmprestimoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        String isbn = request.getParameter("isbn");

        EmprestimoDAO dao = new EmprestimoDAO();
        dao.registrarEmprestimo(idUsuario, idLivro);

        response.sendRedirect("sucesso.html");
    }
}
