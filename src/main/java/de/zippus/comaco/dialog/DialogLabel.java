package de.zippus.comaco.dialog;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class DialogLabel extends JLabel {

	
	
	private static final long serialVersionUID = 1L;
	
	public static Font font = new Font("Verdana", Font.PLAIN, 40);
	
	public DialogLabel(String string, int alignement) {
		super(string, alignement);
		this.setFont(font);
	}

	public DialogLabel(String string, int alignement, Color background, Color foreground) {
		super(string, alignement);
		this.setFont(font);
		this.setOpaque(true);
		this.setBackground(background);
		this.setForeground(foreground);
	}
	
}
