package org.dao;
import org.model.Funcion;
import org.model.Pelicula;
import org.model.Sala;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class SalaDAO {
    private final DataSource dataSource;

    public SalaDAO() {
        this.dataSource = new DataSource();
    }
    public List<Sala> getTodasLasSalas() throws SQLException {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT id, nombre, filas, columnas FROM salas";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int filas = rs.getInt("filas");
                int columnas = rs.getInt("columnas");
                salas.add(new Sala(id, nombre, filas, columnas));
            }
        }
        return salas;
    }

    public void cargarFuncionesDeSala(Sala sala) throws Exception {
        String sql = "SELECT f.id, f.horario, p.id AS pid, p.nombre AS pnombre FROM funciones f " +
                "JOIN peliculas p ON f.id_pelicula = p.id WHERE f.id_sala = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sala.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idFuncion = rs.getInt("id");
                    LocalDateTime horario = rs.getTimestamp("horario").toLocalDateTime();
                    Pelicula pelicula = new Pelicula(rs.getInt("pid"), rs.getString("pnombre"));
                    Funcion funcion = new Funcion(idFuncion, horario, pelicula, sala.getFilas(), sala.getColumnas());
                    sala.agregarFuncion(funcion);
                }
            }
        }
    }
}
