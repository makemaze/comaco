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
import de.zippus.comaco.xml.pojo.ICMResponse;
import junit.framework.TestCase;

public class Test extends TestCase {

	public void testXMLPos() {

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

			final IChannel channel = new Channel(/* listener */);

//			Thread t1 = new Thread() {
//				public void run() {
			ICMResponse response = null;
			try {
				response = channel.doPayment(300, "123", true);
			} catch (PaymentFailedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Zahlungs-ID: " + response.getId());

			if (response instanceof CMPaymentClosed) {
				CMPaymentClosed paymentClosed = (CMPaymentClosed) response;
				System.out.println("Umgesetzter Betrag: " + paymentClosed.getAccepted());
			} else if (response instanceof CMPaymentCancelled) {
				CMPaymentCancelled paymentCancelled = (CMPaymentCancelled) response;
				System.out.println("Umgesetzter Betrag: " + paymentCancelled.getAccepted());
				System.out.println("Zurückgezahlt: " + paymentCancelled.getDispensed());
			}
//				}
//			};
//			t1.start();
//			Thread.sleep(10000);
//			channel.cancelPayment(true);

			// Thread.sleep(10000);
//			while (true) {
//			}

		} catch (IOException e) {
			e.printStackTrace();
		}
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (PaymentFailedException e) {
//			e.printStackTrace();
//		}

	}

}