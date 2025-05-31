package dao;

import bibliotecaFacil.modelo.Aluno;
import bibliotecaFacil.modelo.Professor;
import bibliotecaFacil.modelo.Usuario;
import bibliotecaFacil.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, matricula, cpf, email, usuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setInt(2, usuario.getMatricula());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getTipo().toUpperCase());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario;
                String tipo = rs.getString("usuario");

                if ("ALUNO".equalsIgnoreCase(tipo)) {
                    usuario = new Aluno(
                            rs.getInt("matricula"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email")
                    );
                } else {
                    usuario = new Professor(
                            rs.getInt("matricula"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email")
                    );
                }

                lista.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }
}
