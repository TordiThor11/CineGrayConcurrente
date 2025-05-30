package org.dao;

import org.model.EstadoButaca;
import org.model.Funcion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ButacaDAO {
    private final DataSource dataSource;

    public ButacaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public ButacaDAO() {
        this.dataSource = new DataSource();
    }
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void cargarEstadosDeButacas(Funcion funcion) throws SQLException {   //Carga en la funcion el estado de las butacas.
        String sql = "SELECT fila, columna, estado FROM butacas_reservadas WHERE id_funcion = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, funcion.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int fila = rs.getInt("fila");
                    int columna = rs.getInt("columna");
                    EstadoButaca estado = EstadoButaca.valueOf(rs.getString("estado"));
                    funcion.getButacas()[fila][columna].setEstado(estado);
                }
            }
        }
    }
    public boolean existeReserva(Connection conn, int idFuncion, int fila, int columna) throws SQLException {
        String sql = "SELECT 1 FROM butacas_reservadas WHERE id_funcion = ? AND fila = ? AND columna = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFuncion);
            stmt.setInt(2, fila);
            stmt.setInt(3, columna);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void insertarReserva(Connection conn, int idFuncion, int fila, int columna, EstadoButaca estado) throws SQLException {
        String sql = "INSERT INTO butacas_reservadas (id_funcion, fila, columna, estado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFuncion);
            stmt.setInt(2, fila);
            stmt.setInt(3, columna);
            stmt.setString(4, estado.name());
            stmt.executeUpdate();
        }
    }
}

// Este diseño permite modelar las butacas solo como objetos reservables en memoria,
// y persistir solo aquellas que tienen estado RESERVADA u OCUPADA.
