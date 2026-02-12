package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFirebird {

    private static final String URL =
            "jdbc:firebirdsql://localhost:3050/C:/Adonai_java/BD/ADONAIDB.FDB?encoding=UTF8";

    private static final String USER = "SYSDBA";
    private static final String PASS = "masterkey"; // ou senha custom

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
