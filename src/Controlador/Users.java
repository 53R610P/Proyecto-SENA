/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp; 
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 * @author yeyo_
 */
public class Users {
    
            Connection connection;

            private Integer id_usuario;
            private String nombre_usuario;
            private String apellido_usuario;
            private String documento_usuario;
            private String email_usuario;
            private String contraseña_usuario;
            private String telefono_usuario;
            private String direccion_usuario;
            private Timestamp fecha_creacion;
            private Timestamp fecha_actualizacion;
            private Integer id_ciudad;
            private Integer id_tipo_documento;
            private Integer id_categoria_usuario;   
            private String estado_usuario;
            private byte[] foto;
    
    public Users()  {
        
        }
             
           
    public Users(Integer id_usuario, String NOMBRE_USUARIO, String APELLIDO_USUARIO, String DOCUMENTO_USUARIO,
                     String EMAIL_USUARIO, String CONTRASEÑA_USUARIO, String TELEFONO_USUARIO, String DIRECCION_USUARIO, 
                     Timestamp FECHA_CREACION, Timestamp FECHA_ACTUALIZACION, Integer ID_CIUDAD, Integer ID_TIPO_DOCUMENTO, 
                     Integer ID_CATEGORIA_USUARIO, String ESTADO_USUARIO,byte[] FOTO) {
                        this.id_usuario = id_usuario;
                        this.nombre_usuario = NOMBRE_USUARIO;
                        this.apellido_usuario = APELLIDO_USUARIO;
                        this.documento_usuario = DOCUMENTO_USUARIO;
                        this.email_usuario = EMAIL_USUARIO;
                        this.contraseña_usuario = CONTRASEÑA_USUARIO;
                        this.telefono_usuario = TELEFONO_USUARIO;
                        this.direccion_usuario = DIRECCION_USUARIO;
                        this.fecha_creacion = FECHA_CREACION;
                        this.fecha_actualizacion = FECHA_ACTUALIZACION;
                        this.id_ciudad = ID_CIUDAD;
                        this.id_tipo_documento = ID_TIPO_DOCUMENTO;
                        this.id_categoria_usuario = ID_CATEGORIA_USUARIO;
                        this.estado_usuario = ESTADO_USUARIO;
                        this.foto=FOTO;
    }

   

    public Integer getId_usuario() {
        return id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public String getApellido_usuario() {
        return apellido_usuario;
    }

    public String getDocumento_usuario() {
        return documento_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public String getContraseña_usuario() {
        return contraseña_usuario;
    }

    public String getTelefono_usuario() {
        return telefono_usuario;
    }

    public String getDireccion_usuario() {
        return direccion_usuario;
    }

    public Timestamp getFecha_creacion() {
        return fecha_creacion;
    }

    public Timestamp getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public Integer getId_ciudad() {
        return id_ciudad;
    }

    public Integer getId_tipo_documento() {
        return id_tipo_documento;
    }

    public Integer getId_categoria_usuario() {
        return id_categoria_usuario;
    }

    public String getEstado_usuario() {
        return estado_usuario;
    }

    public byte[] getFoto() {
        return foto;
    }
    
    
    public static void insertUser(Users usuario) throws SQLException {
        java.sql.Connection con = (java.sql.Connection) modelo.Conexion.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("INSERT INTO `usuarios`(`nombre_usuario`, `apellido_usuario`, `email_usuario`, `contrasena_usuario`, "
                    + "`telefono_usuario`,`direccion_usuario`,`numero_documento`,`foto`) VALUES (?,?,?,?,?,?,?,?)");

            ps.setString(1, usuario.getNombre_usuario());
            ps.setString(2, usuario.getApellido_usuario());
            ps.setString(3, usuario.getEmail_usuario());
            ps.setString(4, usuario.getContraseña_usuario());
            ps.setString(5, usuario.getTelefono_usuario());
            ps.setString(6, usuario.getDireccion_usuario());
            ps.setString(7, usuario.getDocumento_usuario());
            ps.setBytes(8, usuario.getFoto());
            
   
            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "New User Added");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
                } catch (SQLException ex) {
                        Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }
    
    
    
     public ArrayList<Users> UsuariosList(){
        
        ArrayList<Users> usuario_list = new ArrayList<>();
        connection = modelo.Conexion.getConnection();

        ResultSet rs;
        PreparedStatement ps;

               String query = "SELECT `id_usuario`,`nombre_usuario`,`apellido_usuario`,`email_usuario`,`contrasena_usuario`, `telefono_usuario`"
                       + ", `direccion_usuario`,`numero_documento`,`fecha_creacion`,`fecha_actualizacion`,`id_ciudad`,`id_tipo_documento`"
                       + ",`id_categoria_usuario`,`estado_usuario`, `foto` FROM `Usuarios`";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
           //el órden del select y del while debe ser como el del constructor
            Users usuario;
            while(rs.next()){
            usuario = new Users (
                                    rs.getInt("id_usuario"),
                                    rs.getString("nombre_usuario"),
                                    rs.getString("apellido_usuario"),
                                    rs.getString("numero_documento"), // Cambia esto si es necesario
                                    rs.getString("email_usuario"),
                                    rs.getString("contrasena_usuario"),
                                    rs.getString("telefono_usuario"),
                                    rs.getString("direccion_usuario"),
                                    rs.getTimestamp("fecha_creacion"), // Añade si necesitas este campo
                                    rs.getTimestamp("fecha_actualizacion"), // Añade si necesitas este campo
                                    rs.getInt("id_ciudad"), // Añade si necesitas este campo
                                    rs.getInt("id_tipo_documento"), // Añade si necesitas este campo
                                    rs.getInt("id_categoria_usuario"), // Añade si necesitas este campo
                                    rs.getString("estado_usuario"),
                                    rs.getBytes("foto")
                                  );
                usuario_list.add(usuario);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario_list;
        
    }
    
     
     
       public static void updateUsers(Users usuario, boolean changeImage)
    {
        Connection con = modelo.Conexion.getConnection();
        PreparedStatement ps;
         //System.out.println("boleano imagen => "+id);
         // si es true es por que cambió la imagen
        if(changeImage)
        {
           
            try {
            
            // ps = con.prepareStatement("INSERT INTO `users`(`full_name`, `username`, `password`, `phone`, `gender`,`picture`) VALUES (?,?,?,?,?,?)");
           
            ps = con.prepareStatement("UPDATE `usuarios` SET `nombre_usuario`=?,`apellido_usuario`=?,`numero_documento`=? ,`email_usuario`=? ,"
                    + "`contrasena_usuario`=? ,`telefono_usuario`=? ,`direccion_usuario`=? ,`foto`=?"
                    + " WHERE `id` = ?");
            // ps.setString(1, product.getName());
            ps.setString(1, usuario.getNombre_usuario());
            ps.setString(2, usuario.getApellido_usuario());
            ps.setString(3, usuario.getDocumento_usuario());
            //como el tipo de usuario no se puede exponer en un formulario el admin crea los diferentes a user
           // ps.setString(3, "user");// the admin will add a user
           
            ps.setString(4, usuario.getEmail_usuario());
            ps.setString(5, usuario.getContraseña_usuario());
            ps.setString(6, usuario.getTelefono_usuario());
            ps.setString(7, usuario.getDireccion_usuario());
            ps.setBytes(8, usuario.getFoto());
            //el ID lo debo enviar para actualizar
            ps.setInt(9, usuario.getId_usuario());

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Product Updated");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

            
        }
        //no actualiza la imagen
        else{
            
            try {
             ps = con.prepareStatement("UPDATE `usuarios` SET `nombre_usuario`=?,`apellido_usuario`=?,`numero_documento`=? ,`email_usuario`=? ,"
                    + "`contrasena_usuario`=? ,`telefono_usuario`=? ,`direccion_usuario`=? ,`foto`=?"
                    + " WHERE `id_usuario` = ?");
            // ps.setString(1, product.getName());
            ps.setString(1, usuario.getNombre_usuario());
            ps.setString(2, usuario.getApellido_usuario());
            ps.setString(3, usuario.getDocumento_usuario());
            //como el tipo de usuario no se puede exponer en un formulario el admin crea los diferentes a user
           // ps.setString(3, "user");// the admin will add a user
           
            ps.setString(4, usuario.getEmail_usuario());
            ps.setString(5, usuario.getContraseña_usuario());
            ps.setString(6, usuario.getTelefono_usuario());
            ps.setString(7, usuario.getDireccion_usuario());
            ps.setBytes(8, usuario.getFoto());
            //el ID lo debo enviar para actualizar
            ps.setInt(9, usuario.getId_usuario());

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Product Updated");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

            
        }
    
    
    }
    
      public static void deleteUsers(Integer id)
    {
          Connection con = modelo.Conexion.getConnection();
        PreparedStatement ps;
        
        
        
        try {
            ps = con.prepareStatement("DELETE FROM `usuarios` WHERE `id_usuario` = ?");
           /*
            
            ps.setInt(1, id);
                ps.executeUpdate(); 
            */
            ps.setInt(1, id);
            //ps.executeUpdate(); 
            // show a confirmation message before deleting the product
            int YesOrNo = JOptionPane.showConfirmDialog(null,"Do You Really Want To Delete This Users","Delete Users", JOptionPane.YES_NO_OPTION);
            if(YesOrNo == 0){
                
                if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Users Deleted");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
                
            }
                        
        } catch (SQLException ex) {
           Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
       
       
    
    
}
