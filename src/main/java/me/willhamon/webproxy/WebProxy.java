package me.willhamon.webproxy;

import com.sun.net.httpserver.HttpServer;
import me.willhamon.webproxy.handlers.IndexHandler;
import me.willhamon.webproxy.handlers.ProxyHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebProxy
{
	//TODO: FIX CUTTING OFF OF REQUEST BODIES AKA WAIT FOR CONNECTION

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
