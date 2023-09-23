package com.alura.hotel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alura.hotel.modelo.Huesped;

public class HuespedDAO extends ConnectionDAO{

    public void registrarHuesped(Huesped huesped) {
        try (Connection con = connectionFactory.MySQLConnection();
        PreparedStatement statement = con.prepareStatement("INSERT INTO huespedes(Nombre, Apellido, Fecha_Nacimiento, Nacionalidad, Telefono, Reserva_ID) VALUES ( ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);){

            statement.setString(1, huesped.getNombre());
			statement.setString(2, huesped.getApellido());
			statement.setDate(3, Date.valueOf(huesped.getFechaDeNacimiento()));
			statement.setString(4, huesped.getNacionalidad());
			statement.setLong(5, huesped.getTelefono());
			statement.setInt(6, huesped.getReserva_ID());

			statement.execute();

			try(ResultSet resultSet = statement.getGeneratedKeys();){
				while(resultSet.next()){
					huesped.setId(resultSet.getInt(1));
				}
			}
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer eliminarHuesped(Integer id) {
        try (Connection con = connectionFactory.MySQLConnection();
        PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE ID = ?");){

            statement.setInt(1, id);
			int registrosAfectados = statement.executeUpdate();
			
            return registrosAfectados;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Huesped> listarHuespedes() {
        try(Connection con = connectionFactory.MySQLConnection();
			PreparedStatement statement = con.prepareStatement("SELECT ID, Nombre, Apellido, Fecha_Nacimiento, Nacionalidad, Telefono, Reserva_ID FROM huespedes");
			ResultSet resultadoQuery = statement.executeQuery();){

			List<Huesped> huespedes = new ArrayList<>();
	
			while(resultadoQuery.next()){
                
				Huesped huesped = new Huesped(Integer.valueOf(resultadoQuery.getInt("ID")), resultadoQuery.getString("Nombre"), resultadoQuery.getString("Apellido"), resultadoQuery.getDate("Fecha_Nacimiento").toLocalDate(), resultadoQuery.getString("Nacionalidad"), resultadoQuery.getLong("Telefono"), Integer.valueOf(resultadoQuery.getInt("Reserva_ID")));

                huespedes.add(huesped);

			}
	
			return huespedes;
	
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
    }

    public Integer editarHuesped(Huesped huespedActualizado) {
		try (Connection con = connectionFactory.MySQLConnection();
        PreparedStatement statement = con.prepareStatement("UPDATE huespedes SET Nombre = ?, Apellido = ?, Fecha_Nacimiento = ?, Nacionalidad = ?, Telefono = ? WHERE ID = ?");){

            statement.setString(1, huespedActualizado.getNombre());
			statement.setString(2, huespedActualizado.getApellido());
			statement.setDate(3, Date.valueOf(huespedActualizado.getFechaDeNacimiento()));
			statement.setString(4, huespedActualizado.getNacionalidad());
			statement.setLong(5, huespedActualizado.getTelefono());

			statement.setInt(6, huespedActualizado.getId());

			int registrosAfectados = statement.executeUpdate();
			
            return registrosAfectados;

        } catch (SQLException e) {
			e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
}
