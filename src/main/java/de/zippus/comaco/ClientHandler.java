package de.zippus.comaco;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import de.zippus.comaco.xml.pojo.App;
import de.zippus.comaco.xml.pojo.CMCommandFailed;
import de.zippus.comaco.xml.pojo.CMPaymentCancel;
import de.zippus.comaco.xml.pojo.CMPaymentCancelled;
import de.zippus.comaco.xml.pojo.CMPaymentClosed;
import de.zippus.comaco.xml.pojo.CMSendPayment;
import de.zippus.comaco.xml.pojo.ICMCommand;
import de.zippus.comaco.xml.pojo.ICMResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

//Enthält die call-Methode für die FutureTask (entspricht run eines Threads)

class ClientHandler implements Callable<ICMResponse> {

	private static String MSG_END_IDENTIFIER = "</App>";

	private String ip;
	private int port;
	private String command;
	private IProgressListener progressListener;
	private Class<ICMCommand> commandClass;
	private Socket socket;
	private PrintWriter printWriter;

	private static Map<Class<? extends ICMCommand>, List<Class<? extends ICMResponse>>> CLOSE_MAP = new HashMap<Class<? extends ICMCommand>, List<Class<? extends ICMResponse>>>();

	{
		// TODO Type safety?
		CLOSE_MAP.put(CMSendPayment.class, new ArrayList<Class<? extends ICMResponse>>(
				Arrays.asList(CMCommandFailed.class, CMPaymentClosed.class, CMPaymentCancelled.class)));
	}

	public ClientHandler(String ip, int port, App app /* String command */, IProgressListener listener) {
		this.ip = ip;
		this.port = port;
		this.command = createXMLCommandFrom(app);
		this.progressListener = listener;
		ICMCommand cmCommand = app.getCmCommand();
		commandClass = (Class<ICMCommand>) (cmCommand != null ? cmCommand.getClass() : null);

	}

	public ICMResponse call() throws Exception { // run the service
		System.out.println("ClientHandler:" + Thread.currentThread());
		return requestServer();
	}

	public void setApp(App app) {
		this.command = createXMLCommandFrom(app);
	}

	// Socket öffnen, Anforderung senden, Ergebnis empfangen, Socket schliessen
	private ICMResponse requestServer() throws IOException {
		ICMResponse finalReceivedMessage = null;

		boolean firstRequest = true;
		if (socket == null) {
			/* Socket */ socket = new Socket(ip, port); // verbindet sich mit Server
		} else {
			firstRequest = false;
		}

		if (command != null) {
			submitCommand(socket, command);
		}
		// Ergebnis empfangen, readMessage blockt solange bis eine finale Nachricht
		// empfangen wurde.
		if (firstRequest) {
			finalReceivedMessage = readMessage(socket);
			socket.close();
		}
		return finalReceivedMessage;
	}

	private void submitCommand(Socket socket, String command) throws IOException {
		if (printWriter == null) {
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		}
		printWriter.print(command);
		printWriter.flush();
	}

	private ICMResponse readMessage(Socket socket) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		int character;
		StringBuilder message = new StringBuilder();
		ICMResponse response = null;

		List<Class<? extends ICMResponse>> closeCondition = CLOSE_MAP.get(this.commandClass);
		boolean run = true;
		while (run && (character = reader.read()) != -1) {

			message.append((char) character);

			if (message.toString().endsWith(MSG_END_IDENTIFIER)) {
				System.out.println("Nachricht empfangen: " + message.toString());

				// Eine Nachricht vom Server ist vollständig empfangen worden

				response = createResponseObjectFromXML(message.toString());

				// informiere den Listener
				if (Objects.nonNull(progressListener)) {

					this.progressListener.updateMessageReceived(response);
				}

				if (closeCondition.contains(response.getClass())) {
					break;
				}

				message = new StringBuilder();

			}

		}

		return response;
	}

	private String createXMLCommandFrom(App app) {
		// Convert the Domain Model to XML
		JAXBContext jaxbContext;
		StringWriter xmlWriter = new StringWriter();
		try {
			jaxbContext = JAXBContext.newInstance(App.class);

			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

			marshaller.marshal(app, xmlWriter);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		String xml = xmlWriter.toString();
		System.out.println(xml);
		return xml;

	}

	private ICMResponse createResponseObjectFromXML(String xml) {
		JAXBContext jaxbContext;
		App appResponse = null;
		try {
			jaxbContext = JAXBContext.newInstance(App.class);

			// Convert XML back to Domain Model
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader xmlReader = new StringReader(xml);

			appResponse = (App) unmarshaller.unmarshal(xmlReader);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return appResponse.getCmResponse();

	}

}