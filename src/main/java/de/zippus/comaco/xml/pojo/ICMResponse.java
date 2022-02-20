package de.zippus.comaco.xml.pojo;

import de.zippus.comaco.IPaymentListener;

public interface ICMResponse {

	//public void setListener(IPaymentListener listener);

	public String getId();
	
	public void fireEvent(IPaymentListener listener);
	
}
