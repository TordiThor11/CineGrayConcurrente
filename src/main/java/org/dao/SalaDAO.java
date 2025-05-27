package org.dao;
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
    public TreeMap<LocalDateTime, Pelicula> getCronogramaPeliculas(String nombreSala) throws SQLException{
        TreeMap<LocalDateTime, Pelicula> cronograma = new TreeMap<>();
        String sql = "SELECT cp.fecha, p.nombre FROM cronograma_peliculas cp JOIN salas s ON cp.id_sala = s.id JOIN peliculas p ON cp.id_pelicula = p.id WHERE s.nombre = ? ORDER BY cp.fecha;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreSala);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LocalDateTime fecha = rs.getTimestamp("fecha").toLocalDateTime();
                    Pelicula pelicula = new Pelicula(rs.getString("nombre"));
                    cronograma.put(fecha, pelicula);
                }
            }
        }
        return cronograma;
    }

    public List<Sala> getAll() throws SQLException {
        String sql = "SELECT nombre FROM salas";
        List<Sala> salas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
//                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                salas.add(new Sala(nombre));
            }

        }

        return salas;
    }
}
