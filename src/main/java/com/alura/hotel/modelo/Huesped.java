package com.alura.hotel.modelo;

import java.time.LocalDate;

public class Huesped {

    private Integer id;
    private String nombre;
    private String apellido;
    private LocalDate fechaDeNacimiento;
    private String nacionalidad;
    private Long telefono;
    private Integer reserva_ID;

    public Huesped(Integer id, String nombre, String apellido, LocalDate fechaDeNacimiento, String nacionalidad,
            Long telefono, Integer reserva_ID) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
        this.reserva_ID = reserva_ID;
    }

    public Huesped(String nombre, String apellido, LocalDate fechaDeNacimiento, String nacionalidad, Long telefono,
            Integer reserva_ID) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
        this.reserva_ID = reserva_ID;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public Long getTelefono() {
        return telefono;
    }

    public Integer getReserva_ID() {
        return reserva_ID;
    }
    
}
