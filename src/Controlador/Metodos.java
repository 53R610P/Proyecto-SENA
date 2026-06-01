/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author yeyo_
 */
public class Metodos {
    
    public static void CargarImagen_JButton(JButton btn, String ruta) {
        // Crear el ImageIcon a partir de la ruta
        ImageIcon imagenIcono = new ImageIcon(ruta);

        // Obtener la imagen del ImageIcon
        Image imagen = imagenIcono.getImage();

        // Obtener las dimensiones del JButton
        int ancho = btn.getWidth();
        int alto = btn.getHeight();

        // Redimensionar la imagen para ajustarse al JButton
        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        // Asignar la imagen redimensionada al JButton
        btn.setIcon(new ImageIcon(imagenEscalada));

        // Refrescar el JButton
        btn.revalidate();
        btn.repaint();
    }
    
    public static void CargarImagen_JLabel(JLabel lbl, String ruta) {
    // Crear el ImageIcon a partir de la ruta
    ImageIcon imagenIcono = new ImageIcon(ruta);

    // Obtener la imagen del ImageIcon
    Image imagen = imagenIcono.getImage();

    // Obtener las dimensiones del JLabel
    int ancho = lbl.getWidth();
    int alto = lbl.getHeight();

    // Redimensionar la imagen para ajustarse al JLabel
    Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

    // Asignar la imagen redimensionada al JLabel
    lbl.setIcon(new ImageIcon(imagenEscalada));

    // Opcional: Refrescar el JLabel para asegurar que se muestre la imagen correctamente
    lbl.revalidate();
    lbl.repaint();
}
  
    
    
}
