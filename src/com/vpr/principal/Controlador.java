package com.vpr.principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.vpr.modales.Nota;
import com.vpr.modales.Sinopsis;
import com.vpr.pojo.Pelicula;
import com.vpr.ui.Login;
import com.vpr.util.Util;

public class Controlador implements ActionListener, ListSelectionListener, MouseListener{
	//Constantes
	private final String DEFAULT_PORTADA = "default.png";
	
	//Atributos
	private Modelo modelo;
	private Vista vista;
	private String sinopsis, notas;
	private File ficheroSeleccionado;
	
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		
		try {
			modelo.conectarDB();
		} catch (ClassNotFoundException e) {
			Util.mensajeError("No se encontró la DB");
			e.printStackTrace();
		} catch (SQLException e) {
			Util.mensajeError("Sentencia SQL incorrecta");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			Util.mensajeError("Fichero de configuración no encontrado");
			e.printStackTrace();
		} catch (IOException e) {
			Util.mensajeError("No se pudo acceder al fichero de configuración");
			e.printStackTrace();
		}
		
		
		modoEdicion(false);
		//iniciarSesion();
		
		poblarRating();
		poblarGenero();
		addListeners();
		refrescarLista();
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
	
	public void poblarRating() {
		for(String rating: Pelicula.getARating()) {
			vista.cbRating.addItem(rating);
		}
	}
	
	public void poblarGenero() {
		for(String genero: Pelicula.getAGenero()) {
			vista.cbGenero.addItem(genero);
		}
	}
	
	public void refrescarLista() {
		vista.modeloPelicula.removeAllElements();
		
		try {
			for(Pelicula p: modelo.getPeliculas())
				vista.modeloPelicula.addElement(p);
		} catch (SQLException e) {
			Util.mensajeError("No se pudieron obetener las peliculas");
			e.printStackTrace();
		}
	}
	
	public void limpiar() {
		vista.tfDirector.setText("");
		vista.tfDuracion.setText("");
		vista.tfNota.setText("");
		vista.tfTitulo.setText("");
		vista.cbGenero.setSelectedIndex(0);
		vista.cbRating.setSelectedIndex(0);
		vista.chbVista.setSelected(false);
		
		vista.dcFechaEstreno.setDate(null);
		vista.dcFechaVista.setDate(null);
	}
	
	public void addListeners() {
		vista.btBorrar.addActionListener(this);
		vista.btCancelar.addActionListener(this);
		vista.btEditar.addActionListener(this);
		vista.btGuardar.addActionListener(this);
		vista.btNueva.addActionListener(this);
		vista.btDeshacer.addActionListener(this);
		vista.btBorrarTodo.addActionListener(this);
		
		vista.btSinopsis.addActionListener(this);
		vista.btNotas.addActionListener(this);
		vista.chbAvanzado.addActionListener(this);
		
		vista.listPeliculas.addListSelectionListener(this);
		
		//vista.tfTitulo.addKeyListener(this);
	}
	
	public void modoEdicion(boolean editando) {
		if(editando) {
			vista.btCancelar.setEnabled(editando);
			vista.btEditar.setEnabled(!editando);
			vista.btNueva.setEnabled(!editando);
			vista.btBorrar.setEnabled(!editando);
			vista.btGuardar.setEnabled(editando);
			
			vista.btSinopsis.setEnabled(editando);
			vista.btNotas.setEnabled(editando);
			
			vista.tfDirector.setEditable(editando);
			vista.tfDuracion.setEditable(editando);
			vista.tfNota.setEditable(editando);
			vista.tfTitulo.setEditable(editando);
			vista.tfNota.setEnabled(editando);
			vista.cbGenero.setEnabled(editando);
			vista.cbRating.setEnabled(editando);
			vista.chbVista.setEnabled(editando);
			vista.dcFechaEstreno.setEnabled(editando);
			vista.dcFechaVista.setEnabled(editando);
			//vista.lbPortada.addMouseListener(this);
			
			vista.listPeliculas.setEnabled(!editando);
		}
		else {
			vista.btCancelar.setEnabled(editando);
			vista.btEditar.setEnabled(editando);
			vista.btNueva.setEnabled(!editando);
			vista.btBorrar.setEnabled(editando);
			vista.btGuardar.setEnabled(editando);
			
			vista.btSinopsis.setEnabled(editando);
			vista.btNotas.setEnabled(editando);
			
			vista.tfDirector.setEditable(editando);
			vista.tfDuracion.setEditable(editando);
			vista.tfNota.setEditable(editando);
			vista.tfTitulo.setEditable(editando);
			vista.tfNota.setEnabled(editando);
			vista.cbGenero.setEnabled(editando);
			vista.cbRating.setEnabled(editando);
			vista.chbVista.setEnabled(editando);
			vista.dcFechaEstreno.setEnabled(editando);
			vista.dcFechaVista.setEnabled(editando);
			//vista.lbPortada.removeMouseListener(this);
			
			vista.listPeliculas.setEnabled(!editando);
			vista.listPeliculas.clearSelection();
		}
	}
	
	public String escribirSinopsis() {
		Sinopsis sinopsis = new Sinopsis();
		sinopsis.ponerVisible(true);
		return sinopsis.getSinopsis();
	}
	
	public String escribirNotas() {
		Nota nota = new Nota();
		nota.ponerVisible(true);
		return nota.getNotas();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(vista.chbAvanzado.isSelected())
			vista.panAvanzado.setVisible(true);
		else
			vista.panAvanzado.setVisible(false);
		
		switch(e.getActionCommand()) {
		case "nueva":
			modoEdicion(true);
			vista.tfTitulo.requestFocus();
			break;
			
		case "cancelar":
			limpiar();
			modoEdicion(false);
			break;
		
		case "modificar":
			modoEdicion(true);
			break;
			
		case "borrar":
			
			try {
				modelo.eliminarPelicula(vista.listPeliculas.getSelectedValue());
				Util.mensajeInformacion("Eliminada", "Película eliminada correctamente");
			} catch (SQLException e2) {
				Util.mensajeError("No se pudo eliminar la pelicula");
				e2.printStackTrace();
			}
			
			refrescarLista();
			limpiar();
			modoEdicion(false);
			break;
			
		case "guardar":
			modoEdicion(false);
			//****CONTROL DE ENTRADA DE DATOS******
			if(vista.tfTitulo.getText().equals("")) {
				Util.mensajeError("El título es obligatorio");
				vista.tfTitulo.requestFocus();
				return;
			}
			
			if(vista.tfDuracion.getText().equals("")) 
				vista.tfDuracion.setText("0");
			if(!Util.isInt(vista.tfDuracion.getText())) {
				Util.mensajeError("La duración es incorrecta");
				vista.tfDuracion.requestFocus();
				vista.tfDuracion.selectAll();
				return;
			}
			
			if(vista.tfNota.getText().equals(""))
				vista.tfNota.setText("0.0");
			if(!Util.isFloat(vista.tfNota.getText())) {
				Util.mensajeError("La nota es incorrecta");
				vista.tfNota.requestFocus();
				vista.tfNota.selectAll();
				return;
			}
				
			
			Pelicula pelicula = new Pelicula();
			
			pelicula.setTitulo(vista.tfTitulo.getText());
			pelicula.setSinopsis(sinopsis);
			pelicula.setGenero(String.valueOf(vista.cbGenero.getSelectedItem()));
			pelicula.setRating(String.valueOf(vista.cbRating.getSelectedItem()));
			pelicula.setDirector(vista.tfDirector.getText());
			pelicula.setDuracion(Integer.parseInt(vista.tfDuracion.getText()));
			pelicula.setNotas(notas);
			pelicula.setVista(vista.chbVista.isSelected());
			
			
			//Decimal
			if(vista.tfNota.getText().contains(","))
				try {
					//TODO no se muestra el decimal con una coma debido a la asignacion de variables en setNota
					pelicula.setNota(Util.parseDecimal(vista.tfNota.getText()));
				} catch (ParseException pe) {
					Util.mensajeError("Error al formatear la nota");
					pe.printStackTrace();
				}
			else
				pelicula.setNota(Float.parseFloat(vista.tfNota.getText()));
			
			//Fechas
			String fechaEstreno;
			String fechaVista;
			Locale fCastellano = new Locale("es","Es","Traditional.win");
			if(vista.dcFechaEstreno.getDate() == null)
				fechaEstreno = "";
			else
				fechaEstreno = DateFormat.getDateInstance(DateFormat.SHORT, fCastellano).format(vista.dcFechaEstreno.getDate());
			if(vista.dcFechaVista.getDate() == null)
				fechaVista = "";
			else
				fechaVista = DateFormat.getDateInstance(DateFormat.SHORT, fCastellano).format(vista.dcFechaVista.getDate());
			
			pelicula.setFechaEstreno(fechaEstreno);
			pelicula.setFechaVista(fechaVista);
			
			//Imagen
			String nombreImagen;
			if(ficheroSeleccionado != null) {
				nombreImagen = ficheroSeleccionado.getName();
				
				try {
					Util.copiarImagen(ficheroSeleccionado.getAbsolutePath(), nombreImagen);
					pelicula.setPortada(ficheroSeleccionado.getAbsolutePath() + File.separator + nombreImagen);
				} catch (IOException e1) {
					Util.mensajeError("No se puedo copiar la imagen");
					e1.printStackTrace();
				}
			}
			//si no selecciono fichero pongo la imagen por defecto
			else {
				nombreImagen = DEFAULT_PORTADA;
				pelicula.setPortada(nombreImagen);
			}
			
			try {
				modelo.guardarPelicula(pelicula);
				Util.mensajeInformacion("Guardada", "Película guardada correctamente");
			} catch (SQLException e1) {
				Util.mensajeError("No se puedo guardar la pelicula");
				e1.printStackTrace();
			}
			
			
			refrescarLista();
			limpiar();
			modoEdicion(false);
			
			break;
			
		case "sinopsis":
			sinopsis = escribirSinopsis();
			break;
			
		case "notas":
			notas = escribirNotas();
			break;
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(vista.listPeliculas.getSelectedIndex() == -1)
			return;
		vista.btEditar.setEnabled(true);
		vista.btBorrar.setEnabled(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == vista.lbPortada) {
			//Abro un selector de fichero
			JFileChooser fc = new JFileChooser();

			//si le da a cancelar se hace nada
			if(fc.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
				return;

			ficheroSeleccionado = fc.getSelectedFile();
			vista.lbPortada.setIcon(new ImageIcon(ficheroSeleccionado.getAbsolutePath()));
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
