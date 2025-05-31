package servlet;

import dao.EmprestimoDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DevolucaoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idEmprestimo = Integer.parseInt(request.getParameter("idEmprestimo"));

        EmprestimoDAO dao = new EmprestimoDAO();
        dao.registrarDevolucao(idEmprestimo);

        response.sendRedirect("sucesso.html");
    }
}
