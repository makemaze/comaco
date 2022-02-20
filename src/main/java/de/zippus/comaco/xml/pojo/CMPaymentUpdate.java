package de.zippus.comaco.xml.pojo;

import de.zippus.comaco.IPaymentListener;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.ToString;

@ToString
public class CMPaymentUpdate implements ICMResponse{
	
	private String id;
	
	private int toAccept;
	
	private int accepted;
	
	private int toDispense;
	
	private int dispensed;

	@XmlAttribute(name="Id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute(name="ToAccept")
	public int getToAccept() {
		return toAccept;
	}

	public void setToAccept(int toAccept) {
		this.toAccept = toAccept;
	}

	@XmlAttribute(name="Accepted")
	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	@XmlAttribute(name="ToDispense")
	public int getToDispense() {
		return toDispense;
	}

	public void setToDispense(int toDispense) {
		this.toDispense = toDispense;
	}

	@XmlAttribute(name="Dispensed")
	public int getDispensed() {
		return dispensed;
	}

	public void setDispensed(int dispensed) {
		this.dispensed = dispensed;
	}
	
	public void fireEvent(IPaymentListener listener) {
		listener.onPaymentUpdated(this);
	}

}
