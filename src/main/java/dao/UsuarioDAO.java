package dao;

import modelo.Aluno;
import modelo.Professor;
import modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, matricula, cpf, email, tipo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getMatricula());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getTipo());

            stmt.executeUpdate();
        } catch (SQLException e) {
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
                if (rs.getString("tipo").equalsIgnoreCase("Aluno")) {
                    usuario = new Aluno(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("matricula"),
                            rs.getString("cpf"),
                            rs.getString("email")
                    );
                } else {
                    usuario = new Professor(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("matricula"),
                            rs.getString("cpf"),
                            rs.getString("email")
                    );
                }
                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
