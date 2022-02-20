package de.zippus.comaco.test;

import java.io.IOException;

import de.zippus.comaco.Channel;
import de.zippus.comaco.IChannel;
import de.zippus.comaco.IPaymentListener;
import de.zippus.comaco.PaymentFailedException;
import de.zippus.comaco.xml.pojo.CMCommandFailed;
import de.zippus.comaco.xml.pojo.CMPaymentCancelled;
import de.zippus.comaco.xml.pojo.CMPaymentClosed;
import de.zippus.comaco.xml.pojo.CMPaymentStarted;
import de.zippus.comaco.xml.pojo.CMPaymentUpdate;
import junit.framework.TestCase;

public class TestCancel extends TestCase {

	public void testCancel() {

		IPaymentListener listener = new IPaymentListener() {

			public void onPaymentStarted(CMPaymentStarted event) {
				System.out.println("Die Zahlungsaufforderung wurde angenommen. ID: " + event.getId());
			}

			public void onPaymentUpdated(CMPaymentUpdate event) {
				System.out.println("Es gibt ein Update zur Zahlung: " + event.getAccepted());
			}

			public void onPaymentClosed(CMPaymentClosed event) {
				System.out.println("Die Zahlung wurde abgeschlossen: " + event.getAccepted());
			}

			public void onPaymentFailed(CMCommandFailed event) {
				System.out.println("Die Zahlung wurde nicht durchgeführt. Es ist ein Fehler aufgetreten: "
						+ event.getDescription() + ", ErrorId: " + event.getErrorId());
			}

			public void onTest(String message) {
				System.out.println("Update an Kunden: " + message);

			}

			public void onPaymentCancelled(CMPaymentCancelled event) {
				System.out.println("Die Zahlungsaufforderung wurde storniert.");

			}

		};

		try {
			IChannel channel = new Channel(listener);
			/* CMPaymentCancelled paymentCancelled= */channel.cancelPayment(true);
			//System.out.println("Umgesetzter Betrag (nach Cancel): " + paymentCancelled.getAccepted());
			//System.out.println("Zahlungs-ID: " + paymentCancelled.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PaymentFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
