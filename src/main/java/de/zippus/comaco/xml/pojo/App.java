package de.zippus.comaco.xml.pojo;

import de.zippus.comaco.IPaymentListener;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.ToString;


@ToString
@XmlRootElement(name="App")
public class App {
	
	private CMSendPayment cmSendPayment;
	
	private CMPaymentStarted cmPaymentStarted;
	
	private CMPaymentUpdate cmPaymentUpdate;
	
	private CMPaymentClosed cmPaymentClosed;
	
	private CMCommandFailed cmCommandFailed;
	
	private CMPaymentCancel cmPaymentCancel;
	
	private CMPaymentCancelled cmPaymentCancelled;
	
	@XmlTransient
	private ICMResponse cmResponse;
	
	@XmlTransient
	private ICMCommand cmCommand;
	
		
	public App() {}
	
	private App(CMSendPayment cmSendPayment) {
		//this.cmSendPayment = cmSendPayment;
		setCmSendPayment(cmSendPayment);
	}
	
	private App(CMPaymentCancel cmPaymentCancel) {
		setCmPaymentCancel(cmPaymentCancel);
	}
	
	public static App createCMSendPayment(int amount, String id) {
		return new App(new CMSendPayment(amount, id));
	}
	
	public static App createCMPaymentCancel(boolean reimburse) {
		return new App(new CMPaymentCancel(reimburse));
	}
	
	public CMSendPayment getCmSendPayment() {
		return cmSendPayment;
	}
	
	@XmlElement(name="CMSendPayment")
	public void setCmSendPayment(CMSendPayment cm) {
		this.cmSendPayment = cm;
		this.cmCommand = cm;
	}
	
	

	public CMPaymentStarted getCmPaymentStarted() {
		return cmPaymentStarted;
	}

	@XmlElement(name="CMPaymentStarted")
	public void setCmPaymentStarted(CMPaymentStarted cmPaymentStarted) {
		this.cmPaymentStarted = cmPaymentStarted;
		this.cmResponse = cmPaymentStarted;
	}


	public CMPaymentUpdate getCmPaymentUpdate() {
		return cmPaymentUpdate;
	}

	@XmlElement(name="CMPaymentUpdate")
	public void setCmPaymentUpdate(CMPaymentUpdate cmPaymentUpdate) {
		this.cmPaymentUpdate = cmPaymentUpdate;
		this.cmResponse = cmPaymentUpdate;
	}
	
	public CMPaymentClosed getCmPaymentClosed() {
		return cmPaymentClosed;
	}

	@XmlElement(name="CMPaymentClosed")
	public void setCmPaymentClosed(CMPaymentClosed cmPaymentClosed) {
		this.cmPaymentClosed = cmPaymentClosed;
		this.cmResponse = cmPaymentClosed;
	}
	
	

	public CMPaymentCancel getCmPaymentCancel() {
		return cmPaymentCancel;
	}

	@XmlElement(name="CMPaymentCancel")
	public void setCmPaymentCancel(CMPaymentCancel cmPaymentCancel) {
		this.cmPaymentCancel = cmPaymentCancel;
		this.cmCommand = cmPaymentCancel;
	}
	
	public CMPaymentCancelled getCmPaymentCancelled() {
		return cmPaymentCancelled;
	}

	@XmlElement(name = "CMPaymentCancelled")
	public void setCmPaymentCancelled(CMPaymentCancelled cmPaymentCancelled) {
		this.cmPaymentCancelled = cmPaymentCancelled;
		this.cmResponse = cmPaymentCancelled;
	}

	public CMCommandFailed getCmCommandFailed() {
		return cmCommandFailed;
	}

	@XmlElement(name="CMCommandFailed")
	public void setCmCommandFailed(CMCommandFailed cmCommandFailed) {
		this.cmCommandFailed = cmCommandFailed;
		this.cmResponse = cmCommandFailed;
	}

	public ICMResponse getCmResponse() {
		return cmResponse;
	}
	
	public ICMCommand getCmCommand() {
		return cmCommand;
	}
	
	
	

}
