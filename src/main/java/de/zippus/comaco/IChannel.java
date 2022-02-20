package de.zippus.comaco;

import de.zippus.comaco.xml.pojo.CMPaymentCancelled;
import de.zippus.comaco.xml.pojo.CMPaymentClosed;
import de.zippus.comaco.xml.pojo.ICMResponse;

public interface IChannel {

	/** initiiert den Zahlungsvorgang am Kassenautomaten<br>
	 * positives Vorzeichen : der Automat fordert zur Einzahlung des Betrag amount auf<br>
	 * negatives Vorzeichen: der Automat zahlt den Betrag amouunt aus<br>
	 * id um die Zahlung  E2E eindeutig zuordnen zu können*/
	public ICMResponse doPayment(int amount, String id) throws PaymentFailedException, Exception;
	
	public ICMResponse doPayment(int amount, String id, boolean showDialog) throws PaymentFailedException, Exception;
	
	public void cancelPayment(boolean reimburse) throws PaymentFailedException;
	
}
