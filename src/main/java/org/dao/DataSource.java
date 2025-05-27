package org.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private final String url = "jdbc:mysql://localhost:3306/cine_gray";
    private final String user = "root"; // cambialo por tu usuario
    private final String password = ""; // cambialo por tu contraseña

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
