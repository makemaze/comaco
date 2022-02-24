package de.zippus.comaco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.zippus.comaco.business.BoPayment;
import de.zippus.comaco.xml.pojo.ICMResponse;

public class Channel implements IChannel, IProgressListener {

	// Test
	
	private IPaymentListener paymentListener;
	private String ip;
	private int port;

	private Client client;

	private BoPayment boPayment;
	
	private List<IPaymentListener> paymentListeners = new ArrayList<IPaymentListener>();

	public Channel(IPaymentListener listener) throws IOException {
	
		this.paymentListener = listener;
		ip = "127.0.0.1"; // localhost
		port = 40003;


		client = new Client(ip, port, this);

		boPayment = new BoPayment(client);

	}

	public Channel() throws IOException {
		this(null);
	}

	public Channel(String host, int port) {
		client = new Client(host, port, this);
		boPayment = new BoPayment(client);
	}

	public ICMResponse doPayment(int amount, String id) throws PaymentFailedException, Exception {
		return doPayment(amount, id, false);
	}

	public ICMResponse doPayment(int amount, String id, boolean showDialog) throws PaymentFailedException, Exception {

		return boPayment.doPayment(amount, id, showDialog);


	}

	public void cancelPayment(boolean reimburse) throws PaymentFailedException {

		boPayment.cancelPayment(reimburse);



	}
	
	public void addPaymentListener(IPaymentListener listener) {
		this.paymentListeners.add(listener);
	}

	public void updateMessageReceived(ICMResponse response) {

		
		for (IPaymentListener listener : paymentListeners) {
			response.fireEvent(listener);
		}
	}
}
