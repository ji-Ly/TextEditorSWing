package com.hly.texteditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TextEditor extends JFrame {

	JTextArea textArea;
	JScrollPane scrollPane;
	JSpinner fontSpinner;
	JLabel fontLabel;
	JButton fontColorButton;
	JComboBox fontBox;
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;

	public TextEditor() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Text Editor");
		this.setLayout(new FlowLayout());
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));

		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450, 450));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		fontLabel = new JLabel("Font: ");

		fontSpinner = new JSpinner();
		fontSpinner.setPreferredSize(new Dimension(50, 25));
		fontSpinner.setValue(20);
		// fontSpinner.setEnabled(false);
		fontSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSpinner.getValue()));

			}
		});

		fontColorButton = new JButton("Color");
		fontColorButton.addActionListener(e -> {

			JColorChooser colorChooser = new JColorChooser();
			Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
			textArea.setForeground(color);

		});

		String[] font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontBox = new JComboBox(font);
		fontBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setFont(
						new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));

			}
		});
		fontBox.setSelectedItem("Arial");
		
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		
		exitItem.addActionListener(e -> System.exit(0));
		saveItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				int response = fileChooser.showSaveDialog(null);
				
				if(response == JFileChooser.APPROVE_OPTION) {
					File file;
					PrintWriter fileout = null;
					file = new File(fileChooser.getSelectedFile().getAbsolutePath());
					try {
						fileout = new PrintWriter(file);
						fileout.println(textArea.getText());
					} catch (Exception e2) {
						// TODO: handle exception
					}
					fileout.close();
				}
				
				
			}
		});
		
		
		this.setJMenuBar(menuBar);
		this.add(fontBox);
		this.add(fontColorButton);
		this.add(fontLabel);
		this.add(fontSpinner);
		this.add(scrollPane);
		this.setVisible(true);
	}
}
