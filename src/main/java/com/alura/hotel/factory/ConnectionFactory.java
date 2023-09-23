package com.alura.hotel.factory;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {

    private DataSource dataSource; 

    public ConnectionFactory(){

        ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();

        pooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/hotel_alura?useTimeZone=true&serverTimeZone=UTC");
        pooledDataSource.setUser("root");
        pooledDataSource.setPassword("root");

        pooledDataSource.setMinPoolSize(1);
        pooledDataSource.setMaxPoolSize(5);
        pooledDataSource.setInitialPoolSize(1);
        pooledDataSource.setAcquireIncrement(0);

        this.dataSource = pooledDataSource;
    }
    

    public Connection MySQLConnection() throws SQLException{

        return this.dataSource.getConnection();
        
    }
    
}
