package me.willhamon.webproxy;

import com.sun.net.httpserver.HttpServer;
import me.willhamon.webproxy.handlers.IndexHandler;
import me.willhamon.webproxy.handlers.ProxyHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

//
//	Web-Proxy
//	Created by Will Hamon
//

public class WebProxy
{
	//TODO: ADD HEADERS TO PROXY RESOPONSE,
	// FIX JS COMMENTS BREAKING SCRIPT TAGS IN JSOUP,
	// ADD RESPONSE CODE HANDLING IN CONNECTION PROXY:CONNECT()

	private static final int PORT = 80;

	public WebProxy()
	{
		try
		{
			HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
			server.createContext("/", new IndexHandler());
			server.createContext("/proxy", new ProxyHandler());
			server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
			server.start();
		}
		catch(IOException e) { e.printStackTrace(); }
	}
}
