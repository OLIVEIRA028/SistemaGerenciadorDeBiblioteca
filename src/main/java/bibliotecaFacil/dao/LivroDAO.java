package bibliotecaFacil.dao;

import bibliotecaFacil.modelo.Livro;
import bibliotecaFacil.dao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public void inserir(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, isbn, ano, editora, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getIsbn());
            stmt.setInt(4, livro.getAno());
            stmt.setString(5, livro.getEditora());
            stmt.setString(6, livro.isDisponivel() ? "disponivel" : "emprestado");

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Livro> listar() {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM livros";

        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        rs.getInt("ano"),
                        rs.getString("editora"),
                        rs.getString("status").equalsIgnoreCase("disponivel")
                );
                lista.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void atualizarStatus(int idLivro, boolean disponivel) {
        String sql = "UPDATE livros SET status = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, disponivel ? "disponivel" : "emprestado");
            stmt.setInt(2, idLivro);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Outros métodos: buscarPorId(), deletar(), etc.
}
