package de.zippus.comaco;

public interface IReceipt {
	
	/** liefert den vom Äutomaten umgesetzten Betrag zurück<br>
	 * positiv : der Automat hat amount  kassiert<br>
	 * neagativ: der Automat hat amount ausbezahlt*/
	public int getConvertedAmount();
	
	/** Liefert die ID zurück mit der die Zahlung  initiiert wurde*/
	public String getId();

}
