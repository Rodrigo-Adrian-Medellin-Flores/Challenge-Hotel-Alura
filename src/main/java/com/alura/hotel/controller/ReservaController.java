package com.alura.hotel.controller;

import java.util.List;

import com.alura.hotel.dao.ReservaDAO;
import com.alura.hotel.modelo.Reserva;

public abstract class ReservaController {

    private static ReservaDAO reservaDAO = new ReservaDAO();

    public static void registrarReserva(Reserva reserva){
        reservaDAO.registrarReserva(reserva);
    }

    public static Integer eliminarReserva(Integer id){
        return reservaDAO.eliminarReserva(id);
    }

    public static List<Reserva> listarReservas() {
        return reservaDAO.listarReservas();
    }

    public static Integer getHuespedId(Integer idReserva) {
        return reservaDAO.getHuespedId(idReserva);
    }

    public static Integer editarReserva(Reserva reservaActualizada) {
        return reservaDAO.editarReserva(reservaActualizada);
    }
    
}
