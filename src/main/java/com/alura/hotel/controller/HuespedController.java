package com.alura.hotel.controller;

import java.util.List;

import com.alura.hotel.dao.HuespedDAO;
import com.alura.hotel.modelo.Huesped;

public abstract class HuespedController {

    private static HuespedDAO huespedDAO = new HuespedDAO();

    public static void registrarHuesped(Huesped huesped){
        huespedDAO.registrarHuesped(huesped);
    }
    
    public static Integer eliminarHuesped(Integer id){
        return huespedDAO.eliminarHuesped(id);
    }

    public static List<Huesped> listarHuespedes() {
        return huespedDAO.listarHuespedes();
    }

    public static Integer editarHuesped(Huesped huespedActualizado) {
        return huespedDAO.editarHuesped(huespedActualizado);
    }
}
