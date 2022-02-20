package de.zippus.comaco.xml.pojo;

import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.ToString;

@ToString
public class CMPaymentCancel implements ICMCommand{

	private boolean reimburse;

	public CMPaymentCancel(boolean reimburse) {
		this.reimburse = reimburse;
	}
	
	@XmlAttribute(name="Reimburse")
	public boolean isReimburse() {
		return reimburse;
	}

	public void setReimburse(boolean reimburse) {
		this.reimburse = reimburse;
	}
	
	
	
}
