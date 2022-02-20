package de.zippus.comaco.xml.pojo;

import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.ToString;

@ToString
public class CMSendPayment implements ICMCommand {

	private int		amount;
	private String	sign;
	private String	id;
	
	public CMSendPayment(int amount, String id) {
		this.amount = Math.abs(amount);
		this.sign = amount > 0 ? "+": "-";
		this.id = id;
	}

	@XmlAttribute(name="Amount")
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@XmlAttribute(name="Sign")
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@XmlAttribute(name="Id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
