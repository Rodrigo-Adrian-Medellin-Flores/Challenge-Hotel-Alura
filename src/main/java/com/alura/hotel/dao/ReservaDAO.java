package com.alura.hotel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alura.hotel.modelo.Reserva;

public class ReservaDAO extends ConnectionDAO{

    public void registrarReserva(Reserva reserva) {
        try (Connection con = connectionFactory.MySQLConnection();
        PreparedStatement statement = con.prepareStatement("INSERT INTO reservas(Fecha_Entrada, Fecha_Salida, Valor, Forma_de_Pago) VALUES ( ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);){

            statement.setDate(1, Date.valueOf(reserva.getFechaEntrada()));
			statement.setDate(2, Date.valueOf(reserva.getFechaSalida()));
			statement.setBigDecimal(3, reserva.getValor());
			statement.setString(4, reserva.getFormaDePago());

			statement.execute();

			try(ResultSet resultSet = statement.getGeneratedKeys();){
				while(resultSet.next()){
					reserva.setId(resultSet.getInt(1));
				}
			}
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

	public Integer eliminarReserva(Integer id){
		try (Connection con = connectionFactory.MySQLConnection();
        PreparedStatement statement = con.prepareStatement("DELETE FROM reservas WHERE ID = ?");){

            statement.setInt(1, id);
			int registrosAfectados = statement.executeUpdate();
			
            return registrosAfectados;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

    public List<Reserva> listarReservas() {
        try(Connection con = connectionFactory.MySQLConnection();
			PreparedStatement statement = con.prepareStatement("SELECT ID, Fecha_Entrada, Fecha_Salida, Valor, Forma_de_Pago FROM reservas");
			ResultSet resultadoQuery = statement.executeQuery();){

			List<Reserva> reservas = new ArrayList<>();
	
			while(resultadoQuery.next()){
                
				Reserva reserva = new Reserva(Integer.valueOf(resultadoQuery.getInt("ID")), resultadoQuery.getDate("Fecha_Entrada").toLocalDate(), resultadoQuery.getDate("Fecha_Salida").toLocalDate(), resultadoQuery.getBigDecimal("Valor"), resultadoQuery.getString("Forma_de_Pago"));

                reservas.add(reserva);

			}
	
			return reservas;
	
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
    }

    public Integer getHuespedId(Integer idReserva) {
		try(Connection con = connectionFactory.MySQLConnection();
			PreparedStatement statement = con.prepareStatement("SELECT R.ID, H.ID FROM reservas R INNER JOIN huespedes H ON R.ID = H.Reserva_ID WHERE R.ID = ?");){

			statement.setInt(1, idReserva);
			
			Integer idHuesped = 0;

			try(ResultSet resultadoQuery = statement.executeQuery()){
				while(resultadoQuery.next()){
					idHuesped = resultadoQuery.getInt("H.ID");
				}
			}
			
			return idHuesped;
	
		} catch (SQLException e){

			throw new RuntimeException(e);

		}
    }

	public Integer editarReserva(Reserva reservaActualizada) {
		try (Connection con = connectionFactory.MySQLConnection();
        PreparedStatement statement = con.prepareStatement("UPDATE reservas SET Fecha_Entrada = ?, Fecha_Salida = ?, Valor = ?, Forma_de_Pago = ? WHERE ID = ?");){

			statement.setDate(1, Date.valueOf(reservaActualizada.getFechaEntrada()));
			statement.setDate(2, Date.valueOf(reservaActualizada.getFechaSalida()));
			statement.setBigDecimal(3, reservaActualizada.getValor());
			statement.setString(4, reservaActualizada.getFormaDePago());

			statement.setInt(5, reservaActualizada.getId());

			int registrosAfectados = statement.executeUpdate();
			
            return registrosAfectados;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
    
}
