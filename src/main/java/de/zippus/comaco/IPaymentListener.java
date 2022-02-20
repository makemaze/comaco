package de.zippus.comaco;

import de.zippus.comaco.xml.pojo.CMCommandFailed;
import de.zippus.comaco.xml.pojo.CMPaymentCancelled;
import de.zippus.comaco.xml.pojo.CMPaymentClosed;
import de.zippus.comaco.xml.pojo.CMPaymentStarted;
import de.zippus.comaco.xml.pojo.CMPaymentUpdate;

public interface IPaymentListener {
	
	// nur für Testzwecke
	public void onTest(String message);

	public void onPaymentStarted(CMPaymentStarted event);
	
	public void onPaymentUpdated(CMPaymentUpdate event);
	
	public void onPaymentClosed(CMPaymentClosed event);
	
	public void onPaymentFailed(CMCommandFailed event);
	
	public void onPaymentCancelled(CMPaymentCancelled event);

}
