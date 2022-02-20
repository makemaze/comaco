package de.zippus.comaco;

import java.io.IOException;

import javax.swing.SwingUtilities;

import de.zippus.comaco.xml.pojo.CMPaymentCancelled;
import de.zippus.comaco.xml.pojo.CMPaymentClosed;
import de.zippus.comaco.xml.pojo.ICMResponse;

public class Comaco {

	public static void main(String[] args) {

//		SwingUtilities.invokeLater(new Runnable() {
//
//			public void run() {
//				start();
//			}
//		});
start();
	}
	
	public static void start() {
//		Thread thread = new Thread() {
//
//			@Override
//			public void run() {
//				new Comaco();
//			}
//			
//		};
//		
//		thread.start();
		new Comaco();
	}

	public Comaco() {
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
	}

}
