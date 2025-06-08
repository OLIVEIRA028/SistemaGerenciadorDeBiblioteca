package bibliotecaFacil.dao;

import bibliotecaFacil.dao.Conexao;
import java.sql.*;

public class EmprestimoDAO {

    public void registrarEmprestimo(int matricula, String isbn) {
        String sql = "INSERT INTO emprestimos (matricula, isbn, data_emprestimo, status) VALUES (?, ?, CURDATE(), 'ATIVO')";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);
            stmt.setString(2, isbn);
            stmt.executeUpdate();

            // Atualiza o status do livro para indisponível
            atualizarDisponibilidadeLivro(conn, isbn, false);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registrarDevolucao(int idEmprestimo, String isbn) {
        String sql = "UPDATE emprestimos SET status = 'DEVOLVIDO', data_devolucao = CURDATE() WHERE id_emprestimo = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmprestimo);
            stmt.executeUpdate();

            // Atualiza o status do livro para disponível
            atualizarDisponibilidadeLivro(conn, isbn, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void atualizarDisponibilidadeLivro(Connection conn, String isbn, boolean disponivel) throws SQLException {
        String sql = "UPDATE livros SET disponivel = ? WHERE isbn = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, disponivel);
            stmt.setString(2, isbn);
            stmt.executeUpdate();
        }
    }
}
