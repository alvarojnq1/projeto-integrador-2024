package com.sid;
import java.sql.*;

public class ConnectionFactory {
    private String usuario = "root";
    private String senha = "imtdb";
    private String host = "localhost";
    private String porta = "3306";
    private String bd = "sid"; 

    public Connection obtemConexao() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + host + ":" + porta + "/" + bd + "?serverTimezone=UTC", usuario, senha);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
