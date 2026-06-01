/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.DriverManager;

/**
 *
 * @author yeyo_
 */
public class Conexion {
    private static String dbname = "corsantos_2";
    private static String username = "root";
    private static String password = "12345678";
    
       static java.sql.Connection con=null;
    public static java.sql.Connection getConnection()
    {
        if (con != null) return con;
        // get db, user, pass from settings file
        return getConnection(dbname, username, password);
    }

    private static java.sql.Connection getConnection(String db_name,String user_name,String password)
    {
        try
        {   //com.mysql.cj.jdbc.Driver Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/"+db_name+"?user="+user_name+"&password="+password);
            System.out.println("connected");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return con;     
    
    }
}
