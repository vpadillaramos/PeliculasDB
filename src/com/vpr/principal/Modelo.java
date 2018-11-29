package com.vpr.principal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.vpr.pojo.Pelicula;
import com.vpr.util.Util;

public class Modelo {
	//Constantes
	private static final String CONFIGURACION = "peliculas.conf";
	
	//Atributos
	private static Connection conexion;
	private PreparedStatement sentencia = null;
	private ResultSet resultado = null;
	private Pelicula peliculaBorrada;
	private CallableStatement procedimiento = null;
	
	public void conectarDB() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(CONFIGURACION));
		String ip = props.getProperty("ip");
		String usuario = props.getProperty("usuario");
		String db = props.getProperty("db");
		String contrasena = props.getProperty("contrasena");
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db, usuario, contrasena);
		
		//esta linea es para probar en casa
		//conexion = DriverManager.getConnection("jdbc:mysql://" + "localhost:3306/" + db + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", usuario, contrasena); //casa

	}
	
	public void desconectarDB() throws SQLException {
		conexion.close();
	}
	
	/**
	 * Consulta en la tabla USUARIOS y comprueba la coincidencia de los parametros
	 * @param usuario
	 * @param contrasena
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * Inserta en la tabla PElICULA la pelicula pasada
	 * @param pelicula
	 * @throws SQLException
	 */
	public void guardarPelicula(Pelicula pelicula) throws SQLException {
		String sentenciaSql = "INSERT INTO pelicula (titulo,sinopsis,genero,rating,director,fecha_estreno,fecha_vista,duracion,portada,nota,notas,vista) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		
		sentencia = conexion.prepareStatement(sentenciaSql, PreparedStatement.RETURN_GENERATED_KEYS); //genero un id
		sentencia.setString(1, pelicula.getTitulo());
		sentencia.setString(2, pelicula.getSinopsis());
		sentencia.setString(3, pelicula.getGenero());
		sentencia.setString(4, pelicula.getRating());
		sentencia.setString(5, pelicula.getDirector());
		
		//Si la fecha no se ha especificado guardo un null
		if(pelicula.getFechaEstreno() == null)
			sentencia.setDate(6, null);
		//si se ha especificado la fehca, la guardo en la DB como dato LocalDate
		else
			sentencia.setObject(6, pelicula.getFechaEstreno().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		if(pelicula.getFechaVista() == null)
			sentencia.setDate(7, null);
		else
			sentencia.setObject(7, pelicula.getFechaVista().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		sentencia.setInt(8, pelicula.getDuracion());
		sentencia.setString(9, pelicula.getPortada());
		sentencia.setFloat(10, pelicula.getNota());
		sentencia.setString(11, pelicula.getNotas());
		sentencia.setBoolean(12, pelicula.isVista());
		
		sentencia.executeUpdate();
		
		//Guardo el id generado
		ResultSet idGenerado = sentencia.getGeneratedKeys();
		idGenerado.next();
		pelicula.setId(idGenerado.getInt(1)); //le asigno un id a la pelicula
	}
	
	/**
	 * Elimina la pelicula pasada de la tabla PELICULA
	 * @param pelicula
	 * @throws SQLException
	 */
	public void eliminarPelicula(Pelicula pelicula) throws SQLException {
		peliculaBorrada = pelicula; //almaceno la ultima pelicula borrada por si se quiere recuperar
		String sentenciaSql = "DELETE FROM pelicula WHERE id = ?";
		
		sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.setInt(1, pelicula.getId());
		sentencia.executeUpdate();
	}
	
	/**
	 * Actualiza la pelicula pasada en la DB
	 * @param pelicula
	 * @throws SQLException
	 */
	public void modificarPelicula(Pelicula pelicula) throws SQLException {
		String sentenciaSql = "UPDATE pelicula SET titulo = ?, sinopsis = ?, genero = ?, rating = ?, director = ?, fecha_estreno = ?, "
				+ "fecha_vista = ?, duracion = ?, portada = ?, nota = ?, notas = ?, vista = ? where id = ?";
		
		sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.setString(1, pelicula.getTitulo());
		sentencia.setString(2, pelicula.getSinopsis());
		sentencia.setString(3, pelicula.getGenero());
		sentencia.setString(4, pelicula.getRating());
		sentencia.setString(5, pelicula.getDirector());
		sentencia.setObject(6, pelicula.getFechaEstreno().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		sentencia.setObject(7, pelicula.getFechaVista().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		sentencia.setInt(8, pelicula.getDuracion());
		sentencia.setString(9, pelicula.getPortada());
		sentencia.setFloat(10, pelicula.getNota());
		sentencia.setString(11, pelicula.getNotas());
		sentencia.setBoolean(12, pelicula.isVista());
		sentencia.setInt(13, pelicula.getId());
		
		sentencia.executeUpdate();
	}
	
	/**
	 * Recupero la ultima pelicula borrada y la guardo en la DB
	 * @throws SQLException
	 */
	public void deshacer() throws SQLException {
		if(peliculaBorrada == null)
			return;
		guardarPelicula(peliculaBorrada);
	}
	
	/**
	 * Mediante la sentencia sql TRUNCATE elimino todos los datos de la tabla. Se pedira confirmacion antes de borrar
	 * @throws SQLException
	 */
	public void borrarTodo() throws SQLException {
		if(getPeliculas().isEmpty())
			return;
		
		int ventana = JOptionPane.showConfirmDialog(null, "¿Quieres borrar todos los datos?", "¡ATENCIÓN!", JOptionPane.YES_NO_OPTION);
		if(ventana == JOptionPane.NO_OPTION || ventana == JOptionPane.CLOSED_OPTION) 
			return;
		peliculaBorrada = null; //si despues de borrar todo le da a deshacer borrado no recuperara si borro solo 1 pelicula antes
		String sentenciaSql = "TRUNCATE TABLE pelicula";
		
		sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.executeUpdate();
		Util.mensajeInformacion("Borrado", "Borrado correctamente");
	}
	
	/**
	 * Devuelve una lista con todas las peliculas almacenadas en la DB
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * Devuelve el numero de peliculas vistas, mediante un procedimiento almacenado
	 * @return
	 * @throws SQLException
	 */
	public int peliculasVistas() throws SQLException {
		int vistas = 0;
		
		String sentenciaSql = "CALL peliculasVistas()";
		procedimiento = conexion.prepareCall(sentenciaSql);
		procedimiento.execute();
		resultado = procedimiento.getResultSet();
		while(resultado.next()) {
			vistas = resultado.getInt(1);
		}
		resultado.close();
		
		return vistas;
	}
	
	/**
	 * Devuelve el total de peliculas almacenadas, mediante un procedimiento almacenado
	 * @return
	 * @throws SQLException
	 */
	public int totalPeliculas() throws SQLException {
		int total = 0;

		String sentenciaSql = "CALL totalPeliculas()";
		procedimiento = conexion.prepareCall(sentenciaSql);
		procedimiento.execute();
		resultado = procedimiento.getResultSet();
		while(resultado.next()) {
			total = resultado.getInt(1);
		}
		resultado.close();

		return total;
	}
	
	/**
	 * Metodo que transforma un ResultSet, que obtiene peliculas, en un objeto Pelicula
	 * @param r
	 * @return
	 * @throws SQLException
	 */
	private Pelicula damePelicula(ResultSet r) throws SQLException {
		Pelicula pelicula = new Pelicula();
		
		pelicula.setId(r.getInt(1));
		pelicula.setTitulo(r.getString(2));
		pelicula.setSinopsis(r.getString(3));
		pelicula.setGenero(r.getString(4));
		pelicula.setRating(r.getString(5));
		pelicula.setDirector(r.getString(6));
		pelicula.setFechaEstreno(r.getDate(7));
		pelicula.setFechaVista(r.getDate(8));
		pelicula.setDuracion(r.getInt(9));
		pelicula.setPortada(r.getString(10));
		pelicula.setNota(r.getFloat(11));
		pelicula.setNotas(r.getString(12));
		pelicula.setVista(r.getBoolean(13));
		
		return pelicula;
	}
}
