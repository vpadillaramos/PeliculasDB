package com.vpr.principal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.vpr.pojo.Pelicula;

public class Modelo {
	//Constantes
	private static final String CONFIGURACION = "peliculas.conf";
	
	//Atributos
	private static Connection conexion;
	private PreparedStatement sentencia = null;
	private ResultSet resultado = null;
	
	public void conectarDB() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(CONFIGURACION));
		String ip = props.getProperty("ip");
		String usuario = props.getProperty("usuario");
		String db = props.getProperty("db");
		String contrasena = props.getProperty("contrasena");
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db, usuario, contrasena);
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
	
	public void guardarPelicula(Pelicula pelicula) throws SQLException {
		String sentenciaSql = "INSERT INTO pelicula (titulo,sinopsis,genero,rating,director,fecha_estreno,fecha_vista,duracion,portada,nota,notas,vista) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		
		sentencia = conexion.prepareStatement(sentenciaSql, PreparedStatement.RETURN_GENERATED_KEYS);
		sentencia.setString(1, pelicula.getTitulo());
		sentencia.setString(2, pelicula.getSinopsis());
		sentencia.setString(3, pelicula.getGenero());
		sentencia.setString(4, pelicula.getRating());
		sentencia.setString(5, pelicula.getDirector());
		sentencia.setString(6, pelicula.getFechaEstreno());
		sentencia.setString(7, pelicula.getFechaVista());
		sentencia.setInt(8, pelicula.getDuracion());
		sentencia.setString(9, pelicula.getPortada());
		sentencia.setFloat(10, pelicula.getNota());
		sentencia.setString(11, pelicula.getNotas());
		sentencia.setBoolean(12, pelicula.isVista());
		
		sentencia.executeUpdate();
		
		//Guardo el id generado
		ResultSet idGenerado = sentencia.getGeneratedKeys();
		idGenerado.next();
		pelicula.setId(idGenerado.getInt(1));
		
	}
	
	public void eliminarPelicula(Pelicula pelicula) throws SQLException {
		String sentenciaSql = "DELETE FROM pelicula WHERE id = ?";
		
		sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.setInt(1, pelicula.getId());
		sentencia.executeUpdate();
	}
	
	public void modificarPelicula(Pelicula pelicula) throws SQLException {
		String sentenciaSql = "UPDATE pelicula SET titulo = ?, sinopsis = ?, genero = ?, rating = ?, director = ?, fecha_estreno = ?, "
				+ "fecha_vista = ?, duracion = ?, portada = ?, nota = ?, notas = ?, vista = ?, ";
		
		sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.setString(1, pelicula.getTitulo());
		sentencia.setString(2, pelicula.getSinopsis());
		sentencia.setString(3, pelicula.getGenero());
		sentencia.setString(4, pelicula.getRating());
		sentencia.setString(5, pelicula.getDirector());
		sentencia.setString(6, pelicula.getFechaEstreno());
		sentencia.setString(7, pelicula.getFechaVista());
		sentencia.setInt(8, pelicula.getDuracion());
		sentencia.setString(9, pelicula.getPortada());
		sentencia.setFloat(10, pelicula.getNota());
		sentencia.setString(11, pelicula.getNotas());
		sentencia.setBoolean(12, pelicula.isVista());
		
		sentencia.executeUpdate();
	}
	
	public List<Pelicula> getPeliculas() throws SQLException{
		Pelicula pelicula = null;
		List<Pelicula> peliculas = new ArrayList<>();
		
		String sentenciaSql = "SELECT id, titulo, sinopsis, genero, rating, director, fecha_estreno, fecha_vista, duracion, portada, nota, notas, vista "
				+ "FROM pelicula";
		sentencia = conexion.prepareStatement(sentenciaSql);
		resultado = sentencia.executeQuery();
		
		while(resultado.next()) {
			pelicula = damePelicula(resultado);
			peliculas.add(pelicula);
		}
		
		return peliculas;
	}
	
	private Pelicula damePelicula(ResultSet r) throws SQLException {
		Pelicula pelicula = new Pelicula();
		
		pelicula.setId(r.getInt(1));
		pelicula.setTitulo(r.getString(2));
		pelicula.setSinopsis(r.getString(3));
		pelicula.setGenero(r.getString(4));
		pelicula.setRating(r.getString(5));
		pelicula.setDirector(r.getString(6));
		pelicula.setFechaEstreno(r.getString(7));
		pelicula.setFechaVista(r.getString(8));
		pelicula.setDuracion(r.getInt(9));
		pelicula.setPortada(r.getString(10));
		pelicula.setNota(r.getFloat(11));
		pelicula.setNotas(r.getString(12));
		pelicula.setVista(r.getBoolean(13));
		
		return pelicula;
	}
}
