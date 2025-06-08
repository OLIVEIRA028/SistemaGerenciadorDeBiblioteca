package bibliotecaFacil.dao;

import java.sql.*;
import modelo.Emprestimo;
import bibliotecaFacil.dao.Conexao;

public class EmprestimoDAO {
    public void registrarEmprestimo(int idUsuario, int idLivro) {
        try (Connection conn = Conexao.getConexao()) {
            String sql1 = "INSERT INTO emprestimos (id_usuario, data, status) VALUES (?, NOW(), 'ativo')";
            PreparedStatement stmt1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            stmt1.setInt(1, idUsuario);
            stmt1.executeUpdate();

            ResultSet generatedKeys = stmt1.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idEmprestimo = generatedKeys.getInt(1);

                String sql2 = "INSERT INTO emprestimo_livros (id_emprestimo, id_livro) VALUES (?, ?)";
                PreparedStatement stmt2 = conn.prepareStatement(sql2);
                stmt2.setInt(1, idEmprestimo);
                stmt2.setInt(2, idLivro);
                stmt2.executeUpdate();

                new LivroDAO().atualizarStatus(idLivro, false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registrarDevolucao(int idEmprestimo, int idLivro) {
        try (Connection conn = Conexao.getConexao()) {
            String sql1 = "UPDATE emprestimos SET status = 'finalizado' WHERE id = ?";
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setInt(1, idEmprestimo);
            stmt1.executeUpdate();

            new LivroDAO().atualizarStatus(idLivro, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
