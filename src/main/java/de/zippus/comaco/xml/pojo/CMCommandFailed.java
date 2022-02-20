package de.zippus.comaco.xml.pojo;

import de.zippus.comaco.IPaymentListener;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.ToString;

@ToString
public class CMCommandFailed implements ICMResponse {

	private String id;

	private String errorId;

	private String description;

	private int toAccept;

	private int accepted;

	private int toDispense;

	private int dispensed;

	@XmlAttribute(name = "Id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute(name = "ErrorId")
	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	@XmlAttribute(name = "Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlAttribute(name = "ToAccept")
	public int getToAccept() {
		return toAccept;
	}

	public void setToAccept(int toAccept) {
		this.toAccept = toAccept;
	}

	@XmlAttribute(name = "Accepted")
	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	@XmlAttribute(name = "ToDispense")
	public int getToDispense() {
		return toDispense;
	}

	public void setToDispense(int toDispense) {
		this.toDispense = toDispense;
	}

	@XmlAttribute(name = "Dispensed")
	public int getDispensed() {
		return dispensed;
	}

	public void setDispensed(int dispensed) {
		this.dispensed = dispensed;
	}
	
	public void fireEvent(IPaymentListener listener) {
		listener.onPaymentFailed(this);
	}

}
