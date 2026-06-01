package Reportes;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.util.JRLoader;

public class Generador_de_Reportes {

    // Método para generar el reporte en el formato especificado (en este caso, solo Excel)
    public static void generarReporte(String nombreReporte, String formato) {
        Connection con = null;

        try {
            // Conexión a la base de datos
            con = modelo.Conexion.getConnection();

            // Ruta al archivo .jasper (reporte ya compilado)
            String reportPath = "src/Reportes/" + nombreReporte + ".jasper";

            // Cargar el archivo .jasper
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportPath);

            // Definir parámetros (incluyendo la ruta al logo)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("LOGO_PATH", "C:\\Users\\yeyo_\\OneDrive\\Documentos\\NetBeansProjects\\Corsantos_SAS\\src\\Vista\\Imagenes\\Logo_Titulo.png");

            // Llenar el reporte con los datos de la base de datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);

            // Crear un JFileChooser para seleccionar la ruta y nombre del archivo
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte en Excel");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            // Filtrar para solo permitir archivos con extensión .xlsx
            fileChooser.setSelectedFile(new File("Reporte_" + nombreReporte + ".xlsx"));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                // Asegurarse de que el archivo tenga la extensión .xlsx
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                // Exportar el reporte a Excel en la ruta seleccionada
                if ("excel".equalsIgnoreCase(formato)) {
                    JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                    xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));

                    SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                    configuration.setOnePagePerSheet(false);
                    configuration.setDetectCellType(true);
                    configuration.setCollapseRowSpan(false);
                    xlsxExporter.setConfiguration(configuration);

                    xlsxExporter.exportReport();
                    System.out.println("Reporte exportado a Excel exitosamente en: " + filePath);
                }

                // Aquí podrías agregar más condiciones para otros formatos si deseas (por ejemplo PDF)
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
