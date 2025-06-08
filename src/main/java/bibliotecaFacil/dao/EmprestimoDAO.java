package bibliotecaFacil.dao;

import bibliotecaFacil.dao.Conexao;
import java.sql.*;

public class EmprestimoDAO {

    public void registrarEmprestimo(int matricula, int idLivro) {
        String sql = "INSERT INTO emprestimos (matricula, id_livro, data_emprestimo, status) VALUES (?, ?, CURDATE(), 'ATIVO')";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);
            stmt.setInt(2, idLivro);
            stmt.executeUpdate();

            // Atualiza o status do livro para indisponível
            atualizarDisponibilidadeLivro(conn, idLivro, false);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registrarDevolucao(int idEmprestimo, int idLivro) {
        String sql = "UPDATE emprestimos SET status = 'DEVOLVIDO', data_devolucao = CURDATE() WHERE id_emprestimo = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmprestimo);
            stmt.executeUpdate();

            // Atualiza o status do livro para disponível
            atualizarDisponibilidadeLivro(conn, idLivro, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void atualizarDisponibilidadeLivro(Connection conn, int idLivro, boolean disponivel) throws SQLException {
        String sql = "UPDATE livros SET disponivel = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, disponivel);
            stmt.setInt(2, idLivro);
            stmt.executeUpdate();
        }
    }
}
