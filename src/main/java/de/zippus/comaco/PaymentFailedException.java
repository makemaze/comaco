package de.zippus.comaco;

import de.zippus.comaco.xml.pojo.CMCommandFailed;

public class PaymentFailedException  extends Exception {

	private static final long serialVersionUID = 1L;
	
	private CMCommandFailed commandFailed;
	
	public PaymentFailedException(CMCommandFailed commandFailed) {
		this.commandFailed = commandFailed;
	}
	
	

	public CMCommandFailed getCommandFailed() {
		return commandFailed;
	}



	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "\nEine Anweisung an den Kassenautomaten ist fehlgeschlagen mit : CMCommandFailed.\nDies kann mehrere Ursachen haben.\nPrüfen Sie den Bargeldbestand im Automaten."; //this.toString() + ", " +commandFailed.getDescription();
	}
	
	
	
	

}
