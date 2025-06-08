package bibliotecaFacil.servlet;

import bibliotecaFacil.dao.Conexao;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/emprestimoDevolucao")
public class EmprestimoDevolucaoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if ("emprestimo".equalsIgnoreCase(acao)) {
                registrarEmprestimo(request, out);
            } else if ("devolucao".equalsIgnoreCase(acao)) {
                registrarDevolucao(request, out);
            } else {
                out.println("<p>Ação inválida.</p>");
            }
            out.println("<br><a href='index.html'>Voltar</a>");
        }
    }

    private void registrarEmprestimo(HttpServletRequest request, PrintWriter out) {
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        int idLivro = Integer.parseInt(request.getParameter("idLivro"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = Conexao.getConexao()) {

                // Verifica se livro está disponível
                String checkLivro = "SELECT status FROM livros WHERE id = ?";
                try (PreparedStatement psCheck = conn.prepareStatement(checkLivro)) {
                    psCheck.setInt(1, idLivro);
                    ResultSet rs = psCheck.executeQuery();
                    if (rs.next()) {
                        boolean disponivel = rs.getBoolean("status");
                        if (!disponivel) {
                            out.println("<p>Livro não está disponível para empréstimo.</p>");
                            return;
                        }
                    } else {
                        out.println("<p>Livro não encontrado.</p>");
                        return;
                    }
                }

                // Insere empréstimo
                String insertEmprestimo = "INSERT INTO emprestimos (id_usuario, id_livro, data_emprestimo, status) VALUES (?, ?, NOW(), ?)";
                try (PreparedStatement psInsert = conn.prepareStatement(insertEmprestimo)) {
                    psInsert.setInt(1, idUsuario);
                    psInsert.setInt(2, idLivro);
                    psInsert.setString(3, "EMPRESTADO");
                    int rows = psInsert.executeUpdate();

                    if (rows > 0) {
                        // Atualiza status do livro para indisponível (false)
                        String updateLivro = "UPDATE livros SET status = false WHERE id = ?";
                        try (PreparedStatement psUpdate = conn.prepareStatement(updateLivro)) {
                            psUpdate.setInt(1, idLivro);
                            psUpdate.executeUpdate();
                        }
                        out.println("<p>Empréstimo registrado com sucesso!</p>");
                    } else {
                        out.println("<p>Erro ao registrar empréstimo.</p>");
                    }
                }

            }
        } catch (Exception e) {
            out.println("<p>Erro: " + e.getMessage() + "</p>");
        }
    }

    private void registrarDevolucao(HttpServletRequest request, PrintWriter out) {
        int idEmprestimo = Integer.parseInt(request.getParameter("idEmprestimo"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = Conexao.getConexao()) {

                // Verifica se empréstimo existe e está ativo
                String checkEmprestimo = "SELECT id_livro, status FROM emprestimos WHERE id = ?";
                try (PreparedStatement psCheck = conn.prepareStatement(checkEmprestimo)) {
                    psCheck.setInt(1, idEmprestimo);
                    ResultSet rs = psCheck.executeQuery();
                    if (rs.next()) {
                        String status = rs.getString("status");
                        int idLivro = rs.getInt("id_livro");

                        if (!"EMPRESTADO".equalsIgnoreCase(status)) {
                            out.println("<p>Empréstimo já foi devolvido ou não está ativo.</p>");
                            return;
                        }

                        // Atualiza status do empréstimo para devolvido
                        String updateEmprestimo = "UPDATE emprestimos SET status = 'DEVOLVIDO', data_devolucao = NOW() WHERE id = ?";
                        try (PreparedStatement psUpdate = conn.prepareStatement(updateEmprestimo)) {
                            psUpdate.setInt(1, idEmprestimo);
                            psUpdate.executeUpdate();
                        }

                        // Atualiza status do livro para disponível
                        String updateLivro = "UPDATE livros SET status = true WHERE id = ?";
                        try (PreparedStatement psUpdateLivro = conn.prepareStatement(updateLivro)) {
                            psUpdateLivro.setInt(1, idLivro);
                            psUpdateLivro.executeUpdate();
                        }

                        out.println("<p>Devolução registrada com sucesso!</p>");
                    } else {
                        out.println("<p>Empréstimo não encontrado.</p>");
                    }
                }

            }
        } catch (Exception e) {
            out.println("<p>Erro: " + e.getMessage() + "</p>");
        }
    }
}
