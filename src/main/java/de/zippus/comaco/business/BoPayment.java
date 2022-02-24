package de.zippus.comaco.business;


import java.math.BigDecimal;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import de.zippus.comaco.Client;
import de.zippus.comaco.IPaymentListener;
import de.zippus.comaco.PaymentFailedException;
import de.zippus.comaco.dialog.PaymentDialog;
import de.zippus.comaco.dialog.PaymentPanel;
import de.zippus.comaco.xml.pojo.App;
import de.zippus.comaco.xml.pojo.CMCommandFailed;
import de.zippus.comaco.xml.pojo.CMPaymentCancelled;
import de.zippus.comaco.xml.pojo.CMPaymentClosed;
import de.zippus.comaco.xml.pojo.CMPaymentStarted;
import de.zippus.comaco.xml.pojo.CMPaymentUpdate;
import de.zippus.comaco.xml.pojo.ICMResponse;

public class BoPayment implements IPaymentListener {

	private Client			client;

	private int				toAccept;

	private PaymentDialog	paymentDialog;

	public BoPayment(Client client) {
		this.client = client;
	}

	public ICMResponse doPayment(int amount, String id, boolean showDialog) throws PaymentFailedException, Exception {

		this.toAccept = amount;

		if (showDialog) {
			PaymentPanel paymentPanel = new PaymentPanel();
			paymentDialog = new PaymentDialog(paymentPanel, this);
			client.getProgressListener().addPaymentListener(this/* paymentDialog */);
		}

		ICMResponse response = null;

		App app = App.createCMSendPayment(amount, id);

		// try {

		response = client.perform(app);

		if (response instanceof CMCommandFailed) {
			throw new PaymentFailedException((CMCommandFailed) response);
		}

		// } catch (Exception e) {
		// e.printStackTrace();
		// //throw new PaymentFailedException(e);
		// }

		return response;
	}

	public void cancelPayment() {
		cancelPayment(true);
	}

	public void cancelPayment(boolean reimburse) {

		Object[] options = { "Ja", "Nein" };
		int n = JOptionPane.showOptionDialog(paymentDialog, "Zahlungsvorgang wirklich abbrechen?", "Zahlung abbrechen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

		if (n == 0) {
			App app = App.createCMPaymentCancel(reimburse);

			try {

				ICMResponse response = client.perform(app);

				if (response instanceof CMCommandFailed) {
					throw new PaymentFailedException((CMCommandFailed) response);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int getToAccept() {
		return this.toAccept;
	}

	public void onTest(String message) {
		// TODO Auto-generated method stub

	}

	public void onPaymentStarted(CMPaymentStarted event) {
		System.out.println("BoPayment.onPaymentStarted() - EDT: " + SwingUtilities.isEventDispatchThread());

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				System.out.println("invokeLater: onPaymentStarted");

				paymentDialog.getPaymentPanel().getAmount().setText(PaymentDialog.SPACE + BigDecimal.valueOf(getToAccept(), 2).toString());
				paymentDialog.setVisible(true);
				paymentDialog.repaint();

			}
		});
		System.out.println("Bla2");
		paymentDialog.repaint();

	}

	public void onPaymentUpdated(CMPaymentUpdate event) {
		System.out.println("BoPayment.onPaymentUpdated() -EDT: " + SwingUtilities.isEventDispatchThread());
		final int accepted = event.getAccepted();
		final int dispensed = event.getDispensed();

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				paymentDialog.getPaymentPanel().getPaid().setText(PaymentDialog.SPACE + BigDecimal.valueOf(accepted - dispensed, 2).toString());
				paymentDialog.getPaymentPanel().getChange().setText(PaymentDialog.SPACE + BigDecimal.valueOf(dispensed, 2).toString());
			}
		});
	}

	public void onPaymentClosed(CMPaymentClosed event) {
		System.out.println("BoPayment.onPaymentClosed() -EDT: " + SwingUtilities.isEventDispatchThread());

		final int dispensed = event.getDispensed();

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				paymentDialog.getPaymentPanel().getChange().setText(PaymentDialog.SPACE + BigDecimal.valueOf(dispensed, 2).toString());
				paymentDialog.getPaymentPanel().getMessage().setText(PaymentDialog.THANKS);
			}
		});

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		paymentDialog.dispose();

	}

	public void onPaymentFailed(CMCommandFailed event) {
		System.out.println("BoPayment.onPaymentFailed() -EDT: " + SwingUtilities.isEventDispatchThread());
		// TODO Auto-generated method stub

	}

	public void onPaymentCancelled(CMPaymentCancelled event) {
		System.out.println("BoPayment.onPaymentCancelled() -EDT: " + SwingUtilities.isEventDispatchThread());
		final int accepted = event.getAccepted();
		final int dispensed = event.getDispensed();

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				paymentDialog.getPaymentPanel().getMessage().setText(PaymentDialog.PAYMENT_CANCEL);
				paymentDialog.getPaymentPanel().getAmountLabel().setText(PaymentDialog.ACCEPTED);
				paymentDialog.getPaymentPanel().getAmount().setText(PaymentDialog.SPACE + BigDecimal.valueOf(accepted, 2).toString());

				paymentDialog.getPaymentPanel().getPaidLabel().setText(PaymentDialog.RETURNED);
				paymentDialog.getPaymentPanel().getPaid().setText(PaymentDialog.SPACE + BigDecimal.valueOf(dispensed, 2).toString());

				paymentDialog.getPaymentPanel().getChangeLabel().setText(PaymentDialog.DUE);
				paymentDialog.getPaymentPanel().getChange().setText(PaymentDialog.SPACE + BigDecimal.valueOf(accepted - dispensed, 2).toString());

				paymentDialog.getPaymentPanel().getCancelButton().setText("");
				paymentDialog.getPaymentPanel().getCancelButton().setEnabled(false);

			}
		});

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		paymentDialog.dispose();
	}

}
