package com.vpr.modales;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Sinopsis extends JDialog implements ActionListener{
	
	//Atributos
	private static String sinopsis;
	
	//Componentes
	public JLabel lbSinopsis;
	public JButton btGuardar;
	public JButton btSalir;
	public JScrollPane sp;
	public JTextArea taSinopsis;


	
	public Sinopsis() {
		setTitle("Sinopsis");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
		lbSinopsis = new JLabel("Escribe la sinopsis");
		lbSinopsis.setBounds(10, 11, 118, 14);
		getContentPane().add(lbSinopsis);
		
		btGuardar = new JButton("Guardar");
		btGuardar.setActionCommand("guardar");
		btGuardar.setBounds(87, 237, 89, 23);
		getContentPane().add(btGuardar);
		
		btSalir = new JButton("Salir");
		btSalir.setActionCommand("salir");
		btSalir.setBounds(207, 237, 89, 23);
		getContentPane().add(btSalir);
		
		sp = new JScrollPane();
		sp.setBounds(10, 48, 374, 151);
		getContentPane().add(sp);
		
		taSinopsis = new JTextArea();
		taSinopsis.setLineWrap(true);
		taSinopsis.setWrapStyleWord(true);
		taSinopsis.setText(sinopsis); //se muestra la sinopsis escrita antes de guardar pelicula
		sp.setViewportView(taSinopsis);
		
		//Listeners
		btGuardar.addActionListener(this);
		btSalir.addActionListener(this);
		
		setModal(true);
	}
	
	public void ponerVisible(boolean b) {
		setVisible(b);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "guardar":
			sinopsis = taSinopsis.getText();
			setVisible(false);
			break;
		case "salir":
			setVisible(false);
			break;
		default:
			
			break;
		}
	}
	
	public String getSinopsis() {
		return sinopsis;
	}
}
