package me.willhamon.webproxy.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.willhamon.webproxy.Main;

import java.io.IOException;
import java.io.InputStream;

public class IndexHandler implements HttpHandler
{
	private static final InputStream index = Main.class.getClassLoader().getResourceAsStream("index.html");
	private static byte[] bytes;

	static
	{
		try
		{
			// read bytes from index.html
			bytes = new byte[index.available()];
			index.read(bytes);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void handle(HttpExchange he) throws IOException
	{
		// write bytes to response body
		he.sendResponseHeaders(200, bytes.length);
		he.getResponseBody().write(bytes);
		he.close();
	}
}