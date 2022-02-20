package de.zippus.comaco;

import de.zippus.comaco.xml.pojo.ICMResponse;

public interface IProgressListener {
	
	public void updateMessageReceived(ICMResponse cmResponse);
	
	public void addPaymentListener(IPaymentListener listener);

}
