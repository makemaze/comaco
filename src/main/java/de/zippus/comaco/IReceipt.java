package de.zippus.comaco;

public interface IReceipt {
	
	/** liefert den vom �utomaten umgesetzten Betrag zur�ck<br>
	 * positiv : der Automat hat amount  kassiert<br>
	 * neagativ: der Automat hat amount ausbezahlt*/
	public int getConvertedAmount();
	
	/** Liefert die ID zur�ck mit der die Zahlung  initiiert wurde*/
	public String getId();

}
