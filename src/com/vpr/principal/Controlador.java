package com.vpr.principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import com.vpr.ui.Login;
import com.vpr.util.Util;

public class Controlador implements ActionListener{
	private Modelo modelo;
	private Vista vista;
	
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
	}
	
	public void iniciarSesion() {
		boolean autenticado = false;
		Login login = new Login();
		
		do {
			
			login.hacerVisible(true);
			try {
				autenticado = modelo.iniciarSesion(login.getUsuario(), login.getContrasena());
				if(!autenticado) {
					login.mensajeError();
					continue;
				}
			} catch (SQLException e) {
				Util.mensajeError("Error al inciar sesión");
				e.printStackTrace();
			}
			
		}while(!autenticado);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "nueva":
			
			break;
			
		case "cancelar":
			
			break;
		
		case "modificar":
			
			break;
			
		case "borrar":
			
			break;
			
		case "guardar":
			
			break;
			
		case "sinopsis":
			
			break;
			
		case "notas":
			
			break;
		}
		
	}
}
