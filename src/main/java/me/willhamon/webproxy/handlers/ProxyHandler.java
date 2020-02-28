package me.willhamon.webproxy.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.willhamon.webproxy.ConnectionProxy;
import me.willhamon.webproxy.Utils;

import java.io.IOException;

public class ProxyHandler implements HttpHandler
{
	@Override
	public void handle(HttpExchange he) throws IOException
	{
		// decode url
		String url = Utils.decodeURL(he.getRequestURI().toString().substring(7));

		// proxy connection
		ConnectionProxy proxy = new ConnectionProxy(url);
		proxy.connect();

		// set headers
		proxy.setHeaders(he.getResponseHeaders());

		// write bytes to response body
		he.sendResponseHeaders(200, proxy.getBodyLength());
		he.getResponseBody().write(proxy.getBody());
		he.close();
	}
}