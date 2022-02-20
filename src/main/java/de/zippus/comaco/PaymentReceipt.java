package de.zippus.comaco;

public class PaymentReceipt implements IReceipt {

	private int amount=0;
	private String id="";
	
	public PaymentReceipt(int amount, String id) {
		this.amount = amount;
		this.id = id;
	}
	
	public int getConvertedAmount() {
		return amount;
	}

	public String getId() {
		return id;
	}

}
