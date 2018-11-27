package com.vpr.principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.vpr.pojo.Pelicula;

public class Modelo {
	//Constantes
	private final String IP = "";
	private final String DB = "";
	private final String USUARIO = "";
	private final String CONTRASENA = "";
	
	//Atributos
	private static Connection conexion;
	private PreparedStatement sentencia = null;
	private ResultSet resultado = null;
	private ArrayList<Pelicula> listPeliculas;
	
	public void conectarDB() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion = DriverManager.getConnection("jdbc:mysql://" + IP + ":3306/" + DB, USUARIO, CONTRASENA);
	}
	
	public void desconectarDB() throws SQLException {
		conexion.close();
	}
	
	public boolean iniciarSesion(String usuario, String contrasena) throws SQLException {
		String sentenciaSql = "SELECT id FROM usuarios WHERE usuario = ? AND contrasena = SHA1(?)";
		sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.setString(1,  usuario);
		sentencia.setString(2,  contrasena);
		resultado = sentencia.executeQuery();
		
		boolean encontrado = resultado.next();
		sentencia.close();
		return encontrado;
	}
	
	public void guardarPelicula() {
		String sentenciaSql = "INSERT INTO pelicula () VALUES (?,?)";
	}
	
	public void eliminarPelicula() {
		
	}
	
	public void modificarPelicula() {
		
	}
}
