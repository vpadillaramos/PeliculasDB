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

public class Nota extends JDialog implements ActionListener{
	//Atributos
	private static String notas;

	//Componentes
	public JLabel lbNotas;
	public JButton btGuardar;
	public JButton btSalir;
	public JScrollPane sp;
	public JTextArea taNotas;

	public Nota() {
		setTitle("Sinopsis");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
		lbNotas = new JLabel("Escribe notas");
		lbNotas.setBounds(10, 11, 118, 14);
		getContentPane().add(lbNotas);
		
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
		
		taNotas = new JTextArea();
		taNotas.setLineWrap(true);
		taNotas.setWrapStyleWord(true);
		taNotas.setText(notas);
		sp.setViewportView(taNotas);
		
		//Listeners
		btGuardar.addActionListener(this);
		btSalir.addActionListener(this);
		
		setModal(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "guardar":
			notas = taNotas.getText();
			setVisible(false);
			break;
		
		case "salir":
			setVisible(false);
			break;
			
		default:
			break;
		}
	}
	
	public void ponerVisible(boolean b) {
		setVisible(b);
	}
	
	public String getNotas() {
		return notas;
	}

}
