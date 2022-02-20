package de.zippus.comaco.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.zippus.comaco.IPaymentListener;
import de.zippus.comaco.business.BoPayment;
import de.zippus.comaco.xml.pojo.CMCommandFailed;
import de.zippus.comaco.xml.pojo.CMPaymentCancelled;
import de.zippus.comaco.xml.pojo.CMPaymentClosed;
import de.zippus.comaco.xml.pojo.CMPaymentStarted;
import de.zippus.comaco.xml.pojo.CMPaymentUpdate;

public class PaymentDialog extends JDialog /*implements IPaymentListener*/ {

	private static final long	serialVersionUID	= 1L;

	public static String		SPACE				= "  ";
	public static String		PAYMENT_CANCEL		= "Zahlung abgebrochen";
	public static String		ACCEPTED			= "Kassiert  :";
	public static String		RETURNED			= "Retuniert  :";
	public static String		DUE					= "Offen  :";
	public static String		THANKS				= "Vielen Dank!";

	public static String		INSERT_MONEY		= "Bitte am Kassenautomaten einzahlen";
	public static String		AMOUNT				= "Zu Bezahlen  :";
	public static String		PAID				= "Eingezahlt  :";
	public static String		CHANGE				= "Wechselgeld  :";
	public static String		CANCEL				= "Zahlung abbrechen";

	private BoPayment			boPayment;

	private PaymentPanel		paymentPanel;

	public PaymentDialog(PaymentPanel paymentPanel, final BoPayment boPayment) {
		this.setContentPane(paymentPanel);
		this.setModal(true);
		this.boPayment = boPayment;
		this.paymentPanel = paymentPanel;

		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);

		final JButton cancelButton = paymentPanel.getCancelButton();

		paymentPanel.getCancelButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boPayment.cancelPayment();
			}
		});

		// Ensure the text field always gets the first focus.
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				cancelButton.requestFocusInWindow();
			}
		});
		
		
		this.pack();
		setLocationRelativeTo(null);
	}

	public void onTest(String message) {

	}

	public void onPaymentStarted(CMPaymentStarted event) {
		//this.pack();
		this.setVisible(true);
		// try {
//		SwingUtilities.invokeLater(new Runnable() {
//
//			public void run() {
				// this.getParent().setVisible(true);
				paymentPanel.getAmount().setText(SPACE + BigDecimal.valueOf(boPayment.getToAccept(), 2).toString());

				//pack();
				//setVisible(true);
//			}
//		});
		// } catch (InvocationTargetException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}
	
	

	public PaymentPanel getPaymentPanel() {
		return paymentPanel;
	}

	public void onPaymentUpdated(CMPaymentUpdate event) {
		int accepted = event.getAccepted();
		int dispensed = event.getDispensed();
		paymentPanel.getPaid().setText(SPACE + BigDecimal.valueOf(accepted - dispensed, 2).toString());
		paymentPanel.getChange().setText(SPACE + BigDecimal.valueOf(dispensed, 2).toString());

	}

	public void onPaymentClosed(CMPaymentClosed event) {
		int dispensed = event.getDispensed();
		paymentPanel.getChange().setText(SPACE + BigDecimal.valueOf(dispensed, 2).toString());
		paymentPanel.getMessage().setText(THANKS);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.dispose();
	}

	public void onPaymentFailed(CMCommandFailed event) {

	}

	public void onPaymentCancelled(CMPaymentCancelled event) {
		int accepted = event.getAccepted();
		int dispensed = event.getDispensed();
		paymentPanel.getMessage().setText(PAYMENT_CANCEL);
		paymentPanel.getAmountLabel().setText(ACCEPTED);
		paymentPanel.getAmount().setText(SPACE + BigDecimal.valueOf(accepted, 2).toString());

		paymentPanel.getPaidLabel().setText(RETURNED);
		paymentPanel.getPaid().setText(SPACE + BigDecimal.valueOf(dispensed, 2).toString());

		paymentPanel.getChangeLabel().setText(DUE);
		paymentPanel.getChange().setText(SPACE + BigDecimal.valueOf(accepted - dispensed, 2).toString());

		paymentPanel.getCancelButton().setText("");
		paymentPanel.getCancelButton().setEnabled(false);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

}
