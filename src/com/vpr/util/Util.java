package com.vpr.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import javax.swing.JOptionPane;

public class Util {
	public static void mensajeInformacion(String titulo, String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void mensajeError(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	public static boolean isInt(String cadena) {
		boolean resultado = false;
		if(cadena.matches("\\d*"))	//esto es una expresion regular que comprueba si son numeros
			resultado = true;
		return resultado;
	}
	
	public static boolean isFloat(String cadena) {
		boolean resultado = false;
		if(cadena.matches("\\d*\\.\\d*") || cadena.matches("\\d*\\,\\d*"))
			resultado = true;
		return resultado;
	}
	
	public static float parseDecimal(String decimal) throws ParseException {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		DecimalFormat df = new DecimalFormat();
		df.setDecimalFormatSymbols(dfs);
		return df.parse(decimal).floatValue();
	}
	
	public static void copiarImagen(String rutaOrigen, String nombreImagen) throws IOException {
		Path origen = FileSystems.getDefault().getPath(rutaOrigen);
		FileOutputStream destino = new FileOutputStream(
				new File(System.getProperty("user.dir") + File.separator + "portadas" + File.separator + nombreImagen)
				);
		Files.copy(origen, destino);
	}
}
