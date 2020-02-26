package me.willhamon.webproxy.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.willhamon.webproxy.Transformers;
import me.willhamon.webproxy.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ProxyHandler implements HttpHandler
{
	@Override
	public void handle(HttpExchange he) throws IOException
	{
		// decode url
		String url = Utils.decodeURL(he.getRequestURI().toString().substring(7));

		// open input stream
		InputStream in = new URL(url).openStream();

		// copy bytes from input stream into byte array
		byte[] bytes = new byte[in.available()];
		in.read(bytes, 0, in.available());

		// close input stream
		in.close();

		// if page is html, open in jsoup
		String html = new String(bytes);
		if(html.contains("<html>"))
		{
			// parse html
			Document document = Jsoup.parse(html);

			//set base uri for transformations
			document.setBaseUri(url);

			// transform document
			Transformers.html(document);

			// copy bytes to array
			bytes = document.html().getBytes();
		}

		// write bytes to response body
		he.sendResponseHeaders(200, bytes.length);
		he.getResponseBody().write(bytes);
		he.close();

		bytes = null;
	}
}