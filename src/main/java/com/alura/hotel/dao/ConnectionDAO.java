package com.alura.hotel.dao;

import com.alura.hotel.factory.ConnectionFactory;

public abstract class ConnectionDAO {

    protected static final ConnectionFactory connectionFactory = new ConnectionFactory();

}
