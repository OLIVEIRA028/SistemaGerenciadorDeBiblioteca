package com.biblioteca.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/livro")
public class LivroServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://gx97kbnhgjzh3efb.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/h5eklyexw4etb6sg";
    private static final String JDBC_USER = "wzgu81p8i02bl0fg";
    private static final String JDBC_PASS = "e8p3bqjlex6zymfs";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Lista de Livros</title></head><body>");
            out.println("<h2>Livros Cadastrados</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>ISBN</th><th>Título</th><th>Autor</th><th>Editora</th><th>Status</th></tr>");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
                    String sql = "SELECT isbn, titulo, autor, editora, status FROM livros";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                out.println("<tr>");
                                out.println("<td>" + rs.getString("isbn") + "</td>");
                                out.println("<td>" + rs.getString("titulo") + "</td>");
                                out.println("<td>" + rs.getString("autor") + "</td>");
                                out.println("<td>" + rs.getString("editora") + "</td>");
                                out.println("<td>" + (rs.getBoolean("status") ? "Disponível" : "Emprestado") + "</td>");
                                out.println("</tr>");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                out.println("<tr><td colspan='5'>Erro ao acessar banco: " + e.getMessage() + "</td></tr>");
            }

            out.println("</table>");
            out.println("<br><a href='index.html'>Voltar</a>");
            out.println("</body></html>");
        }
    }
}
