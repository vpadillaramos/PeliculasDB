package com.vpr.principal;


import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.mxrck.autocompleter.TextAutoCompleter;
import com.toedter.calendar.JDateChooser;
import com.vpr.pojo.Pelicula;

public class Vista extends JFrame {
	//Componentes
	public JLabel lbTitulo;
	public JLabel lbSinopsis;
	public JLabel lbGenero;
	public JLabel lbRating;
	public JLabel lbDirector;
	public JLabel lbFechaEstreno;
	public JLabel lbFechaVista;
	public JLabel lbDuracion;
	public JLabel lbPortada;
	public JLabel lblNota;
	public JLabel lblNotas;
	public JTextField tfTitulo;
	public JTextField tfDirector;
	public JTextField tfDuracion;
	public JTextField tfNota;
	public JComboBox cbGenero;
	public JComboBox cbRating;
	public JButton btNueva;
	public JButton btEditar;
	public JButton btBorrar;
	public JButton btCancelar;
	public JButton btGuardar;
	public JButton btSinopsis;
	public JButton btNotas;
	public JScrollPane scrollPane;
	public JList<Pelicula> listPeliculas;
	public DefaultListModel<Pelicula> modeloPelicula;
	public JCheckBox chbVista;
	public JDateChooser dcFechaEstreno;
	public JDateChooser dcFechaVista;
	public JPanel panBasico;
	public JPanel panAvanzado;
	public JCheckBox chbAvanzado;
	public JButton btDeshacer;
	public JButton btBorrarTodo;
	public JCheckBox chbAdicionRapida;
	public JLabel lbBuscar;
	public JTextField tfBuscar;
	public TextAutoCompleter tac;
	public JLabel lbVistas;
	public JLabel lbTotal;
	public JTextField tfPeliculasVistas;
	public JTextField tfTotalPeliculas;
	
	
	public Vista() {
		setTitle("Películas");
		setSize(700, 650);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		btNueva = new JButton("Nueva");
		btNueva.setActionCommand("nueva");
		btNueva.setBounds(349, 29, 89, 23);
		getContentPane().add(btNueva);
		
		btEditar = new JButton("Editar");
		btEditar.setActionCommand("editar");
		btEditar.setBounds(349, 63, 89, 23);
		getContentPane().add(btEditar);
		
		btBorrar = new JButton("Borrar");
		btBorrar.setActionCommand("borrar");
		btBorrar.setBounds(349, 201, 89, 23);
		getContentPane().add(btBorrar);
		
		btCancelar = new JButton("Cancelar");
		btCancelar.setActionCommand("cancelar");
		btCancelar.setBounds(349, 131, 89, 23);
		getContentPane().add(btCancelar);
		
		btGuardar = new JButton("Guardar");
		btGuardar.setActionCommand("guardar");
		btGuardar.setBounds(349, 97, 89, 23);
		getContentPane().add(btGuardar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(482, 31, 202, 291);
		getContentPane().add(scrollPane);
		
		listPeliculas = new JList<Pelicula>();
		scrollPane.setViewportView(listPeliculas);
		modeloPelicula = new DefaultListModel<Pelicula>();
		listPeliculas.setModel(modeloPelicula);
		
		panBasico = new JPanel();
		panBasico.setBounds(10, 11, 316, 69);
		getContentPane().add(panBasico);
		panBasico.setLayout(null);
		
		lbTitulo = new JLabel("T\u00EDtulo");
		lbTitulo.setBounds(10, 11, 53, 14);
		panBasico.add(lbTitulo);
		
		tfTitulo = new JTextField();
		tfTitulo.setBounds(121, 8, 185, 20);
		panBasico.add(tfTitulo);
		tfTitulo.setColumns(10);
		
		chbVista = new JCheckBox("Vista");
		chbVista.setBounds(121, 39, 97, 23);
		panBasico.add(chbVista);
		chbVista.setActionCommand("vista");
		
		panAvanzado = new JPanel();
		panAvanzado.setBounds(10, 91, 316, 519);
		getContentPane().add(panAvanzado);
		panAvanzado.setLayout(null);
		panAvanzado.setVisible(false);
		
		lbSinopsis = new JLabel("Sinopsis");
		lbSinopsis.setBounds(10, 11, 69, 14);
		panAvanzado.add(lbSinopsis);
		
		btSinopsis = new JButton("Escribir sinopsis");
		btSinopsis.setBounds(121, 7, 120, 23);
		panAvanzado.add(btSinopsis);
		btSinopsis.setActionCommand("sinopsis");
		
		lbGenero = new JLabel("G\u00E9nero");
		lbGenero.setBounds(10, 40, 46, 14);
		panAvanzado.add(lbGenero);
		
		cbGenero = new JComboBox();
		cbGenero.setBounds(121, 37, 120, 20);
		panAvanzado.add(cbGenero);
		
		lbRating = new JLabel("Rating de edad");
		lbRating.setBounds(10, 73, 99, 14);
		panAvanzado.add(lbRating);
		
		cbRating = new JComboBox();
		cbRating.setBounds(121, 68, 120, 20);
		panAvanzado.add(cbRating);
		
		lbDirector = new JLabel("Director/a");
		lbDirector.setBounds(10, 108, 69, 14);
		panAvanzado.add(lbDirector);
		
		tfDirector = new JTextField();
		tfDirector.setBounds(121, 99, 120, 20);
		panAvanzado.add(tfDirector);
		tfDirector.setColumns(10);
		
		lbFechaEstreno = new JLabel("Fecha de estreno");
		lbFechaEstreno.setBounds(10, 142, 111, 14);
		panAvanzado.add(lbFechaEstreno);
		
		dcFechaEstreno = new JDateChooser();
		dcFechaEstreno.setBounds(121, 136, 120, 20);
		panAvanzado.add(dcFechaEstreno);
		
		lbFechaVista = new JLabel("Fecha vista");
		lbFechaVista.setBounds(10, 177, 84, 14);
		panAvanzado.add(lbFechaVista);
		
		dcFechaVista = new JDateChooser();
		dcFechaVista.setBounds(121, 171, 120, 20);
		panAvanzado.add(dcFechaVista);
		
		lbDuracion = new JLabel("Duraci\u00F3n");
		lbDuracion.setBounds(10, 205, 69, 14);
		panAvanzado.add(lbDuracion);
		
		tfDuracion = new JTextField();
		tfDuracion.setBounds(121, 202, 120, 20);
		panAvanzado.add(tfDuracion);
		tfDuracion.setColumns(10);
		
		lblNota = new JLabel("Nota");
		lblNota.setBounds(10, 235, 46, 14);
		panAvanzado.add(lblNota);
		
		tfNota = new JTextField();
		tfNota.setBounds(121, 233, 120, 20);
		panAvanzado.add(tfNota);
		tfNota.setColumns(10);
		
		lblNotas = new JLabel("Notas");
		lblNotas.setBounds(10, 268, 46, 14);
		panAvanzado.add(lblNotas);
		
		btNotas = new JButton("Escribir notas");
		btNotas.setBounds(121, 264, 120, 23);
		panAvanzado.add(btNotas);
		btNotas.setActionCommand("notas");
		
		lbPortada = new JLabel("");
		lbPortada.setBounds(10, 308, 296, 200);
		panAvanzado.add(lbPortada);
		lbPortada.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Portada", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		chbAvanzado = new JCheckBox("Detalles avanzados");
		chbAvanzado.setActionCommand("detallesAvanzados");
		chbAvanzado.setBounds(332, 270, 135, 23);
		getContentPane().add(chbAvanzado);
		
		btDeshacer = new JButton("Deshacer borrado");
		btDeshacer.setActionCommand("deshacer");
		btDeshacer.setBounds(509, 386, 155, 23);
		getContentPane().add(btDeshacer);
		
		btBorrarTodo = new JButton("Borrar todo");
		btBorrarTodo.setActionCommand("borrarTodo");
		btBorrarTodo.setBounds(509, 569, 155, 23);
		getContentPane().add(btBorrarTodo);
		
		chbAdicionRapida = new JCheckBox("Adici\u00F3n r\u00E1pida");
		chbAdicionRapida.setActionCommand("adicionRapida");
		chbAdicionRapida.setBounds(333, 296, 119, 23);
		getContentPane().add(chbAdicionRapida);
		
		lbBuscar = new JLabel("Buscar");
		lbBuscar.setBounds(384, 362, 54, 14);
		getContentPane().add(lbBuscar);
		
		tfBuscar = new JTextField();
		tfBuscar.setActionCommand("buscar");
		tfBuscar.setBounds(340, 387, 127, 20);
		getContentPane().add(tfBuscar);
		tfBuscar.setColumns(10);
		tac = new TextAutoCompleter(tfBuscar);
		tac.setMode(0); //busca una subcadena
		tac.setCaseSensitive(false);
		
		lbVistas = new JLabel("Vistas:");
		lbVistas.setBounds(517, 333, 46, 14);
		getContentPane().add(lbVistas);
		
		lbTotal = new JLabel("Total:");
		lbTotal.setBounds(517, 358, 46, 14);
		getContentPane().add(lbTotal);
		
		tfPeliculasVistas = new JTextField();
		tfPeliculasVistas.setBounds(590, 333, 62, 20);
		getContentPane().add(tfPeliculasVistas);
		tfPeliculasVistas.setColumns(10);
		tfPeliculasVistas.setEditable(false);
		
		tfTotalPeliculas = new JTextField();
		tfTotalPeliculas.setBounds(590, 355, 62, 20);
		getContentPane().add(tfTotalPeliculas);
		tfTotalPeliculas.setColumns(10);
		tfTotalPeliculas.setEditable(false);
		
		
		setVisible(true);
	}
	
	public void anadirBuscar(String titulo) {
		tac.addItem(titulo);
	}
}

