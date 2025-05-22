
package Fontes;

import java.sql.*;

public class UsuarioDAO {
	
    public void cadastrar(Usuario user) throws Exception {
        String sql = "INSERT INTO usuario (email, senha) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getSenha());
            ps.executeUpdate();
        }
    }

    public Usuario autenticar(String email, String senhaCriptografada) throws Exception {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senhaCriptografada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                return u;
            }
            return null;
        }
    }

    public void alterarSenha(String email, String novaSenhaCriptografada) throws Exception {
        String sql = "UPDATE usuario SET senha = ? WHERE email = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, novaSenhaCriptografada);
            ps.setString(2, email);
            ps.executeUpdate();
        }
    }
}