package dao;

import config.ConexaoFirebird;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public boolean validarLogin(String login, String senha) {

        String sql = "SELECT 1 FROM USUARIOS " +
                "WHERE LOGIN = ? AND SENHA = ? AND ATIVO = 1";

        try (Connection conn = ConexaoFirebird.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
