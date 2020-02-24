package me.willhamon.webproxy.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.willhamon.webproxy.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ProxyHandler implements HttpHandler
{
	@Override
	public void handle(HttpExchange he) throws IOException
	{
		//decode url
		String url = Utils.decodeURI(he.getRequestURI().toString().substring(7));

		// open input stream
		InputStream in = new URL(url).openStream();

		// copy bytes from input stream into byte array
		byte[] bytes = new byte[in.available()];
		in.read(bytes, 0, in.available() > 2095 ? 2095 : in.available());

		// write bytes to response body
		he.sendResponseHeaders(200, bytes.length);
		he.getResponseBody().write(bytes);
		he.close();
	}
}