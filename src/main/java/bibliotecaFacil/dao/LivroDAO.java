package bibliotecaFacil.dao;

import bibliotecaFacil.modelo.Livro;
import bibliotecaFacil.dao.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public void inserir(Livro livro) {
        String sql = "INSERT INTO livros (isbn, titulo, autor, publicacao, editora, disponivel) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, livro.getIsbn());
            stmt.setString(2, livro.getTitulo());
            stmt.setString(3, livro.getAutor());

            // Corrigido: converter java.util.Date para java.sql.Date
            java.util.Date dataPublicacao = livro.getPublicacao();
            if (dataPublicacao != null) {
                stmt.setDate(4, new java.sql.Date(dataPublicacao.getTime()));
            } else {
                stmt.setDate(4, null);
            }

            stmt.setString(5, livro.getEditora());
            stmt.setBoolean(6, livro.isDisponivel());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Livro> listar() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                java.sql.Date sqlDate = rs.getDate("publicacao");
                java.util.Date dataPublicacao = (sqlDate != null) ? new java.util.Date(sqlDate.getTime()) : null;

                Livro livro = new Livro(
                        rs.getString("isbn"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        dataPublicacao,
                        rs.getString("editora"),
                        rs.getBoolean("disponivel")
                );
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public void atualizarStatus(String isbn, boolean disponivel) {
        String sql = "UPDATE livros SET disponivel = ? WHERE isbn = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setBoolean(1, disponivel);
            stmt.setString(2, isbn);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Livro buscarPorIsbn(String isbn) {
        String sql = "SELECT * FROM livros WHERE isbn = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                java.sql.Date sqlDate = rs.getDate("publicacao");
                java.util.Date dataPublicacao = (sqlDate != null) ? new java.util.Date(sqlDate.getTime()) : null;

                return new Livro(
                        rs.getString("isbn"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        dataPublicacao,
                        rs.getString("editora"),
                        rs.getBoolean("disponivel")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
