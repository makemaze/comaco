package de.zippus.comaco.dialog;

import static de.zippus.comaco.dialog.PaymentDialog.AMOUNT;
import static de.zippus.comaco.dialog.PaymentDialog.CANCEL;
import static de.zippus.comaco.dialog.PaymentDialog.CHANGE;
import static de.zippus.comaco.dialog.PaymentDialog.INSERT_MONEY;
import static de.zippus.comaco.dialog.PaymentDialog.PAID;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class PaymentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static Color BG_COLOR = Color.BLUE;
	public static Color FG_COLOR = Color.WHITE;


	private JLabel message = new DialogLabel(INSERT_MONEY, SwingConstants.CENTER, BG_COLOR, FG_COLOR);

	private JLabel amountLabel = new DialogLabel(AMOUNT,SwingConstants.RIGHT);
	private JLabel amount = new DialogLabel("",SwingConstants.LEFT);

	private JLabel paidLabel = new DialogLabel(PAID,SwingConstants.RIGHT);
	private JLabel paid = new DialogLabel("",SwingConstants.LEFT);

	private JLabel changeLabel = new DialogLabel(CHANGE,SwingConstants.RIGHT);
	private JLabel change = new DialogLabel("",SwingConstants.LEFT);

	private JButton cancelButton = new JButton(CANCEL);


	public PaymentPanel() {

		BorderLayout layout = new BorderLayout();
		layout.setVgap(30);
		layout.setHgap(100);
		this.setLayout(layout);

		message.setOpaque(true);
		message.setAlignmentX(SwingConstants.CENTER);
		add(message, BorderLayout.NORTH);

		JPanel numbersPanel = new JPanel();
		GridLayout numbersLayout = new GridLayout(0, 2);
		numbersLayout.setVgap(15);
		numbersPanel.setLayout(numbersLayout);

		numbersPanel.add(amountLabel);
		numbersPanel.add(amount);

		numbersPanel.add(paidLabel);
		numbersPanel.add(paid);

		numbersPanel.add(changeLabel);
		numbersPanel.add(change);

		add(numbersPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		
		
		JLabel label1 = new JLabel();
		label1.setPreferredSize(new Dimension(80,0));
		JLabel label2 = new JLabel();
		label2.setPreferredSize(new Dimension(80,0));

		buttonPanel.add(label1, BorderLayout.WEST);
		buttonPanel.add(label2, BorderLayout.EAST);
		cancelButton.setFont(DialogLabel.font);
		cancelButton.requestFocus();
		
		buttonPanel.add(cancelButton, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

	}

	public JLabel getMessage() {
		return message;
	}

	public JLabel getAmount() {
		return amount;
	}

	public JLabel getPaid() {
		return paid;
	}

	public JLabel getChange() {
		return change;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JLabel getAmountLabel() {
		return amountLabel;
	}

	public JLabel getPaidLabel() {
		return paidLabel;
	}

	public JLabel getChangeLabel() {
		return changeLabel;
	}
	
	
	

}
