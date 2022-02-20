package de.zippus.comaco.xml.pojo;

import de.zippus.comaco.IPaymentListener;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.ToString;

@ToString
public class CMPaymentStarted implements ICMResponse {
	
	private String id;
	
	@XmlAttribute(name="Id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void fireEvent(IPaymentListener listener) {
		listener.onPaymentStarted(this);
	}

	

}
