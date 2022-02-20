package de.zippus.comaco;

import java.util.concurrent.FutureTask;

import de.zippus.comaco.xml.pojo.App;
import de.zippus.comaco.xml.pojo.ICMResponse;

public class Client {
	/*
	 * Im 'worker' des Hauptprogramms wird wie folgt verfahren: o Bilde Instanz von
	 * 'FutureTask', gib ihr als Parameter eine Instanz von 'ClientHandler' mit, die
	 * das Interface 'Callable' (ähnlich 'Runnable') implementiert. o Übergib die
	 * 'FutureTask' an einen neuen Thread und starte diesen. Im Thread wird nun die
	 * 'call'-Methode aus dem Interface 'Callable' des ClientHandlers abgearbeitet.
	 * o Dabei wird die komplette Kommunikation mit dem Server durchgeführt. Die
	 * 'call'-Methode gibt nun das Ergebnis vom Server an die 'FutureTask' zurück,
	 * wo es im Hauptprogramm zur Verfügung steht. Hier kann beliebig oft und an
	 * beliebigen Stellen abgefragt werden, ob das Ergebnis bereits vorliegt.
	 */
	private String ip;
	private int port;
	private IProgressListener progressListener;

	private ClientHandler ch;

	public Client(String ip, int port, IProgressListener listener) {
		this.ip = ip;
		this.port = port;
		this.progressListener = listener;
	}

	public ICMResponse perform(App app /* String command */) throws Exception {
		// Klasse die 'Callable' implementiert

		if (ch == null) {
			ch = new ClientHandler(ip, port, app, this.progressListener);
		} else {
			ch.setApp(app);
		}

		// call-Methode 'ch' von ClientHandler wird mit 'FutureTask' asynchron
		// abgearbeitet, das Ergebnis kann dann von der 'FutureTask' abgeholt
		// werden.

		FutureTask<ICMResponse> ft = new FutureTask<ICMResponse>(ch);
		Thread tft = new Thread(ft);
		tft.start();

		// prüfe ob der Thread seine Arbeit getan hat
		while (!ft.isDone()) {
			Thread.yield(); // andere Threads (AndererThread) können drankommen
		}

		System.out.println(ft.get()); // Ergebnis ausgeben

		return ft.get();

	}
	
	public IProgressListener getProgressListener() {
		return this.progressListener;
	}
}