package com.vpr.util;

import javax.swing.JOptionPane;

public class Util {
	public static void mensajeInformacion(String titulo, String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void mensajeError(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
}
