/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;
import Controlador.Users;
import Reportes.Generador_de_Reportes;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.JTableHeader;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Container;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.PopupMenu;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author yeyo_
 */
public final class Administrador_usuarios extends javax.swing.JFrame {
 
     public static Administrador_usuarios ventanaPrincipal;
     private Integer productId;
    // get the image path
     String imagePth = null;
    
     int pos = 0;
     
     // Variables para manejar la webcam
     private Webcam webcam;
    private PopupMenu jTable_USERS;
     
     

    /**
     * Creates new form CRUD_usuarios
     * @throws java.io.IOException
     */
    public Administrador_usuarios() throws IOException {
        initComponents();  
        
        this.setLocationRelativeTo(null);
         populateJtable();
         jTable_Usuarios.setShowGrid(true);
        jTable_Usuarios.setGridColor(Color.BLUE);
        jTable_Usuarios.setSelectionBackground(Color.gray);
        JTableHeader th = jTable_Usuarios.getTableHeader();

        th.setFont(new Font("Tahoma", Font.PLAIN, 12));
        // create a button group for the radiobuttons
       
    }
    
    //*public byte[] imageToByteArray(BufferedImage image) {
      //  ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //try {
          //  ImageIO.write(image, "jpg", baos); // Convertir la imagen a formato JPG o PNG
            //return baos.toByteArray();
       // } catch (IOException e) {
         //   e.printStackTrace();
           // return null;
       // }
    //}
    
    
    
     private void initWebcam() {
    webcam = Webcam.getDefault(); // Obtiene la webcam predeterminada
    if (webcam != null) {
        webcam.setViewSize(WebcamResolution.VGA.getSize()); // Establece la resolución de la webcam
        webcam.open(); // Abre la cámara

        // Crear un hilo para actualizar la imagen en el JLabel
        new VideoFeedUpdater().start();

    } else {
        JOptionPane.showMessageDialog(this, "No se encontró la cámara");
    }
}

// Clase interna para actualizar el JLabel con las imágenes de la webcam
private class VideoFeedUpdater extends Thread {
    @Override
    public void run() {
        while (webcam.isOpen()) {
            // Captura la imagen de la webcam
            BufferedImage image = webcam.getImage();
            
            if (image != null) {
                // Escalar la imagen a las dimensiones del JLabel
                ImageIcon icon = new ImageIcon(image.getScaledInstance(
                    jLabel_Foto_registro.getWidth(), 
                    jLabel_Foto_registro.getHeight(), 
                    Image.SCALE_SMOOTH));

                // Actualizar el JLabel con la imagen escalada
                jLabel_Foto_registro.setIcon(icon);
            }

            try {
                // Actualizar cada 30 ms aproximadamente (33 FPS)
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
    



//Damos nombres al encabezado de la tabla  y por medio del los getter llenamos los campos
    public void populateJtable() throws IOException{
        Controlador.Users usuario = new Controlador.Users();
    ArrayList<Controlador.Users> usuarioList = usuario.UsuariosList();
    
    String[] colNames = {"Id", "Nombre", "Apellido", "Email", "Contraseña", "Telefono", "Direccion", "Numero Documento",
                         "Fecha Actualizacion", "Estado Usuario", "Foto"};

    Object[][] rows = new Object[usuarioList.size()][11]; // Asegúrate de que el tamaño del array es correcto

    for (int i = 0; i < usuarioList.size(); i++) {
        rows[i][0] = usuarioList.get(i).getId_usuario();
        rows[i][1] = usuarioList.get(i).getNombre_usuario();
        rows[i][2] = usuarioList.get(i).getApellido_usuario();
        rows[i][3] = usuarioList.get(i).getEmail_usuario();
        rows[i][4] = usuarioList.get(i).getContraseña_usuario();
        rows[i][5] = usuarioList.get(i).getTelefono_usuario();
        rows[i][6] = usuarioList.get(i).getDireccion_usuario();
        rows[i][7] = usuarioList.get(i).getDocumento_usuario();
        rows[i][8] = usuarioList.get(i).getFecha_actualizacion(); 
        rows[i][9] = usuarioList.get(i).getEstado_usuario();
        
        // Manejar la imagen
        byte[] imageBytes = usuarioList.get(i).getFoto(); // Obtén la foto de cada usuario
        if (imageBytes != null) {
            try {
                // Convertir byte[] a BufferedImage
                InputStream in = new ByteArrayInputStream(imageBytes);
                BufferedImage bufferedImage = ImageIO.read(in);
                
                // Escalar la imagen al tamaño deseado
                Image scaledImage = bufferedImage.getScaledInstance(120, 80, Image.SCALE_SMOOTH);
                ImageIcon pic = new ImageIcon(scaledImage);
                
                rows[i][10] = pic;
            } catch (IOException e) {
                e.printStackTrace();
                rows[i][10] = null; // O una imagen por defecto
            }
        } else {
            // Puedes asignar una imagen por defecto o dejarlo como null
            rows[i][10] = null; // O new ImageIcon("ruta/a/imagen/default.png");
        }
    }
    
    modelo.MyTableModel mmd = new modelo.MyTableModel(rows, colNames);
    jTable_Usuarios.setModel(mmd);
    jTable_Usuarios.setRowHeight(80);
    jTable_Usuarios.getColumnModel().getColumn(10).setPreferredWidth(120);      
                }
    
    public void ShowItem(int index)
    {
        
         //jTextField_ID
           jTextField_Id.setText(Integer.toString(getUsuariosList().get(index).getId_usuario()));
           jTextField_Nombre_Usuario3.setText(getUsuariosList().get(index).getNombre_usuario());
           jTextField_Aplellido_Usuario3.setText(getUsuariosList().get(index).getApellido_usuario());
          //getPassword()); getFullname());
           jTextField_Numero_Documento_Usuario3.setText(getUsuariosList().get(index).getDocumento_usuario());
          
           jTextField_Correo_Electronico3.setText(getUsuariosList().get(index).getEmail_usuario());
           jPasswordField_Contraseña3.setText(getUsuariosList().get(index).getContraseña_usuario());
           jTextField_Telefono_Usuario3.setText(getUsuariosList().get(index).getTelefono_usuario());
           jTextField_Direccion_Usuario3.setText(getUsuariosList().get(index).getDireccion_usuario());
           jTextField_Estado_usuario.setText(getUsuariosList().get(index).getEstado_usuario());
           jLabel_Foto_registro.setIcon(ResizeImage(null, getUsuariosList().get(index).getFoto()));
    }
    
     private BufferedImage captureImage() {
        if (webcam != null && webcam.isOpen()) {
            return webcam.getImage(); // Captura la imagen de la webcam
        }
        return null; // Devuelve null si la cámara no está abierta
    }
     
     public ImageIcon ResizeImage(String imagePath, byte[] pic)
    {
        ImageIcon myImage = null;
        
        if(imagePath != null)
        {
            myImage = new ImageIcon(imagePath);
        }else{
            myImage = new ImageIcon(pic);
        }
        
        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(jLabel_Foto_registro.getWidth(),jLabel_Foto_registro.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;
        
    }
     
     
      public boolean verifyFields()
    {
        
            String Fname = jTextField_Nombre_Usuario3.getText();
            String Lname = jTextField_Aplellido_Usuario3.getText();
            String pass1 = new String(jPasswordField_Contraseña3.getPassword());
            String phone = jTextField_Telefono_Usuario3.getText();
            String dirc = jTextField_Direccion_Usuario3.getText();
            String mail = jTextField_Correo_Electronico3.getText();
            String IdNumber = jTextField_Numero_Documento_Usuario3.getText();

    // Validar que todos los campos estén completos
    if (IdNumber.isEmpty() || Fname.isEmpty() || Lname.isEmpty() || pass1.isEmpty() || phone.isEmpty() || dirc.isEmpty() || mail.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Todos los campos deben ser completados");
        return false;
    } 
        // check if the two password are equals
      /*  else if(!pass1.equals(pass2))
        {
           JOptionPane.showMessageDialog(null, "Password Doesn't Match","Confirm Password",2); 
           return false;
        }*/
        
        // if everything is ok
        else{
            return true;
        }
    
    
    }
     
      public ArrayList<Users> getUsuariosList() {
    ArrayList<Users> usuarioList = new ArrayList<>();
    // Connection con = getConnection();
    Connection con = modelo.Conexion.getConnection();
    String query = "SELECT * FROM usuarios";
    
    Statement st;
    ResultSet rs;
    
    try {
        st = con.createStatement();
        rs = st.executeQuery(query);
        Users user;
        
        while (rs.next()) { 
            // cargo cada registro llamándolo desde la tabla con sus respectivos nombres de campos
            user = new Users(
                rs.getInt("id_usuario"),
                rs.getString("nombre_usuario"),
                rs.getString("apellido_usuario"),
                rs.getString("numero_documento"), 
                rs.getString("email_usuario"),
                rs.getString("contrasena_usuario"),
                rs.getString("telefono_usuario"),
                rs.getString("direccion_usuario"),
                rs.getTimestamp("fecha_creacion"), 
                rs.getTimestamp("fecha_actualizacion"), 
                rs.getInt("id_ciudad"), 
                rs.getInt("id_tipo_documento"), 
                rs.getInt("id_categoria_usuario"), 
                rs.getString("estado_usuario"),
                rs.getBytes("foto")
            );
            usuarioList.add(user); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(Administrador_usuarios.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return usuarioList; 
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField_Nombre_Usuario3 = new javax.swing.JTextField();
        jPasswordField_Contraseña3 = new javax.swing.JPasswordField();
        jTextField_Aplellido_Usuario3 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTextField_Numero_Documento_Usuario3 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jTextField_Correo_Electronico3 = new javax.swing.JTextField();
        jTextField_Telefono_Usuario3 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jTextField_Direccion_Usuario3 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jTextField_Estado_usuario = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jButton_Registar3 = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Usuarios = new javax.swing.JTable();
        jTextField_Id = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton_Actualizar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel_Foto_registro = new javax.swing.JLabel();
        jButton_Abrir_camara = new javax.swing.JButton();
        jButton_Tomar_foto = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton_imprimir = new javax.swing.JButton();
        jButton_Excel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton_Primero = new javax.swing.JButton();
        jButton_Ultimo = new javax.swing.JButton();
        jButton_Siguiente = new javax.swing.JButton();
        jButton_Anterior = new javax.swing.JButton();
        jLabel_RegresaralMenu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(987, 680));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 158, 255), 5, true), new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 8, true)));
        jPanel1.setMaximumSize(new java.awt.Dimension(987, 680));
        jPanel1.setMinimumSize(new java.awt.Dimension(987, 680));
        jPanel1.setPreferredSize(new java.awt.Dimension(987, 680));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField_Nombre_Usuario3.setMaximumSize(new java.awt.Dimension(169, 25));
        jTextField_Nombre_Usuario3.setMinimumSize(new java.awt.Dimension(169, 25));
        jTextField_Nombre_Usuario3.setPreferredSize(new java.awt.Dimension(169, 25));
        jPanel1.add(jTextField_Nombre_Usuario3, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 118, 154, -1));
        jTextField_Nombre_Usuario3.getAccessibleContext().setAccessibleName("");

        jPasswordField_Contraseña3.setText("jPasswordField1");
        jPasswordField_Contraseña3.setMaximumSize(new java.awt.Dimension(169, 25));
        jPasswordField_Contraseña3.setMinimumSize(new java.awt.Dimension(169, 25));
        jPasswordField_Contraseña3.setPreferredSize(new java.awt.Dimension(169, 25));
        jPasswordField_Contraseña3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField_Contraseña3ActionPerformed(evt);
            }
        });
        jPanel1.add(jPasswordField_Contraseña3, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 254, 152, -1));

        jTextField_Aplellido_Usuario3.setMaximumSize(new java.awt.Dimension(169, 25));
        jTextField_Aplellido_Usuario3.setMinimumSize(new java.awt.Dimension(169, 25));
        jTextField_Aplellido_Usuario3.setPreferredSize(new java.awt.Dimension(169, 25));
        jPanel1.add(jTextField_Aplellido_Usuario3, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 153, 154, -1));

        jLabel37.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 158, 255));
        jLabel37.setText("USUARIOS");
        jPanel1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 43, 105, -1));

        jLabel38.setText("Contraseña");
        jPanel1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 258, 70, -1));

        jLabel39.setText("Nombres");
        jPanel1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 118, 60, -1));

        jTextField_Numero_Documento_Usuario3.setMaximumSize(new java.awt.Dimension(169, 25));
        jTextField_Numero_Documento_Usuario3.setMinimumSize(new java.awt.Dimension(169, 25));
        jTextField_Numero_Documento_Usuario3.setPreferredSize(new java.awt.Dimension(169, 25));
        jPanel1.add(jTextField_Numero_Documento_Usuario3, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 188, 152, -1));

        jLabel41.setText("Apellidos");
        jPanel1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 157, 107, -1));

        jTextField_Correo_Electronico3.setMaximumSize(new java.awt.Dimension(169, 25));
        jTextField_Correo_Electronico3.setMinimumSize(new java.awt.Dimension(169, 25));
        jTextField_Correo_Electronico3.setPreferredSize(new java.awt.Dimension(169, 25));
        jTextField_Correo_Electronico3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_Correo_Electronico3ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField_Correo_Electronico3, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 223, 152, -1));

        jTextField_Telefono_Usuario3.setMaximumSize(new java.awt.Dimension(169, 25));
        jTextField_Telefono_Usuario3.setMinimumSize(new java.awt.Dimension(169, 25));
        jTextField_Telefono_Usuario3.setPreferredSize(new java.awt.Dimension(169, 25));
        jPanel1.add(jTextField_Telefono_Usuario3, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 289, 152, -1));

        jLabel43.setText("N. documento");
        jPanel1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 192, 109, -1));

        jTextField_Direccion_Usuario3.setMaximumSize(new java.awt.Dimension(169, 25));
        jTextField_Direccion_Usuario3.setMinimumSize(new java.awt.Dimension(169, 25));
        jTextField_Direccion_Usuario3.setPreferredSize(new java.awt.Dimension(169, 25));
        jPanel1.add(jTextField_Direccion_Usuario3, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 324, 152, -1));

        jLabel44.setText("Correo");
        jPanel1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 227, 54, -1));

        jTextField_Estado_usuario.setMaximumSize(new java.awt.Dimension(169, 25));
        jTextField_Estado_usuario.setMinimumSize(new java.awt.Dimension(169, 25));
        jTextField_Estado_usuario.setPreferredSize(new java.awt.Dimension(169, 25));
        jPanel1.add(jTextField_Estado_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 359, 152, -1));

        jLabel45.setText("Telefono");
        jPanel1.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 293, 70, -1));

        jButton_Registar3.setText("Registar");
        jButton_Registar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Registar3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_Registar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 463, 83, -1));

        jLabel46.setText("Direccion");
        jPanel1.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 328, 70, -1));

        jLabel48.setText("Estado usuario");
        jPanel1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 363, 77, -1));

        jTable_Usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_UsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Usuarios);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(343, 55, 615, 390));

        jTextField_Id.setMaximumSize(new java.awt.Dimension(50, 25));
        jTextField_Id.setMinimumSize(new java.awt.Dimension(50, 25));
        jTextField_Id.setPreferredSize(new java.awt.Dimension(50, 25));
        jPanel1.add(jTextField_Id, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 83, 35, -1));
        jTextField_Id.getAccessibleContext().setAccessibleDescription("");

        jLabel1.setText("Id");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 87, 37, -1));

        jButton_Actualizar.setText("Actualizar");
        jButton_Actualizar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton_Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_Actualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(446, 463, 90, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Camara", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel2.setToolTipText("");
        jPanel2.setMaximumSize(new java.awt.Dimension(269, 270));

        jLabel_Foto_registro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 158, 255), 3, true));
        jLabel_Foto_registro.setMaximumSize(new java.awt.Dimension(200, 150));
        jLabel_Foto_registro.setMinimumSize(new java.awt.Dimension(200, 150));
        jLabel_Foto_registro.setPreferredSize(new java.awt.Dimension(200, 150));

        jButton_Abrir_camara.setText("Abrir Camara");
        jButton_Abrir_camara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Abrir_camaraActionPerformed(evt);
            }
        });

        jButton_Tomar_foto.setText("Tomar Foto");
        jButton_Tomar_foto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Tomar_fotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel_Foto_registro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton_Abrir_camara)
                        .addGap(28, 28, 28)
                        .addComponent(jButton_Tomar_foto)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel_Foto_registro, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Tomar_foto)
                    .addComponent(jButton_Abrir_camara))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 394, -1, -1));

        jButton1.setText("Eliminar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 463, 83, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reporte", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jButton_imprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Imagenes/Print16.png"))); // NOI18N
        jButton_imprimir.setText("Imprimir");
        jButton_imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_imprimirActionPerformed(evt);
            }
        });

        jButton_Excel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Imagenes/excel.png"))); // NOI18N
        jButton_Excel.setText("Exportar");
        jButton_Excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ExcelActionPerformed(evt);
            }
        });

        jLabel2.setText("Exportar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton_Excel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(25, 25, 25))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Excel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(693, 463, -1, -1));

        jButton_Primero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Imagenes/first.png"))); // NOI18N
        jButton_Primero.setText("Primero");
        jButton_Primero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PrimeroActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_Primero, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 560, 120, -1));

        jButton_Ultimo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Imagenes/last.png"))); // NOI18N
        jButton_Ultimo.setText("Ultimo");
        jButton_Ultimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_UltimoActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_Ultimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 560, 110, -1));

        jButton_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Imagenes/next.png"))); // NOI18N
        jButton_Siguiente.setText("Siguiente");
        jButton_Siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_Siguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 510, 120, -1));

        jButton_Anterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Imagenes/previous.png"))); // NOI18N
        jButton_Anterior.setText("Anterior");
        jButton_Anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AnteriorActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_Anterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 510, -1, -1));

        jLabel_RegresaralMenu.setText("Regresar al Menu");
        jLabel_RegresaralMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_RegresaralMenuMouseClicked(evt);
            }
        });
        jPanel1.add(jLabel_RegresaralMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 640, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_Registar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Registar3ActionPerformed
    // Obtener los valores de los campos de texto
    String Fname = jTextField_Nombre_Usuario3.getText();
    String Lname = jTextField_Aplellido_Usuario3.getText();
    String pass1 = new String(jPasswordField_Contraseña3.getPassword());
    String phone = jTextField_Telefono_Usuario3.getText();
    String dirc = jTextField_Direccion_Usuario3.getText();
    String mail = jTextField_Correo_Electronico3.getText();
    String IdNumber = jTextField_Numero_Documento_Usuario3.getText();

    // Validar que todos los campos estén completos
    if (IdNumber.isEmpty() || Fname.isEmpty() || Lname.isEmpty() || pass1.isEmpty() || phone.isEmpty() || dirc.isEmpty() || mail.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Todos los campos deben ser completados");
        return;
    }

    try {
        // Encriptación usando SHA-256 para la contraseña
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(pass1.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        String sha256 = sb.toString();

        // Obtener la imagen del JLabel y convertirla a un arreglo de bytes
        ImageIcon icon = (ImageIcon) jLabel_Foto_registro.getIcon();
        if (icon != null) {
            Image image = icon.getImage();
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D bGr = bufferedImage.createGraphics();
            bGr.drawImage(image, 0, 0, null);
            bGr.dispose();
            byte[] foto = imageToByteArray(bufferedImage); // Usar el método para convertir la imagen a bytes

            // Crear un nuevo objeto usuario con la foto incluida
            Controlador.Users usuario = new Controlador.Users(
                null, Fname, Lname, IdNumber, mail, sha256, phone, dirc, null, null, null, null, null, "activo", foto
            );

            // Insertar el usuario en la base de datos
            Controlador.Users.insertUser(usuario);
            populateJtable();
            
            JOptionPane.showMessageDialog(null, "Datos Insertados Correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna imagen.");
        }
    } catch (HeadlessException | UnsupportedEncodingException | NoSuchAlgorithmException | SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al insertar los datos: " + ex.getMessage());
    }    catch (IOException ex) {
             Logger.getLogger(Administrador_usuarios.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_jButton_Registar3ActionPerformed

    private void jTextField_Correo_Electronico3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_Correo_Electronico3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_Correo_Electronico3ActionPerformed

    private void jTable_UsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_UsuariosMouseClicked
            Integer rowIndex = jTable_Usuarios.getSelectedRow();
         
        productId = Integer.valueOf(jTable_Usuarios.getValueAt(rowIndex, 0).toString());        // TODO add your handling code here:
        jTextField_Id.setText(jTable_Usuarios.getValueAt(rowIndex, 0).toString());
        jTextField_Nombre_Usuario3.setText(jTable_Usuarios.getValueAt(rowIndex, 1).toString());
        jTextField_Aplellido_Usuario3.setText(jTable_Usuarios.getValueAt(rowIndex, 2).toString());
        jTextField_Numero_Documento_Usuario3.setText(jTable_Usuarios.getValueAt(rowIndex, 7).toString());
        jTextField_Correo_Electronico3.setText(jTable_Usuarios.getValueAt(rowIndex, 3).toString());
        jPasswordField_Contraseña3.setText(jTable_Usuarios.getValueAt(rowIndex, 4).toString());
        jTextField_Telefono_Usuario3.setText(jTable_Usuarios.getValueAt(rowIndex, 5).toString());
        jTextField_Direccion_Usuario3.setText(jTable_Usuarios.getValueAt(rowIndex, 6).toString());
        jTextField_Estado_usuario.setText(jTable_Usuarios.getValueAt(rowIndex, 9).toString());
         ImageIcon image1 = (ImageIcon)jTable_Usuarios.getValueAt(rowIndex, 10);
         Image image2 = image1.getImage().getScaledInstance(jLabel_Foto_registro.getWidth(), jLabel_Foto_registro.getHeight()
                     , Image.SCALE_SMOOTH);
            ImageIcon image3 = new ImageIcon(image2);
            jLabel_Foto_registro.setIcon(image3);
        
    }//GEN-LAST:event_jTable_UsuariosMouseClicked

    private void jButton_Abrir_camaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Abrir_camaraActionPerformed
       
        initWebcam();
        Webcam webcam = Webcam.getDefault(); // Obtiene la cámara por defecto
    if (webcam != null) {
        webcam.open(); // Abre la cámara
    }
        // Captura la imagen y la muestra en el JLabel
     
    
      
    }//GEN-LAST:event_jButton_Abrir_camaraActionPerformed

    private void jButton_Tomar_fotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Tomar_fotoActionPerformed
       
       BufferedImage imagenCapturada = webcam.getImage(); // Captura la imagen
    if (imagenCapturada != null) {
        // Escalar la imagen capturada al tamaño del jLabel_Foto_registro
        Image imagenEscalada = imagenCapturada.getScaledInstance(
                jLabel_Foto_registro.getWidth(), // Ancho del JLabel
                jLabel_Foto_registro.getHeight(), // Alto del JLabel
                Image.SCALE_SMOOTH); // Suaviza la escala de la imagen

        // Muestra la imagen escalada en el jLabel_Foto_registro
        ImageIcon icono = new ImageIcon(imagenEscalada);
        jLabel_Foto_registro.setIcon(icono);
        jLabel_Foto_registro.repaint(); // Actualiza el JLabel para mostrar la nueva imagen

        // Cierra la cámara
        webcam.close();
    } else {
        JOptionPane.showMessageDialog(this, "No se encontró ninguna cámara.");
    } 
        
    }//GEN-LAST:event_jButton_Tomar_fotoActionPerformed

    private void jButton_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ActualizarActionPerformed
        // TODO add your handling code here:
          String Fname = jTextField_Nombre_Usuario3.getText();
          String Lname = jTextField_Aplellido_Usuario3.getText();
          String pass1 = String.valueOf(jPasswordField_Contraseña3.getPassword());
          String phone = jTextField_Telefono_Usuario3.getText();
          String dirc = jTextField_Direccion_Usuario3.getText();
          String mail = jTextField_Correo_Electronico3.getText();
          String IdNumber = jTextField_Numero_Documento_Usuario3.getText();
          String idUsuarioStr = jTextField_Id.getText();
          
          
    if (idUsuarioStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "ID de usuario no está especificado.");
        return;
    }
    
    int userId;
    try {
        userId = Integer.parseInt(idUsuarioStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "ID de usuario inválido.");
        return;
    }
    
          Controlador.Users usuario; 
          
         //encriptar la clave
         String sha256 = "";
         // With the java libraries
	try {
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        digest.reset();
                //enviar el nombre de la variable que me recibe el password para este caso es pass1
	        digest.update(pass1.getBytes("utf8"));
	        sha256 = String.format("%040x", new BigInteger(1, digest.digest()));
	} catch (Exception e){
	    e.printStackTrace();
	}
           
            
             if (verifyFields()) {
        byte[] foto = null;
        // Si cambia la foto
        if (imagePth != null) {
            Path path = Paths.get(imagePth);
            try {
                foto = Files.readAllBytes(path);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al leer la imagen");
                ex.printStackTrace();
            }
        } else {
            // Si no hay una nueva ruta de imagen, obtener la imagen actual del JLabel
            ImageIcon icon = (ImageIcon) jLabel_Foto_registro.getIcon();
            if (icon != null) {
                Image image = icon.getImage();
                BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics2D bGr = bufferedImage.createGraphics();
                bGr.drawImage(image, 0, 0, null);
                bGr.dispose();
                foto = imageToByteArray(bufferedImage); // Convertir la imagen a bytes
            }
        }

        usuario =new Controlador.Users(
            userId, Fname, Lname, IdNumber, mail, sha256, phone, dirc, null, null, null, null, null, "activo", foto
        );

        Controlador.Users.updateUsers(usuario, imagePth != null);
        try {
            populateJtable();
        } catch (IOException ex) {
            Logger.getLogger(Administrador_usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Usuario Actualizado");
    
        
        
             }  
    }//GEN-LAST:event_jButton_ActualizarActionPerformed

    private void jPasswordField_Contraseña3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField_Contraseña3ActionPerformed
        // TODO add your handling code here:
                                                     

        
    }//GEN-LAST:event_jPasswordField_Contraseña3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (!jTextField_Id.getText().equals("")) {
        try {
            int id_user = Integer.parseInt(jTextField_Id.getText());
            Controlador.Users.deleteUsers(id_user);
            populateJtable();
            JOptionPane.showMessageDialog(null, "Usuario Eliminado");
        } catch (Exception ex) {
            Logger.getLogger(Administrador_usuarios.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Usuario No Eliminado");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Usuario No Eliminado: No hay ID para eliminar");
    }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_imprimirActionPerformed
   
        // TODO add your handling code here:
        
     // Verifica si el botón dice "Imprimir"
    if (jButton_imprimir.getText().equals("Imprimir")) {
        try {
            // Ruta al archivo .jasper compilado
            String reportPath = "src/Reportes/reporte_Usuarios_corsantos.jasper";
            Map<String, Object> parametros = new HashMap<>();
            // Asegúrate de que el parámetro P_USUARIO exista en tu reporte
            
            parametros.put("LOGO_PATH", "C:\\Users\\yeyo_\\OneDrive\\Documentos\\NetBeansProjects\\Corsantos_SAS\\src\\Vista\\Imagenes\\Logo_Titulo.png");

            // Crear la conexión
            Connection con = modelo.Conexion.getConnection();

            // Cargar el archivo .jasper ya compilado
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportPath);

            // Llenar el reporte con datos de la base de datos usando la conexión con
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, con);

            // Mostrar el reporte en una ventana
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            Container panelReporte = viewer.getContentPane();
            this.jScrollPane1.getViewport().removeAll();
            this.jScrollPane1.getViewport().add(panelReporte);

            // Cambia el texto del botón a "Volver"
         this.jButton_imprimir.setText("Volver");
         this.jButton_imprimir.setMnemonic('V');
         this.jButton_imprimir.setIcon(new ImageIcon(getClass().getResource("/Vista/Imagenes/Back32.png")));


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se puede mostrar los Contactos de la Agenda\n" + ex.getMessage());
        }
    } 
    // Verifica si el botón dice "Volver"
    else if (jButton_imprimir.getText().equals("Volver")) {
        this.jScrollPane1.getViewport().removeAll();
        this.jScrollPane1.getViewport().add(this.jTable_Usuarios);
        this.jButton_imprimir.setText("Imprimir");
        this.jButton_imprimir.setMnemonic('I');
        this.jButton_imprimir.setIcon(new ImageIcon(getClass().getResource("/Vista/Imagenes/Print16.png")));

    }
    }//GEN-LAST:event_jButton_imprimirActionPerformed

    private void jButton_ExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ExcelActionPerformed
        // TODO add your handling code here:
        Generador_de_Reportes.generarReporte("reporte_Usuarios_corsantos", "excel");
       
        
    }//GEN-LAST:event_jButton_ExcelActionPerformed

    private void jButton_PrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PrimeroActionPerformed
        // TODO add your handling code here:
         pos = 0;
        ShowItem(pos);
        
    }//GEN-LAST:event_jButton_PrimeroActionPerformed

    private void jButton_SiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SiguienteActionPerformed
        // TODO add your handling code here:
         pos++;
        
        if(pos >= getUsuariosList().size())
        {
            pos = getUsuariosList().size()-1;
        }
        
        ShowItem(pos);
        
        
    }//GEN-LAST:event_jButton_SiguienteActionPerformed

    private void jButton_AnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AnteriorActionPerformed
        // TODO add your handling code here:
          pos--;
        
        if(pos < 0)
        {
            pos = 0;
        }
        
        ShowItem(pos);
        
        
    }//GEN-LAST:event_jButton_AnteriorActionPerformed

    private void jButton_UltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_UltimoActionPerformed
        // TODO add your handling code here:
        pos = getUsuariosList().size()-1;
        ShowItem(pos);
        
    }//GEN-LAST:event_jButton_UltimoActionPerformed

    private void jLabel_RegresaralMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_RegresaralMenuMouseClicked
        // TODO add your handling code here:
        Vista.Frm_Menu rf = new Vista.Frm_Menu();
        rf.setVisible(true);
        rf.pack();
        rf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose(); 
    }//GEN-LAST:event_jLabel_RegresaralMenuMouseClicked

            private byte[] imageToByteArray(BufferedImage image) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, "png", baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return baos.toByteArray();
        }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Administrador_usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Administrador_usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Administrador_usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Administrador_usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Administrador_usuarios().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(Administrador_usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_Abrir_camara;
    private javax.swing.JButton jButton_Actualizar;
    private javax.swing.JButton jButton_Anterior;
    private javax.swing.JButton jButton_Excel;
    private javax.swing.JButton jButton_Primero;
    private javax.swing.JButton jButton_Registar3;
    private javax.swing.JButton jButton_Siguiente;
    private javax.swing.JButton jButton_Tomar_foto;
    private javax.swing.JButton jButton_Ultimo;
    private javax.swing.JButton jButton_imprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel_Foto_registro;
    private javax.swing.JLabel jLabel_RegresaralMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField_Contraseña3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Usuarios;
    private javax.swing.JTextField jTextField_Aplellido_Usuario3;
    private javax.swing.JTextField jTextField_Correo_Electronico3;
    private javax.swing.JTextField jTextField_Direccion_Usuario3;
    private javax.swing.JTextField jTextField_Estado_usuario;
    private javax.swing.JTextField jTextField_Id;
    private javax.swing.JTextField jTextField_Nombre_Usuario3;
    private javax.swing.JTextField jTextField_Numero_Documento_Usuario3;
    private javax.swing.JTextField jTextField_Telefono_Usuario3;
    // End of variables declaration//GEN-END:variables
}
