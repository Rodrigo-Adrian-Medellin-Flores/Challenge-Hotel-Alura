package com.alura.hotel.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reserva {

    private Integer id;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private BigDecimal valor;
    private String formaDePago;

    public Reserva(Integer id, LocalDate fechaEntrada, LocalDate fechaSalida, BigDecimal valor, String formaDePago) {
        this.id = id;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.valor = valor;
        this.formaDePago = formaDePago;
    }

    public Reserva(LocalDate fechaEntrada, LocalDate fechaSalida, BigDecimal valor, String formaDePago) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.valor = valor;
        this.formaDePago = formaDePago;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getFormaDePago() {
        return formaDePago;
    }
    
}
